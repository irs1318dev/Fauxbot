package edu.wpi.first.wpilibj;

import java.util.HashMap;
import java.util.Map;

public class Joystick
{
    public enum AxisType
    {
        kX,
        kY,
        kZ,
        kTwist,
        kThrottle;
    }

    private final Map<Integer, Boolean> buttons;
    private final Map<AxisType, Double> axes;
    private int pov;

    public Joystick(int port)
    {
        this.buttons = new HashMap<Integer, Boolean>();
        this.axes = new HashMap<AxisType, Double>();
        this.pov = 0;

        JoystickManager.set(port, this);
    }

    public boolean getRawButton(int buttonNumber)
    {
        if (!this.buttons.containsKey(buttonNumber))
        {
            return false;
        }

        return this.buttons.get(buttonNumber);
    }

    public int getPOV()
    {
        return this.pov;
    }

    public double getAxis(AxisType relevantAxis)
    {
        if (!this.axes.containsKey(relevantAxis))
        {
            return 0.0;
        }

        return this.axes.get(relevantAxis);
    }

    public void setRawButton(int buttonNumber, boolean value)
    {
        this.buttons.put(buttonNumber, value);
    }

    public void setAxis(AxisType relevantAxis, double value)
    {
        this.axes.put(relevantAxis, value);
    }

    public void setPov(int value)
    {
        this.pov = value;
    }
}
