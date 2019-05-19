package frc.robot.driver.common;

public class PathStep
{
    private double leftPosition;
    private double rightPosition;
    private double leftVelocity;
    private double rightVelocity;
    private double heading;

    public PathStep(double leftPosition, double rightPosition, double leftVelocity, double rightVelocity, double heading)
    {
        this.leftPosition = leftPosition;
        this.rightPosition = rightPosition;
        this.leftVelocity = leftVelocity;
        this.rightVelocity = rightVelocity;
        this.heading = heading;
    }

    public double getLeftPosition()
    {
        return this.leftPosition;
    }

    public double getRightPosition()
    {
        return this.rightPosition;
    }

    public double getLeftVelocity()
    {
        return this.leftVelocity;
    }

    public double getRightVelocity()
    {
        return this.rightVelocity;
    }

    public double getHeading()
    {
        return this.heading;
    }
}
