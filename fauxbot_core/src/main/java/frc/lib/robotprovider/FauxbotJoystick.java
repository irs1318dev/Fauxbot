package frc.lib.robotprovider;

import java.util.HashMap;
import java.util.Map;

public class FauxbotJoystick implements IJoystick
{
    private final Map<Integer, Boolean> buttons;
    private final Map<Integer, Double> axes;
    private int pov;

    public FauxbotJoystick(int port)
    {
        this.buttons = new HashMap<Integer, Boolean>();
        this.axes = new HashMap<Integer, Double>();
        this.pov = -1;

        FauxbotJoystickManager.set(port, this);
    }

    public boolean getRawButton(int buttonNumber)
    {
        synchronized (this)
        {
            if (!this.buttons.containsKey(buttonNumber))
            {
                return false;
            }

            boolean result = this.buttons.get(buttonNumber);
            return result;
        }
    }

    public boolean isConnected()
    {
        return true;
    }

    public int getPOV()
    {
        synchronized (this)
        {
            return this.pov;
        }
    }

    public double getAxis(int relevantAxis)
    {
        synchronized (this)
        {
            if (!this.axes.containsKey(relevantAxis))
            {
                return 0.0;
            }

            return this.axes.get(relevantAxis);
        }
    }

    public void setRumble(JoystickRumbleType type, double value)
    {
    }

    public void setButton(int buttonNumber, boolean value)
    {
        synchronized (this)
        {
            this.buttons.put(buttonNumber, value);
        }
    }

    public void setPOV(int value)
    {
        synchronized (this)
        {
            this.pov = value;
        }
    }

    public void clearPOV()
    {
        this.setPOV(-1);
    }

    public void setAxis(int axis, double value)
    {
        synchronized (this)
        {
            this.axes.put(axis, value);
        }
    }
}
