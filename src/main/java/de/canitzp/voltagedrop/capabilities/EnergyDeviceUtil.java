package de.canitzp.voltagedrop.capabilities;

/**
 * @author canitzp
 */
public class EnergyDeviceUtil{

    public static String getDisplayable(IEnergyDevice device){
        if(device != null){
            float current = device.getSavedCurrentPerHour();
            float max = device.getMaxCurrent();
            float displayCurrent = Math.round(current * 100F) / 100F;
            return displayCurrent + "Ah / " + max + "Ah";
        }
        return "";
    }

}
