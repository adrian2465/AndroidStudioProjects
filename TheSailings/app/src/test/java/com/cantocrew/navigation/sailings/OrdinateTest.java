package com.cantocrew.navigation.sailings;

import org.junit.Test;

public class OrdinateTest {
    @Test
    public void ordinateTest() {
        Ordinate lat = new Ordinate(35.5, OrdinateType.LAT);
        assert(lat.getDegrees()==35);
        assert(lat.getMinutes()==30);
    }
    @Test
    public void formatTest() {
        Ordinate o = new Ordinate(-35.5123, OrdinateType.LAT);
        System.out.println(o);
        assert(o.toString().equals("35°30.74'S"));
        o = new Ordinate(-35.5123, OrdinateType.LON);
        System.out.println(o);
        assert(o.toString().equals("035°30.74'W"));
        o = new Ordinate(35.5123, OrdinateType.LAT);
        System.out.println(o);
        assert(o.toString().equals("35°30.74'N"));
        o = new Ordinate(35.5123, OrdinateType.LON);
        System.out.println(o);
        assert(o.toString().equals("035°30.74'E"));
    }
    @Test
    public void ordinateFromStringTest() {
        String oStr="35°30.74'S"; Ordinate o = new Ordinate(oStr); assert(oStr.equals(o.toString()));
        oStr="35°30.74'N"; o = new Ordinate(oStr); assert(oStr.equals(o.toString()));
    }
    @Test
    public void coordinateFromStringTest() {
        String positionStr="35d30.74'S,79°30.74'W";
        Coordinate c = new Coordinate(positionStr);
        System.out.println("Position "+positionStr+" constructed to "+c);
        assert(c.toString().equals("35°30.74'S, 079°30.74'W"));
    }
}
