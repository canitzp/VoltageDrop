package de.canitzp.voltagedrop.render;

import de.canitzp.voltagedrop.tile.TileEntityDevice;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.math.BlockPos;

/**
 * @author canitzp
 */
public interface IInfoRender{

    void render(FontRenderer font, ScaledResolution res, WorldClient world, EntityPlayerSP player, BlockPos pos, IBlockState state, TileEntityDevice tile);
}
