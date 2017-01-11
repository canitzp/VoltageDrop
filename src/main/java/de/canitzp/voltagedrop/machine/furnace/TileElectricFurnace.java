package de.canitzp.voltagedrop.machine.furnace;

import de.canitzp.voltagedrop.capabilities.SidedEnergyDevice;
import de.canitzp.voltagedrop.capabilities.UserEnergyDevice;
import de.canitzp.voltagedrop.tile.TileEntityDevice;

/**
 * @author canitzp
 */
public class TileElectricFurnace extends TileEntityDevice<UserEnergyDevice>{

    public TileElectricFurnace(){
        super("electric_furnace", SidedEnergyDevice.createSingleEmpty(UserEnergyDevice.class, 230, 5));
    }

    @Override
    protected boolean autoSync(){
        return true;
    }
}
