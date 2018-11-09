package frc.team1318.robot.common.robotprovider;

public interface IJoystick
{
    double getAxis(AnalogAxis relevantAxis);

    int getPOV();

    boolean getRawButton(int value);
}
