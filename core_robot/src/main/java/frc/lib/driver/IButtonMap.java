package frc.lib.driver;

import frc.lib.driver.descriptions.*;

/**
 * Representation of a button map
 */
public interface IButtonMap
{
    /**
     * Retrieve the array of shift descriptions for the button map
     * @return array of shift descriptions
     */
    ShiftDescription[] getShiftSchema();
    
    /**
     * Retrieve the array of analog operation descriptions for the button map
     * @return array of analog operation descriptions
     */
    AnalogOperationDescription[] getAnalogOperationSchema();
    
    /**
     * Retrieve the array of digital operation descriptions for the button map
     * @return array of digital operation descriptions
     */
    DigitalOperationDescription[] getDigitalOperationSchema();

    /**
     * Retrieve the array of macro operation descriptions for the button map
     * @return array of macro operation descriptions
     */
    MacroOperationDescription[] getMacroOperationSchema();
}
