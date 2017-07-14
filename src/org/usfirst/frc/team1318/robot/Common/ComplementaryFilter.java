package org.usfirst.frc.team1318.robot.Common;

public class ComplementaryFilter
{
    private final double kO;
    private final double kN;

    private double currentValue; 

    public ComplementaryFilter(double kO, double kN)
    {
        this(kO, kN, 0.0);
    }

    public ComplementaryFilter(double kO, double kN, double startingValue)
    {
        this.kO = kO;
        this.kN = kN;

        this.currentValue = startingValue;
    }

    public double getValue()
    {
        return this.currentValue;
    }
    
    public void update(double value)
    {
        this.currentValue = this.currentValue * this.kO + value * this.kN;
    }
}
