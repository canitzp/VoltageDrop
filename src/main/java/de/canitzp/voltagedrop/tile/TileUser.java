package de.canitzp.voltagedrop.tile;

import de.canitzp.ctpcore.recipe.ICustomRecipe;
import de.canitzp.ctpcore.recipe.RecipeRegistry;
import de.canitzp.ctpcore.util.NBTSaveType;
import de.canitzp.ctpcore.util.NBTUtil;
import de.canitzp.voltagedrop.Values;
import de.canitzp.voltagedrop.api.recipe.Recipes;
import de.canitzp.voltagedrop.capabilities.SidedEnergyDevice;
import de.canitzp.voltagedrop.capabilities.UserEnergyDevice;
import de.canitzp.voltagedrop.capabilities.Voltages;
import de.canitzp.voltagedrop.machine.solidgenerator.BlockSolidGenerator;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author canitzp
 */
public abstract class TileUser extends TileEntityDevice<UserEnergyDevice> {

    protected final Voltages voltage;
    protected final float capacity, consumption;
    protected final String recipeCat;
    protected final EnumParticleTypes particleTypes;
    @Nullable protected final PropertyBool blockProperty;

    protected int timeLeft;
    protected ItemStack outputStack = ItemStack.EMPTY;

    public TileUser(Voltages voltage, float capacity, float consumption, String recipeCategory, EnumParticleTypes particle, @Nullable PropertyBool blockProperty) {
        this.voltage = voltage;
        this.capacity = capacity;
        this.consumption = consumption;
        this.recipeCat = recipeCategory;
        this.particleTypes = particle;
        this.blockProperty = blockProperty;
    }

    @Override
    protected SidedEnergyDevice<UserEnergyDevice> getSidedEnergyDevice(World world, BlockPos pos) {
        return SidedEnergyDevice.createSingleEmpty(UserEnergyDevice.class, this.voltage, this.capacity);
    }

    @Override
    public void update(){
        super.update();
        this.userUpdate();
    }

    protected void userUpdate(){
        if(!world.isRemote){
            if(timeLeft <= 0){
                if(outputStack.isEmpty()){
                    if(world.getTotalWorldTime() % 20 == 0){
                        List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, getInside(1F));
                        for(EntityItem item : items){
                            ItemStack stack = item.getEntityItem();
                            if(RecipeRegistry.hasRecipeFor(this.recipeCat, stack)){
                                if(checkForBurn(stack)){
                                    this.spawnParticle(this.particleTypes, item.getPosition().getX(), item.getPosition().getY() + 0.5, item.getPosition().getZ(), 50, 0.25D, new int[0]);
                                }
                            }
                        }
                    } else {
                        ItemStack stack = getInvAbove();
                        if(!stack.isEmpty()){
                            if(RecipeRegistry.hasRecipeFor(this.recipeCat, stack)){
                                checkForBurn(stack);
                            }
                        }
                    }
                } else {
                    if(!getInvUnder(this.outputStack)){
                        this.world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY() + 1D, pos.getZ(), this.outputStack.copy()));
                    }
                    this.outputStack = ItemStack.EMPTY;
                }
            }
            if(timeLeft > 0){
                if(this.getDeviceForSide(EnumFacing.NORTH).getStored() >= this.consumption){
                    timeLeft--;
                    this.getDeviceForSide(EnumFacing.NORTH).useEnergy(this.voltage, this.consumption);
                }
            }
        } else if(this.blockProperty != null){
            boolean set = timeLeft > 0 && (this.getDeviceForSide(EnumFacing.NORTH).getStored() >= this.consumption);
            world.setBlockState(pos, world.getBlockState(pos).withProperty(this.blockProperty, set));
        }
    }

    protected boolean checkForBurn(ItemStack stack){
        ICustomRecipe customRecipe = RecipeRegistry.getRecipeFor(this.recipeCat, stack);
        if(customRecipe != null && customRecipe instanceof Recipes.SimpleBurn){
            this.outputStack = ((Recipes.SimpleBurn)customRecipe).getOutput().copy();
            stack.shrink(1);
            this.timeLeft = ((Recipes.SimpleBurn)customRecipe).getBurnTime();
            return true;
        }
        return false;
    }

    protected ItemStack getInvAbove(){
        TileEntity tile = world.getTileEntity(this.pos.offset(EnumFacing.UP));
        if(tile != null && tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN)){
            IItemHandler handler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
            for(int i = 0; i < handler.getSlots(); i++){
                ItemStack stack = handler.getStackInSlot(i);
                if(!stack.isEmpty() && RecipeRegistry.hasRecipeFor(this.recipeCat, stack)){
                    return stack;
                }
            }
        }
        return ItemStack.EMPTY;
    }

    protected boolean getInvUnder(ItemStack stack){
        TileEntity tile = world.getTileEntity(this.pos.offset(EnumFacing.DOWN));
        if(tile != null && tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP)){
            IItemHandler handler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);
            for(int i = 0; i < handler.getSlots(); i++){
                if(handler.insertItem(i, stack, true).isEmpty()){
                    return handler.insertItem(i, stack, false).isEmpty();
                }
            }
        }
        return false;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound, NBTSaveType type){
        super.writeToNBT(compound, type);
        compound.setInteger("TimeLeft", this.timeLeft);
        NBTUtil.setItemStack(compound, "OutStack", this.outputStack);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound, NBTSaveType type){
        super.readFromNBT(compound, type);
        this.timeLeft = compound.getInteger("TimeLeft");
        this.outputStack = NBTUtil.getItemStack(compound, "OutStack");
    }

}
