package frc.robot.mechanisms;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.ITalonSRX;
import frc.lib.robotprovider.ITimer;
import frc.lib.robotprovider.RobotMode;
import frc.lib.robotprovider.TalonSRXControlMode;
import frc.lib.robotprovider.TalonSRXFeedbackDevice;
import frc.robot.ElectronicsConstants;
import frc.robot.HardwareConstants;
import frc.robot.TuningConstants;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;
import frc.lib.robotprovider.PneumaticsModuleType;
import frc.lib.robotprovider.DoubleSolenoidValue;
import frc.lib.robotprovider.IDoubleSolenoid;

@Singleton
public class PrinterMechanism implements IMechanism{
    private final IDriver driver;
    private final IDoubleSolenoid penstate;
    private final ITalonSRX XMotor;
    private final ITalonSRX YMotor;
    private double XPosition;
    private double YPosition;
    

    @Inject
    public PrinterMechanism(IDriver driver, IRobotProvider provider, ITimer timer){
        this.driver = driver;  
        this.penstate = provider.getDoubleSolenoid(PneumaticsModuleType.PneumaticsControlModule, ElectronicsConstants.PRINTER_PEN_UP_PCMCHANNEL, ElectronicsConstants.PRINTER_PEN_DOWN_PCMCHANNEL);
        this.XMotor = provider.getTalonSRX(ElectronicsConstants.XAXIS_MOTOR_PCMCHANNEL);
        this.XMotor.setSensorType(TalonSRXFeedbackDevice.QuadEncoder);
        this.XMotor.setControlMode(TalonSRXControlMode.Position);
        this.XMotor.setPIDF(
            TuningConstants.PRINTER_X_MOTOR_KP,
            TuningConstants.PRINTER_X_MOTOR_KI,
            TuningConstants.PRINTER_X_MOTOR_KD,
            TuningConstants.PRINTER_X_MOTOR_KF,
            0
        );
        this.YMotor = provider.getTalonSRX(ElectronicsConstants.YAXIS_MOTOR_PCMCHANNEL);
        this.YMotor.setSensorType(TalonSRXFeedbackDevice.QuadEncoder);
        this.YMotor.setControlMode(TalonSRXControlMode.Position);
        this.YMotor.setPIDF(
            TuningConstants.PRINTER_Y_MOTOR_KP,
            TuningConstants.PRINTER_Y_MOTOR_KI,
            TuningConstants.PRINTER_Y_MOTOR_KD,
            TuningConstants.PRINTER_Y_MOTOR_KF,
            1
        );
    }
    @Override
    public void readSensors() {
        this.XPosition = this.driver.getAnalog(AnalogOperation.XAxisPosition);
        this.YPosition = this.driver.getAnalog(AnalogOperation.YAxisPosition);
    }

    @Override
    public void update(RobotMode mode) {
        if (this.driver.getDigital(DigitalOperation.PrinterPenUp)){
            this.penstate.set(DoubleSolenoidValue.Reverse);
        }
        if (this.driver.getDigital(DigitalOperation.PrinterPenDown)){
            this.penstate.set(DoubleSolenoidValue.Forward);
        }
        this.XMotor.set(getScaledXPosition(this.XPosition));
        this.YMotor.set(getScaledYPosition(this.YPosition));
    }

    @Override
    public void stop() {
     
    }

    private double getScaledXPosition(double xPosition){
        return xPosition * (HardwareConstants.X_AXIS_MAX_VALUE-HardwareConstants.X_AXIS_MIN_VALUE);
    }
    private double getScaledYPosition(double yPosition){
        return yPosition * (HardwareConstants.Y_AXIS_MAX_VALUE-HardwareConstants.Y_AXIS_MIN_VALUE);
    }
}
