package de.canitzp.voltagedrop.machine.furnace;

import de.canitzp.voltagedrop.capabilities.EnergyDevice;
import de.canitzp.voltagedrop.capabilities.SidedEnergyDevice;
import de.canitzp.voltagedrop.tile.TileEntityDevice;

/**
 * @author canitzp
 */
public class TileElectricFurnace extends TileEntityDevice<EnergyDevice>{

    public TileElectricFurnace(){
        super("electric_furnace", SidedEnergyDevice.createSingleEmpty(EnergyDevice.class, 230, 5));
    }

}
