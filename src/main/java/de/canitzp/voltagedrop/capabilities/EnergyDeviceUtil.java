package de.canitzp.voltagedrop.capabilities;

/**
 * @author canitzp
 */
public class EnergyDeviceUtil{

    public static String getDisplayable(IEnergyDevice device){
        if(device != null){
            float current = device.getStored();
            float max = device.getMaxStoreable();
            float displayCurrent = Math.round(current * 100F) / 100F;
            return device.getVoltage().getRating() + "V  " + displayCurrent + "Ah / " + max + "Ah";
        }
        return "";
    }

}
