package com.cantocrew.gpslocation;

import android.location.Location;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Formatters {
    private static final String DEGREE_CHAR_STR=Character.toString((char)176);

    public static String formatNS(double lat) {
        String ns=(lat>0?"N":"S");
        lat = Math.abs(lat);
        double latDeg = Math.floor(lat);
        double latMinutes = (lat-latDeg)*60;
        return String.format("%2.0f%s %06.3f%s",latDeg, DEGREE_CHAR_STR,latMinutes,ns);
    }
    public static String formatEW(double lon) {
        String ns=(lon>0?"E":"W");
        lon = Math.abs(lon);
        double lonDeg = Math.floor(lon);
        double lonMinutes = (lon-lonDeg)*60;
        return String.format("%03.0f%s %06.3f%s",lonDeg, DEGREE_CHAR_STR,lonMinutes,ns);
    }

    public static String formatHeadingTrue(double b) {
        return String.format("%03.0f%sT\n",b, DEGREE_CHAR_STR);
    }
    public static String formatBearingMag(double b) {
        return String.format("%03.0f%sM\n",b, DEGREE_CHAR_STR);
    }
    public static double metersPerSecondToKnots(double mps) {
        return mps * 1.94384f;
    }
    public static double metersToNauticalMiles(double m) {
        return m * 0.000539957f;
    }

    public static String formatSpeedKts(double speed) {
        return String.format("%4.1f kts", speed);
    }
    public static String formatSpeedMph(double speed) {
        return String.format("%4.1f mph", speed);
    }
}
