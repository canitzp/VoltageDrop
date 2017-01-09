package de.canitzp.voltagedrop;

import de.canitzp.ctpcore.base.BlockContainerBase;
import de.canitzp.ctpcore.registry.MCRegistry;
import de.canitzp.voltagedrop.machine.batterymains.BlockBatteryMains;
import de.canitzp.voltagedrop.machine.solidgenerator.BlockSolidGenerator;
import de.canitzp.voltagedrop.tile.TileEntityDevice;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

/**
 * @author canitzp
 */
public class Registry{

    public static void preInit(){
        MCRegistry.register(new BlockSolidGenerator());
        MCRegistry.register(new BlockBatteryMains());
    }

}
