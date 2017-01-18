package de.canitzp.voltagedrop.capabilities;

/**
 * @author canitzp
 */
public enum Voltages{

    LOWEST(3.3F),
    LOW(5),
    FAST_CHARGE(9),
    MAINS(230);

    private float rating;

    Voltages(float rating){
        this.rating = rating;
    }

    public float getRating(){
        return rating;
    }

    public int toInt(){
        return this.ordinal();
    }

    public static Voltages fromInt(int i){
        return Voltages.values()[i];
    }

}
