package de.canitzp.voltagedrop.machine;

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
public abstract class GuiContainerDevice extends GuiContainerBase{

    public static final ResourceLocation BAR_LOC = new ResourceLocation(VoltageDrop.MODID, "textures/gui/device_bar.png");

    public GuiContainerDevice(ContainerBase container){
        super(container);
    }

    public void drawEnergyBar(int x, int y, float current, float max){
        this.bindTexture(BAR_LOC);
        this.drawTexturedModalRect(this.guiLeft + x, this.guiTop + y, 0, 0, 25, 71);
        int scale = max > 0 ? Math.round(current * 69 / (max * 1.0F)) : 0;
        this.drawTexturedModalRect(this.guiLeft + x + 1, this.guiTop + y + 69 - scale + 1, 0, 140 - scale, 23, 69);
    }

    protected void drawEnergyBar(int x, int y){
        if(this.tile.hasCapability(Capabilities.ENERGY, EnumFacing.NORTH)){
            IEnergyDevice device = this.tile.getCapability(Capabilities.ENERGY, EnumFacing.NORTH);
            if(device != null){
                this.drawEnergyBar(x, y, device.getSavedCurrentPerHour(), device.getMaxCurrent());
            }
        }
    }

}
