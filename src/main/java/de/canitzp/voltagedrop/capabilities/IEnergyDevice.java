package de.canitzp.voltagedrop.capabilities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

/**
 * @author canitzp
 */
public interface IEnergyDevice extends INBTSerializable<NBTTagCompound>{

    /**
     * @return The maximum voltage for the block
     */
    Voltages getVoltage();

    /**
     * The voltage is equal to the max output voltage.
     * @return The maximum of saveable current per hour in Amps/Hour
     */
    float getStored();

    void setStored(float currentPerHour);

    ErrorTypes receiveEnergy(Voltages voltage, float energy, boolean simulate);

    ErrorTypes extractEnergy(Voltages voltage, float energy, boolean simulate);

    float getMaxStoreable();

    boolean canExtract();

    boolean canReceive();

    float getMaxFlow();

}
