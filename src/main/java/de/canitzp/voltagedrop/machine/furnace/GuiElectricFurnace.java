package de.canitzp.voltagedrop.machine.furnace;

import de.canitzp.ctpcore.inventory.GuiContainerBase;
import de.canitzp.voltagedrop.VoltageDrop;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

/**
 * @author canitzp
 */
public class GuiElectricFurnace extends GuiContainerBase{

    public static final ResourceLocation RESOURCE_LOCATION = new ResourceLocation(VoltageDrop.MODID, "textures/gui/gui_electric_furnace.png");

    public GuiElectricFurnace(EntityPlayer player, int x, int y, int z){
        super(new ContainerElectricFurnace(player, x, y, z));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
        this.drawBackgroundLocation(RESOURCE_LOCATION);
    }

}
