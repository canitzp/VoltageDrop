package de.canitzp.voltagedrop.machine.upgrade;

import de.canitzp.voltagedrop.tile.TileEntityDevice;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * @author canitzp
 */
public class MultimeterUpgrade extends BasicUpgrade{

    public MultimeterUpgrade(ItemStack item){
        super(item);
    }

    @Override
    public float getX(){
        return 0.375F;
    }

    @Override
    public float getY(){
        return 0.375F;
    }

    @Override
    public float getScale(){
        return 3.5F/16F;
    }

    @Override
    public ItemStack getRenderStack(TileEntityDevice tile){
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setLong("TilePosition", tile.getPos().toLong());
        nbt.setInteger("TileSide", this.side.ordinal());
        ItemStack stack = super.getRenderStack(tile);
        if(stack.hasTagCompound()){
            stack.getTagCompound().merge(nbt);
        } else {
            stack.setTagCompound(nbt);
        }
        return stack;
    }
}
