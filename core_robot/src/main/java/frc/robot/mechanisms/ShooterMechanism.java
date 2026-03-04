package frc.robot.mechanisms;
import com.google.inject.Inject;

import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.DoubleSolenoidValue;
import frc.lib.robotprovider.IDoubleSolenoid;
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
        this.currentState = State.Stopped;

        this.kicker = provider.getDoubleSolenoid(
            PneumaticsModuleType.PneumaticsControlModule,
            ElectronicsConstants.SHOOTER_KICKER_SOLENOID_CHANNEL_A,
            ElectronicsConstants.SHOOTER_KICKER_SOLENOID_CHANNEL_B);

        this.hood = provider.getTalonSRX(ElectronicsConstants.SHOOTER_HOOD_MOTOR_CHANNEL);
        this.flywheel = provider.getTalonSRX(ElectronicsConstants.SHOOTER_FLYWHEEL_MOTOR_CHANNEL);
        this.hood.setSensorType(TalonSRXFeedbackDevice.QuadEncoder);
        this.flywheel.setSensorType(TalonSRXFeedbackDevice.QuadEncoder);
        this.hood.setControlMode(TalonSRXControlMode.Velocity);
        this.flywheel.setControlMode(TalonSRXControlMode.Position);
        this.hood.setPIDF(
            TuningConstants.HOOD_PID_KP, 
            TuningConstants.HOOD_PID_KI, 
            TuningConstants.HOOD_PID_KD, 
            TuningConstants.HOOD_PID_KF, 
            0);
        this.flywheel.setPIDF(
            TuningConstants.FLYWHEEL_PID_KP, 
            TuningConstants.FLYWHEEL_PID_KI, 
            TuningConstants.FLYWHEEL_PID_KD, 
            TuningConstants.FLYWHEEL_PID_KF, 
            0);
    }

    @Override
    public void readSensors() {
    }



    @Override
    public void update(RobotMode mode) {
        if (this.driver.getDigital(DigitalOperation.Spin)) {
            this.currentState = State.Spinning;
        } else{
            if (this.driver.getDigital(DigitalOperation.Shoot)) {
                if (this.currentState == State.Spinning) {
                    this.currentState = State.Shooting;
                }
                else {
                    this.currentState = State.Stopped;
                }
            }
        }

        switch (this.currentState) {
            case Stopped -> {
                this.flywheel.stop();
                this.kicker.set(DoubleSolenoidValue.Reverse);
                this.flywheel.set(200);
            }
            case Spinning -> {
                this.flywheel.set(200);
                this.kicker.set(DoubleSolenoidValue.Reverse);
            }
            case Shooting -> {
                this.flywheel.set(200);
            }
        }

        this.hood.set(this.driver.getAnalog(AnalogOperation.HoodPosition) * 90);
        switch (this.currentState) {
            case Stopped -> {
                this.flywheel.set(0);
                this.kicker.set(DoubleSolenoidValue.Reverse);
            }
            case Spinning -> {
                this.flywheel.set(200); // Set to desired flywheel speed
                this.kicker.set(DoubleSolenoidValue.Reverse);
            }
            case Shooting -> {
                this.flywheel.set(200); // Set to desired flywheel speed
                this.kicker.set(DoubleSolenoidValue.Forward); // Activate kicker to shoot
            }
        }

    }

    @Override
    public void stop() {
        this.flywheel.set(0);
        this.hood.set(0);
    }
}