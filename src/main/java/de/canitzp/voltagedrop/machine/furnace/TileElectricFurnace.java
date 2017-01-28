package de.canitzp.voltagedrop.machine.furnace;

import de.canitzp.ctpcore.util.NBTSaveType;
import de.canitzp.ctpcore.util.NBTUtil;
import de.canitzp.ctpcore.util.StackUtil;
import de.canitzp.voltagedrop.Values;
import de.canitzp.voltagedrop.api.recipe.RecipeElectricFurnace;
import de.canitzp.voltagedrop.capabilities.SidedEnergyDevice;
import de.canitzp.voltagedrop.capabilities.UserEnergyDevice;
import de.canitzp.voltagedrop.tile.TileEntityDevice;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nullable;

/**
 * @author canitzp
 */
public class TileElectricFurnace extends TileEntityDevice<UserEnergyDevice>{

    public InvWrapper inventory = new InvWrapper(new InventoryBasic("electric_furnace", false, 2));

    private int timeLeft, max;
    private ItemStack burnStack = ItemStack.EMPTY;

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
        NBTUtil.setItemStack(compound, "BurnStack", this.burnStack);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound, NBTSaveType type){
        super.readFromNBT(compound, type);
        this.timeLeft = compound.getInteger("TimeLeft");
        this.max = compound.getInteger("TimeMax");
        this.burnStack = NBTUtil.getItemStack(compound, "BurnStack");
    }

    @Override
    public void update(){
        super.update();
        if(!world.isRemote){
            ItemStack input = getInputSlot();
            if(timeLeft <= 0){
                if(this.burnStack.isEmpty()){
                    if(!input.isEmpty() && checkOutputSlot(input)){
                        RecipeElectricFurnace.RecipePattern recipe = RecipeElectricFurnace.getRecipe(input);
                        if(recipe != null){
                            getInputSlot().shrink(recipe.in.getCount());
                            this.timeLeft = this.max = recipe.burnTime;
                            this.burnStack = recipe.in;
                        }
                    }
                } else {
                    RecipeElectricFurnace.RecipePattern recipe = RecipeElectricFurnace.getRecipe(this.burnStack);
                    if(getOutputSlot().isEmpty()){
                        setOutputSlot(recipe.out.copy());
                    } else {
                        getOutputSlot().grow(recipe.out.getCount());
                    }
                    this.burnStack = ItemStack.EMPTY;
                    this.max = 0;
                }
            } else if(timeLeft > 0){
                timeLeft--;
                this.getDeviceForSide(EnumFacing.NORTH).useEnergy(Values.ELECTRIC_FURNACE_VOLTAGE, Values.ELECTRIC_FURNACE_CONSUMPTION);
            }
        }
    }

    public ItemStack getInputSlot(){
        return this.inventory.getStackInSlot(0);
    }

    public ItemStack getOutputSlot(){
        return this.inventory.getStackInSlot(1);
    }

    public void setOutputSlot(ItemStack stack){
        this.inventory.setStackInSlot(1, stack);
    }

    public boolean checkOutputSlot(ItemStack stack){
        RecipeElectricFurnace.RecipePattern recipe = RecipeElectricFurnace.getRecipe(stack);
        return getOutputSlot().isEmpty() || (recipe != null && StackUtil.canMerge(recipe.out, getOutputSlot()));
    }

    public int getTimeLeft(){
        return this.timeLeft;
    }

    public int getMaxTime(){
        return this.max;
    }

}
