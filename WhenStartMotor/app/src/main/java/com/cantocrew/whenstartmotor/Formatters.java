package com.cantocrew.whenstartmotor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Formatters {
    private static final DateFormat simple = new SimpleDateFormat("yy/MM/dd HH:mm");
    private static final String DEGREE_CHAR_STR=Character.toString((char)176);

    public static String formatNS(double lat) {
        String ns=(lat>0?"N":"S");
        lat = Math.abs(lat);
        double latDeg = Math.floor(lat);
        double latMinutes = (lat-latDeg)*60;
        return String.format("Lat=%2.0f%s %04.1f%s",latDeg, DEGREE_CHAR_STR,latMinutes,ns);
    }
    public static String formatEW(double lon) {
        String ns=(lon>0?"E":"W");
        lon = Math.abs(lon);
        double lonDeg = Math.floor(lon);
        double lonMinutes = (lon-lonDeg)*60;
        return String.format("Lon=%03.0f%s %04.1f%s",lonDeg, DEGREE_CHAR_STR,lonMinutes,ns);
    }
    public static String formatTime(long ms) {
        Date result = new Date(ms);
        return simple.format(result);
    }
    public static long parseTime(String myDateStr) {
        DateFormat formatter = new SimpleDateFormat("yy/MM/dd HH:mm");
        try {
            Date dt = formatter.parse(myDateStr);
            Calendar cal = Calendar.getInstance();
            cal.setTime(dt);
            return cal.getTimeInMillis();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    public static String formatElapsedHMS(long elapsedMs) {
        long seconds = elapsedMs / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        return String.format("%d days, %02d hrs, %02d min, %02d sec\n",days,hours % 24,minutes % 60,seconds % 60);
    }
    public static String formatBearing(float b) {
        return String.format("%03.0f%sT\n",b, DEGREE_CHAR_STR);
    }

    public static float metersPerSecondToKnots(float mps) {
        return mps * 1.94384f;
    }
    public static float metersToNauticalMiles(float m) {
        return m * 0.000539957f;
    }

    public static String formatSpeed(float speed) {
        return String.format("%4.1f kts", speed);
    }

    public static String formatDistanceMeters(float distance) {
        return ""+metersToNauticalMiles(distance);
    }
}
