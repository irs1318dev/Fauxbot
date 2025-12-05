package frc.robot.mechanisms;

import com.google.inject.Inject;

<<<<<<< Updated upstream
=======
import frc.lib.controllers.PIDHandler;
>>>>>>> Stashed changes
import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.DoubleSolenoidValue;
import frc.lib.robotprovider.IDoubleSolenoid;
<<<<<<< Updated upstream
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.ITalonSRX;
=======
import frc.lib.robotprovider.IEncoder;
import frc.lib.robotprovider.IMotor;
import frc.lib.robotprovider.IRobotProvider;
>>>>>>> Stashed changes
import frc.lib.robotprovider.ITimer;
import frc.lib.robotprovider.PneumaticsModuleType;
import frc.lib.robotprovider.RobotMode;
import frc.robot.ElectronicsConstants;
<<<<<<< Updated upstream
=======
import frc.robot.TuningConstants;
>>>>>>> Stashed changes
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;

public class ShooterMechanism implements IMechanism
{
    private final IDriver driver;
<<<<<<< Updated upstream
    private final ITalonSRX hood;
    private final ITalonSRX flywheel;
    private final IDoubleSolenoid kicker;
=======
    private final IMotor hood;
    private final IMotor flywheel;
    private final IDoubleSolenoid kicker;
    private final IEncoder encoder;
    private final PIDHandler pidHandler;
    private double currentPosition;
    private double currentSpeed;
>>>>>>> Stashed changes

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
<<<<<<< Updated upstream
=======
        this.hood = provider.getTalon(ElectronicsConstants.SHOOTER_HOOD_MOTOR_CHANNEL);
        this.flywheel = provider.getTalon(ElectronicsConstants.SHOOTER_FLYWHEEL_MOTOR_CHANNEL);
>>>>>>> Stashed changes
        this.kicker = provider.getDoubleSolenoid(
            PneumaticsModuleType.PneumaticsControlModule,
            ElectronicsConstants.SHOOTER_KICKER_SOLENOID_CHANNEL_A,
            ElectronicsConstants.SHOOTER_KICKER_SOLENOID_CHANNEL_B);
<<<<<<< Updated upstream
        this.hood = provider.getTalonSRX(ElectronicsConstants.SHOOTER_HOOD_MOTOR_CHANNEL);
        this.flywheel = provider.getTalonSRX(ElectronicsConstants.SHOOTER_FLYWHEEL_MOTOR_CHANNEL);
=======
        this.pidHandler = new PIDHandler(   // Use same as elevator for now
            TuningConstants.SHOOTER_PID_KP, 
            TuningConstants.SHOOTER_PID_KI, 
            TuningConstants.SHOOTER_PID_KD, 
            TuningConstants.SHOOTER_PID_KF, 
            TuningConstants.SHOOTER_PID_KS,
            TuningConstants.SHOOTER_MIN_OUTPUT,
            TuningConstants.SHOOTER_MAX_OUTPUT,
            timer
            );
        this.encoder = provider.getEncoder(ElectronicsConstants.SHOOTER_ENCODER_CHANNEL_A, ElectronicsConstants.SHOOTER_ENCODER_CHANNEL_B);
>>>>>>> Stashed changes
    }

    @Override
    public void readSensors() {
<<<<<<< Updated upstream
    }

=======
        this.currentPosition = this.encoder.getDistance();
        this.currentSpeed = this.encoder.getRate();
    }


>>>>>>> Stashed changes
    @Override
    public void update(RobotMode mode) {
        if (this.driver.getDigital(DigitalOperation.Spin)) {
            this.currentState = State.Spinning;
        } else if (this.driver.getDigital(DigitalOperation.Shoot)) {
            this.currentState = State.Shooting;
        } else {
            this.currentState = State.Stopped;
        }
<<<<<<< Updated upstream
        switch (this.currentState) {
            case Stopped -> {
                this.flywheel.stop();
                this.kicker.set(DoubleSolenoidValue.Reverse);
            }
            case Spinning -> {
                this.flywheel.set(this.driver.getAnalog(AnalogOperation.ShooterWheelPower));
                this.kicker.set(DoubleSolenoidValue.Reverse);
            }
            case Shooting -> {
                this.flywheel.set(this.driver.getAnalog(AnalogOperation.ShooterWheelPower));
            }
        }
        this.hood.setPosition(this.driver.getAnalog(AnalogOperation.HoodPosition));
=======
        this.hood.set(pidHandler.calculatePosition(this.driver.getAnalog(AnalogOperation.HoodPosition) + 1 * 45, this.currentPosition));
        switch (this.currentState) {
            case Stopped -> {
                this.flywheel.set(0);
                this.kicker.set(DoubleSolenoidValue.Reverse);
            }
            case Spinning -> {
                this.flywheel.set(pidHandler.calculateVelocity(200, this.currentSpeed)); // Set to desired flywheel speed
                this.kicker.set(DoubleSolenoidValue.Reverse);
            }
            case Shooting -> {
                this.flywheel.set(pidHandler.calculateVelocity(200, this.currentSpeed)); // Set to desired flywheel speed
                this.kicker.set(DoubleSolenoidValue.Forward); // Activate kicker to shoot
            }
        }
>>>>>>> Stashed changes
    }

    @Override
    public void stop() {
        this.flywheel.set(0);
        this.hood.set(0);
    }
}