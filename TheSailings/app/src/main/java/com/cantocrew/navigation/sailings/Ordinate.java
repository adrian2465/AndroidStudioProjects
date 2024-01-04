package com.cantocrew.navigation.sailings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ordinate {
    public final static String DEGREE_CHAR = "\u00b0";
    public double value;
    public OrdinateType type;

    public Ordinate(double value, OrdinateType t) {
        this.value = value;
        this.type =t;
    }
    private final Pattern re = Pattern.compile("^([0-9]{2,3})["+DEGREE_CHAR+"d]([0-9]{2}\\.[0-9]*)'([NSEWnsew])$");

    public Ordinate(String ordinateStr) {
        ordinateStr=ordinateStr.trim();
        Matcher matcher = re.matcher(ordinateStr);
        if (matcher.matches()) {
            double deg = Double.parseDouble(matcher.group(1));
            double min = Double.parseDouble(matcher.group(2));
            String type = matcher.group(3).toUpperCase();
//            System.out.println("deg=" + deg + ", min=" + min + ", type=" + type);
            switch(type.charAt(0)) {
                case 'N': value = deg + min/60.0d; this.type =OrdinateType.LAT; break;
                case 'S': value = -(deg + min/60.0d); this.type =OrdinateType.LAT; break;
                case 'E': value = deg + min/60.0d; this.type =OrdinateType.LON; break;
                case 'W': value = -(deg + min/60.0d); this.type =OrdinateType.LON; break;

            }
        }

    }
    public Ordinate(int i, double v, char dir) {
        OrdinateType t;
        double factor=1.0;
        switch(Character.toUpperCase(dir)) {
            case 'N': t=OrdinateType.LAT; break;
            case 'S': t=OrdinateType.LAT; factor=-1;break;
            case 'E': t=OrdinateType.LON; break;
            case 'W': t=OrdinateType.LON; factor=-1;break;
            default: t=null;
        }
        this.value =factor*(i + v / 60.0);
        this.type =t;
    }
    public int getDegrees() {
        return (int)(value);
    }
    public double getMinutes() {
        return (value - getDegrees()) * 60;
    }
    private String stringAsLatitude() {
        return String.format("%02d%s%05.2f'%s",Math.abs(getDegrees()),DEGREE_CHAR,Math.abs(getMinutes()),getDegrees()<0?"S":"N");
    }
    private String stringAsLongitude() {
        return String.format("%03d%s%05.2f'%s",Math.abs(getDegrees()),DEGREE_CHAR,Math.abs(getMinutes()),getDegrees()<0?"W":"E");
    }
    public String toString() {
        switch (type) {
            case LAT: return stringAsLatitude();
            default: return stringAsLongitude();
        }
    }

    public boolean equals(Ordinate o) {
        return Math.abs(this.value - o.value)<0.02 && type ==o.type;
    }
}
