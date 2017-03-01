package de.canitzp.voltagedrop;

import de.canitzp.ctpcore.base.ItemBase;
import de.canitzp.ctpcore.registry.MCRegistry;
import de.canitzp.voltagedrop.api.recipe.Recipes;
import de.canitzp.voltagedrop.block.BlockWeightedOre;
import de.canitzp.voltagedrop.item.ItemMultimeter;
import de.canitzp.voltagedrop.machine.arcoven.BlockArcOven;
import de.canitzp.voltagedrop.machine.battery.batterymains.BlockBatteryMains;
import de.canitzp.voltagedrop.machine.furnace.BlockElectricFurnace;
import de.canitzp.voltagedrop.machine.photovoltaicspanel.BlockPhotovoltaic;
import de.canitzp.voltagedrop.machine.solidgenerator.BlockSolidGenerator;
import de.canitzp.voltagedrop.machine.transformer.BlockTransformer;
import de.canitzp.voltagedrop.machine.transformer.basic.BlockTrafoBasic;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

/**
 * @author canitzp
 */
public class Registry{

    public static BlockSolidGenerator solidGenerator;
    public static BlockBatteryMains batteryMains;
    public static BlockElectricFurnace electricFurnace;
    public static BlockTransformer trafoBasic;
    public static BlockPhotovoltaic photovoltaic;
    public static BlockArcOven arcOven;
    public static BlockWeightedOre orePendlandite, oreSulfur, oreDolomite, oreIlmenite;

    public static ItemBase multimeter;

    public static void preInit(){
        MCRegistry.register(solidGenerator = new BlockSolidGenerator());
        MCRegistry.register(batteryMains = new BlockBatteryMains());
        MCRegistry.register(electricFurnace = new BlockElectricFurnace());
        MCRegistry.register(trafoBasic = new BlockTrafoBasic());
        MCRegistry.register(photovoltaic = new BlockPhotovoltaic());
        MCRegistry.register(arcOven = new BlockArcOven());
        MCRegistry.register(orePendlandite = new BlockWeightedOre("pentlandite", 0x553911).spawnInWorld(8, 60, 0, 6, 0).addOreDictionary("oreNickel"));
        MCRegistry.register(oreSulfur = new BlockWeightedOre("sulfur", 0xF2ED99).spawnInWorld(6, 40, 0, 10, 0).addOreDictionary("oreSulfur"));
        MCRegistry.register(oreDolomite = new BlockWeightedOre("dolomite", 0xA58C65).spawnInWorld(10, 75, 15, 5, 0).addOreDictionary("oreMagnesium"));
        MCRegistry.register(oreIlmenite = new BlockWeightedOre("ilmenite", 0x4E4F49).spawnInWorld(4, 25, 0, 4, 0).addOreDictionary("oreTitan"));

        MCRegistry.register(multimeter = new ItemMultimeter());

        MCRegistry.register(new Recipes.ArcOven(new ItemStack(Items.APPLE), new ItemStack(Blocks.LEAVES)));
    }

}
