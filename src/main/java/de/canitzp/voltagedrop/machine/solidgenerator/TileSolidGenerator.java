package de.canitzp.voltagedrop.machine.solidgenerator;

import de.canitzp.ctpcore.sync.ISyncable;
import de.canitzp.ctpcore.util.NBTSaveType;
import de.canitzp.voltagedrop.capabilities.GeneratorEnergyDevice;
import de.canitzp.voltagedrop.capabilities.SidedEnergyDevice;
import de.canitzp.voltagedrop.capabilities.Voltages;
import de.canitzp.voltagedrop.tile.TileEntityDevice;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

/**
 * @author canitzp
 */
public class TileSolidGenerator extends TileEntityDevice<GeneratorEnergyDevice> implements ISyncable{

    public static final float CURRENT_TO_GEN = 0.01F;

    private int timeLeft;

    @Override
    public void update(){
        super.update();
        if(!world.isRemote){
            if(world.getTotalWorldTime() % 20 == 0 && timeLeft <= 0){
                List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(this.pos.getX() - 3, this.pos.getY() - 3, this.pos.getZ() - 3, this.pos.getX() + 3, this.pos.getY() + 3, this.pos.getZ() + 3));
                for(EntityItem item : items){
                    ItemStack stack = item.getEntityItem();
                    if(TileEntityFurnace.isItemFuel(stack)){
                        if(checkForBurn(stack)){
                            this.spawnParticle(EnumParticleTypes.CRIT_MAGIC, item.getPosition().getX(), item.getPosition().getY() + 0.5, item.getPosition().getZ(), 50, 0.25D, new int[0]);
                        }
                    }
                }
            }
            if(timeLeft > 0){
                timeLeft--;
                GeneratorEnergyDevice device = this.getDeviceForSide(EnumFacing.NORTH);
                if(device != null){
                    device.generate(Voltages.MAINS, CURRENT_TO_GEN);
                }
            }
            super.pushEnergy();
        }
    }

    @Override
    protected boolean autoSync(){
        return false;
    }

    @Override
    protected SidedEnergyDevice<GeneratorEnergyDevice> getSidedEnergyDevice(World world, BlockPos pos){
        return SidedEnergyDevice.createSingleEmpty(GeneratorEnergyDevice.class, Voltages.MAINS, 100);
    }

    private boolean checkForBurn(ItemStack stack){
        if(timeLeft <= 0){
            int burnTime = TileEntityFurnace.getItemBurnTime(stack);
            if(this.sidedEnergyDevice.canStoreCurrent(burnTime * CURRENT_TO_GEN, EnumFacing.NORTH)){
                stack.shrink(1);
                this.timeLeft = burnTime;
                return true;
            }
        }
        return false;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound, NBTSaveType type){
        compound.setInteger("BurnTimeLeft", this.timeLeft);
        super.writeToNBT(compound, type);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound, NBTSaveType type){
        this.timeLeft = compound.getInteger("BurnTimeLeft");
        super.readFromNBT(compound, type);
    }

    @Override
    public NBTTagCompound getSyncableData(){
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt, NBTSaveType.SYNC);
        return nbt;
    }

    @Override
    public int getSyncTimeInTicks(){
        return 20;
    }

    @Override
    public void receiveData(NBTTagCompound data){
        this.readFromNBT(data, NBTSaveType.SYNC);
    }
}
