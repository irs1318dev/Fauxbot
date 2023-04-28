package frc.lib.robotprovider;

public interface IPigeon2
{
    /**
     * Get Yaw, Pitch, and Roll data.
     * @param ypr_deg array to fill with yaw[0], pitch[1], and roll[2] data. Yaw is within [-368,640, +368,640] degrees. Pitch is within [-90,+90] degrees. Roll is within [-90,+90] degrees.
     */
    void getYawPitchRoll(double[] ypr_deg);

    /**
     * Get Raw Gyro data.
     * @param xyz_dps Array to fill with x[0], y[1], and z[2] data in degrees per second.
     */
    void getRawGyro(double[] xyz_dps);

    /**
     * Sets the Yaw register to the specified value.
     * @param angleDeg New yaw in degrees [+/- 368,640 degrees]
     */
    void setYaw(double angleDeg);

    /**
     * Sets the update period for the YPR frames.
     * @param timeoutMs update period in milliseconds
     */
    void setYPRUpdatePeriod(int timeoutMs);

    /**
     * Sets the update period for the Gyro frames.
     * @param timeoutMs update period in milliseconds
     */
    void setGyroUpdatePeriod(int timeoutMs);
}
