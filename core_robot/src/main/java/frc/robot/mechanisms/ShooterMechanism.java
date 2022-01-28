package frc.robot.mechanisms;

import javax.inject.Singleton;

import frc.robot.ElectronicsConstants;
import frc.robot.HardwareConstants;
import frc.robot.LoggingKey;
import frc.robot.TuningConstants;
import frc.robot.common.IMechanism;
import frc.robot.common.LoggingManager;
import frc.robot.common.robotprovider.*;
import frc.robot.driver.*;
import frc.robot.driver.common.IDriver;

import com.google.inject.Inject;

@Singleton
public class ShooterMechanism implements IMechanism
{
    private final IDriver driver;
    private final ILogger logger;
    private final ITalonSRX angleMotor;
    private final ITalonSRX flyWheelMotor;
    private final IDoubleSolenoid kicker;

    @Inject
    public ShooterMechanism(IDriver driver, LoggingManager logger, IRobotProvider provider)
    {
        this.driver = driver;
        this.logger = logger;

        this.angleMotor = provider.getTalonSRX(ElectronicsConstants.SHOOTER_ANGLE_MOTOR_CAN_ID);
        this.angleMotor.setSensorType(TalonXFeedbackDevice.QuadEncoder);
        this.angleMotor.setControlMode(TalonXControlMode.Position);
        this.angleMotor.setPIDF(
            TuningConstants.SHOOTER_ANGLE_MOTOR_KP,
            TuningConstants.SHOOTER_ANGLE_MOTOR_KI,
            TuningConstants.SHOOTER_ANGLE_MOTOR_KD,
            TuningConstants.SHOOTER_ANGLE_MOTOR_KF,
            0);

        this.flyWheelMotor = provider.getTalonSRX(ElectronicsConstants.SHOOTER_FLY_WHEEL_MOTOR_CAN_ID);
        this.flyWheelMotor.setSensorType(TalonXFeedbackDevice.QuadEncoder);
        this.flyWheelMotor.setControlMode(TalonXControlMode.Velocity);
        this.flyWheelMotor.setPIDF(
            TuningConstants.SHOOTER_FLY_WHEEL_MOTOR_KP,
            TuningConstants.SHOOTER_FLY_WHEEL_MOTOR_KI,
            TuningConstants.SHOOTER_FLY_WHEEL_MOTOR_KD,
            TuningConstants.SHOOTER_FLY_WHEEL_MOTOR_KF,
            0);

        this.kicker = provider.getDoubleSolenoid(PneumaticsModuleType.PneumaticsControlModule, ElectronicsConstants.SHOOTER_KICKER_FORWARD_PCM_CHANNEL, ElectronicsConstants.SHOOTER_KICKER_BACKWARD_PCM_CHANNEL);
    }

    @Override
    public void readSensors()
    {
        this.logger.logNumber(LoggingKey.ShooterAngle, this.angleMotor.getPosition());
        this.logger.logNumber(LoggingKey.ShooterSpeed, this.flyWheelMotor.getVelocity());
    }

    @Override
    public void update()
    {
        double desiredAngle = this.driver.getAnalog(AnalogOperation.ShooterAngle);
        desiredAngle = ((desiredAngle + 1.0) / 2.0) * (HardwareConstants.SHOOTER_MAX_ANGLE - HardwareConstants.SHOOTER_MIN_ANGLE) + HardwareConstants.SHOOTER_MIN_ANGLE;
        this.angleMotor.set(desiredAngle);

        double spinSpeed = 0.0;
        if (this.driver.getDigital(DigitalOperation.ShooterSpin))
        {
            spinSpeed = TuningConstants.SHOOTER_SPIN_SPEED;
        }

        this.flyWheelMotor.set(spinSpeed);

        boolean kick = false;
        if (this.driver.getDigital(DigitalOperation.ShooterFire))
        {
            kick = true;
        }

        this.kicker.set(kick ? DoubleSolenoidValue.Forward : DoubleSolenoidValue.Reverse);
    }

    @Override
    public void stop()
    {
        this.angleMotor.stop();
        this.flyWheelMotor.stop();
        this.kicker.set(DoubleSolenoidValue.Off);
    }
}