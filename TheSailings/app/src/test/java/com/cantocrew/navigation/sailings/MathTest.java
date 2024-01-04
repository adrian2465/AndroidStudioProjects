package com.cantocrew.navigation.sailings;

import org.junit.Test;

public class MathTest {
    @Test
    public void degRadTest() {
        double rads = DMSMath.PI / 2.0; // Should be 90 degrees.
        double degs = 90; // Should be PI/2 degrees.
        assert(DMSMath.r2d(rads) == degs);
        assert(java.lang.Math.abs(DMSMath.d2r(degs) - rads)<0.000001);
    }
    @Test
    public void arctanTest() {
        assert(DMSMath.atan(1) == 45.0);
        assert(DMSMath.atan(0) == 0);
        assert(DMSMath.atan(-1) == -45.0);
        assert(Math.abs(DMSMath.atan(999999999)-90) < 0.000001);
    }
    @Test
    public void tanTest() {
        assert(DMSMath.tan(45) - 1 < 0.000001);
        assert(DMSMath.tan(0) == 0);
        assert(DMSMath.tan(-45) + 1 < 0.000001);
        assert(Math.abs(DMSMath.tan(90)) > 999999999);
        assert(DMSMath.tan(315) + 1 < 0.000001);
    }
    @Test
    public void cosTest() {
        assert(Math.abs(DMSMath.cos(0) - 1 ) < 0.000001);
        assert(Math.abs(DMSMath.cos(60) - 0.5) < 0.000001);
        assert(DMSMath.cos(90) < 0.000001);
        assert(DMSMath.cos(270) < 0.000001);
        assert(Math.abs(DMSMath.cos(180) + 1 ) < 0.000001);
    }
    @Test
    public void meridionalPartTest1() {
        double expected=6.3830;
        double actual=DMSMath.meridionalDifference(-25.0667,-19.1566);
        assert(Math.abs(expected-actual)<0.01);
    }
}
