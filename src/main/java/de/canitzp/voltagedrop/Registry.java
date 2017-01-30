package de.canitzp.voltagedrop;

import de.canitzp.ctpcore.base.ItemBase;
import de.canitzp.ctpcore.registry.MCRegistry;
import de.canitzp.voltagedrop.api.recipe.Recipes;
import de.canitzp.voltagedrop.block.BlockWeightedOre;
import de.canitzp.voltagedrop.machine.arcoven.BlockArcOven;
import de.canitzp.voltagedrop.machine.battery.batterymains.BlockBatteryMains;
import de.canitzp.voltagedrop.machine.furnace.BlockElectricFurnace;
import de.canitzp.voltagedrop.machine.furnace.TileElectricFurnace;
import de.canitzp.voltagedrop.machine.photovoltaicspanel.BlockPhotovoltaic;
import de.canitzp.voltagedrop.machine.solidgenerator.BlockSolidGenerator;
import de.canitzp.voltagedrop.machine.transformer.BlockTransformer;
import de.canitzp.voltagedrop.machine.transformer.basic.BlockTrafoBasic;
import de.canitzp.voltagedrop.machine.upgrade.MultimeterUpgrade;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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
        MCRegistry.register(orePendlandite = new BlockWeightedOre("pendlandite").spawnInWorld(8, 60, 0, 6, 0).addOreDictionary("oreNickel"));
        MCRegistry.register(oreSulfur = new BlockWeightedOre("sulfur").spawnInWorld(6, 40, 0, 10, 0).addOreDictionary("oreSulfur"));
        MCRegistry.register(oreDolomite = new BlockWeightedOre("dolomite").spawnInWorld(10, 75, 15, 5, 0).addOreDictionary("oreMagnesium"));
        MCRegistry.register(oreIlmenite = new BlockWeightedOre("ilmenite").spawnInWorld(4, 25, 0, 4, 0).addOreDictionary("oreTitan"));

        MCRegistry.register(multimeter = new ItemBase(new ResourceLocation(VoltageDrop.MODID, "multimeter")){
            @Override
            public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
                if(!world.isRemote){
                    TileEntity tile = world.getTileEntity(pos);
                    if(tile != null && tile instanceof TileElectricFurnace){
                        boolean b = ((TileElectricFurnace) tile).installUpgrade(new MultimeterUpgrade(multimeter).setSide(facing));
                        return b ? EnumActionResult.SUCCESS : EnumActionResult.PASS;
                    }
                }
                return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
            }
        });

        MCRegistry.register(new Recipes.ArcOven(new ItemStack(Items.APPLE), new ItemStack(Blocks.LEAVES)));
    }

}
