package de.canitzp.voltagedrop.machine.arcoven;

import de.canitzp.ctpcore.recipe.RecipeRegistry;
import de.canitzp.ctpcore.util.NBTSaveType;
import de.canitzp.ctpcore.util.NBTUtil;
import de.canitzp.voltagedrop.Values;
import de.canitzp.voltagedrop.api.recipe.Recipes;
import de.canitzp.voltagedrop.capabilities.SidedEnergyDevice;
import de.canitzp.voltagedrop.capabilities.UserEnergyDevice;
import de.canitzp.voltagedrop.capabilities.Voltages;
import de.canitzp.voltagedrop.tile.TileUser;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author canitzp
 */
public class TileArcOven extends TileUser{

    public TileArcOven() {
        super(Values.ARC_OVEN_VOLTAGE, Values.ARC_OVEN_CAPACITY, Values.ARC_OVEN_CONSUMPTION, Recipes.ARC_OVEN.getCategory(), EnumParticleTypes.FLAME, BlockArcOven.ACTIVE);
    }

    @Override
    protected boolean checkForBurn(ItemStack stack){
        if(!(world.getBlockState(pos.offset(EnumFacing.DOWN)).getMaterial().equals(Material.LAVA))){
           return false;
        }
        Recipes.ArcOven recipe = (Recipes.ArcOven) RecipeRegistry.getRecipeFor(Recipes.ARC_OVEN.getCategory(), stack);
        if(recipe != null){
            this.outputStack = recipe.getOutput().copy();
            stack.shrink(1);
            this.timeLeft = recipe.getBurnTime();
            return true;
        }
        return false;
    }

}