package de.canitzp.voltagedrop.capabilities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

/**
 * @author canitzp
 */
public interface IEnergyDevice extends INBTSerializable<NBTTagCompound>{

    /**
     * @return The maximum of output voltage in Volts
     */
    float getOutputVoltage();

    /**
     * @return The maximum input voltage in Volts
     */
    float getInputVoltage();

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

    float receiveEnergy(float voltage, float current, boolean simulate);

    float extractEnergy(float voltage, float current, boolean simulate);

    float getMaxCurrent();

    boolean canExtract();

    boolean canReceive();

    float getMaxFlowCurrent();

}
