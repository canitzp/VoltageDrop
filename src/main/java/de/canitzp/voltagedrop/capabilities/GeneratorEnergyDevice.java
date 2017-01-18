package de.canitzp.voltagedrop.capabilities;

/**
 * @author canitzp
 */
public class GeneratorEnergyDevice extends EnergyDevice{

    public GeneratorEnergyDevice(){}

    public GeneratorEnergyDevice(Voltages voltage, float maxCurrentCurrent){
        super(voltage, maxCurrentCurrent);
    }

    @Override
    public boolean canReceive(){
        return false;
    }

    public ErrorTypes generate(Voltages voltage, float current){
        if(!checkVoltageRating(voltage).equals(ErrorTypes.OK)){
            return checkVoltageRating(voltage);
        }
        float energyReceived = Math.min(this.getMaxStoreable() - this.getStored(), Math.min(this.getMaxFlow(), current));
        this.curentEnergy += energyReceived;
        return ErrorTypes.setOK(energyReceived);
    }
}
