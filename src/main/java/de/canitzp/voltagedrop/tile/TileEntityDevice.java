package de.canitzp.voltagedrop.tile;

import de.canitzp.ctpcore.base.TileEntityBase;
import de.canitzp.ctpcore.util.NBTSaveType;
import de.canitzp.voltagedrop.VoltageDrop;
import de.canitzp.voltagedrop.capabilities.Capabilities;
import de.canitzp.voltagedrop.capabilities.EnergyDevice;
import de.canitzp.voltagedrop.capabilities.IEnergyDevice;
import de.canitzp.voltagedrop.capabilities.SidedEnergyDevice;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author canitzp
 */
public abstract class TileEntityDevice<E extends IEnergyDevice> extends TileEntityBase implements ITickable{

    public SidedEnergyDevice<E> sidedEnergyDevice;
    protected Map<BlockPos, EnumFacing> energyPos = new HashMap<BlockPos, EnumFacing>();
    protected boolean firstTick;

    public TileEntityDevice(String name, SidedEnergyDevice<E> sidedEnergyDevice){
        super(new ResourceLocation(VoltageDrop.MODID, name));
        this.sidedEnergyDevice = sidedEnergyDevice;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing){
        return this.getCapability(capability, facing) != null;
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing){
        if(capability.equals(Capabilities.ENERGY)){
            IEnergyDevice device = this.sidedEnergyDevice.getDeviceForSide(facing);
            if(device != null){
                return (T) device;
            }
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound, NBTSaveType type){
        if(type == NBTSaveType.SAVE || type == NBTSaveType.DROP_BLOCK){
            compound = this.sidedEnergyDevice.write(compound);
        } else {
            compound = this.sidedEnergyDevice.sync(compound);
        }
        super.writeToNBT(compound, type);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound, NBTSaveType type){
        if(type == NBTSaveType.SAVE || type == NBTSaveType.DROP_BLOCK){
            this.sidedEnergyDevice.read(compound);
        } else {
            this.sidedEnergyDevice.getSync(compound);
        }
        super.readFromNBT(compound, type);
    }

    @Override
    public void update(){
        if(!firstTick){
            this.onBlockUpdate();
            this.syncToClient();
            firstTick = true;
        }
    }


    public void onBlockUpdate(){
        this.energyPos.clear();
        for(EnumFacing side : EnumFacing.values()){
            BlockPos pos = this.pos.offset(side);
            TileEntity tile = this.world.getTileEntity(pos);
            if(tile != null && tile.hasCapability(Capabilities.ENERGY, side.getOpposite())){
                this.energyPos.put(pos, side.getOpposite());
            }
        }
    }

    protected void pushEnergy(){
        if(!world.isRemote){
            for(Map.Entry<BlockPos, EnumFacing> entry : energyPos.entrySet()){
                IEnergyDevice device = world.getTileEntity(entry.getKey()).getCapability(Capabilities.ENERGY, entry.getValue());
                if(device != null){
                    IEnergyDevice thisDev = this.sidedEnergyDevice.getDeviceForSide(entry.getValue().getOpposite());
                    float voltage = thisDev.getOutputVoltage();
                    float current = Math.min(thisDev.getSavedCurrentPerHour(), device.getMaxFlowCurrent());
                    if(canDevicesTransfer(thisDev, device, voltage, current)){
                        device.receiveEnergy(voltage, thisDev.extractEnergy(voltage, current, false), false);
                    }
                }
            }
        }
    }

    public EnumFacing getSideFromBlocks(BlockPos pos1, BlockPos pos2){
        Vec3i vec =  pos2.subtract(pos1);
        if(vec.getY() == -1) return EnumFacing.UP;
        else if(vec.getY() == 1) return EnumFacing.DOWN;
        else if(vec.getX() == -1) return EnumFacing.WEST;
        else if(vec.getX() == 1) return EnumFacing.EAST;
        else if(vec.getZ() == -1) return EnumFacing.NORTH;
        else return EnumFacing.SOUTH;
    }

    public boolean canDevicesTransfer(IEnergyDevice from, IEnergyDevice to, float voltage, float current){
        return from.canExtract() && to.canReceive() && from.extractEnergy(voltage, current, true) > 0 && to.receiveEnergy(voltage, current, true) > 0;
    }

}
