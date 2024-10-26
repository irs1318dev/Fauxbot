package frc.lib.robotprovider;

/**
 * Represents Analog Input for reading voltage
 */
public interface IAnalogInput
{
    /**
     * Retreives the voltage from this input
     * @return current voltage
     */
    double getVoltage();
}
