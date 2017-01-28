package de.canitzp.voltagedrop.capabilities;

import de.canitzp.voltagedrop.machine.transformer.TileTransformer;
import net.minecraft.util.EnumFacing;

/**
 * @author canitzp
 */
public class TransformerDevice extends EnergyDevice{

    private boolean isInput;

    public TransformerDevice(){}

    public TransformerDevice(Voltages voltages, boolean isInput, float maxEnergy){
        super(voltages, maxEnergy);
        this.isInput = isInput;
    }

    public static SidedEnergyDevice<TransformerDevice> createTransformer(Voltages input, Voltages output, EnumFacing inputSide){
        SidedEnergyDevice<TransformerDevice> device = new SidedEnergyDevice<>();
        device.add(new TransformerDevice(input, true, 1), inputSide);
        device.add(new TransformerDevice(output, false, output.getConversionRateTo(input)), inputSide.getOpposite());
        return device;
    }

    @Override
    public boolean canExtract(){
        return !this.isInput;
    }

    @Override
    public boolean canReceive(){
        return this.isInput;
    }

    @Override
    public float getMaxFlow(){
        return getMaxStoreable();
    }

    public void updateTransformer(TileTransformer transformer){
        if(this.isInput){
            TransformerDevice output = transformer.getDeviceForSide(transformer.getFacings().getOpposite());
            if(output != null){
                float transferEnergy = this.getStored() * Voltages.getConversionRateBetween(output.getVoltage(), this.getVoltage());
                this.extract(output.insert(transferEnergy));
            }
        }
    }

    private float insert(float current){
        float energyReceived = Math.min(this.getMaxStoreable() - this.getStored(), Math.min(this.getMaxFlow(), current));
        this.curentEnergy += energyReceived;
        return energyReceived;
    }

    private float extract(float current){
        float energyReceived = Math.min(this.getStored(), Math.min(this.getMaxFlow(), current));
        this.curentEnergy -= energyReceived;
        return energyReceived;
    }

}
