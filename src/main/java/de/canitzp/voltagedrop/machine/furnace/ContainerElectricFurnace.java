package de.canitzp.voltagedrop.machine.furnace;

import de.canitzp.ctpcore.base.TileEntityBase;
import de.canitzp.ctpcore.inventory.ContainerBase;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @author canitzp
 */
public class ContainerElectricFurnace extends ContainerBase{

    public ContainerElectricFurnace(EntityPlayer player, int x, int y, int z){
        super(player, x, y, z);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn){
        return true;
    }
}
