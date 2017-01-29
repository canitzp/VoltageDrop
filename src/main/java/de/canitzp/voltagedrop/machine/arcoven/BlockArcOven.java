package de.canitzp.voltagedrop.machine.arcoven;

import de.canitzp.voltagedrop.machine.BlockEnergyDevice;
import net.minecraft.block.material.Material;

/**
 * @author canitzp
 */
public class BlockArcOven extends BlockEnergyDevice{

    public BlockArcOven(){
        super(Material.IRON, "arc_oven", TileArcOven.class);
    }

    @Override
    protected boolean shouldRenderOverlay(){
        return true;
    }
}
