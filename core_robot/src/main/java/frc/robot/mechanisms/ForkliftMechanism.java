package frc.robot.mechanisms;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.DoubleSolenoidValue;
import frc.lib.robotprovider.IDoubleSolenoid;
import frc.lib.robotprovider.IMotor;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.RobotMode;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;

@Singleton
public class ForkliftMechanism implements IMechanism {

    private final IDriver driver;
    private final IMotor leftDriveTrain;
    private final IMotor rightDriveTrain;
    private final IDoubleSolenoid lifter;

    @Inject
    public ForkliftMechanism(IRobotProvider provider, IDriver driver) {
        this.leftDriveTrain = provider.getTalon(0);
        this.rightDriveTrain = provider.getTalon(1);
        this.lifter = provider.getDoubleSolenoid(null, 7, 8);
        this.driver = driver;
    }

    @Override
    public void readSensors() {
        
    }

    @Override
    public void update(RobotMode mode) {
        double leftDriveTrainPower = this.driver.getAnalog(AnalogOperation.LeftPower);
        double rightDriveTrainPower = this.driver.getAnalog(AnalogOperation.RightPower);
        boolean lifterUp = this.driver.getDigital(DigitalOperation.ForkliftUp);
        boolean lifterDown = this.driver.getDigital(DigitalOperation.ForkliftDown);

        this.leftDriveTrain.set(leftDriveTrainPower);
        this.rightDriveTrain.set(-rightDriveTrainPower);
        if (lifterUp) {
            this.lifter.set(DoubleSolenoidValue.Forward);
        }
        if (lifterDown) {
            this.lifter.set(DoubleSolenoidValue.Reverse);
        }
    }

    @Override
    public void stop() {
        this.leftDriveTrain.set(0.0);
        this.rightDriveTrain.set(0.0);
        this.lifter.set(DoubleSolenoidValue.Off);
    }


}
