package frc.robot.mechanisms;

import com.google.inject.Inject;

import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.DoubleSolenoidValue;
import frc.lib.robotprovider.IDoubleSolenoid;
import frc.lib.robotprovider.IMotor;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.ITalonSRX;
import frc.lib.robotprovider.RobotMode;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;

public class ForkliftMechanism implements IMechanism {

    private final IDriver driver;
    private final IMotor driveTrainLeft;
    private final IMotor driveTrainRight;
    private final IDoubleSolenoid lifter;

    @Inject 
    public ForkliftMechanism(IDriver driver, IRobotProvider provider) {
        this.driver = driver;
        this.driveTrainLeft= provider.getTalon();
        this.driveTrainRight = ;
        this.lifter = lifter;
    }

    @Override
    public void readSensors() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'readSensors'");
    }

    @Override
    public void update(RobotMode mode) {
        double driveTrainLeftAmount = this.driver.getAnalog(AnalogOperation.driveTrainLeft);
        this.driveTrainLeft.set(driveTrainLeftAmount);

        double driveTrainRightAmount = this.driver.getAnalog(AnalogOperation.driveTrainRight);
        this.driveTrainRight.set(driveTrainRightAmount);

        boolean shouldForkliftUp = this.driver.getDigital(DigitalOperation.forkliftUp);
        if (shouldForkliftUp) {
            this.lifter.set(DoubleSolenoidValue.Forward);
        }

        boolean shouldForkliftDown = this.driver.getDigital(DigitalOperation.forkliftDown);
        if (shouldForkliftDown) {
            this.lifter.set(DoubleSolenoidValue.Reverse);
        }
    }

    @Override
    public void stop() {
        this.driveTrainLeft.set(0.0);
        this.driveTrainRight.set(0.0);
        this.lifter.set(DoubleSolenoidValue.Off);
    }
    
}
