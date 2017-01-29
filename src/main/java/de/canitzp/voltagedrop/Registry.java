package de.canitzp.voltagedrop;

import de.canitzp.ctpcore.base.BlockBase;
import de.canitzp.ctpcore.registry.MCRegistry;
import de.canitzp.voltagedrop.api.recipe.RecipeArcOven;
import de.canitzp.voltagedrop.machine.arcoven.BlockArcOven;
import de.canitzp.voltagedrop.machine.battery.batterymains.BlockBatteryMains;
import de.canitzp.voltagedrop.machine.furnace.BlockElectricFurnace;
import de.canitzp.voltagedrop.machine.photovoltaicspanel.BlockPhotovoltaic;
import de.canitzp.voltagedrop.machine.solidgenerator.BlockSolidGenerator;
import de.canitzp.voltagedrop.machine.transformer.BlockTransformer;
import de.canitzp.voltagedrop.machine.transformer.basic.BlockTrafoBasic;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

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
    public static BlockBase orePendlandite, oreSulfur, oreDolomite, oreIlmenite;

    public static void preInit(){
        MCRegistry.register(solidGenerator = new BlockSolidGenerator());
        MCRegistry.register(batteryMains = new BlockBatteryMains());
        MCRegistry.register(electricFurnace = new BlockElectricFurnace());
        MCRegistry.register(trafoBasic = new BlockTrafoBasic());
        MCRegistry.register(photovoltaic = new BlockPhotovoltaic());
        MCRegistry.register(arcOven = new BlockArcOven());
        MCRegistry.register(orePendlandite = new BlockBase(Material.ROCK, new ResourceLocation(VoltageDrop.MODID, "ore_pendlandite"))
                .spawnInWorld(Blocks.STONE, 8, 60, 0, 6, 0).addOreDictionary("oreNickel"));
        MCRegistry.register(oreSulfur = new BlockBase(Material.ROCK, new ResourceLocation(VoltageDrop.MODID, "ore_sulfur"))
                .spawnInWorld(Blocks.STONE, 6, 40, 0, 10, 0).addOreDictionary("oreSulfur"));
        MCRegistry.register(oreDolomite = new BlockBase(Material.ROCK, new ResourceLocation(VoltageDrop.MODID, "ore_dolomite"))
                .spawnInWorld(Blocks.STONE, 10, 75, 15, 5, 0).addOreDictionary("oreMagnesium"));
        MCRegistry.register(oreIlmenite = new BlockBase(Material.ROCK, new ResourceLocation(VoltageDrop.MODID, "ore_ilmenite"))
                .spawnInWorld(Blocks.STONE, 4, 25, 0, 4, 0).addOreDictionary("oreTitan"));

        RecipeArcOven.addRecipe(new RecipeArcOven.RecipePattern(new ItemStack(Items.APPLE), new ItemStack(Blocks.LEAVES)));
    }

}
