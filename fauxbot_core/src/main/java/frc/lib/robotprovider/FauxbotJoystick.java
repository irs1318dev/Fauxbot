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
        this.pov = 0;

        FauxbotJoystickManager.set(port, this);
    }

    public boolean getRawButton(int buttonNumber)
    {
        if (!this.buttons.containsKey(buttonNumber))
        {
            return false;
        }

        boolean result = this.buttons.get(buttonNumber);
        return result;
    }

    public boolean isConnected()
    {
        return true;
    }

    public int getPOV()
    {
        return this.pov;
    }

    public double getAxis(int relevantAxis)
    {
        if (!this.axes.containsKey(relevantAxis))
        {
            return 0.0;
        }

        return this.axes.get(relevantAxis);
    }

    public void setRumble(JoystickRumbleType type, double value)
    {
    }
}
