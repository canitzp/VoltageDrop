package de.canitzp.voltagedrop.capabilities;

/**
 * @author canitzp
 */
public enum ErrorTypes{

    OK,
    NOT_TRANSFERABLE,
    VOLTAGE_OVERFLOW,
    VOLTAGE_UNDERRATED

    ;

    public float energy = 0;

    public static ErrorTypes setOK(float energy){
        OK.energy = energy;
        return OK;
    }

}
