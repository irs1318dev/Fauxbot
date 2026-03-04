package frc.robot.mechanisms;

import com.google.inject.Inject;

import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.DoubleSolenoidValue;
import frc.lib.robotprovider.IDoubleSolenoid;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.ITalonSRX;
import frc.lib.robotprovider.PneumaticsModuleType;
import frc.lib.robotprovider.RobotMode;
import frc.lib.robotprovider.TalonSRXControlMode;
import frc.lib.robotprovider.TalonSRXFeedbackDevice;
import frc.robot.ElectronicsConstants;
import frc.robot.TuningConstants;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;

public class PrinterMechanism implements IMechanism
{
    private IDriver driver;
    private IRobotProvider provider;
    private ITalonSRX xMotor;
    private ITalonSRX yMotor;
    private IDoubleSolenoid penSolenoid;

    @Inject
    public PrinterMechanism(IDriver driver, IRobotProvider provider) {
        this.driver = driver;
        this.provider = provider;
        this.xMotor = provider.getTalonSRX(ElectronicsConstants.PRINTER_X_MOTOR_CHANNEL);
        this.yMotor = provider.getTalonSRX(ElectronicsConstants.PRINTER_Y_MOTOR_CHANNEL);
        this.xMotor.setControlMode(TalonSRXControlMode.Position);
        this.yMotor.setControlMode(TalonSRXControlMode.Position);
        this.xMotor.setSensorType(TalonSRXFeedbackDevice.QuadEncoder);
        this.yMotor.setSensorType(TalonSRXFeedbackDevice.QuadEncoder);
        this.xMotor.setPIDF(
            TuningConstants.PRINTER_X_PID_KP,
            TuningConstants.PRINTER_X_PID_KI,
            TuningConstants.PRINTER_X_PID_KD,
            TuningConstants.PRINTER_X_PID_KF,
            0);
        this.yMotor.setPIDF(
            TuningConstants.PRINTER_Y_PID_KP,
            TuningConstants.PRINTER_Y_PID_KI,
            TuningConstants.PRINTER_Y_PID_KD,
            TuningConstants.PRINTER_Y_PID_KF,
            0);
        this.penSolenoid = provider.getDoubleSolenoid(
            PneumaticsModuleType.PneumaticsControlModule,
            ElectronicsConstants.PRINTER_PEN_SOLENOID_CHANNEL_A,
            ElectronicsConstants.PRINTER_PEN_SOLENOID_CHANNEL_B);
    }

    @Override
    public void readSensors() {
    }

    @Inject
    public double getXPosition() {
        return this.xMotor.getPosition();
    }

    @Inject
    public double getYPosition() {
        return this.yMotor.getPosition();
    }

    public boolean penIsDown() {
        return this.driver.getDigital(DigitalOperation.PenUp);
    }

    @Override
    public void update(RobotMode mode) {
        this.xMotor.set(100-this.driver.getAnalog(AnalogOperation.XPosition)*100);
        this.yMotor.set(100-this.driver.getAnalog(AnalogOperation.YPosition)*100);
        if (this.driver.getDigital(DigitalOperation.PenUp)) {
            this.penSolenoid.set(DoubleSolenoidValue.Reverse);
        }
        else if (this.driver.getDigital(DigitalOperation.PenDown)) {
            this.penSolenoid.set(DoubleSolenoidValue.Forward);
        }
    }

    @Override
    public void stop() {
        this.xMotor.set(0);
        this.yMotor.set(0);
        this.penSolenoid.set(DoubleSolenoidValue.Off);
    }
}