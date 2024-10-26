package frc.lib.robotprovider;

/**
 * Represents a CANCoder device
 */
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
}