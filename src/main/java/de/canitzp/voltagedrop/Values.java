package de.canitzp.voltagedrop;

import de.canitzp.voltagedrop.capabilities.Voltages;

/**
 * @author canitzp
 */
public class Values{

    /** Generator */
    public static final Voltages SOLID_GENERATOR_VOLTAGE = Voltages.MAINS;
    public static final float SOLID_GENERATOR_CAPACITY = 75F;
    public static final float SOLID_GENERATOR_PRODUCE = 0.01F;

    public static final Voltages PHOTOVOLTAIC_VOLTAGE = Voltages.MAINS;
    public static final float PHOTOVOLTAIC_CAPACITY = 25F;
    public static final float PHOTOVOLTAIC_PRODUCE = 0.00075F;

    /** Transformer */
    public static final Voltages TRANSFORMER_BASIC_IN_VOLTAGE = Voltages.MAINS;
    public static final Voltages TRANSFORMER_BASIC_OUT_VOLTAGE = Voltages.LOW;

    /** Batteries */
    public static final Voltages BATTERY_MAINS_VOLTAGE = Voltages.MAINS;
    public static final float BATTERY_MAINS_CAPACITY = 450F;

    /** Furnace */
    public static final Voltages ELECTRIC_FURNACE_VOLTAGE = Voltages.MAINS;
    public static final float ELECTRIC_FURNACE_CAPACITY = 5F;
    public static final float ELECTRIC_FURNACE_CONSUMPTION = 0.004F;

    /** Arc Furnace */
    public static final Voltages ARC_OVEN_VOLTAGE = Voltages.MAINS;
    public static final float ARC_OVEN_CAPACITY = 15F;
    public static final float ARC_OVEN_CONSUMPTION = 0.0075F;

}
