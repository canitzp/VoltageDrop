package de.canitzp.voltagedrop;

import de.canitzp.ctpcore.CTPCore;
import de.canitzp.voltagedrop.capabilities.Capabilities;
import de.canitzp.voltagedrop.render.TileUpgradeRenderer;
import de.canitzp.voltagedrop.tile.TileEntityDevice;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author canitzp
 */
@Mod(modid = VoltageDrop.MODID, name = VoltageDrop.MODNAME, version = VoltageDrop.VERSION)
public class VoltageDrop{

    public static final String MODID = "voltagedrop";
    public static final String MODNAME = "Voltage Drop";
    public static final String VERSION = "@VERSION@";

    public static final Logger LOGGER = LogManager.getLogger(MODNAME);

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
        LOGGER.info("[PreInitialization] Startup " + MODNAME);
        LOGGER.info("[PreInitialization] Registering Blocks, Items and TileEntities");
        Registry.preInit();
        LOGGER.info("[PreInitialization] Registering Capabilities");
        Capabilities.init();
        if(event.getSide().isClient()){
            LOGGER.info("[PreInitialization] Initialize Client");
            ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDevice.class, new TileUpgradeRenderer<>());
        }
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        LOGGER.info("[Initialization] Registering to CTP-Core");
        CTPCore.init(this, event);
    }

}
