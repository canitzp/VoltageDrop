package de.canitzp.voltagedrop.capabilities;

/**
 * @author canitzp
 */
public interface IEnergyDevice{

    /**
     * @return The maximum of output voltage in Volts
     */
    int getOutputVoltage();

    /**
     * @return The maximum input voltage in Volts
     */
    int getInputVoltage();

    /**
     * The voltage is equal to the max output voltage.
     * @return The maximum of saveable current per hour in Amps/Hour
     */
    float getSavedCurrentPerHour();

    void setSavedCurrentPerHour(float currentPerHour);

    /**
     * @return The devices internal resistance in Ohm
     */
    int getInternalResistance();

    float receiveEnergy(int voltage, float current, boolean simulate);

    float extractEnergy(int voltage, float current, boolean simulate);

    float getMaxCurrent();

    boolean canExtract();

    boolean canReceive();

}
