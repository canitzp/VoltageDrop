package de.canitzp.voltagedrop.capabilities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

import java.util.Arrays;

/**
 * @author canitzp
 */
public class SidedEnergyDevice<T extends IEnergyDevice>{

    private T[] sidedDevices = (T[]) new IEnergyDevice[6];
    private boolean unstable;

    public SidedEnergyDevice<T> add(T device, EnumFacing side){
        this.sidedDevices[side.ordinal()] = device;
        return this;
    }

    public SidedEnergyDevice<T> clear(){
        this.sidedDevices = (T[]) new IEnergyDevice[6];
        return this;
    }

    public T getDeviceForSide(EnumFacing side){
        return side != null ? this.sidedDevices[side.ordinal()] : null;
    }

    public boolean canStoreCurrent(float current, EnumFacing side){
        T device = getDeviceForSide(side);
        return canStoreEnergy(current, device);
    }

    public static boolean canStoreEnergy(float toStore, IEnergyDevice device){
        return device != null && device.getStored() + toStore <= device.getMaxStoreable();
    }

    public static float getStoreable(IEnergyDevice device){
        return device.getMaxStoreable() - device.getStored();
    }

    public static <T extends IEnergyDevice> SidedEnergyDevice<T> createSingleEmpty(Class<T> deviceClass, Voltages voltage, float maxSavableCurrent){
        SidedEnergyDevice<T> device = new SidedEnergyDevice<>();
        try{
            T energyDevice = deviceClass.getConstructor(Voltages.class, float.class).newInstance(voltage, maxSavableCurrent);
            for(EnumFacing side : EnumFacing.values()){
                device.add(energyDevice, side);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return device;
    }

    public NBTTagCompound write(NBTTagCompound nbt){
        for(EnumFacing side : EnumFacing.values()){
            T device = getDeviceForSide(side);
            if(device != null){
                nbt.setTag(side.getName(), device.serializeNBT());
                nbt.setString(side.getName() + "Class", device.getClass().getName());
            }
        }
        return nbt;
    }

    public void read(NBTTagCompound nbt, boolean forceNewClasses){
        for(EnumFacing side : EnumFacing.values()){
            if(nbt.hasKey(side.getName())){
                try{
                    T device;
                    if(forceNewClasses){
                        device = (T) Class.forName(nbt.getString(side.getName() + "Class")).newInstance();
                    } else {
                        device = getDeviceForSide(side);
                    }
                    if(device != null){
                        device.deserializeNBT((NBTTagCompound) nbt.getTag(side.getName()));
                    }
                    this.add(device, side);
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public NBTTagCompound sync(NBTTagCompound nbt){
        for(EnumFacing side : EnumFacing.values()){
            T device = getDeviceForSide(side);
            if(device != null){
                nbt.setFloat(side.getName(), getDeviceForSide(side).getStored());
            }
        }
        return nbt;
    }

    public void getSync(NBTTagCompound nbt){
        for(EnumFacing side : EnumFacing.values()){
            if(nbt.hasKey(side.getName())){
                T device = getDeviceForSide(side);
                if(device != null){
                    device.setStored(nbt.getFloat(side.getName()));
                }
            }
        }
    }

    public SidedEnergyDevice<T> merge(SidedEnergyDevice<T> device){
        for(int i = 0; i < device.sidedDevices.length; i++){
            T device1 = device.sidedDevices[i];
            if(device1 != null){
                this.sidedDevices[i].deserializeNBT(device1.serializeNBT());
            }
        }
        return this;
    }

    public SidedEnergyDevice<T> setUnstable(){
        this.unstable = true;
        return this;
    }

    public boolean isUnstable(){
        return this.unstable;
    }

    @Override
    public String toString(){
        return "SidedEnergyDevice{unstable = " + unstable + ", " + Arrays.toString(this.sidedDevices) + "}";
    }
}
