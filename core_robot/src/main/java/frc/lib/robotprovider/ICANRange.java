package frc.lib.robotprovider;

public interface ICANRange
{
    /**
     * Sets the ToF Field of View parameters for the CANrange.
     * The exact values for the center and range may be different for different CANrange devices due to imperfections in the sensing silicon.
     * The magnitude of each range component is capped to abs(27 - 2*FOVCenterX).
     * 
     * @param centerX Specifies the target center of the Field of View in the X direction. -11.8-11.8deg, default 0deg
     * @param centerY Specifies the target center of the Field of View in the Y direction. -11.8-11.8deg, default 0deg
     * @param rangeX  Specifies the target range of the Field of View in the X direction. This is the full range of the FOV. 6.75-27deg, default 27deg
     * @param rangeY  Specifies the target range of the Field of View in the Y direction. This is the full range of the FOV. 6.75-27deg, default 27deg
     */
    public void setFovParams(double centerX, double centerY, double rangeX, double rangeY);

    /**
     * Sets the ToF Proximity detection parameters for the CANrange.
     * 
     * @param proximityThreshold                   Threshold for object detection. 0-4m, default 0.4m
     * @param proximityHysteresis                  How far above and below the threshold the distance needs to be to trigger undetected and detected,
     *                                             respectively. This is used to prevent bouncing between the detected and undetected states for objects
     *                                             on the threshold. If the threshold is set to 0.1 meters, and the hysteresis is 0.01 meters, then an
     *                                             object needs to be within 0.09 meters to be detected. After the object is first detected, the distance
     *                                             then needs to exceed 0.11 meters to become undetected again. 0-1m, default 0.01m
     * @param minSignalStrengthForValidMeasurement The minimum allowable signal strength before determining the measurement is valid. If the signal
     *                                             strength is particularly low, this typically means the object is far away and there's fewer total
     *                                             samples to derive the distance from. Set this value to be below the lowest strength you see when you're
     *                                             detecting an object with the CANrange; the default of 2500 is typically acceptable in most cases.
     *                                             1-15000, default 2500.
     */
    public void setProximityParams(double proximityThreshold, double proximityHysteresis, double minSignalStrengthForValidMeasurement);

    /**
     * Sets the Time-of-Flight parameters for the CANrange.
     * 
     * @param updateFrequency Rate at which the CANrange will take measurements. A lower frequency may provide more stable readings but will reduce the
     *                        data rate of the sensor. 5-50 Hz, default 50 Hz
     * @param updateMode      Update mode of the CANrange. The CANrange supports short-range and long-range detection at various update frequencies.
     */
    public void setToFParams(double updateFrequency, CANRangeUpdateMode updateMode);

    /**
     * Gets the distance to the nearest object in the configured field of view of the CANrange, in inches.
     * Default Rates:
     * CAN 2.0: 100.0 Hz
     * CAN FD: 100.0 Hz (TimeSynced with Pro)
     * 
     * @return distance in inches, from 0.0 to 2580.11811
     */
    public double getDistance();

    /**
     * Gets the Standard Deviation of the distance measurement, in inches
     * Default Rates:
     * CAN 2.0: 20.0 Hz
     * CAN FD: 100.0 Hz (TimeSynced with Pro)
     * 
     * @return standard deviation of distance in inches, from 0.0 to 51.6023622
     */
    public double getDistanceStdDev();

    /**
     * Gets whether the CANrange detects an object using the configured proximity parameters.
     * Default Rates:
     * CAN 2.0: 100.0 Hz
     * CAN FD: 100.0 Hz (TimeSynced with Pro)
     * 
     * @return true if an object is detected, false otherwise
     */
    public Boolean getIsDetected();
}
