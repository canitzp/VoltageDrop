package de.canitzp.cosmos.space;

import de.canitzp.cosmos.Cosmos;
import net.minecraft.util.ResourceLocation;

/**
 * @author canitzp
 */
public class Star extends SpaceObject {

    public static final Star SUN = new Star(new ResourceLocation(Cosmos.MODID, "sun"));

    public Star(ResourceLocation name) {
        super(new ResourceLocation(name.getResourceDomain(), "star:" + name.getResourcePath()));
    }

}
