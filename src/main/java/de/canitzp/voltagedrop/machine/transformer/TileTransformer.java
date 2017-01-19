package de.canitzp.voltagedrop.machine.transformer;

import de.canitzp.voltagedrop.capabilities.SidedEnergyDevice;
import de.canitzp.voltagedrop.capabilities.TransformerDevice;
import de.canitzp.voltagedrop.capabilities.Voltages;
import de.canitzp.voltagedrop.tile.TileEntityDevice;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author canitzp
 */
public abstract class TileTransformer extends TileEntityDevice<TransformerDevice>{

    private Voltages inputVoltage, outputVoltage;

    public TileTransformer(Voltages inputVoltage, Voltages outputVoltage){
        this.inputVoltage = inputVoltage;
        this.outputVoltage = outputVoltage;
    }

    @Override
    public void update(){
        super.update();
        if(!this.world.isRemote){
            TransformerDevice device = this.getDeviceForSide(this.getFacings());
            if(device != null){
                device.updateTransformer(this);
            }
        }
    }

    @Override
    protected SidedEnergyDevice<TransformerDevice> getSidedEnergyDevice(World world, BlockPos pos){
        return TransformerDevice.createTransformer(inputVoltage, outputVoltage, this.getFacings());
    }

    @Override
    protected boolean autoSync(){
        return true;
    }

}
