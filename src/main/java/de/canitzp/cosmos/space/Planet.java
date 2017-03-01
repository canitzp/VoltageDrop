package de.canitzp.cosmos.space;

import de.canitzp.cosmos.Cosmos;
import de.canitzp.cosmos.Util;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

/**
 * @author canitzp
 */
public class Planet extends SpaceObject {

    public static final Planet EARTH = new Planet(new ResourceLocation(Cosmos.MODID, "earth"), StarSystem.SOLAR_SYSTEM).setDistance(1D);

    private StarSystem starSystem;

    /**
     * The Unit is AU (Astronomical Unit)
     */
    private double distanceToSun = 0.5D;

    public Planet(ResourceLocation name, @Nonnull StarSystem starSystem) {
        super(new ResourceLocation(name.getResourceDomain(), "planet:" + name.getResourcePath()));
        this.starSystem = starSystem;
    }

    public Planet setDistanceKm(long kilometer){
        return setDistance(Util.kilometerToAstronomicalUnits(kilometer));
    }

    public Planet setDistance(double au){
        this.distanceToSun = au;
        return this;
    }

    @Nonnull
    public StarSystem getStarSystem() {
        return starSystem;
    }

    @Override
    public String toString() {
        return "Planet{name=" + this.getRegisterName().getResourcePath().substring(7) + "; distanceToSun=" + this.distanceToSun + "AU}";
    }

}
