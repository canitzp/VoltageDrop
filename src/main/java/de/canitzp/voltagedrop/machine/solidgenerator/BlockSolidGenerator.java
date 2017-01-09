package de.canitzp.voltagedrop.machine.solidgenerator;

import de.canitzp.ctpcore.base.BlockContainerBase;
import de.canitzp.ctpcore.base.TileEntityBase;
import de.canitzp.voltagedrop.VoltageDrop;
import de.canitzp.voltagedrop.machine.BlockEnergyDevice;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

/**
 * @author canitzp
 */
public class BlockSolidGenerator extends BlockEnergyDevice{

    public BlockSolidGenerator(){
        super(Material.IRON, new ResourceLocation(VoltageDrop.MODID, "solid_generator"), TileSolidGenerator.class);
    }

}
