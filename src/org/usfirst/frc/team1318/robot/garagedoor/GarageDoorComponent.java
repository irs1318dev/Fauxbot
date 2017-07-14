package org.usfirst.frc.team1318.robot.garagedoor;

import org.usfirst.frc.team1318.robot.common.wpilibmocks.IDigitalInput;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.IMotor;

import com.google.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public class GarageDoorComponent
{
    private IDigitalInput throughBeamSensor;
    private IDigitalInput openSensor;
    private IDigitalInput closedSensor;

    private IMotor motor;

    @Inject
    public GarageDoorComponent(
        @Named("GARAGEDOOR_MOTOR") IMotor motor,
        @Named("GARAGEDOOR_THROUGHBEAM_SENSOR") IDigitalInput throughBeamSensor,
        @Named("GARAGEDOOR_OPEN_SENSOR") IDigitalInput openSensor,
        @Named("GARAGEDOOR_CLOSED_SENSOR") IDigitalInput closedSensor)
    {
        this.motor = motor;
        this.throughBeamSensor = throughBeamSensor;
        this.openSensor = openSensor;
        this.closedSensor = closedSensor;
    }

    public void setMotorPower(double value)
    {
        this.motor.set(value);
    }

    public boolean isBlocked()
    {
        return this.throughBeamSensor.get();
    }

    public boolean isOpen()
    {
        return this.openSensor.get();
    }

    public boolean isClosed()
    {
        return this.closedSensor.get();
    }
}
