package frc.lib.mechanisms;

/**
 * Generic interface for interacting with an IMU (Inertial Measurement Unit)
 */
public interface IIMUManager extends IMechanism
{
    /**
     * Retrieve whether the device is connected
     * @return whether the device is connected
     */
    boolean getIsConnected();

    /**
     * Retrieve the current angle (counter-clockwise) in degrees
     * @return the current angle in degrees
     */
    double getYaw();
}
