package frc.lib.robotprovider;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.controllers.Controller;

public class FauxbotJoystick implements IJoystick
{
    private static final int PovAxis = 0;

    private final Map<Integer, Boolean> buttons;
    private final Map<Integer, Double> axes;
    private int pov;
    private Controller controller;

    public FauxbotJoystick(int port)
    {
        this.buttons = new HashMap<Integer, Boolean>();
        this.axes = new HashMap<Integer, Double>();
        this.pov = -1;
        this.controller = null;

        FauxbotJoystickManager.set(port, this);
    }

    public void setController(Controller controller)
    {
        synchronized (this)
        {
            this.controller = controller;
        }
    }

    public boolean getRawButton(int buttonNumber)
    {
        synchronized (this)
        {
            if (this.controller != null)
            {
                return this.controller.getButton(buttonNumber);
            }

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
        synchronized (this)
        {
            if (this.controller != null)
            {
                return this.controller.isConnected();
            }
        }

        return true;
    }

    public int getPOV()
    {
        synchronized (this)
        {
            if (this.controller != null)
            {
                return (int)this.controller.getAxis(FauxbotJoystick.PovAxis);
            }

            return this.pov;
        }
    }

    public double getAxis(int relevantAxis)
    {
        synchronized (this)
        {
            if (this.controller != null)
            {
                return this.controller.getAxis(relevantAxis);
            }

            if (!this.axes.containsKey(relevantAxis))
            {
                return 0.0;
            }

            return this.axes.get(relevantAxis);
        }
    }

    public void setRumble(JoystickRumbleType type, double value)
    {
        synchronized (this)
        {
            if (this.controller != null &&
                this.controller.canVibrate())
            {
                this.controller.startVibration(500, (float)value);
            }
        }
    }

    public void setButton(int buttonNumber, boolean value)
    {
        synchronized (this)
        {
            if (this.controller != null)
            {
                throw new RuntimeException("We shouldn't be attempting to set a button when using a controller");
            }

            this.buttons.put(buttonNumber, value);
        }
    }

    public void clearPOV()
    {
        this.setPOV(-1);
    }

    public void setPOV(int value)
    {
        synchronized (this)
        {
            if (this.controller != null)
            {
                throw new RuntimeException("We shouldn't be attempting to set POV when using a controller");
            }

            this.pov = value;
        }
    }

    public void setAxis(int axis, double value)
    {
        synchronized (this)
        {
            if (this.controller != null)
            {
                throw new RuntimeException("We shouldn't be attempting to set an axis when using a controller");
            }

            this.axes.put(axis, value);
        }
    }
}
