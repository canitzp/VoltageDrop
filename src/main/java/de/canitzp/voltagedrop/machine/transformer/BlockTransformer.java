package de.canitzp.voltagedrop.machine.transformer;

import de.canitzp.ctpcore.base.TileEntityBase;
import de.canitzp.voltagedrop.machine.BlockEnergyDevice;
import net.minecraft.block.material.Material;

/**
 * @author canitzp
 */
public class BlockTransformer extends BlockEnergyDevice{

    public BlockTransformer(Material material, String name, Class<? extends TileEntityBase> tileClass){
        super(material, "transformer_".concat(name), tileClass);
    }

    @Override
    protected boolean shouldRenderOverlay(){
        return true;
    }

}
