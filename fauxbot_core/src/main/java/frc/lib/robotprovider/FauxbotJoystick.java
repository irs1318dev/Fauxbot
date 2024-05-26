package frc.lib.robotprovider;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.controllers.Controller;

public class FauxbotJoystick implements IJoystick
{
    // Sadly, the button mapping isn't consistent between what WPILib wants and what SDL wants.
    // Map of buttonNumber/axis passed to IJoystick API to the corresponding GDX Controller button number
    // Also note that for POV, up == 11, down == 12, left == 13, right == 14.
    private static int[] XBONE_BUTTON_MAP =
        new int[]
        {
            -1, // (No such button)
            0, // XBONE_A_BUTTON(1),
            1, // XBONE_B_BUTTON(2),
            2, // XBONE_X_BUTTON(3),
            3, // XBONE_Y_BUTTON(4),
            9, // XBONE_LEFT_BUTTON(5), // LB
            10, // XBONE_RIGHT_BUTTON(6), // RB
            4, // XBONE_SELECT_BUTTON(7), // aka "View", the one with the squares
            6, // XBONE_START_BUTTON(8), // aka "Menu", the hamburgler one
            7, // XBONE_LEFT_STICK_BUTTON(9), // LS
            8, // XBONE_RIGHT_STICK_BUTTON(10), // RS
        };
    private static int[] XBONE_AXIS_MAP =
        new int[]
        {
            0, // XBONE_LSX(0),
            1, // XBONE_LSY(1),
            4, // XBONE_LT(2),
            5, // XBONE_RT(3),
            2, // XBONE_RSX(4),
            3, // XBONE_RSY(5),
        };

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
                return this.controller.getButton(FauxbotJoystick.XBONE_BUTTON_MAP[buttonNumber]);
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
                // for XBONE: for POV, up == 11, down == 12, left == 13, right == 14.
                if (this.controller.getButton(11))
                {
                    return 0;
                }
                else if (this.controller.getButton(12))
                {
                    return 180;
                }
                else if (this.controller.getButton(13))
                {
                    return 270;
                }
                else if (this.controller.getButton(14))
                {
                    return 90;
                }
                else
                {
                    return -1;
                }
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
                return this.controller.getAxis(FauxbotJoystick.XBONE_AXIS_MAP[relevantAxis]);
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
