package frc.lib.robotprovider;

/**
 * Represents a NavX IMU device
 */
public interface INavx
{
    /**
     * Indicates whether the sensor is currently connected
     * to the host computer.  A connection is considered established
     * whenever communication with the sensor has occurred recently.
     *<p>
     * @return Returns true if a valid update has been recently received
     * from the sensor.
     */
    boolean isConnected();

    /**
     * Returns the total accumulated yaw angle (Z Axis, in degrees)
     * reported by the sensor.
     *<p>
     * NOTE: The angle is continuous, meaning it's range is beyond 360 degrees.
     * This ensures that algorithms that wouldn't want to see a discontinuity 
     * in the gyro output as it sweeps past 0 on the second time around.
     *<p>
     * Note that the returned yaw value will be offset by a user-specified
     * offset value; this user-specified offset value is set by 
     * invoking the zeroYaw() method.
     *<p>
     * @return The current total accumulated yaw angle (Z axis) of the robot 
     * in degrees. This heading is based on integration of the returned rate 
     * from the Z-axis (yaw) gyro.  Positive is clockwise (left-hand rule...).
     */
    double getAngle();

    double getPitch();

    double getRoll();

    double getYaw();

    /**
     * Returns the displacement (in meters) of the X axis since resetDisplacement()
     * was last invoked [Experimental].
     * 
     * NOTE:  This feature is experimental.  Displacement measures rely on double-integration
     * of acceleration values from MEMS accelerometers which yield "noisy" values.  The
     * resulting displacement are not known to be very accurate, and the amount of error 
     * increases quickly as time progresses.
     * @return Displacement since last reset (in meters).
     */
    double getDisplacementX();

    /**
     * Returns the displacement (in meters) of the Y axis since resetDisplacement()
     * was last invoked [Experimental].
     * 
     * NOTE:  This feature is experimental.  Displacement measures rely on double-integration
     * of acceleration values from MEMS accelerometers which yield "noisy" values.  The
     * resulting displacement are not known to be very accurate, and the amount of error 
     * increases quickly as time progresses.
     * @return Displacement since last reset (in meters).
     */
    double getDisplacementY();

    /**
     * Returns the displacement (in meters) of the Z axis since resetDisplacement()
     * was last invoked [Experimental].
     * 
     * NOTE:  This feature is experimental.  Displacement measures rely on double-integration
     * of acceleration values from MEMS accelerometers which yield "noisy" values.  The
     * resulting displacement are not known to be very accurate, and the amount of error 
     * increases quickly as time progresses.
     * @return Displacement since last reset (in meters).
     */
    double getDisplacementZ();

    /**
     * Reset the Yaw gyro.
     *<p>
     * Resets the Gyro Z (Yaw) axis to a heading of zero. This can be used if 
     * there is significant drift in the gyro and it needs to be recalibrated 
     * after it has been running.
     */
    void reset();

    /**
     * Zeros the displacement integration variables.   Invoke this at the moment when
     * integration begins.
     */
    void resetDisplacement();
}