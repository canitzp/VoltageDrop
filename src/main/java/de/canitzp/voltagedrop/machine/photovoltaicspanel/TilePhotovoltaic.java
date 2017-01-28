package de.canitzp.voltagedrop.machine.photovoltaicspanel;

import de.canitzp.ctpcore.util.WorldUtil;
import de.canitzp.voltagedrop.Values;
import de.canitzp.voltagedrop.capabilities.GeneratorEnergyDevice;
import de.canitzp.voltagedrop.capabilities.SidedEnergyDevice;
import de.canitzp.voltagedrop.tile.TileEntityDevice;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author canitzp
 */
public class TilePhotovoltaic extends TileEntityDevice<GeneratorEnergyDevice>{

    @Override
    protected SidedEnergyDevice<GeneratorEnergyDevice> getSidedEnergyDevice(World world, BlockPos pos){
        return SidedEnergyDevice.createSingleEmpty(GeneratorEnergyDevice.class, Values.PHOTOVOLTAIC_VOLTAGE, Values.PHOTOVOLTAIC_CAPACITY);
    }

    @Override
    public void update(){
        super.update();
        super.pushEnergy();
        if(!world.isRemote){
            if(WorldUtil.isDaytime(world) && WorldUtil.canBlockSeeSky(world, pos)){
                this.getDeviceForSide(EnumFacing.NORTH).generate(Values.PHOTOVOLTAIC_VOLTAGE, Values.PHOTOVOLTAIC_PRODUCE / (world.isRaining() ? 2 : 1));
            }
        }
    }

}
