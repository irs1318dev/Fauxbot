package frc.lib.driver.descriptions;

import frc.robot.TuningConstants;

import java.util.EnumSet;

import frc.lib.driver.AnalogAxis;
import frc.lib.driver.UserInputDeviceButton;
import frc.lib.driver.UserInputDevicePOV;
import frc.lib.helpers.ExceptionHelpers;
import frc.lib.robotprovider.IJoystick;
import frc.robot.driver.OperationContext;
import frc.robot.driver.Shift;

/**
 * Describes a modifier that will change the behavior of the buttons/axes on the controller device.
 */
public class ShiftDescription
{
    private final Shift shift;

    private final UserInputDevice userInputDevice;
    private final UserInputDeviceButton userInputDeviceButton;

    private final UserInputDevicePOV userInputDevicePovValue;

    private final AnalogAxis userInputDeviceAxis;
    private final double userInputDeviceAxisRangeMin;
    private final double userInputDeviceAxisRangeMax;

    private final EnumSet<OperationContext> relevantContexts;

    /**
     * Initializes a new ShiftDescription based on a user interaction
     * @param shift the identity of the shift being described
     * @param userInputDevice which device will indicate the shift (driver or codriver joystick) 
     * @param userInputDeviceButton the button on the device that performs the shift
     */
    public ShiftDescription(Shift shift, UserInputDevice userInputDevice, UserInputDeviceButton userInputDeviceButton)
    {
        this(
            shift,
            userInputDevice,
            userInputDeviceButton,
            UserInputDevicePOV.NONE,
            AnalogAxis.NONE,
            0.0,
            0.0,
            null);
    }

    /**
     * Initializes a new ShiftDescription based on a user interaction
     * @param shift the identity of the shift being described
     * @param userInputDevice which device will indicate the shift (driver or codriver joystick) 
     * @param userInputDeviceButton the button on the device that performs the shift
     * @param relevantContexts the contexts that should be considered when checking if we should use the shift
     */
    public ShiftDescription(
        Shift shift,
        UserInputDevice userInputDevice,
        UserInputDeviceButton userInputDeviceButton,
        EnumSet<OperationContext> relevantContexts)
    {
        this(
            shift,
            userInputDevice,
            userInputDeviceButton,
            UserInputDevicePOV.NONE,
            AnalogAxis.NONE,
            0.0,
            0.0,
            relevantContexts);
    }

    /**
     * Initializes a new ShiftDescription based on a user interaction
     * @param shift the identity of the shift being described
     * @param userInputDevice which device will indicate the shift (driver or codriver joystick) 
     * @param povValue the value of the POV (hat) used to perform the shift
     */
    public ShiftDescription(
        Shift shift,
        UserInputDevice userInputDevice,
        UserInputDevicePOV povValue)
    {
        this(
            shift,
            userInputDevice,
            UserInputDeviceButton.POV,
            povValue,
            AnalogAxis.NONE,
            0.0,
            0.0,
            null);
    }

    /**
     * Initializes a new ShiftDescription based on a user interaction
     * @param shift the identity of the shift being described
     * @param userInputDevice which device will indicate the shift (driver or codriver joystick) 
     * @param povValue the value of the POV (hat) used to perform the shift
     * @param relevantContexts the contexts that should be considered when checking if we should use the shift
     */
    public ShiftDescription(
        Shift shift,
        UserInputDevice userInputDevice,
        UserInputDevicePOV povValue,
        EnumSet<OperationContext> relevantContexts)
    {
        this(
            shift,
            userInputDevice,
            UserInputDeviceButton.POV,
            povValue,
            AnalogAxis.NONE,
            0.0,
            0.0,
            relevantContexts);
    }

    /**
     * Initializes a new ShiftDescription based on a user interaction on an axis
     * @param shift the identity of the shift being described
     * @param userInputDevice which device will indicate the shift (driver or codriver joystick)
     * @param analogAxis the analog axis used to perform the shift
     * @param axisRangeMinValue the min value of the range that triggers the shift
     * @param axisRangeMaxValue the max value of the range that triggers the shift
     */
    public ShiftDescription(
        Shift shift,
        UserInputDevice userInputDevice,
        AnalogAxis analogAxis,
        double axisRangeMinValue,
        double axisRangeMaxValue)
    {
        this(
            shift,
            userInputDevice,
            UserInputDeviceButton.ANALOG_AXIS_RANGE,
            UserInputDevicePOV.NONE,
            analogAxis,
            axisRangeMinValue,
            axisRangeMaxValue,
            null);
    }

    /**
     * Initializes a new ShiftDescription based on a user interaction on an axis
     * @param shift the identity of the shift being described
     * @param userInputDevice which device will indicate the shift (driver or codriver joystick)
     * @param analogAxis the analog axis used to perform the shift
     * @param axisRangeMinValue the min value of the range that triggers the shift
     * @param axisRangeMaxValue the max value of the range that triggers the shift
     * @param relevantContexts the contexts that should be considered when checking if we should use the shift
     */
    public ShiftDescription(
        Shift shift,
        UserInputDevice userInputDevice,
        AnalogAxis analogAxis,
        double axisRangeMinValue,
        double axisRangeMaxValue,
        EnumSet<OperationContext> relevantContexts)
    {
        this(
            shift,
            userInputDevice,
            UserInputDeviceButton.ANALOG_AXIS_RANGE,
             UserInputDevicePOV.NONE,
             analogAxis,
             axisRangeMinValue,
             axisRangeMaxValue,
             relevantContexts);
    }

    private ShiftDescription(
        Shift shift,
        UserInputDevice userInputDevice,
        UserInputDeviceButton userInputDeviceButton,
        UserInputDevicePOV povValue,
        AnalogAxis analogAxis,
        double axisRangeMinValue,
        double axisRangeMaxValue,
        EnumSet<OperationContext> relevantContexts)
    {
        this.shift = shift;
        this.userInputDevice = userInputDevice;
        this.userInputDeviceButton = userInputDeviceButton;
        this.userInputDevicePovValue = povValue;
        this.userInputDeviceAxis = analogAxis;
        this.userInputDeviceAxisRangeMin = axisRangeMinValue;
        this.userInputDeviceAxisRangeMax = axisRangeMaxValue;
        this.relevantContexts = relevantContexts;
    }

    public Shift getShift()
    {
        return this.shift;
    }

    public UserInputDevice getUserInputDevice()
    {
        return this.userInputDevice;
    }

    public UserInputDeviceButton getUserInputDeviceButton()
    {
        return this.userInputDeviceButton;
    }

    public UserInputDevicePOV getUserInputDevicePovValue()
    {
        return this.userInputDevicePovValue;
    }

    public EnumSet<OperationContext> getRelevantContexts()
    {
        return this.relevantContexts;
    }

    public boolean checkInput(IJoystick[] joysticks, OperationContext currentContext)
    {
        UserInputDevice userInputDevice = this.getUserInputDevice();
        IJoystick relevantJoystick = null;
        if (userInputDevice != UserInputDevice.None)
        {
            relevantJoystick = joysticks[userInputDevice.getId()];
        }

        if (relevantJoystick == null || !relevantJoystick.isConnected())
        {
            ExceptionHelpers.Assert(TuningConstants.EXPECT_UNUSED_JOYSTICKS, "Unexpected user input device " + userInputDevice.toString());
            return false;
        }

        if (this.relevantContexts != null && !this.relevantContexts.contains(currentContext))
        {
            return false;
        }

        // find the appropriate button and grab the value from the relevant joystick
        UserInputDeviceButton relevantButton = this.getUserInputDeviceButton();

        if (relevantButton == UserInputDeviceButton.POV)
        {
            return relevantJoystick.getPOV() == this.getUserInputDevicePovValue().Value;
        }
        else if (relevantButton == UserInputDeviceButton.ANALOG_AXIS_RANGE)
        {
            double value = relevantJoystick.getAxis(this.userInputDeviceAxis.Value);
            return
                value >= this.userInputDeviceAxisRangeMin &&
                value <= this.userInputDeviceAxisRangeMax;
        }
        else if (relevantButton != UserInputDeviceButton.NONE)
        {
            return relevantJoystick.getRawButton(relevantButton.Value);
        }
        else
        {
            return false;
        }
    }
}
