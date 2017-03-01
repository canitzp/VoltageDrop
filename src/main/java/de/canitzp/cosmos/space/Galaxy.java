package de.canitzp.cosmos.space;

import de.canitzp.cosmos.Cosmos;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

/**
 * @author canitzp
 */
public class Galaxy extends SpaceObject {

    public static final Galaxy MILKYWAY = new Galaxy(new ResourceLocation(Cosmos.MODID, "milkyway"), Group.LOCAL_GROUP);

    @Nonnull
    private Group group;

    public Galaxy(ResourceLocation name, @Nonnull Group group) {
        super(new ResourceLocation(name.getResourceDomain(), "galaxy:" + name.getResourcePath()));
        this.group = group;
    }

    @Nonnull
    public Group getGroup() {
        return group;
    }
}
