package de.canitzp.voltagedrop.capabilities;

/**
 * @author canitzp
 */
public class GeneratorEnergyDevice extends EnergyDevice{

    public GeneratorEnergyDevice(){}

    public GeneratorEnergyDevice(float voltage, float maxCurrentCurrent){
        super(voltage, maxCurrentCurrent);
    }

    @Override
    public boolean canReceive(){
        return false;
    }

    public float generate(float voltage, float current){
        if(!super.isVoltageCorrect(voltage)){
            return -1;
        }
        float energyReceived = Math.min(this.maxCurrentCurrent - this.currentCurrent, Math.min(this.maxFlowCurrent, current));
        this.currentCurrent += energyReceived;
        return energyReceived;
    }
}
