package frc.robot.common.robotprovider;

public interface IJoystick
{
    double getAxis(int relevantAxis);

    int getPOV();

    boolean getRawButton(int value);
}
