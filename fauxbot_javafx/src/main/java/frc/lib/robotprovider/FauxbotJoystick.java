package frc.lib.robotprovider;

import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class FauxbotJoystick implements IJoystick
{
    private final Map<Integer, BooleanProperty> buttons;
    private final Map<Integer, DoubleProperty> axes;
    private IntegerProperty povProperty;

    public FauxbotJoystick(int port)
    {
        this.buttons = new HashMap<Integer, BooleanProperty>();
        this.axes = new HashMap<Integer, DoubleProperty>();
        this.povProperty = new SimpleIntegerProperty();

        FauxbotJoystickManager.set(port, this);
    }

    public boolean getRawButton(int buttonNumber)
    {
        if (!this.buttons.containsKey(buttonNumber))
        {
            return false;
        }

        BooleanProperty property = this.buttons.get(buttonNumber);
        boolean result = property.get();
        property.set(false);
        return result;
    }

    public boolean isConnected()
    {
        return true;
    }

    public int getPOV()
    {
        return this.povProperty.get();
    }

    public double getAxis(int relevantAxis)
    {
        if (!this.axes.containsKey(relevantAxis))
        {
            return 0.0;
        }

        return this.axes.get(relevantAxis).get();
    }

    public void setRumble(JoystickRumbleType type, double value)
    {
    }

    public BooleanProperty getButtonProperty(int buttonNumber)
    {
        if (!this.buttons.containsKey(buttonNumber))
        {
            this.buttons.put(buttonNumber, new SimpleBooleanProperty());
        }

        return this.buttons.get(buttonNumber);
    }

    public DoubleProperty getAxisProperty(int relevantAxis)
    {
        if (!this.axes.containsKey(relevantAxis))
        {
            this.axes.put(relevantAxis, new SimpleDoubleProperty());
        }

        return this.axes.get(relevantAxis);
    }

    public IntegerProperty getPovProperty()
    {
        return this.povProperty;
    }
}
