package de.canitzp.voltagedrop.machine.batterymains;

import de.canitzp.voltagedrop.capabilities.EnergyDevice;
import de.canitzp.voltagedrop.capabilities.SidedEnergyDevice;
import de.canitzp.voltagedrop.tile.TileEntityDevice;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author canitzp
 */
public class TileBatteryMains extends TileEntityDevice<EnergyDevice>{

    public TileBatteryMains(){
        super("battery_mains");
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

    @Override
    protected SidedEnergyDevice<EnergyDevice> getSidedEnergyDevice(World world, BlockPos pos){
        return SidedEnergyDevice.createSingleEmpty(EnergyDevice.class, 230, 750);
    }

}
