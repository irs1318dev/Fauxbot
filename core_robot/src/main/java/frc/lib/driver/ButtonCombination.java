package frc.lib.driver;

import frc.lib.driver.descriptions.*;

class ButtonCombination implements Comparable<ButtonCombination>
{
    public final UserInputDevice device;
    public final UserInputDeviceButton button;
    public final int pov;
    public final AnalogAxis axis;

    public ButtonCombination(UserInputDevice device, UserInputDeviceButton button, int pov, AnalogAxis axis)
    {
        this.device = device;
        this.button = button;
        this.pov = pov;
        this.axis = axis;
    }

    @Override
    public int hashCode()
    {
        return this.device.hashCode() ^ this.button.hashCode() ^ this.pov ^ this.axis.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof ButtonCombination))
        {
            return false;
        }

        ButtonCombination other = (ButtonCombination)obj;
        return (this.device == other.device &&
            this.button == other.button &&
            this.pov == other.pov &&
            this.axis == other.axis);
    }

    @Override
    public String toString()
    {
        if (this.button == UserInputDeviceButton.NONE)
        {
            return String.format("%s joystick, %s Axis", this.device, this.axis);
        }

        if (this.button == UserInputDeviceButton.ANALOG_AXIS_RANGE && this.axis != AnalogAxis.NONE)
        {
            return String.format("%s joystick, %s Axis (range)", this.device, this.axis);
        }

        if (this.button == UserInputDeviceButton.POV)
        {
            return String.format("%s joystick, POV %d", this.device, this.pov);
        }

        return String.format("%s joystick, %s button", this.device, this.button);
    }

    @Override
    public int compareTo(ButtonCombination o)
    {
        if (o == null)
        {
            return 1;
        }

        int deviceComparison = this.device.compareTo(o.device);
        if (deviceComparison != 0)
        {
            return deviceComparison;
        }

        int buttonComparison = this.button.compareTo(o.button);
        if (buttonComparison != 0)
        {
            return buttonComparison;
        }

        int povComparison = Integer.compare(this.pov, o.pov);
        if (povComparison != 0)
        {
            return povComparison;
        }

        int axisComparison = this.axis.compareTo(o.axis);
        return axisComparison;
    }
}
