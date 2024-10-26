package frc.lib.robotprovider;

/**
 * Represents a Pigeon2 IMU device
 */
public interface IPigeon2
{
    /**
     * Get Yaw, Pitch, and Roll data.
     * @param ypr_deg array to fill with yaw[0], pitch[1], and roll[2] data. Yaw is within [-368,640, +368,640] degrees. Pitch is within [-90,+90] degrees. Roll is within [-90,+90] degrees.
     */
    void getYawPitchRoll(double[] ypr_deg);

    /**
     * Get Roll, Pitch, and Yaw Rate data.
     * @param xyz_dps Array to fill with x[0], y[1], and z[2] data in degrees per second.
     */
    void getRollPitchYawRates(double[] xyz_dps);

    /**
     * Sets the Yaw register to the specified value.
     * @param angleDeg New yaw in degrees [+/- 368,640 degrees]
     */
    void setYaw(double angleDeg);

    /**
     * Sets the update frequency for the YPR data.
     * @param frequencyHz frequency in #times/sec
     */
    void setYPRUpdateFrequency(double frequencyHz);

    /**
     * Sets the update frequency for the RPY Rate data.
     * @param frequencyHz frequency in #times/sec
     */
    void setRPYRateUpdateFrequency(double frequencyHz);
}
