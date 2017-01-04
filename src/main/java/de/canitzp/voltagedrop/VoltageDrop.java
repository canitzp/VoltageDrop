package de.canitzp.voltagedrop;

import de.canitzp.voltagedrop.capabilities.Capabilities;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * @author canitzp
 */
@Mod(modid = VoltageDrop.MODID, name = VoltageDrop.MODNAME, version = VoltageDrop.VERSION)
public class VoltageDrop{

    public static final String MODID = "voltagedrop";
    public static final String MODNAME = "Voltage Drop";
    public static final String VERSION = "@VERSION@";

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        Registry.preInit();
        Capabilities.init();
    }

}
