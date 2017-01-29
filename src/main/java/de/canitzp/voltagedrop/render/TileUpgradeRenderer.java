package de.canitzp.voltagedrop.render;

import de.canitzp.ctpcore.base.BlockContainerBase;
import de.canitzp.ctpcore.util.RenderUtil;
import de.canitzp.voltagedrop.machine.upgrade.IUpgrade;
import de.canitzp.voltagedrop.tile.TileEntityDevice;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * @author canitzp
 */
public class TileUpgradeRenderer<T extends TileEntityDevice> extends TileEntitySpecialRenderer<T>{

    @Override
    public void renderTileEntityAt(T tile, double x, double y, double z, float partialTicks, int destroyStage){
        IBlockState state = tile.getWorld().getBlockState(tile.getPos());
        List<IUpgrade> upgrades = tile.getInstalledUpgrades();
        for(IUpgrade upgrade : upgrades){
            GlStateManager.pushMatrix();
            upgrade.renderSpecial(tile, x, y, z);
            GlStateManager.translate((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
            RenderUtil.transformToFront(state.getValue(((BlockContainerBase) state.getBlock()).getFacing()), upgrade.getX(), upgrade.getY(), 0);
            GlStateManager.scale(upgrade.getScale(), upgrade.getScale(), upgrade.getScale());
            RenderUtil.renderItemInWorld(new ItemStack(upgrade.getItem()));
            GlStateManager.popMatrix();
        }
        super.renderTileEntityAt(tile, x, y, z, partialTicks, destroyStage);
    }

}
