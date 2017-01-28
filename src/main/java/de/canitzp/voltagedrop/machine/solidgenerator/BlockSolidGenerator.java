package de.canitzp.voltagedrop.machine.solidgenerator;

import de.canitzp.voltagedrop.machine.BlockEnergyDevice;
import de.canitzp.voltagedrop.render.IInfoRender;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;

/**
 * @author canitzp
 */
public class BlockSolidGenerator extends BlockEnergyDevice implements IInfoRender{

    public static final PropertyBool ACTIVE = PropertyBool.create("active");

    public BlockSolidGenerator(){
        super(Material.IRON, "solid_generator", TileSolidGenerator.class);
        this.setDefaultState(this.blockState.getBaseState().withProperty(ACTIVE, false));
    }

    @Override
    protected boolean shouldRenderOverlay(){
        return true;
    }

    @Override
    protected BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, getFacing(), ACTIVE);
    }
}
