package de.canitzp.cosmos.machine.spaceeye;

import de.canitzp.cosmos.Cosmos;
import de.canitzp.ctpcore.base.BlockContainerBase;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

/**
 * @author canitzp
 */
public class BlockSpaceEye extends BlockContainerBase {

    public BlockSpaceEye() {
        super(Material.IRON, new ResourceLocation(Cosmos.MODID, "space_eye"), TileSpaceEye.class);
        setCreativeTab(Cosmos.tab);
        addGuiContainer(Cosmos.instance, GuiSpaceEye.class, ContainerSpaceEye.class);
    }

}
