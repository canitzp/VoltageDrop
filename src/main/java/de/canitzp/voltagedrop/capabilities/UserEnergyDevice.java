package de.canitzp.voltagedrop.capabilities;

/**
 * @author canitzp
 */
public class UserEnergyDevice extends EnergyDevice{

    public UserEnergyDevice(){}

    public UserEnergyDevice(float voltage, float maxCurrentCurrent){
        super(voltage, maxCurrentCurrent);
    }

    @Override
    public boolean canExtract(){
        return false;
    }

    public float useEnergy(float voltage, float current){
        if(!this.isVoltageCorrect(voltage)){
            return -1;
        }
        float energyReceived = Math.min(this.currentCurrent, Math.min(this.maxFlowCurrent, current));
        this.currentCurrent -= energyReceived;
        return energyReceived;
    }
}
