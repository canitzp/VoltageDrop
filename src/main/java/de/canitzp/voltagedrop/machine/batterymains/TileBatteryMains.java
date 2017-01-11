package de.canitzp.voltagedrop.machine.batterymains;

import de.canitzp.voltagedrop.capabilities.EnergyDevice;
import de.canitzp.voltagedrop.capabilities.SidedEnergyDevice;
import de.canitzp.voltagedrop.tile.TileEntityDevice;
import net.minecraft.util.EnumFacing;

/**
 * @author canitzp
 */
public class TileBatteryMains extends TileEntityDevice<EnergyDevice>{

    private float cachedCurrent;

    public TileBatteryMains(){
        super("battery_mains", SidedEnergyDevice.createSingleEmpty(EnergyDevice.class, 230, 5000));
    }

    @Override
    public void update(){
        super.update();
        super.pushEnergy();
    }

    @Override
    protected boolean autoSync(){
        return true;
    }

}
