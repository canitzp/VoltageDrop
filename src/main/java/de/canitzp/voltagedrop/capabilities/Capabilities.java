package de.canitzp.voltagedrop.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import java.util.concurrent.Callable;

/**
 * @author canitzp
 */
public class Capabilities{

    @CapabilityInject(IEnergyDevice.class)
    public static Capability<IEnergyDevice> ENERGY = null;

    public static void init(){
        CapabilityManager.INSTANCE.register(IEnergyDevice.class, new Capability.IStorage<IEnergyDevice>(){
            @Override
            public NBTBase writeNBT(Capability<IEnergyDevice> capability, IEnergyDevice instance, EnumFacing side){
                return new NBTTagFloat(instance.getSavedCurrentPerHour());
            }

            @Override
            public void readNBT(Capability<IEnergyDevice> capability, IEnergyDevice instance, EnumFacing side, NBTBase nbt){
                instance.setSavedCurrentPerHour(((NBTTagFloat)nbt).getFloat());
            }
        }, new Callable<IEnergyDevice>(){
            @Override
            public IEnergyDevice call() throws Exception{
                return new EnergyDevice(230, 0);
            }
        });
    }

}
