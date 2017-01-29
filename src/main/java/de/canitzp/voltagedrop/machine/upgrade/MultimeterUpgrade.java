package de.canitzp.voltagedrop.machine.upgrade;

import de.canitzp.ctpcore.base.ItemBase;
import de.canitzp.voltagedrop.tile.TileEntityDevice;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

/**
 * @author canitzp
 */
public class MultimeterUpgrade extends BasicUpgrade{

    public MultimeterUpgrade(ItemBase item){
        super(item);
    }

    @Override
    public void renderSpecial(TileEntityDevice tile, double x, double y, double z){
        //TODO fix this shit
        float scale = 90F/tile.getDeviceForSide(side).getMaxStoreable()*tile.getDeviceForSide(side).getStored();
        Tessellator tessy = Tessellator.getInstance();
        VertexBuffer buffer = tessy.getBuffer();
        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.translate(x, y, z);
        GlStateManager.rotate(scale, 1, 0, 0);
        buffer.begin(1, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos(1.025, 0.175F, -0.875).color(1.0F, 0.5F, 0.5F, 1F).endVertex();
        buffer.pos(1.025, 0.06, -0.875).color(1.0F, 0.5F, 0.5F, 1F).endVertex();
        tessy.draw();
        GlStateManager.enableBlend();
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
    }

    @Override
    public float getX(){
        return 0.375F;
    }

    @Override
    public float getY(){
        return 0.375F;
    }

    @Override
    public float getScale(){
        return 3.5F/16F;
    }
}
