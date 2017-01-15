package de.canitzp.voltagedrop.machine;

import de.canitzp.ctpcore.base.BlockContainerBase;
import de.canitzp.ctpcore.base.TileEntityBase;
import de.canitzp.voltagedrop.VoltageDrop;
import de.canitzp.voltagedrop.capabilities.EnergyDeviceUtil;
import de.canitzp.voltagedrop.render.IInfoRender;
import de.canitzp.voltagedrop.tile.TileEntityDevice;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * @author canitzp
 */
public abstract class BlockEnergyDevice extends BlockContainerBase implements IInfoRender{

    public BlockEnergyDevice(Material material, String name, Class<? extends TileEntityBase> tileClass){
        super(material, new ResourceLocation(VoltageDrop.MODID, name), tileClass);
        this.setCreativeTab(VoltageDrop.tab);
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos neighbor){
        TileEntity tile = world.getTileEntity(pos);
        if(tile != null && tile instanceof TileEntityDevice){
            ((TileEntityDevice) tile).onBlockUpdate();
        }
        super.neighborChanged(state, world, pos, blockIn, neighbor);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
        TileEntity tile = world.getTileEntity(pos);
        if(tile != null && tile instanceof TileEntityDevice){
            ((TileEntityDevice) tile).onBlockPlaced();
            ((TileEntityDevice) tile).onBlockUpdate();
        }
        super.onBlockPlacedBy(world, pos, state, placer, stack);
    }

    @Override
    public void render(FontRenderer font, ScaledResolution res, WorldClient world, EntityPlayerSP player, BlockPos pos, IBlockState state, TileEntityDevice tile){
        if(shouldRenderOverlay() && tile.sidedEnergyDevice != null){
            String text = EnergyDeviceUtil.getDisplayable(tile.sidedEnergyDevice.getDeviceForSide(Minecraft.getMinecraft().objectMouseOver.sideHit));
            float x = res.getScaledWidth() / 2 - font.getStringWidth(text) / 2;
            float y = res.getScaledHeight() / 2 + 10;
            font.drawStringWithShadow(text, x, y, 0xFFFFFF);
        }
    }

    /**
     * For wrenching or something like it
     * @param world
     * @param pos
     * @param state
     * @param changer
     */
    public void blockChange(World world, BlockPos pos, IBlockState state, @Nullable EntityPlayer changer){
        TileEntity tile = world.getTileEntity(pos);
        if(tile != null && tile instanceof TileEntityDevice){
            ((TileEntityDevice) tile).onBlockUpdate();
        }
    }

    protected abstract boolean shouldRenderOverlay();

}
