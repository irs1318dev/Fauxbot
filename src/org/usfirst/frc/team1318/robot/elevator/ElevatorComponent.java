package org.usfirst.frc.team1318.robot.elevator;

import org.usfirst.frc.team1318.robot.common.wpilibmocks.IDigitalInput;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.IEncoder;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.IMotor;

import com.google.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public class ElevatorComponent
{
    private final IMotor motor;
    private final IEncoder encoder;

    @Inject
    public ElevatorComponent(
        @Named("ELEVATOR_MOTOR") IMotor motor,
        @Named("ELEVATOR_ENCODER") IEncoder encoder)
    {
        this.motor = motor;
        this.encoder = encoder;
    }

    public void setMotorPower(double value)
    {
        this.motor.set(value);
    }

    public double getElevatorHeight()
    {
        return this.encoder.getDistance();
    }
}
