package de.canitzp.voltagedrop.machine.battery.batterymains;

import de.canitzp.voltagedrop.capabilities.Voltages;
import de.canitzp.voltagedrop.machine.battery.TileBattery;

/**
 * @author canitzp
 */
public class TileBatteryMains extends TileBattery{

    public TileBatteryMains(){
        super(Voltages.MAINS, 750);
    }

}
