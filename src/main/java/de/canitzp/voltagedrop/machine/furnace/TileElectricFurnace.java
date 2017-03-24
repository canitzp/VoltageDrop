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
import de.canitzp.voltagedrop.tile.TileUser;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nullable;
import java.util.List;


/**
 * @author canitzp
 */
public class TileElectricFurnace extends TileUser {

    public TileElectricFurnace() {
        super(Values.ELECTRIC_FURNACE_VOLTAGE, Values.ELECTRIC_FURNACE_CAPACITY, Values.ELECTRIC_FURNACE_CONSUMPTION, Recipes.ELECTRIC_FURNACE.getCategory(), EnumParticleTypes.TOTEM, BlockElectricFurnace.ACTIVE);
    }

}
