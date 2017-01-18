package de.canitzp.voltagedrop.machine.transformer;

import de.canitzp.voltagedrop.capabilities.SidedEnergyDevice;
import de.canitzp.voltagedrop.capabilities.UserEnergyDevice;
import de.canitzp.voltagedrop.capabilities.Voltages;
import de.canitzp.voltagedrop.tile.TileEntityDevice;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author canitzp
 */
//TODO rework transformer
public abstract class TileTransformer extends TileEntityDevice<UserEnergyDevice>{

    private Voltages inputVoltage, outputVoltage;

    public TileTransformer(Voltages inputVoltage, Voltages outputVoltage){
        this.inputVoltage = inputVoltage;
        this.outputVoltage = outputVoltage;
    }

    @Override
    public void update(){
        super.update();
        if(!this.world.isRemote){
            UserEnergyDevice device = this.getDeviceForSide(this.getFacings());
            if(device != null){
                //device.updateTransformer(this);
            }
        }
    }

    @Override
    protected SidedEnergyDevice<UserEnergyDevice> getSidedEnergyDevice(World world, BlockPos pos){
        return SidedEnergyDevice.createSingleEmpty(UserEnergyDevice.class, Voltages.MAINS, 50); //TransformerDevice.createTransformer(inputVoltage, outputVoltage, this.getFacings());
    }

    @Override
    protected boolean autoSync(){
        return true;
    }

}
