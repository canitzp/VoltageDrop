package de.canitzp.voltagedrop.machine.battery;

import de.canitzp.voltagedrop.capabilities.EnergyDevice;
import de.canitzp.voltagedrop.capabilities.SidedEnergyDevice;
import de.canitzp.voltagedrop.capabilities.Voltages;
import de.canitzp.voltagedrop.tile.TileEntityDevice;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author canitzp
 */
public class TileBattery extends TileEntityDevice<EnergyDevice>{

    private Voltages voltage;
    private float savableCurrent;

    public TileBattery(Voltages voltage, float savableCurrent){
        this.voltage = voltage;
        this.savableCurrent = savableCurrent;
    }

    @Override
    public void update(){
        super.update();
        super.pushEnergy();
    }

    @Override
    protected SidedEnergyDevice<EnergyDevice> getSidedEnergyDevice(World world, BlockPos pos){
        return SidedEnergyDevice.createSingleEmpty(EnergyDevice.class, this.voltage, this.savableCurrent);
    }

}
