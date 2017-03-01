package de.canitzp.cosmos.space;

import de.canitzp.cosmos.Cosmos;
import net.minecraft.util.ResourceLocation;

/**
 * @author canitzp
 */
public class Group extends SpaceObject{

    public static final Group LOCAL_GROUP = new Group(new ResourceLocation(Cosmos.MODID, "local_group"));

    public Group(ResourceLocation name) {
        super(new ResourceLocation(name.getResourceDomain(), "group:" + name.getResourcePath()));
    }

}
