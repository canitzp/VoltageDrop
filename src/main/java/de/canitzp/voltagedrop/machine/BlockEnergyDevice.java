package de.canitzp.voltagedrop.machine;

import de.canitzp.ctpcore.base.BlockContainerBase;
import de.canitzp.ctpcore.base.TileEntityBase;
import de.canitzp.voltagedrop.tile.TileEntityDevice;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * @author canitzp
 */
public class BlockEnergyDevice extends BlockContainerBase{

    public BlockEnergyDevice(Material material, ResourceLocation resource, Class<? extends TileEntityBase> tileClass){
        super(material, resource, tileClass);
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos neighbor){
        TileEntity tile = world.getTileEntity(pos);
        if(tile != null && tile instanceof TileEntityDevice){
            ((TileEntityDevice) tile).onBlockUpdate();
        }
        super.neighborChanged(state, world, pos, blockIn, neighbor);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
        TileEntity tile = world.getTileEntity(pos);
        if(tile != null && tile instanceof TileEntityDevice){
            ((TileEntityDevice) tile).onBlockUpdate();
        }
        super.onBlockPlacedBy(world, pos, state, placer, stack);
    }
}
