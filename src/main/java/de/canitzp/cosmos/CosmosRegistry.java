package de.canitzp.cosmos;

import de.canitzp.cosmos.machine.spaceeye.BlockSpaceEye;
import de.canitzp.ctpcore.registry.MCRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * @author canitzp
 */
public class CosmosRegistry {

    public static BlockSpaceEye spaceEye;

    public static void preInit(FMLPreInitializationEvent event){
        MCRegistry.register(spaceEye = new BlockSpaceEye());
    }

}
