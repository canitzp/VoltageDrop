package de.canitzp.voltagedrop.machine.photovoltaicspanel;

import de.canitzp.voltagedrop.machine.BlockEnergyDevice;
import net.minecraft.block.material.Material;

/**
 * @author canitzp
 */
public class BlockPhotovoltaic extends BlockEnergyDevice{

    public BlockPhotovoltaic(){
        super(Material.IRON, "photovoltaic", TilePhotovoltaic.class);
    }

    @Override
    protected boolean shouldRenderOverlay(){
        return true;
    }
}
