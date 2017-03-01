package de.canitzp.cosmos;

import de.canitzp.cosmos.space.Galaxy;
import de.canitzp.cosmos.space.Group;
import de.canitzp.cosmos.space.Planet;
import de.canitzp.cosmos.space.StarSystem;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author canitzp
 */
public class SpacePosition implements INBTSerializable<NBTTagCompound>{

    @Nullable private Group currentGroup;
    @Nullable private Galaxy currentGalaxy;
    @Nullable private StarSystem currentStarSystem;
    @Nullable private Planet currentPlanet;

    public SpacePosition(){}

    public SpacePosition(@Nonnull Group group){
        this.setGroup(group);
    }

    public SpacePosition(@Nonnull Galaxy galaxy){
        this.setGalaxy(galaxy);
    }

    public SpacePosition(@Nonnull StarSystem starSystem){
        this.setStarSystem(starSystem);
    }

    public SpacePosition(@Nonnull Planet planet){
        this.setPlanet(planet);
    }

    public SpacePosition(@Nonnull NBTTagCompound nbt){this.deserializeNBT(nbt);}

    public SpacePosition setGroup(@Nonnull Group group){
        this.currentGroup = group;
        this.currentGalaxy = null;
        this.currentStarSystem = null;
        this.currentPlanet = null;
        return this;
    }

    public SpacePosition setGalaxy(@Nonnull Galaxy galaxy){
        this.setGroup(galaxy.getGroup());
        this.currentGalaxy = galaxy;
        this.currentStarSystem = null;
        this.currentPlanet = null;
        return this;
    }

    public SpacePosition setStarSystem(@Nonnull StarSystem starSystem){
        this.setGalaxy(starSystem.getGalaxy());
        this.currentStarSystem = starSystem;
        this.currentPlanet = null;
        return this;
    }

    public SpacePosition setPlanet(@Nonnull Planet planet){
        this.setStarSystem(planet.getStarSystem());
        this.currentPlanet = planet;
        return this;
    }

    public SpacePosition setUnsafe(@Nullable Group group, @Nullable Galaxy galaxy, @Nullable StarSystem starSystem, @Nullable Planet planet){
        this.currentGroup = group;
        this.currentGalaxy = galaxy;
        this.currentStarSystem = starSystem;
        this.currentPlanet = planet;
        return this;
    }

    public SpacePosition setUnsafeIfNotNull(@Nullable Group group, @Nullable Galaxy galaxy, @Nullable StarSystem starSystem, @Nullable Planet planet){
        if(group != null) this.currentGroup = group;
        if(galaxy != null) this.currentGalaxy = galaxy;
        if(starSystem != null) this.currentStarSystem = starSystem;
        if(planet != null) this.currentPlanet = planet;
        return this;
    }

    @Nullable
    public Group getGroup() {
        return currentGroup;
    }

    @Nullable
    public Galaxy getGalaxy() {
        return currentGalaxy;
    }

    @Nullable
    public StarSystem getStarSystem() {
        return currentStarSystem;
    }

    @Nullable
    public Planet getPlanet() {
        return currentPlanet;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        if(this.currentGroup != null) nbt.setString("Group", this.currentGroup.getRegisterName().toString());
        if(this.currentGalaxy != null) nbt.setString("Galaxy", this.currentGalaxy.getRegisterName().toString());
        if(this.currentStarSystem != null) nbt.setString("StarSystem", this.currentStarSystem.getRegisterName().toString());
        if(this.currentPlanet != null) nbt.setString("Planet", this.currentPlanet.getRegisterName().toString());
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        if(nbt.hasKey("Group", Constants.NBT.TAG_STRING)){
            this.currentGroup = SpaceUtil.getByName(new ResourceLocation(nbt.getString("Group")));
            if(nbt.hasKey("Galaxy", Constants.NBT.TAG_STRING)){
                this.currentGalaxy = SpaceUtil.getByName(new ResourceLocation(nbt.getString("Galaxy")));
                if(nbt.hasKey("StarSystem", Constants.NBT.TAG_STRING)){
                    this.currentStarSystem = SpaceUtil.getByName(new ResourceLocation(nbt.getString("StarSystem")));
                    if(nbt.hasKey("Planet", Constants.NBT.TAG_STRING)){
                        this.currentPlanet = SpaceUtil.getByName(new ResourceLocation(nbt.getString("Planet")));
                    }
                }
            }
        }
    }


    @Override
    public String toString() {
        return String.format("SpacePosition{group=%s, galaxy=%s, starSystem=%s, planet=%s}", getGroup(), getGalaxy(), getStarSystem(), getPlanet());
    }
}
