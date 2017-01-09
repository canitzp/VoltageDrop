package de.canitzp.voltagedrop.render;

import de.canitzp.voltagedrop.tile.TileEntityDevice;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author canitzp
 */
@Mod.EventBusSubscriber
public class OverlayRenderer{

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onGameRender(RenderGameOverlayEvent.Post event){
        Minecraft minecraft = Minecraft.getMinecraft();
        if(minecraft.currentScreen == null){
            if(minecraft.objectMouseOver.typeOfHit.equals(RayTraceResult.Type.BLOCK)){
                BlockPos pos = minecraft.objectMouseOver.getBlockPos();
                IBlockState state = minecraft.world.getBlockState(pos);
                if(state.getBlock() instanceof IInfoRender){
                    TileEntity tile = minecraft.world.getTileEntity(pos);
                    if(tile != null && tile instanceof TileEntityDevice){
                        ((IInfoRender) state.getBlock()).render(minecraft.fontRendererObj, event.getResolution(), minecraft.world, minecraft.player, pos, state, (TileEntityDevice) tile);
                    }
                }
            }
        }
    }

}
