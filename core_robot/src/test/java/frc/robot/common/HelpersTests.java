package frc.robot.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import frc.lib.helpers.Helpers;

public class HelpersTests
{
    @Test
    public void testPolarConversion_Origin()
    {
        double result = Helpers.convertToPolarAngle(0.0, 0.0);
        assertEquals(-1.0, result, "expect origin to return -1");
    }

    @Test
    public void testPolarConversion_xAxis()
    {
        double result = Helpers.convertToPolarAngle(0.2, 0.0);
        assertEquals(0.0, result, "expect x-axis to return 0 degrees");
    }

    @Test
    public void testPolarConversion_negXAxis()
    {
        double result = Helpers.convertToPolarAngle(-0.4, 0.0);
        assertEquals(180.0, result, "expect negative x-axis to return 180 degrees");
    }

    @Test
    public void testPolarConversion_yAxis()
    {
        double result = Helpers.convertToPolarAngle(0.0, 0.75);
        assertEquals(90.0, result, "expect y-axis to return 90 degrees");
    }

    @Test
    public void testPolarConversion_negYAxis()
    {
        double result = Helpers.convertToPolarAngle(0.0, -0.75);
        assertEquals(270.0, result, "expect y-axis to return 270 degrees");
    }

    @Test
    public void testPolarConversion_quadrant1Center()
    {
        double result = Helpers.convertToPolarAngle(0.5, 0.5);
        assertEquals(45.0, result, "expect Quadrant I to return 45 degrees");
    }

    @Test
    public void testPolarConversion_quadrant2Center()
    {
        double result = Helpers.convertToPolarAngle(-1.0, 1.0);
        assertEquals(135.0, result, "expect Quadrant II to return 135 degrees");
    }

    @Test
    public void testPolarConversion_quadrant3Center()
    {
        double result = Helpers.convertToPolarAngle(-0.75, -0.75);
        assertEquals(225.0, result, "expect Quadrant III to return 225 degrees");
    }

    @Test
    public void testPolarConversion_quadrant4Center()
    {
        double result = Helpers.convertToPolarAngle(0.1, -0.1);
        assertEquals(315.0, result, "expect Quadrant IV to return 315 degrees");
    }

    @Test
    public void testPolarConversion_nearAngle()
    {
        double result = Helpers.convertToPolarAngle(0.0000001, 0.1);
        assertEquals(90.0, result, 0.01, "expect near the y axis to return near 90 degrees");
    }
}