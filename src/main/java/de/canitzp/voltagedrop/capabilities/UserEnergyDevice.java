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
}
