package frc.robot.driver.common;

public class PathStep
{
    private double leftPosition;
    private double rightPosition;
    private double leftVelocity;
    private double rightVelocity;
    private double leftAcceleration;
    private double rightAcceleration;
    private double heading;

    public PathStep(double leftPosition, double rightPosition, double leftVelocity, double rightVelocity, double leftAcceleration, double rightAcceleration, double heading)
    {
        this.leftPosition = leftPosition;
        this.rightPosition = rightPosition;
        this.leftVelocity = leftVelocity;
        this.rightVelocity = rightVelocity;
        this.leftAcceleration = leftAcceleration;
        this.rightAcceleration = rightAcceleration;
        this.heading = heading;
    }

    public PathStep(double leftPosition, double rightPosition, double leftVelocity, double rightVelocity, double heading)
    {
        this(leftPosition, rightPosition, leftVelocity, rightVelocity, 0.0, 0.0, heading);
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

    public double getLeftAcceleration()
    {
        return this.leftAcceleration;
    }

    public double getRightAcceleration()
    {
        return this.rightAcceleration;
    }

    public double getHeading()
    {
        return this.heading;
    }
}
