package de.canitzp.voltagedrop.machine.batterymains;

import de.canitzp.voltagedrop.VoltageDrop;
import de.canitzp.voltagedrop.capabilities.EnergyDevice;
import de.canitzp.voltagedrop.capabilities.SidedEnergyDevice;
import de.canitzp.voltagedrop.tile.TileEntityDevice;
import net.minecraft.util.ResourceLocation;

/**
 * @author canitzp
 */
public class TileBatteryMains extends TileEntityDevice<EnergyDevice>{

    public TileBatteryMains(){
        super(new ResourceLocation(VoltageDrop.MODID, "battery_mains"), SidedEnergyDevice.createSingleEmpty(EnergyDevice.class, 230, 5000));
    }

    @Override
    public void update(){
        super.update();
        super.pushEnergy();
    }

}
