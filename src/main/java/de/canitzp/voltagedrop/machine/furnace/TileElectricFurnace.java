package de.canitzp.voltagedrop.machine.furnace;

import de.canitzp.ctpcore.recipe.RecipeRegistry;
import de.canitzp.ctpcore.util.NBTSaveType;
import de.canitzp.ctpcore.util.NBTUtil;
import de.canitzp.voltagedrop.Values;
import de.canitzp.voltagedrop.api.recipe.Recipes;
import de.canitzp.voltagedrop.capabilities.SidedEnergyDevice;
import de.canitzp.voltagedrop.capabilities.UserEnergyDevice;
import de.canitzp.voltagedrop.capabilities.Voltages;
import de.canitzp.voltagedrop.machine.solidgenerator.BlockSolidGenerator;
import de.canitzp.voltagedrop.tile.TileEntityDevice;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nullable;
import java.util.List;


/**
 * @author canitzp
 */
public class TileElectricFurnace extends TileEntityDevice<UserEnergyDevice>{

    public InvWrapper inventory = new InvWrapper(new InventoryBasic("electric_furnace", false, 2));

    private int timeLeft, max;
    private ItemStack outputStack = ItemStack.EMPTY;

    @Override
    protected SidedEnergyDevice<UserEnergyDevice> getSidedEnergyDevice(World world, BlockPos pos){
        return SidedEnergyDevice.createSingleEmpty(UserEnergyDevice.class, Values.ELECTRIC_FURNACE_VOLTAGE, Values.ELECTRIC_FURNACE_CAPACITY);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing){
        if(capability.equals(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)){
            return (T) this.inventory;
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound, NBTSaveType type){
        super.writeToNBT(compound, type);
        compound.setInteger("TimeLeft", this.timeLeft);
        compound.setInteger("TimeMax", this.max);
        NBTUtil.setItemStack(compound, "OutStack", this.outputStack);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound, NBTSaveType type){
        super.readFromNBT(compound, type);
        this.timeLeft = compound.getInteger("TimeLeft");
        this.max = compound.getInteger("TimeMax");
        this.outputStack = NBTUtil.getItemStack(compound, "OutStack");
    }

    @Override
    public void update(){
        super.update();
        if(!world.isRemote){
            if(world.getTotalWorldTime() % 20 == 0 && timeLeft <= 0){
                if(outputStack.isEmpty()){
                    List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, getInside(0.55F));
                    for(EntityItem item : items){
                        ItemStack stack = item.getEntityItem();
                        if(RecipeRegistry.hasRecipeFor(Recipes.ELECTRIC_FURNACE.getCategory(), stack)){
                            if(checkForBurn(stack)){
                                this.spawnParticle(EnumParticleTypes.TOTEM, item.getPosition().getX(), item.getPosition().getY() + 0.5, item.getPosition().getZ(), 50, 0.25D, new int[0]);
                            }
                        }
                    }
                } else {
                    this.world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY() + 1D, pos.getZ(), this.outputStack.copy()));
                    this.outputStack = ItemStack.EMPTY;
                }
            }
            if(timeLeft > 0){
                if(this.getDeviceForSide(EnumFacing.NORTH).getStored() >= Values.ELECTRIC_FURNACE_CONSUMPTION){
                    timeLeft--;
                    this.getDeviceForSide(EnumFacing.NORTH).useEnergy(Voltages.MAINS, Values.ELECTRIC_FURNACE_CONSUMPTION);
                }
            }
        } else {
            world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockSolidGenerator.ACTIVE, timeLeft> 0 && (this.getDeviceForSide(EnumFacing.NORTH).getStored() >= Values.ELECTRIC_FURNACE_CONSUMPTION)));
        }
    }

    private boolean checkForBurn(ItemStack stack){
        Recipes.ElectricFurnace recipe = (Recipes.ElectricFurnace) RecipeRegistry.getRecipeFor(Recipes.ELECTRIC_FURNACE.getCategory(), stack);
        if(recipe != null){
            this.outputStack = recipe.getOutput().copy();
            stack.shrink(1);
            this.timeLeft = recipe.getBurnTime();
            return true;
        }
        return false;
    }

    public int getTimeLeft(){
        return this.timeLeft;
    }

    public int getMaxTime(){
        return this.max;
    }

}
