package de.canitzp.voltagedrop.block;

import de.canitzp.ctpcore.base.BlockBase;
import de.canitzp.ctpcore.base.ItemBlockBase;
import de.canitzp.ctpcore.registry.IRegistryEntry;
import de.canitzp.ctpcore.registry.SmeltingRecipeProvider;
import de.canitzp.ctpcore.render.RenderingRegistry;
import de.canitzp.voltagedrop.VoltageDrop;
import de.canitzp.voltagedrop.api.recipe.Recipes;
import de.canitzp.voltagedrop.item.ItemDust;
import de.canitzp.voltagedrop.item.ItemIngot;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collections;
import java.util.List;

/**
 * @author canitzp
 */
public class BlockWeightedOre extends BlockBase<BlockWeightedOre> {

    public static final PropertyEnum<Weight> WEIGHT = PropertyEnum.create("weight", Weight.class);
    private static final String PREFIX = I18n.format("prefix.voltagedrop:ore");

    private ItemIngot ingot;
    private ItemDust dust;
    private ItemBlockBase itemBlock = new ItemBlockBase(this);
    private String name;

    public BlockWeightedOre(String name, int color){
        super(Material.ROCK, new ResourceLocation(VoltageDrop.MODID, "ore_".concat(name)));
        this.setCreativeTab(VoltageDrop.tab);
        this.setDefaultState(this.blockState.getBaseState().withProperty(WEIGHT, Weight.MEDIUM));
        this.ingot = new ItemIngot(name, color);
        this.dust = new ItemDust(name, color);
        this.name = name;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced){
        if(stack.hasTagCompound() && stack.getTagCompound().hasKey("OreWeight")){
            tooltip.add("Ore weight: " + Weight.values()[stack.getTagCompound().getInteger("OreWeight")].getFormattedName());
        }
        super.addInformation(stack, player, tooltip, advanced);
    }

    @Override
    public void spawnedAt(World world, BlockPos pos, int veinSize){
        IBlockState state = world.getBlockState(pos);
        Weight weight;
        int next = MathHelper.getInt(world.rand, 0, veinSize*2);
        // LLLLLMMMMMMMMMMMMMHH
        if(next <= veinSize/2){
            weight = Weight.LOW;
        } else if(next > veinSize/2 && next <= veinSize*1.8){
            weight = Weight.MEDIUM;
        } else {
            weight = Weight.HIGH;
        }
        world.setBlockState(pos, state.withProperty(WEIGHT, weight), 1 | 2 | 3);
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune){
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("OreWeight", state.getValue(WEIGHT).ordinal());
        List<ItemStack> stacks = super.getDrops(world, pos, state, fortune);
        ItemStack stack = stacks.get(0);
        stack.setTagCompound(nbt);
        return Collections.singletonList(stack);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
        if(stack.hasTagCompound() && stack.getTagCompound().hasKey("OreWeight")){
            world.setBlockState(pos, state.withProperty(WEIGHT, Weight.values()[stack.getTagCompound().getInteger("OreWeight")]));
        }
        super.onBlockPlacedBy(world, pos, state, placer, stack);
    }

    @Override
    protected BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, WEIGHT);
    }

    @Override
    public IBlockState getStateFromMeta(int meta){
        return this.getDefaultState().withProperty(WEIGHT, Weight.values()[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state){
        return state.getValue(WEIGHT).ordinal();
    }

    @Override
    public String getLocalizedName(){
        return I18n.format("ore.voltagedrop:" + this.name) + " " + PREFIX;
    }

    @Override
    public IRegistryEntry[] getRegisterElements(){
        return new IRegistryEntry[]{this, itemBlock, this.ingot, this.dust,
                new SmeltingRecipeProvider(new ItemStack(this.dust), new ItemStack(this.ingot)),
                new Recipes.ArcOven(new ItemStack(this.itemBlock), new ItemStack(this.dust))};
    }

    public ItemIngot getIngot(){
        return ingot;
    }

    public BlockWeightedOre customIngot(ItemIngot item){
        this.ingot = item;
        return this;
    }

    public enum Weight implements IStringSerializable{
        HIGH(TextFormatting.RED),
        MEDIUM(TextFormatting.GREEN),
        LOW(TextFormatting.BLUE);

        private TextFormatting color;
        Weight(TextFormatting color){
            this.color = color;
        }

        @Override
        public String getName(){
            return name().toLowerCase();
        }

        public String getFormattedName(){
            return color.toString() + name() + TextFormatting.RESET;
        }
    }

}
