package de.canitzp.voltagedrop.machine.solidgenerator;

import de.canitzp.ctpcore.base.BlockContainerBase;
import de.canitzp.ctpcore.base.TileEntityBase;
import de.canitzp.voltagedrop.VoltageDrop;
import de.canitzp.voltagedrop.capabilities.EnergyDeviceUtil;
import de.canitzp.voltagedrop.machine.BlockEnergyDevice;
import de.canitzp.voltagedrop.render.IInfoRender;
import de.canitzp.voltagedrop.tile.TileEntityDevice;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

/**
 * @author canitzp
 */
public class BlockSolidGenerator extends BlockEnergyDevice implements IInfoRender{

    public BlockSolidGenerator(){
        super(Material.IRON, "solid_generator", TileSolidGenerator.class);
    }

    @Override
    public void render(FontRenderer font, ScaledResolution res, WorldClient world, EntityPlayerSP player, BlockPos pos, IBlockState state, TileEntityDevice tile){
        String text = EnergyDeviceUtil.getDisplayable(tile.sidedEnergyDevice.getDeviceForSide(Minecraft.getMinecraft().objectMouseOver.sideHit));
        float x = res.getScaledWidth() / 2 - font.getStringWidth(text) / 2;
        float y = res.getScaledHeight() / 2 + 10;
        font.drawStringWithShadow(text, x, y, 0xFFFFFF);
    }

    @Override
    protected boolean shouldRenderOverlay(){
        return true;
    }

}
