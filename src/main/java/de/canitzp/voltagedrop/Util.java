package de.canitzp.voltagedrop;

/**
 * @author canitzp
 */
public class Util{

    public static float calculateCurrent(float inputVoltage, float outputVoltage, float inputCurrent){
        return (inputCurrent * ((outputVoltage * 2) / (inputVoltage * 2))) * 0.9F;
    }

}
