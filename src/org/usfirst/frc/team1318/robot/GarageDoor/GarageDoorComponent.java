package org.usfirst.frc.team1318.robot.GarageDoor;

import org.usfirst.frc.team1318.robot.ElectronicsConstants;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Victor;

public class GarageDoorComponent
{
    private DigitalInput throughBeamSensor;
    private DigitalInput openSensor;
    private DigitalInput closedSensor;

    private Victor motor;

    public GarageDoorComponent()
    {
        this.throughBeamSensor = new DigitalInput(ElectronicsConstants.GARAGEDOOR_THROUGHBEAM_CHANNEL);
        this.openSensor = new DigitalInput(ElectronicsConstants.GARAGEDOOR_OPENSENSOR_CHANNEL);
        this.closedSensor = new DigitalInput(ElectronicsConstants.GARAGEDOOR_CLOSEDSENSOR_CHANNEL);
        
        this.motor = new Victor(ElectronicsConstants.GARAGEDOOR_MOTOR_CHANNEL);
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
