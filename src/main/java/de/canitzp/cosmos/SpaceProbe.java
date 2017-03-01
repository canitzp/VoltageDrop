package de.canitzp.cosmos;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

/**
 * @author canitzp
 */
public class SpaceProbe implements INBTSerializable<NBTTagCompound>{

    private String name;

    public SpaceProbe(){}

    public SpaceProbe(NBTTagCompound nbt){
        this.name = nbt.getString("Name");
    }

    public NBTTagCompound getRawData(){
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("Name", this.getName());
        return nbt;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return new NBTTagCompound();
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {

    }

    public String getName() {
        return name;
    }
}
