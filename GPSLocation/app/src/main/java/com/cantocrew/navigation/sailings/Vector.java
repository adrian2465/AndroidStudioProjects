package com.cantocrew.navigation.sailings;

public class Vector {
    public final static String DEGREE_CHAR = "\u00b0";
    public double direction;
    public double distance;
    public Vector (double direction, double distance) {this.direction=direction; this.distance=distance;}
    public String directionAsString() {
        return String.format("%03d%sT",((int)direction),DEGREE_CHAR);
    }
    private static double getDistance(Coordinate p1, Coordinate p2, double c) {
        double cosine= DMSMath.cos(c);
        if (cosine<0.00000001d) return Math.abs(60.0d * (p2.lon.value-p1.lon.value)* DMSMath.cos(p2.lat.value));
        else return Math.abs(60.0d * (p2.lat.value-p1.lat.value)/cosine);
    }
    private static double getCourse(Coordinate p1, Coordinate p2, double c) {
        if (p2.isNorthOf(p1) && p2.isWestOf(p1)) c= 360.0d-c; // Going north and west
        else if (p2.isSouthOf(p1) && p2.isEastOf(p1)) c= 180.0d-c;// Going south and east
        else if (p2.isSouthOf(p1) && p2.isWestOf(p1)) c= 180.0d+c; // Going south and west
        else c=c; // Going north and east.
        if (c>=360) c=c-360d;
        return c;
    }

    private static double calc_c(Coordinate p1, Coordinate p2) {
        return DMSMath.atan(Math.abs((p2.lon.value-p1.lon.value)/DMSMath.meridionalDifference(p1.lat.value,p2.lat.value)));
    }
    public static Vector getVector(Coordinate p1, Coordinate p2) {
        double c=calc_c(p1, p2);
        return new Vector(getCourse(p1, p2, c), getDistance(p1, p2, c));
    }

    public static Coordinate getCoordinate(Coordinate origin, Vector v) {
        double newLatValue = origin.lat.value + v.distance * DMSMath.cos(v.direction)/60.0d;
        double newLonValue;
        double factor = (v.direction > 180) ? -1 : 1; // Add for easterly direction, subtract for westerly direction.
        if (v.direction==270||v.direction==90) {
            newLonValue = origin.lon.value + factor*v.distance / (60 * DMSMath.cos(origin.lat.value));
        }else{
            double mDiff = DMSMath.meridionalDifference(origin.lat.value, newLatValue);
            System.out.println("mDiff=" + mDiff);
            double dLo = Math.abs(DMSMath.tan(v.direction) * mDiff);
            System.out.println("DLO=" + dLo + "; lo=" + origin.lon + ";  newLo = " + (origin.lon.value + dLo) + " or " + (-origin.lon.value + dLo));
            newLonValue = origin.lon.value + factor * dLo;
        }
        return new Coordinate(new Ordinate(newLatValue,OrdinateType.LAT), new Ordinate(newLonValue,OrdinateType.LON));
    }

    public String toString() {
        return String.format("c=%d%sT, d=%5.1f", (int)direction, DEGREE_CHAR, distance);
    }
}
