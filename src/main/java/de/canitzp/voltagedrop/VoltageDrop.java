package de.canitzp.voltagedrop;

import de.canitzp.ctpcore.inventory.CTPGuiHandler;
import de.canitzp.voltagedrop.capabilities.Capabilities;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * @author canitzp
 */
@Mod(modid = VoltageDrop.MODID, name = VoltageDrop.MODNAME, version = VoltageDrop.VERSION)
public class VoltageDrop{

    public static final String MODID = "voltagedrop";
    public static final String MODNAME = "Voltage Drop";
    public static final String VERSION = "@VERSION@";

    @Mod.Instance(MODID)
    public static VoltageDrop instance;

    public static CreativeTabs tab = new CreativeTabs(MODID){
        @Override
        public ItemStack getTabIconItem(){
            return new ItemStack(Registry.electricFurnace);
        }
    };

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        Registry.preInit();
        Capabilities.init();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        CTPGuiHandler.registerMod(this);
    }

}
