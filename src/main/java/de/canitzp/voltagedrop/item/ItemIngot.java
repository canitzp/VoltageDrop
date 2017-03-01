package de.canitzp.voltagedrop.item;

import de.canitzp.ctpcore.base.ItemColored;
import de.canitzp.ctpcore.render.RenderingRegistry;
import de.canitzp.voltagedrop.VoltageDrop;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author canitzp
 */
public class ItemIngot extends ItemColored{

    private static final String PREFIX = I18n.translateToLocal("prefix.voltagedrop:ingot");

    private String name;

    public ItemIngot(String name, int color){
        super(new ResourceLocation(VoltageDrop.MODID, "ingot_" + name), color);
        this.setCreativeTab(VoltageDrop.tab);
        this.name = name;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerRenderer(){
        RenderingRegistry.addRenderer(this, new ModelResourceLocation(new ResourceLocation(VoltageDrop.MODID, "ingot"), "inventory"));
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack){
        return PREFIX + " " + I18n.translateToLocal("ore.voltagedrop:" + this.name);
    }
}
