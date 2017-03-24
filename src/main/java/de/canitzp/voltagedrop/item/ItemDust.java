package de.canitzp.voltagedrop.item;

import de.canitzp.ctpcore.base.ItemColored;
import de.canitzp.ctpcore.render.RenderingRegistry;
import de.canitzp.voltagedrop.VoltageDrop;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author canitzp
 */
public class ItemDust extends ItemColored {

    private static final String PREFIX = I18n.format("prefix.voltagedrop:dust");

    private String name;

    public ItemDust(String name, int color) {
        super(new ResourceLocation(VoltageDrop.MODID, "dust_" + name), color);
        this.setCreativeTab(VoltageDrop.tab);
        this.name = name;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerRenderer(){
        RenderingRegistry.addRenderer(this, new ModelResourceLocation(new ResourceLocation(VoltageDrop.MODID, "dust"), "inventory"));
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack){
        return I18n.format("ore.voltagedrop:" + this.name) + " " + PREFIX;
    }

}
