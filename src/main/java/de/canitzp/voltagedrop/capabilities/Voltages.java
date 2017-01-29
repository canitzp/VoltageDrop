package de.canitzp.voltagedrop.capabilities;

/**
 * @author canitzp
 */
public enum Voltages{

    LOWEST(3.3F, 6.5F),
    LOW(5, 6.45F),
    FAST_CHARGE(9, 6.25F),
    LAPTOP_CHARGE(19, 6),
    MAINS(230, 0),
    THREE_PHASE(400, 0.33F);

    private float rating, conversionRateToMain;

    Voltages(float rating, float conversionRate){
        this.rating = rating;
        this.conversionRateToMain = conversionRate;
    }

    public float getRating(){
        return rating;
    }

    public float getConversionRateToMain(){
        return conversionRateToMain;
    }

    public int toInt(){
        return this.ordinal();
    }

    public static Voltages fromInt(int i){
        return Voltages.values()[i];
    }

    public float getConversionRateTo(Voltages other){
        return getConversionRateBetween(this, other);
    }

    public static float getConversionRateBetween(Voltages first, Voltages second){
        return first.getConversionRateToMain() - second.getConversionRateToMain();
    }

    public String getLocalizated(){
        return this.name();
    }

}
