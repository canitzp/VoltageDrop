package de.canitzp.voltagedrop.machine.battery;

import de.canitzp.ctpcore.base.TileEntityBase;
import de.canitzp.voltagedrop.machine.BlockEnergyDevice;
import net.minecraft.block.material.Material;

/**
 * @author canitzp
 */
public class BlockBattery extends BlockEnergyDevice{

    public BlockBattery(Material material, String name, Class<? extends TileEntityBase> tileClass){
        super(material, "battery_".concat(name), tileClass);
    }

    @Override
    protected boolean shouldRenderOverlay(){
        return true;
    }
}
