package frc.robot.common.robotprovider;

public interface IColorSensorV3
{
    /**
     * Get the raw proximity value from the sensor ADC (11 bit). This value 
     * is largest when an object is close to the sensor and smallest when 
     * far away.
     * 
     * @return Proximity measurement value, ranging from 0 to 2047
     */
    int getProximity();

    /**
     * Get the raw color values from their respective ADCs (20-bit).
     * 
     * @return ColorValues struct containing red, green, blue and IR values
     */
    RawColorRGBIR getRawColor();

    /**
     * Starts checking the color sensor for colors
     */
    void start();

    /**
     * Stops checking the color sensor for colors
     */
    void stop();
}
