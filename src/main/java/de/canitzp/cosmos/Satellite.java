package de.canitzp.cosmos;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

/**
 * @author canitzp
 */
public class Satellite implements INBTSerializable<NBTTagCompound> {

    private SpacePosition position;
    private String name;

    public Satellite(String name, SpacePosition position) {
        this.name = name;
        this.position = position;
    }

    public Satellite(NBTTagCompound nbt){
        this.name = nbt.getString("Name");
        this.position = new SpacePosition(nbt.getCompoundTag("Position"));
    }

    public NBTTagCompound getRawData(){
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("Name", this.name);
        nbt.setTag("Position", this.position.serializeNBT());
        return nbt;
    }

    public String getName() {
        return name;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return new NBTTagCompound();
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {

    }

    @Override
    public String toString() {
        return String.format("Satellite{name=%s, position=%s}", getName(), this.position.toString());
    }
}
