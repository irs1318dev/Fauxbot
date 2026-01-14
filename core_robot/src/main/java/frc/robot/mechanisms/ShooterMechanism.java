package frc.robot.mechanisms;

import com.google.inject.Inject;

import frc.lib.controllers.PIDHandler;
import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.DoubleSolenoidValue;
import frc.lib.robotprovider.IDoubleSolenoid;
import frc.lib.robotprovider.IEncoder;
import frc.lib.robotprovider.IMotor;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.ITalonSRX;
import frc.lib.robotprovider.ITimer;
import frc.lib.robotprovider.PneumaticsModuleType;
import frc.lib.robotprovider.RobotMode;
import frc.lib.robotprovider.TalonSRXControlMode;
import frc.lib.robotprovider.TalonSRXFeedbackDevice;
import frc.robot.ElectronicsConstants;
import frc.robot.TuningConstants;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;

public class ShooterMechanism implements IMechanism
{
    private final IDriver driver;
    private final ITalonSRX hood;
    private final ITalonSRX flywheel;
    private final IDoubleSolenoid kicker;
    private final IEncoder encoder;
    private final PIDHandler pidHandler;
    private double currentPosition;
    private double currentSpeed;

    private enum State
    {
        Stopped,
        Spinning,
        Shooting,
    }

    private State currentState;

    @Inject
    public ShooterMechanism(IDriver driver, IRobotProvider provider, ITimer timer) {
        this.driver = driver;
        this.hood = provider.getTalonSRX(ElectronicsConstants.SHOOTER_HOOD_MOTOR_CHANNEL);
        this.flywheel = provider.getTalonSRX(ElectronicsConstants.SHOOTER_FLYWHEEL_MOTOR_CHANNEL);
        this.kicker = provider.getDoubleSolenoid(
            PneumaticsModuleType.PneumaticsControlModule,
            ElectronicsConstants.SHOOTER_KICKER_SOLENOID_CHANNEL_A,
            ElectronicsConstants.SHOOTER_KICKER_SOLENOID_CHANNEL_B);
        this.hood.setSensorType(TalonSRXFeedbackDevice.QuadEncoder);
        this.hood.setControlMode(TalonSRXControlMode.Position);
        this.hood.setPIDF(
            TuningConstants.SHOOTER_HOOD_MOTOR_KP, 
            TuningConstants.SHOOTER_HOOD_MOTOR_KI, 
            TuningConstants.SHOOTER_HOOD_MOTOR_KD, 
            TuningConstants.SHOOTER_HOOD_MOTOR_KF,
            1);

        this.flywheel.setSensorType(TalonSRXFeedbackDevice.QuadEncoder);
        this.flywheel.setControlMode(TalonSRXControlMode.Velocity);
        this.flywheel.setPIDF(
            TuningConstants.SHOOTER_FLYWHEEL_MOTOR_KP, 
            TuningConstants.SHOOTER_FLYWHEEL_MOTOR_KI, 
            TuningConstants.SHOOTER_FLYWHEEL_MOTOR_KD, 
            TuningConstants.SHOOTER_FLYWHEEL_MOTOR_KF,
            1);

        
        this.encoder = provider.getEncoder(ElectronicsConstants.SHOOTER_ENCODER_CHANNEL_A, ElectronicsConstants.SHOOTER_ENCODER_CHANNEL_B);
    }

    @Override
    public void readSensors() {
    
    }

    @Override
    public void update(RobotMode mode) {
        if (this.driver.getDigital(DigitalOperation.SPIN)) {
            this.currentState = State.Spinning;
        } else if (this.driver.getDigital(DigitalOperation.FIRE)) {
            this.currentState = State.Shooting;
        } else {
            this.currentState = State.Stopped;
        }
        this.hood.set(pidHandler.calculatePosition(this.driver.getAnalog(AnalogOperation.HoodPosition) + 1 * 45, this.currentPosition));
        switch (this.currentState) {
            case Stopped -> {
                this.flywheel.set(0);
                this.kicker.set(DoubleSolenoidValue.Reverse);
            }
            case Spinning -> {
                this.flywheel.set(pidHandler.calculateVelocity(200, this.currentSpeed)); 
                this.kicker.set(DoubleSolenoidValue.Reverse);
            }
            case Shooting -> {
                this.flywheel.set(pidHandler.calculateVelocity(200, this.currentSpeed)); 
                this.kicker.set(DoubleSolenoidValue.Forward); 
            }
        }
    }

    @Override
    public void stop() {
        this.flywheel.set(0);
        this.hood.set(0);
    }
}