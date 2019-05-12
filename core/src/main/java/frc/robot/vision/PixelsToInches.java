package frc.robot.vision;

public class PixelsToInches{
    private final double inches;
    private final int pixels;

    public PixelsToInches(int pixels, double inches)
    {
        this.inches = inches;
        this.pixels = pixels;
    }

    public double getInches()
    {
        return this.inches;
    }
    public int getPixels()
    {
        return this.pixels;
    }
}