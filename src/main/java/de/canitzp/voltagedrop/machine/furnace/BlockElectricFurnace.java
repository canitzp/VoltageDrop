package de.canitzp.voltagedrop.machine.furnace;

import de.canitzp.ctpcore.registry.IRegistryEntry;
import de.canitzp.ctpcore.registry.MCRegistry;
import de.canitzp.voltagedrop.api.recipe.Recipes;
import de.canitzp.voltagedrop.machine.BlockEnergyDevice;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Map;

/**
 * @author canitzp
 */
public class BlockElectricFurnace extends BlockEnergyDevice{

    public static final PropertyBool ACTIVE = PropertyBool.create("active");

    public BlockElectricFurnace(){
        super(Material.ROCK, "electric_furnace", TileElectricFurnace.class);
        this.setDefaultState(this.blockState.getBaseState().withProperty(ACTIVE, false));
    }

    @Override
    protected boolean shouldRenderOverlay(){
        return true;
    }

    @Override
    protected BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, getFacing(), ACTIVE);
    }

    @Override
    public void init(Side side) {
        for(Map.Entry<ItemStack, ItemStack> entry : FurnaceRecipes.instance().getSmeltingList().entrySet()){
            MCRegistry.register(new Recipes.ElectricFurnace(entry.getKey(), entry.getValue(), 150));
        }
    }
}
