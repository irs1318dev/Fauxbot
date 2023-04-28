package frc.lib.mechanisms;

public interface IPositionManager extends IMechanism
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
