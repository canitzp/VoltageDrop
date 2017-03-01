package de.canitzp.cosmos;

/**
 * @author canitzp
 */
public class Util {

    public static double kilometerToAstronomicalUnits(long kilometer){
        return Math.round(((kilometer * 1000) / 149597870700D) * 100) / 100D;
    }

    public static double kilometerToAstronomicalUnits(long apKM, long periKM){
        return kilometerToAstronomicalUnits(periKM + ((apKM-periKM) / 2));
    }

}
