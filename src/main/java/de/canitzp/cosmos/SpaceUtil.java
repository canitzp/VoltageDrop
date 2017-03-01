package de.canitzp.cosmos;

import de.canitzp.cosmos.space.Galaxy;
import de.canitzp.cosmos.space.Planet;
import de.canitzp.cosmos.space.SpaceObject;
import de.canitzp.cosmos.space.StarSystem;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author canitzp
 */
public class SpaceUtil {

    private static List<Planet> allPlanets;
    private static List<StarSystem> allStarSystems;

    public static <T extends SpaceObject> T getByName(ResourceLocation name){
        for(SpaceObject spaceObject : SpaceObject.REGISTRY.values()){
            if(spaceObject.getRegisterName().equals(name)){
                return (T) spaceObject;
            }
        }
        return null;
    }

    private static <T extends SpaceObject> List<T> getAll(Class<T> clazz){
        List<T> list = new ArrayList<>();
        for(SpaceObject spaceObject : SpaceObject.REGISTRY.values()){
            if(spaceObject.getClass().isAssignableFrom(clazz)){
                list.add((T) spaceObject);
            }
        }
        return list;
    }

    public static List<Planet> getAllPlanets(){
        if(allPlanets == null){
            allPlanets = getAll(Planet.class);
        }
        return allPlanets;
    }

    public static List<StarSystem> getAllStarSystems(){
        if(allStarSystems == null){
            allStarSystems = getAll(StarSystem.class);
        }
        return allStarSystems;
    }

    public static List<Planet> getPlanets(StarSystem starSystem){
        List<Planet> planets = new ArrayList<>();
        for(Planet planet : getAllPlanets()){
            if(planet.getStarSystem().equals(starSystem)){
                planets.add(planet);
            }
        }
        return planets;
    }

    public static List<StarSystem> getStarSystem(Galaxy galaxy){
        List<StarSystem> starSystems = new ArrayList<>();
        for(StarSystem system : getAllStarSystems()){
            if(system.getGalaxy().equals(galaxy)){
                starSystems.add(system);
            }
        }
        return starSystems;
    }


}
