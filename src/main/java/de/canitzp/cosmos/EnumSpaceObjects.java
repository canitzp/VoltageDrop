package de.canitzp.cosmos;

import de.canitzp.cosmos.space.*;
import de.canitzp.ctpcore.registry.MCRegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * @author canitzp
 */
public enum EnumSpaceObjects {

    /**
     * Groups:
     */
    LOCAL_GROUP(Group.LOCAL_GROUP),

    /**
     * Galaxies:
     */
    MILKYWAY(Galaxy.MILKYWAY),

    /**
     * Star Systems:
     */
    SOLAR_SYSTEM(StarSystem.SOLAR_SYSTEM),

    /**
     * Planets:
     *  -Solar System
     *
     */
    MERCURY(new Planet(new ResourceLocation(Cosmos.MODID, "mercury"), StarSystem.SOLAR_SYSTEM).setDistanceKm(57910000L)),
    VENUS(new Planet(new ResourceLocation(Cosmos.MODID, "venus"), StarSystem.SOLAR_SYSTEM).setDistanceKm(108200000L)),
    EARTH(Planet.EARTH), // This is the default Planet
    MARS(new Planet(new ResourceLocation(Cosmos.MODID, "mars"), StarSystem.SOLAR_SYSTEM).setDistanceKm(227900000L)),
    JUPITER(new Planet(new ResourceLocation(Cosmos.MODID, "jupiter"), StarSystem.SOLAR_SYSTEM).setDistanceKm(778500000L)),
    SATURN(new Planet(new ResourceLocation(Cosmos.MODID, "saturn"), StarSystem.SOLAR_SYSTEM).setDistanceKm(1430000000L)),
    URANUS(new Planet(new ResourceLocation(Cosmos.MODID, "uranus"), StarSystem.SOLAR_SYSTEM).setDistanceKm(2817000000L)),
    NEPTUNE(new Planet(new ResourceLocation(Cosmos.MODID, "neptune"), StarSystem.SOLAR_SYSTEM).setDistanceKm(4498000000L)),
    PLANET_X(new Planet(new ResourceLocation(Cosmos.MODID, "planet_x"), StarSystem.SOLAR_SYSTEM).setDistanceKm(4506163200000L)),

    ;

    private final SpaceObject spaceObject;
    private SpaceObject INSTANCE;

    EnumSpaceObjects(SpaceObject spaceObject) {
        this.spaceObject = spaceObject;
    }

    public static void preInit(FMLPreInitializationEvent event){
        for(EnumSpaceObjects enumSpaceObjects : EnumSpaceObjects.values()){
           enumSpaceObjects.INSTANCE =  MCRegistry.register(enumSpaceObjects.spaceObject);
        }
    }

    public SpaceObject getInstance(){
        return this.INSTANCE;
    }

    public <T extends SpaceObject> T getInstance(Class<T> spaceObjectType){
        if(this.INSTANCE.getClass().isAssignableFrom(spaceObjectType)){
            return (T) this.INSTANCE;
        } else {
            throw new IllegalArgumentException("The requested SpaceObject isn't a " + spaceObjectType.getName());
        }
    }
}
