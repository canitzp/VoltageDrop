package de.canitzp.cosmos.space;

import de.canitzp.cosmos.Cosmos;
import de.canitzp.ctpcore.registry.IRegistryEntry;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

/**
 * @author canitzp
 */
public class StarSystem extends SpaceObject {

    public static final StarSystem SOLAR_SYSTEM = new StarSystem(new ResourceLocation(Cosmos.MODID, "solar_system"), Star.SUN, Galaxy.MILKYWAY);

    @Nonnull
    private Galaxy galaxy;

    @Nonnull
    private Star star;

    public StarSystem(ResourceLocation name, @Nonnull Star star, @Nonnull Galaxy galaxy) {
        super(new ResourceLocation(name.getResourceDomain(), "system:" + name.getResourcePath()));
        this.galaxy = galaxy;
        this.star = star;
    }

    @Override
    public IRegistryEntry[] getRegisterElements() {
        return new IRegistryEntry[]{this, this.star};
    }

    @Nonnull
    public Galaxy getGalaxy() {
        return galaxy;
    }

}
