package frc.robot.common.robotprovider;

public class ColorSensorV3Wrapper implements IColorSensorV3
{
    public ColorSensorV3Wrapper()
    {
    }

    /**
     * Get the raw proximity value from the sensor ADC (11 bit). This value 
     * is largest when an object is close to the sensor and smallest when 
     * far away.
     * 
     * @return Proximity measurement value, ranging from 0 to 2047
     */
    @Override
    public int getProximity()
    {
        return 0;
    }

    /**
     * Get the raw color values from their respective ADCs (20-bit).
     * 
     * @return ColorValues struct containing red, green, blue and IR values
     */
    @Override
    public RawColorRGBIR getRawColor()
    {
        return new RawColorRGBIR(0, 0, 0, 0);
    }

    /**
     * Starts checking the color sensor for colors
     */
    public void start()
    {
    }

    /**
     * Stops checking the color sensor for colors
     */
    public void stop()
    {
    }
}
