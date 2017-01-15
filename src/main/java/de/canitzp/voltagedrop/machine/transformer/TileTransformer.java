package de.canitzp.voltagedrop.machine.transformer;

import de.canitzp.voltagedrop.capabilities.SidedEnergyDevice;
import de.canitzp.voltagedrop.capabilities.TransformerDevice;
import de.canitzp.voltagedrop.tile.TileEntityDevice;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author canitzp
 */
public abstract class TileTransformer extends TileEntityDevice<TransformerDevice>{

    private float inputVoltage, outputVoltage;

    public TileTransformer(String name, float inputVoltage, float outputVoltage){
        super("transformer_".concat(name));
        this.inputVoltage = inputVoltage;
        this.outputVoltage = outputVoltage;
    }

    @Override
    public void update(){
        super.update();
        if(!this.world.isRemote){
            TransformerDevice device = this.sidedEnergyDevice.getDeviceForSide(this.getFacings());
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
