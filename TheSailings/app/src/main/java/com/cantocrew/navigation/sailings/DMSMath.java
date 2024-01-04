package com.cantocrew.navigation.sailings;

public class DMSMath {
    public static double PI = Math.PI;
    public static double r2d(double rads) {return (rads * 180.0d/PI);}
    public static double d2r(double degs) {return (degs * PI/180.0d);}
    public static double atan(double slope) {return r2d(Math.atan(slope));}
    public static double tan(double dAngle) {return Math.tan(d2r(dAngle));}
    public static double cos(double dAngle) {return Math.cos(d2r(dAngle)); }
    public static double logtanfun(double lat) {return Math.log(DMSMath.tan(  45.0d+lat/2.0));}
    public static double meridionalDifference(double lat1, double lat2) {return Math.abs(180*(logtanfun(lat2)-logtanfun(lat1))/PI);}
//    public static double meridionalDifference(double lat1, double lat2) {return Math.abs(180*(logtanfun(lat2)-logtanfun(lat1))/PI);}
}
