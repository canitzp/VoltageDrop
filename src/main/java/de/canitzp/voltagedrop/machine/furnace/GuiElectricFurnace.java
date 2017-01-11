package de.canitzp.voltagedrop.machine.furnace;

import de.canitzp.ctpcore.inventory.GuiContainerBase;
import de.canitzp.voltagedrop.VoltageDrop;
import de.canitzp.voltagedrop.machine.GuiContainerDevice;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

/**
 * @author canitzp
 */
public class GuiElectricFurnace extends GuiContainerDevice{

    public static final ResourceLocation RESOURCE_LOCATION = new ResourceLocation(VoltageDrop.MODID, "textures/gui/electric_furnace.png");

    public GuiElectricFurnace(EntityPlayer player, int x, int y, int z){
        super(new ContainerElectricFurnace(player, x, y, z));
        this.xSize = 175;
        this.ySize = 165;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
        this.clearColor();
        this.drawBackgroundLocation(RESOURCE_LOCATION);
        this.drawEnergyBar(7, 7);
    }


}
