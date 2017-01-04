package de.canitzp.voltagedrop.tile;

import de.canitzp.ctpcore.base.TileEntityBase;
import de.canitzp.voltagedrop.VoltageDrop;
import de.canitzp.voltagedrop.capabilities.Capabilities;
import de.canitzp.voltagedrop.capabilities.EnergyDevice;
import de.canitzp.voltagedrop.capabilities.IEnergyDevice;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

/**
 * @author canitzp
 */
public class TileEntityDevice extends TileEntityBase implements ITickable{

    private IEnergyDevice energyDevice;

    public TileEntityDevice(){
        super(new ResourceLocation(VoltageDrop.MODID, "testTile"));
        this.energyDevice = new EnergyDevice(4, 20.1F);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing){
        return this.getCapability(capability, facing) != null;
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing){
        if(capability.equals(Capabilities.ENERGY)){
            return (T) this.energyDevice;
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void update(){
        if(!world.isRemote){
            IEnergyDevice device = getCapability(Capabilities.ENERGY, EnumFacing.NORTH);
            if(device != null){
                System.out.println(device);
                device.receiveEnergy(2, 1, false);
            }
        }
    }
}
