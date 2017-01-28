package de.canitzp.voltagedrop.machine.battery.batterymains;

import de.canitzp.voltagedrop.Values;
import de.canitzp.voltagedrop.machine.battery.BlockBattery;
import de.canitzp.voltagedrop.machine.battery.TileBattery;
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

    public static class TileBatteryMains extends TileBattery{
        public TileBatteryMains(){
            super(Values.BATTERY_MAINS_VOLTAGE, Values.BATTERY_MAINS_CAPACITY);
        }
    }

}
