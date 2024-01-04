package com.cantocrew.navigation.sailings;

public class Coordinate {
    public Ordinate lat;
    public Ordinate lon;
    public Coordinate(Ordinate lat, Ordinate lon) {this.lat=lat;this.lon=lon;}

    public Coordinate(String coordStr) {
        coordStr=coordStr.trim();
        String[] ordinates = coordStr.split(",");
        Ordinate o1 = new Ordinate(ordinates[0]);
        Ordinate o2 = new Ordinate(ordinates[1]);
        if (o1.type == OrdinateType.LAT) {
            lat = o1;
            lon = o2;
        } else {
            lat = o2;
            lon = o1;
        }
    }
    public boolean isNorthOf(Coordinate p2) {
        return (this.lat.value>p2.lat.value);
    }
    public boolean isEastOf(Coordinate p2) {
        return (this.lon.value>p2.lon.value);
    }
    public boolean isSouthOf(Coordinate p2) { return !isNorthOf(p2);}
    public boolean isWestOf(Coordinate p2) {return !isEastOf(p2);}
    public String toString() {
        return String.format("%s, %s",lat, lon);
    }
    public boolean equals(Coordinate c) {
        return lat.equals(c.lat) && lon.equals(c.lon);
    }
}
