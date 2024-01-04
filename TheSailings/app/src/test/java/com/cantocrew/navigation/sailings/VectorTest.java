package com.cantocrew.navigation.sailings;

import org.junit.Test;

public class VectorTest {

    @Test
    public void courseTest1() {
        Coordinate p1= new Coordinate(
                new Ordinate(42, 38.0, 'N'),
                new Ordinate(70,34.0, 'W'));
        Coordinate p2 = new Coordinate(
                new Ordinate(43, 23.0, 'N'),
                new Ordinate(65,37.0, 'W'));
        Vector course = Vector.getVector(p1,p2);
        System.out.println("Course="+course.directionAsString()+ " Distance="+course.distance);
        assert(course.directionAsString().equals("078°T"));
        assert(Math.abs(course.distance - 221.8) < 0.01);
    }
    @Test
    public void courseTest2() {
        Coordinate p1= new Coordinate(
                new Ordinate(37, 3, 's'),
                new Ordinate(12,18, 'W'));
        Coordinate p2 = new Coordinate(
                new Ordinate(40, 20, 's'),
                new Ordinate(10,00, 'W'));
        Vector course = Vector.getVector(p1,p2);
        System.out.println("Course="+course.directionAsString()+ " Distance="+course.distance);
        assert(course.directionAsString().equals("151°T"));
        assert(Math.abs(course.distance - 224.6) < 0.2);
    }
    @Test
    public void courseTest3() {
        Coordinate p1= new Coordinate(
                new Ordinate(34, 29, 's'),
                new Ordinate(172,38, 'e'));
        Coordinate p2 = new Coordinate(
                new Ordinate(41, 00, 's'),
                new Ordinate(148,21, 'e'));
        Vector course = Vector.getVector(p1,p2);
        System.out.println("Course="+course.directionAsString()+ " Distance="+course.distance);
        assert(course.directionAsString().equals("251°T"));
        assert(Math.abs(course.distance - 1215.4) < 0.2);
    }
    @Test
    public void courseTest4() {
        Coordinate p1= new Coordinate(
                new Ordinate(41,9, 'n'),
                new Ordinate(71,33, 'w'));
        Coordinate p2 = new Coordinate(
                new Ordinate(32, 28, 'n'),
                new Ordinate(64,46, 'w'));
        Vector course = Vector.getVector(p1,p2);
        System.out.println("Course="+course.directionAsString()+ " Distance="+course.distance);
        assert(course.directionAsString().equals("148°T"));
        assert(Math.abs(course.distance - 614.2) < 0.2);
    }
    @Test
    public void courseTest5() { // Punta Mala, Panama to Galapagox between Santa Cruz and San Cristobal.
        //7°28′00″N 80°00′32″O
        Coordinate p1= new Coordinate(
                new Ordinate(7.47286,OrdinateType.LAT),
                new Ordinate(-79.9792,OrdinateType.LON));
        Coordinate p2 = new Coordinate(
                new Ordinate(-1,OrdinateType.LAT),
                new Ordinate(-90,OrdinateType.LON));
        Vector course = Vector.getVector(p1,p2);
        System.out.println("Course="+course.directionAsString()+ " Distance="+course.distance);
        assert(course.directionAsString().equals("229°T"));
        assert(Math.abs(course.distance - 786.2) < 0.2);
    }
    @Test
    public void courseTest6() {
        Coordinate p1= new Coordinate(
                new Ordinate(35,OrdinateType.LAT),
                new Ordinate(-79,OrdinateType.LON));
        Coordinate p2 = new Coordinate(
                new Ordinate(36,OrdinateType.LAT),
                new Ordinate(-78,OrdinateType.LON));
        Vector course = Vector.getVector(p1,p2);
        System.out.println("Course="+course.directionAsString()+ " Distance="+course.distance);
        assert(course.directionAsString().equals("039°T"));
        assert(Math.abs(course.distance - 77.37) < 0.2);
    }
    @Test
    public void courseTest7() {
        Coordinate p1= new Coordinate(
                new Ordinate(35,OrdinateType.LAT),
                new Ordinate(-79,OrdinateType.LON));
        Coordinate p2 = new Coordinate(
                new Ordinate(35,OrdinateType.LAT),
                new Ordinate(-78,OrdinateType.LON));
        Vector course = Vector.getVector(p1,p2);
        System.out.println("Course="+course.directionAsString()+ " Distance="+course.distance);
        assert(course.directionAsString().equals("090°T"));
        assert(Math.abs(course.distance - 49.15) < 0.2);
    }
    @Test
    public void courseTest8() {
        Coordinate p1= new Coordinate(
                new Ordinate(35,OrdinateType.LAT),
                new Ordinate(-79,OrdinateType.LON));
        Coordinate p2 = new Coordinate(
                new Ordinate(35,OrdinateType.LAT),
                new Ordinate(-80,OrdinateType.LON));
        Vector course = Vector.getVector(p1,p2);
        System.out.println("Course="+course.directionAsString()+ " Distance="+course.distance);
        assert(course.directionAsString().equals("270°T"));
        assert(Math.abs(course.distance - 49.15) < 0.2);
    }
    @Test
    public void courseTest9() {
        Coordinate p1= new Coordinate(
                new Ordinate(35,OrdinateType.LAT),
                new Ordinate(-79,OrdinateType.LON));
        Coordinate p2 = new Coordinate(
                new Ordinate(36,OrdinateType.LAT),
                new Ordinate(-79,OrdinateType.LON));
        Vector course = Vector.getVector(p1,p2);
        System.out.println("Course="+course.directionAsString()+ " Distance="+course.distance);
        assert(course.directionAsString().equals("000°T"));
        assert(Math.abs(course.distance - 60) < 0.2);
    }
    @Test
    public void courseTest10() {
        Coordinate p1= new Coordinate(
                new Ordinate(35,OrdinateType.LAT),
                new Ordinate(-79,OrdinateType.LON));
        Coordinate p2 = new Coordinate(
                new Ordinate(36,OrdinateType.LAT),
                new Ordinate(-80,OrdinateType.LON));
        Vector course = Vector.getVector(p1,p2);
        System.out.println("Course="+course.directionAsString()+ " Distance="+course.distance);
        assert(course.directionAsString().equals("320°T"));
        assert(Math.abs(course.distance - 77.4) < 0.2);
    }
    @Test
    public void destinationTest1() {
        Coordinate src = new Coordinate(
                new Ordinate(25, 4.0, 'S'),
                new Ordinate(130, 5.0, 'W'));
        Vector v = new Vector(38, 450);
        Coordinate expected = new Coordinate(
                new Ordinate(19, 9.4, 'S'),
                new Ordinate(125, 5.8, 'W'));
        Coordinate actual = Vector.getCoordinate(src, v);
        System.out.println("Actual=" + actual + ", expected=" + expected);
        assert (actual.equals(expected));
    }
    @Test
    public void destinationTest2() {
        Coordinate src=new Coordinate(
                new Ordinate(5,55,'n'),
                new Ordinate(80,36, 'e'));
        Vector v = new Vector(160, 350);
        Coordinate expected=new Coordinate(
                new Ordinate(0,26.1,'n'),
                new Ordinate(82,35.1, 'e'));
        Coordinate actual=Vector.getCoordinate(src,v);
        System.out.println("Actual="+actual+", expected="+expected);
        assert(actual.equals(expected));
    }
    @Test
    public void destinationTest3() {
        Coordinate src=new Coordinate(
                new Ordinate(20,18,'s'),
                new Ordinate(118,35, 'e'));
        Vector v = new Vector(310, 110);
        Coordinate expected=new Coordinate(
                new Ordinate(19,7.3,'s'),
                new Ordinate(117,6.1, 'e'));
        Coordinate actual=Vector.getCoordinate(src,v);
        System.out.println("Actual="+actual+", expected="+expected);
        assert(actual.equals(expected));
    }
    @Test
    public void destinationTest4() {
        Coordinate src=new Coordinate(
                new Ordinate(52,34,'n'),
                new Ordinate(9,56, 'w'));
        Vector v = new Vector(252, 240);
        Coordinate expected=new Coordinate(
                new Ordinate(51,19.8,'n'),
                new Ordinate(16,5.6, 'w'));
        Coordinate actual=Vector.getCoordinate(src,v);
        System.out.println("Actual="+actual+", expected="+expected);
        assert(actual.equals(expected));
    }
    @Test
    public void destinationTest5() {
        Coordinate src=new Coordinate(
                new Ordinate(35,OrdinateType.LAT),
                new Ordinate(-80,OrdinateType.LON));
        Vector v = new Vector(90, 60);
        Coordinate expected=new Coordinate(
                new Ordinate(35,0,'n'),
                new Ordinate(78,46,'w')); // TODO: Broken test case. Just due east (west?) broken.
        Coordinate actual=Vector.getCoordinate(src,v);
        System.out.println("Actual="+actual+", expected="+expected);
        assert(actual.equals(expected));
    }
    @Test
    public void destinationTest6() {
        Coordinate src=new Coordinate(
                new Ordinate(35,OrdinateType.LAT),
                new Ordinate(-80,OrdinateType.LON));
        Vector v = new Vector(270, 60);
        Coordinate expected=new Coordinate(
                new Ordinate(35,0,'n'),
                new Ordinate(81,13,'w')); // TODO: Broken test case. Just due east (west?) broken.
        Coordinate actual=Vector.getCoordinate(src,v);
        System.out.println("Actual="+actual+", expected="+expected);
        assert(actual.equals(expected));
    }
    @Test
    public void destinationTest7() {
        Coordinate src=new Coordinate(
                new Ordinate(35,OrdinateType.LAT),
                new Ordinate(-80,OrdinateType.LON));
        Vector v = new Vector(0, 60);
        Coordinate expected=new Coordinate(
                new Ordinate(36,0,'n'),
                new Ordinate(80,0,'w')); // TODO: Broken test case. Just due east (west?) broken.
        Coordinate actual=Vector.getCoordinate(src,v);
        System.out.println("Actual="+actual+", expected="+expected);
        assert(actual.equals(expected));
    }
    @Test
    public void destinationTest8() {
        Coordinate src=new Coordinate(
                new Ordinate(35,OrdinateType.LAT),
                new Ordinate(-80,OrdinateType.LON));
        Vector v = new Vector(180, 60);
        Coordinate expected=new Coordinate(
                new Ordinate(34,0,'n'),
                new Ordinate(80,0,'w')); // TODO: Broken test case. Just due east (west?) broken.
        Coordinate actual=Vector.getCoordinate(src,v);
        System.out.println("Actual="+actual+", expected="+expected);
        assert(actual.equals(expected));
    }
}
