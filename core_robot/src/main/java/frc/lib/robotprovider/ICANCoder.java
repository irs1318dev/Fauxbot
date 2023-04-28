package frc.lib.robotprovider;

public interface ICANCoder
{
    /**
     * Gets the position of the sensor.  This may be relative or absolute depending on configuration.
     * The units are determined by the coefficient and unit-string configuration params, default is degrees.
     * @return The position of the sensor.
     */
    double getPosition();

    /**
     * Gets the velocity of the sensor.
     * The units are determined by the coefficient and unit-string configuration params, default is degrees per second.
     * @return The Velocity of the sensor.
     */
    double getVelocity();

    /**
     * Gets the absolute position of the sensor.
     * The absolute position may be unsigned (for example: [0,360) deg), or signed (for example: [-180,+180) deg).  This is determined by a configuration.  The default selection is unsigned.
     * The units are determined by the coefficient and unit-string configuration params, default is degrees.
     * Note: this signal is not affected by calls to SetPosition().
     * @return The position of the sensor.
     */
    double getAbsolutePosition();

    /**
     * Sets the position of the sensor.
     * The units are determined by the coefficient and unit-string configuration params, default is degrees.
     * @param newPosition to use
     */
    void setPosition(double newPosition);

    /**
     * Choose which direction is interpreted as positive displacement.
     * This affects both "Position" and "Absolute Position".
     * @param clockwisePositive
     *            False (default) means positive rotation occurs when magnet
     *            is spun counter-clockwise when observer is facing the LED side of CANCoder.
     */
    void configSensorDirection(boolean clockwisePositive);

    /**
     * Sets the signage and range of the "Absolute Position" signal.
     * @param useZeroToThreeSixty whether to use a range of [0, 360), or [-180, 180)
     */
    void configAbsoluteRange(boolean useZeroToThreeSixty);

    /**
     * Adjusts the zero point for the absolute position register.
     * The absolute position of the sensor will always have a discontinuity (360 -> 0 deg) or (+180 -> -180)
     * and a hard-limited mechanism may have such a discontinuity in its functional range.
     * In which case use this config to move the discontinuity outside of the function range.
     * @param offsetDegrees
     *            Offset in degrees (unit string and coefficient DO NOT apply for this config).
     */
    void configMagnetOffset(double offsetDegrees);
}