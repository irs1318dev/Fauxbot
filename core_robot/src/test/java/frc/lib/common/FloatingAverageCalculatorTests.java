package frc.lib.common;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import frc.lib.filters.FloatingAverageCalculator;
import frc.lib.robotprovider.ITimer;

public class FloatingAverageCalculatorTests
{
    // @Test
    public void testFloatingAverageOverOneSecond()
    {
        ITimer timer = mock(ITimer.class);
        FloatingAverageCalculator calc = new FloatingAverageCalculator(timer, 1000.0, 1.0, 50.0);

        when(timer.get()).thenReturn(0.1);
        double result = calc.update(3.0);
        Assertions.assertEquals(3.0, result, 0.0001, "expect floating average to start at the initial value");
        Assertions.assertEquals(3.0, calc.getValue(), 0.0001, "expect floating average to maintain its value when no updates have been applied");
        Assertions.assertEquals(3.0, calc.getValue(), 0.0001, "expect floating average to maintain its value when no updates have been applied");

        when(timer.get()).thenReturn(0.12);
        result = calc.update(3.0);
        Assertions.assertEquals(3.0, result, 0.0001, "expect floating average to maintain equivalent value when the next update has the same value");
        Assertions.assertEquals(3.0, calc.getValue(), 0.0001, "expect floating average to maintain its value when no updates have been applied");
        Assertions.assertEquals(3.0, calc.getValue(), 0.0001, "expect floating average to maintain its value when no updates have been applied");

        when(timer.get()).thenReturn(0.14);
        result = calc.update(3.5);
        Assertions.assertEquals(3.01, result, 0.0001, "expect floating average to maintain equivalent value when the next update has the same value");

        when(timer.get()).thenReturn(0.16);
        result = calc.update(3.5);
        Assertions.assertEquals(3.02, result, 0.0001, "expect floating average to maintain equivalent value when the next update has the same value");

        when(timer.get()).thenReturn(0.20);
        result = calc.update(3.5);
        Assertions.assertEquals(3.04, result, 0.0001, "expect floating average to maintain equivalent value when the next update has the same value");

        when(timer.get()).thenReturn(0.21);
        result = calc.update(3.5);
        Assertions.assertEquals(3.04, result, 0.0001, "expect floating average to maintain equivalent value when the next update has the same value");

        when(timer.get()).thenReturn(0.25);
        result = calc.update(3.5);
        Assertions.assertEquals(3.06, result, 0.0001, "expect floating average to maintain equivalent value when the next update has the same value");

        when(timer.get()).thenReturn(0.62);
        result = calc.update(3.5);
        Assertions.assertEquals(3.25, result, 0.0001, "expect floating average to maintain equivalent value when the next update has the same value");

        when(timer.get()).thenReturn(1.14);
        result = calc.update(3.5);
        Assertions.assertEquals(3.5, result, 0.0001, "expect floating average to maintain equivalent value when the next update has the same value");
    }

    // @Test
    public void testFloatingAverageOverHalfSecond()
    {
        ITimer timer = mock(ITimer.class);
        FloatingAverageCalculator calc = new FloatingAverageCalculator(timer, 1000.0, 0.5, 50.0);

        when(timer.get()).thenReturn(0.1);
        double result = calc.update(13.0);
        Assertions.assertEquals(13.0, result, 0.0001, "expect floating average to start at the initial value");
        Assertions.assertEquals(13.0, calc.getValue(), 0.0001, "expect floating average to maintain its value when no updates have been applied");
        Assertions.assertEquals(13.0, calc.getValue(), 0.0001, "expect floating average to maintain its value when no updates have been applied");

        when(timer.get()).thenReturn(0.12);
        result = calc.update(13.0);
        Assertions.assertEquals(13.0, result, 0.0001, "expect floating average to maintain equivalent value when the next update has the same value");
        Assertions.assertEquals(13.0, calc.getValue(), 0.0001, "expect floating average to maintain its value when no updates have been applied");
        Assertions.assertEquals(13.0, calc.getValue(), 0.0001, "expect floating average to maintain its value when no updates have been applied");

        when(timer.get()).thenReturn(0.14);
        result = calc.update(13.5);
        Assertions.assertEquals(13.02, result, 0.0001, "expect floating average to maintain equivalent value when the next update has the same value");

        when(timer.get()).thenReturn(0.16);
        result = calc.update(13.5);
        Assertions.assertEquals(13.04, result, 0.0001, "expect floating average to maintain equivalent value when the next update has the same value");

        when(timer.get()).thenReturn(0.20);
        result = calc.update(13.5);
        Assertions.assertEquals(13.08, result, 0.0001, "expect floating average to maintain equivalent value when the next update has the same value");

        when(timer.get()).thenReturn(0.21);
        result = calc.update(13.5);
        Assertions.assertEquals(13.08, result, 0.0001, "expect floating average to maintain equivalent value when the next update has the same value");

        when(timer.get()).thenReturn(0.25);
        result = calc.update(13.5);
        Assertions.assertEquals(13.12, result, 0.0001, "expect floating average to maintain equivalent value when the next update has the same value");

        when(timer.get()).thenReturn(0.62);
        result = calc.update(13.5);
        Assertions.assertEquals(13.5, result, 0.0001, "expect floating average to maintain equivalent value when the next update has the same value");
    }

    // @Test
    public void testFloatingAverageOverTwoSeconds()
    {
        ITimer timer = mock(ITimer.class);
        FloatingAverageCalculator calc = new FloatingAverageCalculator(timer, 1000.0, 2.0, 50.0);

        when(timer.get()).thenReturn(1.0);
        double result = calc.update(10.0);
        Assertions.assertEquals(10.0, result, 0.0001, "expect floating average to start at the initial value");
        Assertions.assertEquals(10.0, calc.getValue(), 0.0001, "expect floating average to maintain its value when no updates have been applied");
        Assertions.assertEquals(10.0, calc.getValue(), 0.0001, "expect floating average to maintain its value when no updates have been applied");

        when(timer.get()).thenReturn(1.02);
        result = calc.update(10.0);
        Assertions.assertEquals(10.0, result, 0.0001, "expect floating average to maintain equivalent value when the next update has the same value");
        Assertions.assertEquals(10.0, calc.getValue(), 0.0001, "expect floating average to maintain its value when no updates have been applied");
        Assertions.assertEquals(10.0, calc.getValue(), 0.0001, "expect floating average to maintain its value when no updates have been applied");

        when(timer.get()).thenReturn(1.04);
        result = calc.update(10.5);
        Assertions.assertEquals(10.005, result, 0.0001, "expect floating average to maintain equivalent value when the next update has the same value");

        when(timer.get()).thenReturn(1.06);
        result = calc.update(10.5);
        Assertions.assertEquals(10.01, result, 0.0001, "expect floating average to maintain equivalent value when the next update has the same value");

        when(timer.get()).thenReturn(1.10);
        result = calc.update(10.5);
        Assertions.assertEquals(10.02, result, 0.0001, "expect floating average to maintain equivalent value when the next update has the same value");

        when(timer.get()).thenReturn(1.11);
        result = calc.update(10.5);
        Assertions.assertEquals(10.02, result, 0.0001, "expect floating average to maintain equivalent value when the next update has the same value");

        when(timer.get()).thenReturn(1.15);
        result = calc.update(10.5);
        Assertions.assertEquals(10.03, result, 0.0001, "expect floating average to maintain equivalent value when the next update has the same value");

        when(timer.get()).thenReturn(1.52);
        result = calc.update(10.5);
        Assertions.assertEquals(10.125, result, 0.0001, "expect floating average to maintain equivalent value when the next update has the same value");
    }

    // @Test
    public void testFloatingAverageOverQuarterSecond()
    {
        ITimer timer = mock(ITimer.class);
        FloatingAverageCalculator calc = new FloatingAverageCalculator(timer, 20.0, 0.25, 100.0);

        when(timer.get()).thenReturn(0.1);
        double result = calc.update(13.0);
        Assertions.assertEquals(13.0, result, 0.0001, "expect floating average to start at the initial value");
        Assertions.assertEquals(13.0, calc.getValue(), 0.0001, "expect floating average to maintain its value when no updates have been applied");
        Assertions.assertEquals(13.0, calc.getValue(), 0.0001, "expect floating average to maintain its value when no updates have been applied");

        when(timer.get()).thenReturn(0.2);
        result = calc.update(25.0);// over limit!
        Assertions.assertEquals(15.8, result, 0.0001, "expect floating average to maintain equivalent value when the next update has the same value");
        Assertions.assertEquals(15.8, calc.getValue(), 0.0001, "expect floating average to maintain its value when no updates have been applied");
        Assertions.assertEquals(15.8, calc.getValue(), 0.0001, "expect floating average to maintain its value when no updates have been applied");

        when(timer.get()).thenReturn(0.3);
        result = calc.update(13.5);
        Assertions.assertEquals(16.0, result, 0.0001, "expect floating average to maintain equivalent value when the next update has the same value");

        when(timer.get()).thenReturn(0.4);
        result = calc.update(13.5);
        Assertions.assertEquals(14.8, result, 0.0001, "expect floating average to maintain equivalent value when the next update has the same value");

        // calc.setValue(25.0);

        when(timer.get()).thenReturn(0.5);
        result = calc.update(13.5);
        Assertions.assertEquals(13.5, result, 0.0001, "expect floating average to maintain equivalent value when the next update has the same value");
    }
}
