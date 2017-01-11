package de.canitzp.voltagedrop.capabilities;

import net.minecraft.nbt.NBTTagCompound;

/**
 * @author canitzp
 */
public class EnergyDevice implements IEnergyDevice{

    public float voltage;
    public float currentCurrent;
    public float maxCurrentCurrent;

    public float voltageTolerance = 5;
    public float maxFlowCurrent = 0.25F;

    public EnergyDevice(){}

    public EnergyDevice(float voltage, float maxCurrentCurrent){
        this.voltage = voltage;
        this.maxCurrentCurrent = maxCurrentCurrent;
    }

    @Override
    public float getOutputVoltage(){
        return this.voltage;
    }

    @Override
    public float getInputVoltage(){
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
    public float receiveEnergy(float voltage, float current, boolean simulate){
        if(!canReceive()){
            return 0;
        }
        if(!this.isVoltageCorrect(voltage)){
            return -1;
        }
        float energyReceived = Math.min(this.maxCurrentCurrent - this.currentCurrent, Math.min(this.maxFlowCurrent, current));
        if(!simulate){
            this.currentCurrent += energyReceived;
        }
        return energyReceived;
    }

    @Override
    public float extractEnergy(float voltage, float current, boolean simulate){
        if(!canExtract()){
            return 0;
        }
        if(!this.isVoltageCorrect(voltage)){
            return -1;
        }
        float energyReceived = Math.min(this.currentCurrent, Math.min(this.maxFlowCurrent, current));
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

    public float getMaxFlowCurrent(){
        return maxFlowCurrent;
    }

    public EnergyDevice setMaxCurrentFlow(float maxCurrentFlow){
        this.maxFlowCurrent = maxCurrentFlow;
        return this;
    }

    public boolean isVoltageCorrect(float voltage){
        return this.voltage + this.voltageTolerance > voltage;
    }

    @Override
    public String toString(){
        return "EnergyDevice={voltage=" + this.voltage + "; current=" + this.currentCurrent + "/" + this.maxCurrentCurrent + "}";
    }

    @Override
    public NBTTagCompound serializeNBT(){
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setFloat("Voltage", this.voltage);
        nbt.setFloat("Current", this.currentCurrent);
        nbt.setFloat("MaxCurrent", this.maxCurrentCurrent);
        nbt.setFloat("VoltageTolerance", this.voltageTolerance);
        nbt.setFloat("MaxFlow", this.maxFlowCurrent);
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt){
        System.out.println(nbt);
        this.voltage = nbt.getFloat("Voltage");
        this.currentCurrent = nbt.getFloat("Current");
        this.maxCurrentCurrent = nbt.getFloat("MaxCurrent");
        this.voltageTolerance = nbt.getFloat("VoltageTolerance");
        this.maxFlowCurrent = nbt.getFloat("MaxFlow");
    }
}
