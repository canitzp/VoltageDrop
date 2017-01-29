package de.canitzp.voltagedrop.machine.upgrade;

import de.canitzp.ctpcore.base.ItemBase;
import de.canitzp.voltagedrop.tile.TileEntityDevice;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author canitzp
 */
public interface IUpgrade<E extends IUpgrade> extends INBTSerializable<NBTTagCompound>{

    float getX();

    float getY();

    EnumFacing getSide();

    float getScale();

    E setX(float x);

    E setY(float y);

    E setSide(EnumFacing side);

    String getName();

    ItemBase getItem();

    @SideOnly(Side.CLIENT)
    default void renderSpecial(TileEntityDevice tile, double x, double y, double z){}

}
