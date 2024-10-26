package frc.lib.robotprovider;

/**
 * Represents a PowerDistributionHub (or PowerDistributionPanel) device
 */
public interface IPowerDistribution
{
    /**
     * Gets the voltage being provided by the battery
     * @return battery strength in volts
     */
    double getBatteryVoltage();

    /**
     * Gets the current being consumed by the provided channel
     * @param channel on the PDP/PDH
     * @return current in amperes
     */
    double getCurrent(int channel);

    /**
     * Gets the total current being consumed
     * @return current in amperes
     */
    double getTotalCurrent();

    /**
     * Gets the total energy being consumed
     * @return energy in Joules
     */
    double getTotalEnergy();

    /**
     * Gets the total power being consumed
     * @return power in Watts
     */
    double getTotalPower();

    /**
     * Gets the temperature of the PDH/PDP
     * @return temperature in degrees Celcius
     */
    double getTemperature();

    /**
     * Set the switchable channel output
     * @param enabled or disabled
     */
    void setSwitchableChannel(boolean enabled);
}
