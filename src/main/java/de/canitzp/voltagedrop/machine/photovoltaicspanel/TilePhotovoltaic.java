package de.canitzp.voltagedrop.machine.photovoltaicspanel;

import de.canitzp.voltagedrop.capabilities.GeneratorEnergyDevice;
import de.canitzp.voltagedrop.capabilities.SidedEnergyDevice;
import de.canitzp.voltagedrop.capabilities.Voltages;
import de.canitzp.voltagedrop.tile.TileEntityDevice;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author canitzp
 */
public class TilePhotovoltaic extends TileEntityDevice<GeneratorEnergyDevice>{

    public static final float GENERATION = 0.00075F;

    @Override
    protected SidedEnergyDevice<GeneratorEnergyDevice> getSidedEnergyDevice(World world, BlockPos pos){
        return SidedEnergyDevice.createSingleEmpty(GeneratorEnergyDevice.class, Voltages.MAINS, 25);
    }

    @Override
    public void update(){
        super.update();
        super.pushEnergy();
        if(!world.isRemote){
            //if(world.canBlockSeeSky(this.pos)){
              //  System.out.println();
                this.getDeviceForSide(EnumFacing.NORTH).generate(Voltages.MAINS, world.isRaining() ? GENERATION/2 : GENERATION);
            //}
        }
    }

}
