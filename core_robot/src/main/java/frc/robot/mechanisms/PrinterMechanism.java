package frc.robot.mechanisms;

import javax.inject.Singleton;

import frc.robot.ElectronicsConstants;
import frc.robot.HardwareConstants;
import frc.robot.TuningConstants;
import frc.robot.common.IMechanism;
import frc.robot.common.robotprovider.*;
import frc.robot.driver.*;
import frc.robot.driver.common.IDriver;

import com.google.inject.Inject;

@Singleton
public class PrinterMechanism implements IMechanism
{
    private final IDriver driver;
    private final ITalonSRX xMotor;
    private final ITalonSRX yMotor;
    private final IDoubleSolenoid pen;

    @Inject
    public PrinterMechanism(IDriver driver, IRobotProvider provider)
    {
        this.driver = driver;
        this.xMotor = provider.getTalonSRX(ElectronicsConstants.PRINTER_X_MOTOR_CAN_ID);
        this.xMotor.setSensorType(TalonXFeedbackDevice.QuadEncoder);
        this.xMotor.setControlMode(TalonXControlMode.Position);
        this.xMotor.setPIDF(
            TuningConstants.PRINTER_X_MOTOR_KP,
            TuningConstants.PRINTER_X_MOTOR_KI,
            TuningConstants.PRINTER_X_MOTOR_KD,
            TuningConstants.PRINTER_X_MOTOR_KF,
            0);

        this.yMotor = provider.getTalonSRX(ElectronicsConstants.PRINTER_Y_MOTOR_CAN_ID);
        this.yMotor.setSensorType(TalonXFeedbackDevice.QuadEncoder);
        this.yMotor.setControlMode(TalonXControlMode.Position);
        this.yMotor.setPIDF(
            TuningConstants.PRINTER_Y_MOTOR_KP,
            TuningConstants.PRINTER_Y_MOTOR_KI,
            TuningConstants.PRINTER_Y_MOTOR_KD,
            TuningConstants.PRINTER_Y_MOTOR_KF,
            0);

        this.pen = provider.getDoubleSolenoid(ElectronicsConstants.PRINTER_PEN_FORWARD_PCM_CHANNEL, ElectronicsConstants.PRINTER_PEN_BACKWARD_PCM_CHANNEL);
    }

    @Override
    public void readSensors()
    {
    }

    @Override
    public void update()
    {
        double xPosition = (this.driver.getAnalog(AnalogOperation.PrinterMoveX) + 1.0) / 2.0;
        xPosition = xPosition * (HardwareConstants.PRINTER_MAX_X - HardwareConstants.PRINTER_MIN_X) + HardwareConstants.PRINTER_MIN_X;
        double yPosition = (this.driver.getAnalog(AnalogOperation.PrinterMoveY) + 1.0) / 2.0;
        yPosition = yPosition * (HardwareConstants.PRINTER_MAX_Y - HardwareConstants.PRINTER_MIN_Y) + HardwareConstants.PRINTER_MIN_Y;

        this.xMotor.set(xPosition);
        this.yMotor.set(yPosition);

        if (this.driver.getDigital(DigitalOperation.PrinterPenDown))
        {
            this.pen.set(DoubleSolenoidValue.Forward);
        }
        else if (this.driver.getDigital(DigitalOperation.PrinterPenUp))
        {
            this.pen.set(DoubleSolenoidValue.Reverse);
        }
    }

    @Override
    public void stop()
    {
        this.xMotor.stop();
        this.yMotor.stop();
        this.pen.set(DoubleSolenoidValue.Off);
    }
}