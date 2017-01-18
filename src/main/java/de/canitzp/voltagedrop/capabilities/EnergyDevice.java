package de.canitzp.voltagedrop.capabilities;

import net.minecraft.nbt.NBTTagCompound;

/**
 * @author canitzp
 */
public class EnergyDevice implements IEnergyDevice{

    public Voltages voltage;
    public float curentEnergy;
    public float maxEnergy;

    public float maxFlow = 0.25F;

    public EnergyDevice(){}

    public EnergyDevice(Voltages voltage, float maxEnergy){
        this.voltage = voltage;
        this.maxEnergy = maxEnergy;
    }

    @Override
    public Voltages getVoltage(){
        return this.voltage;
    }

    @Override
    public float getStored(){
        return this.curentEnergy;
    }

    @Override
    public void setStored(float energy){
        this.curentEnergy = energy;
    }

    @Override
    public float getMaxStoreable(){
        return this.maxEnergy;
    }

    @Override
    public ErrorTypes receiveEnergy(Voltages voltage, float current, boolean simulate){
        if(!canReceive()){
            return ErrorTypes.NOT_TRANSFERABLE;
        }
        if(!checkVoltageRating(voltage).equals(ErrorTypes.OK)){
            return checkVoltageRating(voltage);
        }
        float energyReceived = Math.min(this.getMaxStoreable() - this.getStored(), Math.min(this.getMaxFlow(), current));
        if(!simulate){
            this.curentEnergy += energyReceived;
        }
        return ErrorTypes.setOK(energyReceived);
    }

    @Override
    public ErrorTypes extractEnergy(Voltages voltage, float current, boolean simulate){
        if(!canExtract()){
            return ErrorTypes.NOT_TRANSFERABLE;
        }
        if(!checkVoltageRating(voltage).equals(ErrorTypes.OK)){
            return checkVoltageRating(voltage);
        }
        float energyReceived = Math.min(this.getStored(), Math.min(this.getMaxFlow(), current));
        if(!simulate){
            this.curentEnergy -= energyReceived;
        }
        return ErrorTypes.setOK(energyReceived);
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
    public float getMaxFlow(){
        return this.maxFlow;
    }

    public EnergyDevice setMaxCurrentFlow(float maxCurrentFlow){
        this.maxFlow = maxCurrentFlow;
        return this;
    }

    @Override
    public String toString(){
        return "EnergyDevice={voltage=" + this.getVoltage() + "; current=" + this.getStored() + "/" + this.getMaxStoreable() + "}";
    }

    @Override
    public NBTTagCompound serializeNBT(){
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("Voltage", this.getVoltage().toInt());
        nbt.setFloat("Current", this.getStored());
        nbt.setFloat("MaxCurrent", this.getMaxStoreable());
        nbt.setFloat("MaxFlow", this.getMaxFlow());
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt){
        this.voltage = Voltages.fromInt(nbt.getInteger("Voltage"));
        this.curentEnergy = nbt.getFloat("Current");
        this.maxEnergy = nbt.getFloat("MaxCurrent");
        this.maxFlow = nbt.getFloat("MaxFlow");
    }

    protected ErrorTypes checkVoltageRating(Voltages other){
        return other.equals(this.getVoltage()) ? ErrorTypes.OK : other.getRating() > this.getVoltage().getRating() ? ErrorTypes.VOLTAGE_OVERFLOW : ErrorTypes.VOLTAGE_UNDERRATED;
    }

}
