package de.canitzp.voltagedrop.capabilities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

import java.lang.reflect.InvocationTargetException;

/**
 * @author canitzp
 */
public class SidedEnergyDevice<T extends IEnergyDevice>{

    private T[] sidedDevices = (T[]) new IEnergyDevice[6];

    public SidedEnergyDevice add(T device, EnumFacing side){
        this.sidedDevices[side.ordinal()] = device;
        return this;
    }

    public T getDeviceForSide(EnumFacing side){
        return side != null ? this.sidedDevices[side.ordinal()] : null;
    }

    public boolean canStoreCurrent(float current, EnumFacing side){
        T device = getDeviceForSide(side);
        return device != null && device.getSavedCurrentPerHour() + current <= device.getMaxCurrent();
    }

    public static <T extends IEnergyDevice> SidedEnergyDevice<T> createSidedEmpty(Class<T> deviceClass, float voltage, float maxSavableCurrent){
        SidedEnergyDevice<T> device = new SidedEnergyDevice<T>();
        T energyDevice = null;
        try{
            energyDevice = deviceClass.getConstructor(float.class, float.class).newInstance(voltage, maxSavableCurrent);
        } catch(InstantiationException e){
            e.printStackTrace();
        } catch(IllegalAccessException e){
            e.printStackTrace();
        } catch(InvocationTargetException e){
            e.printStackTrace();
        } catch(NoSuchMethodException e){
            e.printStackTrace();
        }
        for(EnumFacing side : EnumFacing.values()){
            device.add(energyDevice, side);
        }
        return device;
    }

    public static <T extends IEnergyDevice> SidedEnergyDevice<T> createSingleEmpty(Class<T> deviceClass, float voltage, float maxSavableCurrent){
        SidedEnergyDevice<T> device = new SidedEnergyDevice<T>();
        T energyDevice = null;
        try{
            energyDevice = deviceClass.getConstructor(float.class, float.class).newInstance(voltage, maxSavableCurrent);
        } catch(InstantiationException e){
            e.printStackTrace();
        } catch(IllegalAccessException e){
            e.printStackTrace();
        } catch(InvocationTargetException e){
            e.printStackTrace();
        } catch(NoSuchMethodException e){
            e.printStackTrace();
        }
        for(EnumFacing side : EnumFacing.values()){
            device.add(energyDevice, side);
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

    public void read(NBTTagCompound nbt){
        for(EnumFacing side : EnumFacing.values()){
            if(nbt.hasKey(side.getName())){
                try{
                    Class<T> deviceClass = (Class<T>) Class.forName(nbt.getString(side.getName() + "Class"));
                    T device = deviceClass.newInstance();
                    device.deserializeNBT((NBTTagCompound) nbt.getTag(side.getName()));
                    add(device, side);
                } catch(ClassNotFoundException e){
                    e.printStackTrace();
                } catch(IllegalAccessException e){
                    e.printStackTrace();
                } catch(InstantiationException e){
                    e.printStackTrace();
                }
            }
        }
    }


    public NBTTagCompound sync(NBTTagCompound nbt){
        for(EnumFacing side : EnumFacing.values()){
            T device = getDeviceForSide(side);
            if(device != null){
                nbt.setFloat(side.getName(), getDeviceForSide(side).getSavedCurrentPerHour());
            }
        }
        return nbt;
    }

    public void getSync(NBTTagCompound nbt){
        for(EnumFacing side : EnumFacing.values()){
            if(nbt.hasKey(side.getName())){
                T device = getDeviceForSide(side);
                if(device != null){
                    device.setSavedCurrentPerHour(nbt.getFloat(side.getName()));
                }
            }
        }
    }

}
