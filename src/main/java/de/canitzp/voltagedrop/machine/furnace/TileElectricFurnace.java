package de.canitzp.voltagedrop.machine.furnace;

import de.canitzp.ctpcore.registry.IRegistryEntry;
import de.canitzp.ctpcore.util.NBTSaveType;
import de.canitzp.ctpcore.util.NBTUtil;
import de.canitzp.ctpcore.util.StackUtil;
import de.canitzp.voltagedrop.api.recipe.RecipeElectricFurnace;
import de.canitzp.voltagedrop.capabilities.SidedEnergyDevice;
import de.canitzp.voltagedrop.capabilities.UserEnergyDevice;
import de.canitzp.voltagedrop.tile.TileEntityDevice;
import mezz.jei.RecipeRegistry;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * @author canitzp
 */
public class TileElectricFurnace extends TileEntityDevice<UserEnergyDevice>{

    public static final float ENERGY_USAGE = 0.1F;

    public InvWrapper inventory = new InvWrapper(new InventoryBasic("electric_furnace", false, 2));

    private int timeLeft, max;
    private ItemStack burnStack = ItemStack.EMPTY;

    public TileElectricFurnace(){
        super("electric_furnace", SidedEnergyDevice.createSingleEmpty(UserEnergyDevice.class, 230, 5));
    }

    @Override
    protected boolean autoSync(){
        return true;
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
    public void ownRegistry(){
        super.ownRegistry();
        RecipeElectricFurnace.processFurnaceRecipes();
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
                this.sidedEnergyDevice.getDeviceForSide(EnumFacing.NORTH).useEnergy(230, ENERGY_USAGE);
                this.syncToClient();
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
