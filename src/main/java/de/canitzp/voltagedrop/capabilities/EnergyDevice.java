package de.canitzp.voltagedrop.capabilities;

/**
 * @author canitzp
 */
public class EnergyDevice implements IEnergyDevice{

    public int voltage;
    public float currentCurrent;
    public float maxCurrentCurrent;

    public int voltageTolerance = 5;
    public float maxFlowCurrent = 3;

    public EnergyDevice(int voltage, float maxCurrentCurrent){
        this.voltage = voltage;
        this.maxCurrentCurrent = maxCurrentCurrent;
    }

    @Override
    public int getOutputVoltage(){
        return this.voltage;
    }

    @Override
    public int getInputVoltage(){
        return this.voltage;
    }

    @Override
    public float getSavedCurrentPerHour(){
        return this.currentCurrent;
    }

    @Override
    public void setSavedCurrentPerHour(float currentPerHour){
        this.currentCurrent = currentPerHour;
    }

    @Override
    public int getInternalResistance(){
        return Math.round(this.voltage / this.maxCurrentCurrent);
    }

    @Override
    public float receiveEnergy(int voltage, float current, boolean simulate){
        if(!canReceive()){
            return 0;
        }
        if(this.voltage + this.voltageTolerance < voltage){
            return -1;
        }
        float energyReceived = Math.min(this.maxCurrentCurrent - current, Math.min(this.maxFlowCurrent, this.maxFlowCurrent));
        if(!simulate){
            this.currentCurrent += energyReceived;
        }
        return energyReceived;
    }

    @Override
    public float extractEnergy(int voltage, float current, boolean simulate){
        if(!canExtract()){
            return 0;
        }
        if(this.voltage + this.voltageTolerance < voltage){
            return -1;
        }
        float energyReceived = Math.min(this.maxCurrentCurrent, Math.min(this.maxFlowCurrent, current));
        if(!simulate){
            this.currentCurrent -= energyReceived;
        }
        return energyReceived;
    }

    @Override
    public float getMaxCurrent(){
        return this.maxCurrentCurrent;
    }

    @Override
    public boolean canExtract(){
        return true;
    }

    @Override
    public boolean canReceive(){
        return true;
    }

    @Override
    public String toString(){
        return "EnergyDevice={voltage=" + this.voltage + "; current=" + this.currentCurrent + "}";
    }
}
