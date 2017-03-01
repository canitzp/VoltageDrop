package de.canitzp.voltagedrop.item;

import de.canitzp.ctpcore.base.ItemBase;
import de.canitzp.voltagedrop.VoltageDrop;
import de.canitzp.voltagedrop.capabilities.IEnergyDevice;
import de.canitzp.voltagedrop.machine.upgrade.MultimeterUpgrade;
import de.canitzp.voltagedrop.tile.TileEntityDevice;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

/**
 * @author canitzp
 */
public class ItemMultimeter extends ItemBase{

    public ItemMultimeter(){
        super(new ResourceLocation(VoltageDrop.MODID, "multimeter"));
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
        if(!world.isRemote){
            TileEntity tile = world.getTileEntity(pos);
            if(tile != null && tile instanceof TileEntityDevice){
                boolean b = ((TileEntityDevice) tile).installUpgrade(new MultimeterUpgrade(new ItemStack(this)).setSide(facing));
                return b ? EnumActionResult.SUCCESS : EnumActionResult.PASS;
            }
        }
        return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public void registerRenderer(){
        for(int i = 0; i < 12; i++){
            ModelBakery.registerItemVariants(this, new ModelResourceLocation(new ResourceLocation(VoltageDrop.MODID, "multimeter/multimeter_" + i), "inventory"));
        }
        ModelLoader.setCustomMeshDefinition(this, new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                if(stack.hasTagCompound() && stack.getTagCompound().hasKey("TilePosition", 4)){
                    TileEntity tile = Minecraft.getMinecraft().world.getTileEntity(BlockPos.fromLong(stack.getTagCompound().getLong("TilePosition")));
                    EnumFacing side = EnumFacing.values()[stack.getTagCompound().getInteger("TileSide")];
                    if(tile != null && tile instanceof TileEntityDevice){
                        IEnergyDevice device = ((TileEntityDevice) tile).getDeviceForSide(side);
                        if(device != null){
                            int scale = Math.round(11 * device.getStored() / device.getMaxStoreable());
                            return new ModelResourceLocation(new ResourceLocation(VoltageDrop.MODID, "multimeter/multimeter_" + scale), "inventory");
                        }
                    }
                }
                return new ModelResourceLocation(new ResourceLocation(VoltageDrop.MODID, "multimeter/multimeter_0"), "inventory");
            }
        });
    }
}
