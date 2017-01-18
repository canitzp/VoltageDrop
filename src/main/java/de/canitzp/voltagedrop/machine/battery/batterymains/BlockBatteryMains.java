package de.canitzp.voltagedrop.machine.battery.batterymains;

import de.canitzp.voltagedrop.machine.battery.BlockBattery;
import de.canitzp.voltagedrop.render.IInfoRender;
import net.minecraft.block.material.Material;

/**
 * @author canitzp
 */
public class BlockBatteryMains extends BlockBattery implements IInfoRender{

    public BlockBatteryMains(){
        super(Material.IRON, "mains", TileBatteryMains.class);
    }

    @Override
    protected boolean shouldRenderOverlay(){
        return true;
    }

}
