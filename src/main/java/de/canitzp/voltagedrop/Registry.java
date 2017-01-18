package de.canitzp.voltagedrop;

import de.canitzp.ctpcore.registry.MCRegistry;
import de.canitzp.voltagedrop.machine.battery.batterymains.BlockBatteryMains;
import de.canitzp.voltagedrop.machine.furnace.BlockElectricFurnace;
import de.canitzp.voltagedrop.machine.solidgenerator.BlockSolidGenerator;
import de.canitzp.voltagedrop.machine.transformer.BlockTransformer;
import de.canitzp.voltagedrop.machine.transformer.basic.BlockTrafoBasic;

/**
 * @author canitzp
 */
public class Registry{

    public static BlockSolidGenerator solidGenerator;
    public static BlockBatteryMains batteryMains;
    public static BlockElectricFurnace electricFurnace;
    public static BlockTransformer trafoBasic;

    public static void preInit(){
        MCRegistry.register(solidGenerator = new BlockSolidGenerator());
        MCRegistry.register(batteryMains = new BlockBatteryMains());
        MCRegistry.register(electricFurnace = new BlockElectricFurnace());
        MCRegistry.register(trafoBasic = new BlockTrafoBasic());
    }

}
