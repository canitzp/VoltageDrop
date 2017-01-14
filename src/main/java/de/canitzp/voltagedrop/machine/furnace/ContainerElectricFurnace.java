package de.canitzp.voltagedrop.machine.furnace;

import de.canitzp.ctpcore.base.TileEntityBase;
import de.canitzp.ctpcore.inventory.ContainerBase;
import de.canitzp.ctpcore.inventory.slot.SlotOutput;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

/**
 * @author canitzp
 */
public class ContainerElectricFurnace extends ContainerBase<TileElectricFurnace>{

    public ContainerElectricFurnace(EntityPlayer player, int x, int y, int z){
        super(player, x, y, z);
        this.addPlayerInventorySlots(8, 84);
        this.addPlayerHotbarSlots(8, 142);
        IInventory inv = this.tile.inventory.getInv();
        this.addSlotToContainer(new Slot(inv, 0, 94, 18));
        this.addSlotToContainer(new SlotOutput(inv, 1, 94, 51));
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn){
        return true;
    }
}
