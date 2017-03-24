package de.canitzp.voltagedrop.machine.arcoven;

import de.canitzp.voltagedrop.machine.BlockEnergyDevice;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;

/**
 * @author canitzp
 */
public class BlockArcOven extends BlockEnergyDevice{

    public static final PropertyBool ACTIVE = PropertyBool.create("active");

    public BlockArcOven(){
        super(Material.IRON, "arc_oven", TileArcOven.class);
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
