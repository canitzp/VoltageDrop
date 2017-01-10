package de.canitzp.voltagedrop.machine.batterymains;

import de.canitzp.ctpcore.base.TileEntityBase;
import de.canitzp.voltagedrop.VoltageDrop;
import de.canitzp.voltagedrop.capabilities.EnergyDeviceUtil;
import de.canitzp.voltagedrop.machine.BlockEnergyDevice;
import de.canitzp.voltagedrop.render.IInfoRender;
import de.canitzp.voltagedrop.tile.TileEntityDevice;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

/**
 * @author canitzp
 */
public class BlockBatteryMains extends BlockEnergyDevice implements IInfoRender{

    public BlockBatteryMains(){
        super(Material.IRON, "battery_mains", TileBatteryMains.class);
    }

    @Override
    protected boolean shouldRenderOverlay(){
        return true;
    }

}
