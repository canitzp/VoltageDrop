package de.canitzp.cosmos.machine.spaceeye;

import de.canitzp.cosmos.Cosmos;
import de.canitzp.cosmos.Satellite;
import de.canitzp.cosmos.data.SpaceData;
import de.canitzp.ctpcore.inventory.GuiContainerBase;
import de.canitzp.ctpcore.util.GuiUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.GuiScrollingList;
import org.lwjgl.input.Mouse;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author canitzp
 */
public class GuiSpaceEye extends GuiContainerBase<TileSpaceEye> {

    public static final ResourceLocation guiLoc = new ResourceLocation(Cosmos.MODID, "textures/gui/gui_space_eye.png");

    private List<IElement> elements = new ArrayList<>();
    private int activeElement = -1;
    public Info scrollInfo = new Info(this);

    public GuiSpaceEye(EntityPlayer player, int x,int y, int z) {
        super(new ContainerSpaceEye(player, x, y, z));
        this.xSize = 200;
        this.ySize = 166;
    }

    @Override
    public void initGui() {
        super.initGui();
        List<Satellite> satellites = SpaceData.satellites;
        for (int i = 0; i < satellites.size(); i++) {
            elements.add(new SatelliteElement(satellites.get(i), this.guiLeft + 7, this.guiTop + i * 7 + 7));
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        clearColor();
        drawBackgroundLocation(guiLoc);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        if(scrollInfo != null)
            scrollInfo.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void handleMouseInput() throws IOException {
        int mouseX = Mouse.getEventX() * this.width / this.mc.displayWidth;
        int mouseY = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
        super.handleMouseInput();
        if(scrollInfo != null)
            scrollInfo.handleMouseInput(mouseX, mouseY);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if(GuiUtil.isMouseInRange(mouseX, mouseY, this.guiLeft,this.guiTop, 7, 7, 52, 150)){

        }
    }

    public abstract class IElement{
        public int x, y;
        abstract @Nonnull String getFirstLine();
        abstract @Nonnull List<String> getDescription();
        public IElement(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    public class SatelliteElement extends IElement{

        public Satellite satellite;

        public SatelliteElement(Satellite satellite, int x, int y){
            super(x, y);
            this.satellite = satellite;
        }

        @Nonnull
        @Override
        public String getFirstLine(){
            return TextFormatting.BLACK + this.satellite.getName();
        }

        @Nonnull
        @Override
        public List<String> getDescription() {
            return new ArrayList<>();
        }
    }

    public class Info extends GuiScrollingList{

        public Info(GuiSpaceEye gui) {
            super(Minecraft.getMinecraft(), 52, 150, gui.getGuiLeft() + 7, gui.getGuiTop() + 156, gui.getGuiLeft() + 58, 10, gui.getXSize(), gui.getYSize());
        }

        @Override
        protected int getSize() {
            return elements.size();
        }

        @Override
        protected void elementClicked(int index, boolean doubleClick) {

        }

        @Override
        protected boolean isSelected(int index) {
            return false;
        }

        @Override
        protected void drawBackground() {

        }

        @Override
        protected void drawSlot(int slotIdx, int entryRight, int slotTop, int slotBuffer, Tessellator tess) {
            for (int i = 0; i < elements.size(); i++) {
                IElement element = elements.get(i);
                GuiSpaceEye.this.drawTexturedModalRect(GuiSpaceEye.this.guiLeft + 7, GuiSpaceEye.this.guiTop + i * 7 + 7, 0, i == activeElement ? 175 : 166, 52, 10);
                //GlStateManager.pushAttrib();
                //GlStateManager.pushMatrix();
                //GlStateManager.translate(element.x + 1.5, element.y + 1.5, GuiSpaceEye.this.zLevel);
                //GlStateManager.scale(0.9, 0.9, 0.9);
                //GuiSpaceEye.this.mc.fontRendererObj.drawString(element.getFirstLine(), 0, 0, 0xFFFFFFFF);
                //GlStateManager.popMatrix();
                //GlStateManager.popAttrib();
            }
        }
    }
}
