package de.canitzp.voltagedrop.capabilities;

/**
 * @author canitzp
 */
public class UserEnergyDevice extends EnergyDevice{

    public UserEnergyDevice(){}

    public UserEnergyDevice(Voltages voltage, float maxCurrentCurrent){
        super(voltage, maxCurrentCurrent);
    }

    @Override
    public boolean canExtract(){
        return false;
    }

    public ErrorTypes useEnergy(Voltages voltage, float current){
        if(!checkVoltageRating(voltage).equals(ErrorTypes.OK)){
            return checkVoltageRating(voltage);
        }
        float energyReceived = Math.min(this.getStored(), Math.min(this.getMaxFlow(), current));
        this.curentEnergy -= energyReceived;
        return ErrorTypes.setOK(energyReceived);
    }
}
