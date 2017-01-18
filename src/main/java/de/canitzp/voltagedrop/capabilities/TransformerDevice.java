package de.canitzp.voltagedrop.capabilities;

/**
 * @author canitzp
 */
//TODO rewrite this whole thing
public class TransformerDevice extends EnergyDevice{

    /*
    private float inputVoltage, outputVoltage;
    private boolean isInputSide;

    public TransformerDevice(float inputVoltage, float outputVoltage, boolean isInputSide){
        super(isInputSide ? inputVoltage : outputVoltage, 1.0F);
        this.inputVoltage = inputVoltage;
        this.outputVoltage = outputVoltage;
        this.isInputSide = isInputSide;
        setMaxCurrentFlow(getMaxCurrent());
    }

    public static SidedEnergyDevice<TransformerDevice> createTransformer(float inputVoltage, float outputVoltage, EnumFacing inputSide){
        SidedEnergyDevice<TransformerDevice> device = new SidedEnergyDevice<>();
        device.add(new TransformerDevice(inputVoltage, outputVoltage, true), inputSide);
        device.add(new TransformerDevice(inputVoltage, outputVoltage, false), inputSide.getOpposite());
        return device;
    }

    @Override
    public boolean canExtract(){
        return !this.isInputSide;
    }

    @Override
    public boolean canReceive(){
        return this.isInputSide;
    }

    @Override
    public float getMaxFlowCurrent(){
        return getMaxCurrent();
    }

    @Override
    public boolean isVoltageCorrect(float voltage){
        return voltage == (this.isInputSide ? this.inputVoltage : this.outputVoltage);
    }

    public void updateTransformer(TileTransformer transformer){
        if(this.isInputSide){
            TransformerDevice output = transformer.sidedEnergyDevice.getDeviceForSide(transformer.getFacings().getOpposite());
            if(output != null){
                this.extract(output.insert(Util.calculateCurrent(this.inputVoltage, this.outputVoltage, this.getStored())));
            }
        }
    }

    private float insert(float current){
        float energyReceived = Math.min(this.maxCurrentCurrent - this.currentCurrent, Math.min(this.maxFlowCurrent, current));
        this.currentCurrent += energyReceived;
        return energyReceived;
    }

    private float extract(float current){
        float energyReceived = Math.min(this.currentCurrent, Math.min(this.maxFlowCurrent, current));
        this.currentCurrent -= energyReceived;
        return energyReceived;
    }
    */

}
