package com.cantocrew.whenstartmotor;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private static final long T_20200419_104300 =1587307380000L;
    private static final long T_20200418_170000 =1587243600000L;
    private static final long T_20200418_103000 =1587220200000L;
    private static final long T_20200418_165320 =1587243200000L;
    private static final long T_20200419_063000 =1587292200000L;
    Calculator calc = new Calculator();

    @Before
    public void Setup() throws Exception {
        calc.setMotoringSpeed(5);
        calc.setTotalDistance(40);
        calc.setSailSpeed(3.0);
        calc.setStartTime(Formatters.parseTime("20/04/18 08:00"));
        calc.setDesiredEta(Formatters.parseTime("20/04/18 17:00"));
    }
    @Test
    public void testViable() {
        assertTrue("Viable desired ETA wrongly marked as not viable",calc.isViable());
        calc.setTotalDistance(120);
        assertFalse("Unviable desired ETA wrongly marked as viable",calc.isViable());
    }
    @Test
    public void testParseTime() { assertEquals(T_20200419_104300,Formatters.parseTime("20/04/19 10:43")); }
    @Test
    public void testFormatTime() { assertEquals("20/04/19 10:43",Formatters.formatTime(T_20200419_104300));}
    @Test
    public void testSlowDistance() {assertEquals(7.5, calc.getDistanceSailing(),0.001);}
    @Test
    public void testArrivalTimeSailingSlowly() {
        assertEquals(T_20200418_170000,calc.getArrivalTimeMs());
    }
    @Test
    public void testIsAllSail() {
        assertFalse(calc.isAllSail()); // Sailing slowly - must motor.
        calc.setSailSpeed(4.5); // Go faster!
        assertTrue(calc.isAllSail()); // Sailing fast - can make it!
    }
    @Test
    public void testArrivalTimeSailingFast() {
        calc.setSailSpeed(4.5);
        assertEquals(T_20200418_165320,calc.getArrivalTimeMs());
    }
    @Test
    public void testArrivalTimeOutsideOfDesiredETA() {
        calc.setTotalDistance(120); // Too far to cover by 5pm at 5 knots.
        assertEquals(T_20200419_063000,calc.getArrivalTimeMs());
    }
    @Test
    public void testStartMotorTime() {assertEquals(T_20200418_103000,calc.getStartMotorTimeMs());}
    @Test
    public void testActualETA() {assertEquals(T_20200418_170000,calc.getArrivalTimeMs());}
    @Test
    public void testHoursSpent() {
        assertEquals(6.5,calc.getHoursMotoring(),0.001);
        assertEquals(2.5,calc.getHoursSailing(),0.001);
        calc.setSailSpeed(5.0);
        assertEquals(0.0,calc.getHoursMotoring(),0.001);
        assertEquals(8.0,calc.getHoursSailing(),0.001);
    }
}