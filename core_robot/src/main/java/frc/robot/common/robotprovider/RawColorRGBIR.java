package frc.robot.common.robotprovider;

public class RawColorRGBIR
{
    private final int red;
    private final int green;
    private final int blue;
    private final int ir;

    public RawColorRGBIR(int red, int green, int blue, int ir)
    {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.ir = ir;
    }

    /**
     * Gets the red component of this color
     * 
     * @return the amount of red detected in the sample, from 0 to 1048575
     */
    public int getRed()
    {
        return this.red;
    }

    /**
     * Gets the green component of this color
     * 
     * @return the amount of green detected in the sample, from 0 to 1048575
     */
    public int getGreen()
    {
        return this.green;
    }

    /**
     * Gets the blue component of this color
     * 
     * @return the amount of blue detected in the sample, from 0 to 1048575
     */
    public int getBlue()
    {
        return this.blue;
    }

    /**
     * Gets the infrared component of this color
     * 
     * @return the amount of infrared detected in the sample, from 0 to 1048575
     */
    public int getIR()
    {
        return this.ir;
    }
}
