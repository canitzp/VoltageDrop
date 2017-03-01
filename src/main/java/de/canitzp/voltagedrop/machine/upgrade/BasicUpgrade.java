package de.canitzp.voltagedrop.machine.upgrade;

import de.canitzp.voltagedrop.tile.TileEntityDevice;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

/**
 * @author canitzp
 */
public class BasicUpgrade implements IUpgrade<BasicUpgrade>{

    public EnumFacing side = EnumFacing.NORTH;
    public float x, y;
    public String name;
    public ItemStack item;

    public BasicUpgrade(ItemStack item){
        this.name = item.getItem().getRegistryName().getResourceDomain() + "_upgrade";
        this.item = item;
    }

    @Override
    public float getX(){
        return this.x;
    }

    @Override
    public float getY(){
        return this.y;
    }

    @Override
    public EnumFacing getSide(){
        return this.side;
    }

    @Override
    public float getScale(){
        return 3F/16F;
    }

    @Override
    public BasicUpgrade setX(float x){
        this.x = x;
        return this;
    }

    @Override
    public BasicUpgrade setY(float y){
        this.y = y;
        return this;
    }

    @Override
    public BasicUpgrade setSide(EnumFacing side){
        this.side = side;
        return this;
    }

    @Override
    public String getName(){
        return this.name;
    }

    @Override
    public ItemStack getRenderStack(TileEntityDevice tile){
        return item;
    }

    @Override
    public NBTTagCompound serializeNBT(){
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setFloat("x", this.getX());
        nbt.setFloat("y", this.getY());
        nbt.setInteger("side", this.getSide().ordinal());
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt){
        this.setX(nbt.getFloat("x"));
        this.setY(nbt.getFloat("y"));
        this.setSide(EnumFacing.values()[nbt.getInteger("side")]);
    }
}
