package de.canitzp.voltagedrop.machine.arcoven;

import de.canitzp.ctpcore.util.NBTSaveType;
import de.canitzp.ctpcore.util.NBTUtil;
import de.canitzp.voltagedrop.api.recipe.RecipeArcOven;
import de.canitzp.voltagedrop.capabilities.SidedEnergyDevice;
import de.canitzp.voltagedrop.capabilities.UserEnergyDevice;
import de.canitzp.voltagedrop.capabilities.Voltages;
import de.canitzp.voltagedrop.tile.TileEntityDevice;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

/**
 * @author canitzp
 */
public class TileArcOven extends TileEntityDevice<UserEnergyDevice>{

    private int timeLeft;
    private ItemStack outputStack = ItemStack.EMPTY;

    @Override
    protected SidedEnergyDevice<UserEnergyDevice> getSidedEnergyDevice(World world, BlockPos pos){
        return SidedEnergyDevice.createSingleEmpty(UserEnergyDevice.class, Voltages.MAINS, 25);
    }

    @Override
    public void update(){
        super.update();
        if(!world.isRemote){
            if(world.getTotalWorldTime() % 20 == 0 && timeLeft <= 0){
                if(outputStack.isEmpty()){
                    List<EntityItem> items = this.world.getEntitiesWithinAABB(EntityItem.class, getInside(0.55F));
                    System.out.println(items);
                    for(EntityItem item : items){
                        ItemStack stack = item.getEntityItem();
                        if(RecipeArcOven.getRecipe(stack) != null){
                            if(checkForBurn(stack)){
                                this.spawnParticle(EnumParticleTypes.FLAME, item.getPosition().getX(), item.getPosition().getY() + 0.5, item.getPosition().getZ(), 50, 0.25D, new int[0]);
                            }
                        }
                    }
                } else {
                    this.world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY() + 0.5D, pos.getZ(), this.outputStack));
                    this.outputStack = ItemStack.EMPTY;
                }
            }
            if(timeLeft > 0){
                if(this.getDeviceForSide(EnumFacing.NORTH).getStored() >= 0.01F){
                    timeLeft--;
                    this.getDeviceForSide(EnumFacing.NORTH).extractEnergy(Voltages.THREE_PHASE, 0.01F, false);
                }
            }
        }
    }

    private boolean checkForBurn(ItemStack stack){
        if(!(world.getBlockState(pos.offset(EnumFacing.DOWN)).getBlock() == Blocks.LAVA)){
           return false;
        }
        RecipeArcOven.RecipePattern recipe = RecipeArcOven.getRecipe(stack);
        if(recipe != null){
            this.outputStack = recipe.out.copy();
            stack.shrink(1);
            this.timeLeft = recipe.burnTime;
            return true;
        }
        return false;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound, NBTSaveType type){
        compound.setInteger("TimeLeft", this.timeLeft);
        NBTUtil.setItemStack(compound, "OutStack", this.outputStack);
        super.writeToNBT(compound, type);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound, NBTSaveType type){
        this.timeLeft = compound.getInteger("TimeLeft");
        this.outputStack = NBTUtil.getItemStack(compound, "OutStack");
        super.readFromNBT(compound, type);
    }

}