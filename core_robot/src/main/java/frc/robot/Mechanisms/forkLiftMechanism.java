package frc.robot.mechanisms;

import frc.lib.mechanisms.IMechanism;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import frc.lib.driver.IDriver;
import frc.lib.robotprovider.*;
import frc.robot.ElectronicsConstants;
import frc.robot.LoggingKey;
import frc.robot.TuningConstants;
import frc.robot.driver.*;

@Singleton
public class ForkLiftMechanism implements IMechanism {
    // Lifter
    private final IDoubleSolenoid lifter;
    // Motors
    private final IMotor rightMotor;
    private final IMotor leftMotor;
    // Drivers
    private final IDriver driver;

    @Inject
    public ForkLiftMechanism(IRobotProvider provider, IDriver driver) {
        this.driver = driver;
        this.leftMotor = provider.getTalon(ElectronicsConstants.FORKLIFT_LEFT_MOTOR_CHANNEL);
        this.rightMotor = provider.getTalon(ElectronicsConstants.FORKLIFT_RIGHT_MOTOR_CHANNEL);
        this.lifter = provider.getDoubleSolenoid(
            PneumaticModuleType.PneumaticsControlModule,
            ElectronicsConstants.FORKLIFT_DOUBLE_SOLENOID_FORWARD,
            ElectronicsConstants.FORKLIFT_DOUBLE_SOLENOID_REVERSE
        );
    }

    @Override
    public void readSensors() {
        // empty
    }

    @Override
    public void update(RobotMode mode) {
        // Lifter control based on button presses
        if (this.driver.getDigitalOperation(DigitalOperation.LifterUp)) {
            this.lifter.set(DoubleSolenoidValue.Forward);
        } else if (this.driver.getDigitalOperation(DigitalOperation.LifterDown)) {
            this.lifter.set(DoubleSolenoidValue.Reverse);
        }

        // Motor control based on analog input
        if (this.driver.getAnalogOperation(AnalogOperation.TurnRight)) {
            this.rightMotor.set(1.0);
        } else if (this.driver.getAnalogOperation(AnalogOperation.TurnLeft)) {
            this.leftMotor.set(1.0);
        }
    }

    @Override
    public void stop() {
        this.leftMotor.set(0.0);  // Stop the left motor
        this.rightMotor.set(0.0);  // Stop the right motor
        this.lifter.set(DoubleSolenoidValue.Off);  // Stop the solenoid
    }
}