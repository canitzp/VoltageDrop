package de.canitzp.voltagedrop.machine.furnace;

import de.canitzp.ctpcore.registry.IRegistryEntry;
import de.canitzp.voltagedrop.VoltageDrop;
import de.canitzp.voltagedrop.api.recipe.RecipeElectricFurnace;
import de.canitzp.voltagedrop.machine.BlockEnergyDevice;
import net.minecraft.block.material.Material;

/**
 * @author canitzp
 */
public class BlockElectricFurnace extends BlockEnergyDevice{

    public BlockElectricFurnace(){
        super(Material.IRON, "electric_furnace", TileElectricFurnace.class);
        this.addGuiContainer(VoltageDrop.instance, GuiElectricFurnace.class, ContainerElectricFurnace.class);
    }

    @Override
    protected boolean shouldRenderOverlay(){
        return true;
    }

    @Override
    public void onRegister(IRegistryEntry[] otherEntries){
        super.onRegister(otherEntries);
        RecipeElectricFurnace.processFurnaceRecipes();
    }
}
