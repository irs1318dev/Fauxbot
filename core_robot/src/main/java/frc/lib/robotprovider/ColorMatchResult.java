package frc.lib.robotprovider;

public class ColorMatchResult
{
    private String name;
    private double confidence;

    public ColorMatchResult(String name, double confidence)
    {
        this.name = name;
        this.confidence = confidence;
    }

    public String getName()
    {
        return this.name;
    }

    public double getConfidence()
    {
        return this.confidence;
    }
}
