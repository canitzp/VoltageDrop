package de.canitzp.voltagedrop.tile;

import de.canitzp.ctpcore.base.TileEntityBase;
import de.canitzp.ctpcore.sync.ISyncable;
import de.canitzp.ctpcore.util.NBTSaveType;
import de.canitzp.voltagedrop.capabilities.*;
import de.canitzp.voltagedrop.machine.transformer.BlockTransformer;
import de.canitzp.voltagedrop.machine.upgrade.IUpgrade;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author canitzp
 */
public abstract class TileEntityDevice<K extends IEnergyDevice> extends TileEntityBase implements ITickable, ISyncable{

    protected List<IUpgrade> installedUpgrades = new ArrayList<>();
    public SidedEnergyDevice<K> sidedEnergyDevice = new SidedEnergyDevice<K>().setUnstable();
    protected int ticks;

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing){
        return this.getCapability(capability, facing) != null;
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing){
        if(capability.equals(Capabilities.ENERGY)){
            IEnergyDevice device = this.getDeviceForSide(facing);
            if(device != null){
                return (T) device;
            }
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound, NBTSaveType type){
        if(this.sidedEnergyDevice != null){
            if(type == NBTSaveType.SAVE || type == NBTSaveType.DROP_BLOCK){
                compound = this.sidedEnergyDevice.write(compound);
            } else {
                compound = this.sidedEnergyDevice.sync(compound);
            }
        }
        if(!installedUpgrades.isEmpty()){
            NBTTagCompound upgrades = new NBTTagCompound();
            for(IUpgrade upgrade : installedUpgrades){
                NBTTagCompound nbt = (NBTTagCompound) upgrade.serializeNBT();
                upgrades.setTag(upgrade.getName(), nbt);
                upgrades.setString(upgrade.getName() + "Class", upgrade.getClass().getName());
                upgrades.setString(upgrade.getName() + "Item", upgrade.getRenderStack(this).getItem().getRegistryName().toString());
            }
            compound.setTag("InstalledUpgrades", upgrades);
        }
        super.writeToNBT(compound, type);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound, NBTSaveType type){
        if(this.sidedEnergyDevice != null){
            if(type == NBTSaveType.SAVE || type == NBTSaveType.DROP_BLOCK){
                this.sidedEnergyDevice.read(compound, type == NBTSaveType.SAVE);
            } else {
                this.sidedEnergyDevice.getSync(compound);
            }
        }
        if(compound.hasKey("InstalledUpgrades")){
            if(type == NBTSaveType.SYNC){
                this.installedUpgrades.clear();
            }
            NBTTagCompound upgrades = compound.getCompoundTag("InstalledUpgrades");
            for(String key : upgrades.getKeySet()){
                if(key.endsWith("Class")){
                    try{
                        Class<IUpgrade> c = (Class<IUpgrade>) Class.forName(upgrades.getString(key));
                        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(upgrades.getString(key.substring(0, key.length()-5) + "Item")));
                        IUpgrade upgrade = c.getConstructor(ItemStack.class).newInstance(new ItemStack(item));
                        upgrade.deserializeNBT(upgrades.getCompoundTag(key.substring(0, key.length()-6)));
                        installedUpgrades.add(upgrade);
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
        super.readFromNBT(compound, type);
    }

    @Override
    public void update(){
        if(ticks == 0){
            setSidedEnergyIfNull();
        }
        ticks++;
    }

    public void onBlockPlaced(){
        setSidedEnergyIfNull();
    }

    protected void pushEnergy(){
        if(!world.isRemote){
            for(EnumFacing side : EnumFacing.values()){
                BlockPos pos = this.pos.offset(side);
                IBlockState state = world.getBlockState(pos);
                if(!(state.getBlock() == Blocks.AIR.getDefaultState()) && state.getBlock() instanceof ITileEntityProvider){
                    TileEntity tile = world.getTileEntity(pos);
                    if(tile != null && tile.hasCapability(Capabilities.ENERGY, side.getOpposite())){
                        IEnergyDevice device = tile.getCapability(Capabilities.ENERGY, side.getOpposite());
                        if(device != null){
                            IEnergyDevice thisDev = this.getDeviceForSide(side.getOpposite());
                            Voltages voltage = thisDev.getVoltage();
                            float current = Math.min(thisDev.getStored(), Math.min(device.getMaxFlow(), thisDev.getMaxFlow()));
                            if(!SidedEnergyDevice.canStoreEnergy(current, device)){
                                current = SidedEnergyDevice.getStoreable(device);
                            }
                            if(current > 0 && canDevicesTransfer(thisDev, device, voltage, current)){
                                device.receiveEnergy(voltage, thisDev.extractEnergy(voltage, current, false).energy, false);
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean canDevicesTransfer(IEnergyDevice from, IEnergyDevice to, Voltages voltage, float current){
        return from.canExtract() && to.canReceive() && from.extractEnergy(voltage, current, true).equals(ErrorTypes.OK) && to.receiveEnergy(voltage, current, true).equals(ErrorTypes.OK);
    }

    @Override
    public final boolean canSync(EntityPlayerMP player){
        return false;
    }

    @Override
    public final boolean canSync(){
        return false;
    }

    public EnumFacing getFacings(){
        IBlockState state = getWorld().getBlockState(getPos());
        if(state.getBlock() instanceof BlockTransformer){
            return state.getValue(((BlockTransformer) state.getBlock()).getFacing()).getVanillaFacing();
        }
        return EnumFacing.NORTH;
    }

    protected abstract SidedEnergyDevice<K> getSidedEnergyDevice(World world, BlockPos pos);

    protected void setSidedEnergyIfNull(){
        if(this.sidedEnergyDevice.isUnstable()){
            this.sidedEnergyDevice = getSidedEnergyDevice(this.world, this.pos).merge(this.sidedEnergyDevice);
        }
    }

    public K getDeviceForSide(EnumFacing side){
        return this.sidedEnergyDevice != null && !this.sidedEnergyDevice.isUnstable() ? this.sidedEnergyDevice.getDeviceForSide(side) : null;
    }

    @Override
    public NBTTagCompound getSyncableData(){
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt, NBTSaveType.SYNC);
        return nbt;
    }

    @Override
    public int getSyncTimeInTicks(){
        return 10;
    }

    @Override
    public void receiveData(NBTTagCompound data){
        this.readFromNBT(data, NBTSaveType.SYNC);
    }

    public boolean installUpgrade(IUpgrade upgrade){
        for(IUpgrade installed : installedUpgrades){
            if(installed.getName().equals(upgrade.getName())){
                return false;
            }
        }
        installedUpgrades.add(upgrade);
        return true;
    }

    public List<IUpgrade> getInstalledUpgrades(){
        return installedUpgrades;
    }
}
