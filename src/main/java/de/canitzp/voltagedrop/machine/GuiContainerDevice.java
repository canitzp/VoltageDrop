package de.canitzp.voltagedrop.machine;

import de.canitzp.ctpcore.base.TileEntityBase;
import de.canitzp.ctpcore.inventory.ContainerBase;
import de.canitzp.ctpcore.inventory.GuiContainerBase;
import de.canitzp.voltagedrop.VoltageDrop;
import de.canitzp.voltagedrop.capabilities.Capabilities;
import de.canitzp.voltagedrop.capabilities.IEnergyDevice;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

/**
 * @author canitzp
 */
public abstract class GuiContainerDevice<T extends TileEntityBase> extends GuiContainerBase<T>{

    public static final ResourceLocation BAR_LOC = new ResourceLocation(VoltageDrop.MODID, "textures/gui/device_bar.png");

    public GuiContainerDevice(ContainerBase container){
        super(container);
    }

    public void drawEnergyBar(int x, int y, float current, float max){
        this.bindTexture(BAR_LOC);
        this.drawTexturedModalRect(this.guiLeft + x, this.guiTop + y, 0, 0, 25, 71);
        int scale = getScale(current, max, 69);
        this.drawTexturedModalRect(this.guiLeft + x + 1, this.guiTop + y + 69 - scale + 1, 0, 140 - scale, 23, 69);
    }

    protected void drawEnergyBar(int x, int y){
        if(this.tile.hasCapability(Capabilities.ENERGY, EnumFacing.NORTH)){
            IEnergyDevice device = this.tile.getCapability(Capabilities.ENERGY, EnumFacing.NORTH);
            if(device != null){
                this.drawEnergyBar(x, y, device.getStored(), device.getMaxStoreable());
            }
        }
    }

    protected int getScale(float current, float max, int height){
        return max > 0 ? Math.round(current * height / max) : 0;
    }

    protected void drawHorizontalBar(int x, int y, int textX, int textY, int width, int scale){
        this.drawTexturedModalRect(this.guiLeft + x, this.guiTop + y, textX, textY, width, scale);
    }

}
