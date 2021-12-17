package frc.robot.driver.common;

import frc.robot.driver.common.descriptions.*;

class ButtonCombination
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
}