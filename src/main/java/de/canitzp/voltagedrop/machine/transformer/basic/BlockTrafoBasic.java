package de.canitzp.voltagedrop.machine.transformer.basic;

import de.canitzp.voltagedrop.Values;
import de.canitzp.voltagedrop.machine.transformer.BlockTransformer;
import de.canitzp.voltagedrop.machine.transformer.TileTransformer;
import net.minecraft.block.material.Material;

/**
 * @author canitzp
 */
public class BlockTrafoBasic extends BlockTransformer{

    public BlockTrafoBasic(){
        super(Material.WOOD, "basic", Tile.class);
    }

    public static class Tile extends TileTransformer{
        public Tile(){
            super(Values.TRANSFORMER_BASIC_IN_VOLTAGE, Values.TRANSFORMER_BASIC_OUT_VOLTAGE);
        }
    }

}
