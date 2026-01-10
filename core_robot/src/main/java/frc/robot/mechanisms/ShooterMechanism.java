package frc.robot.mechanisms;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.ITalonSRX;
import frc.lib.robotprovider.RobotMode;
import frc.lib.robotprovider.TalonSRXControlMode;
import frc.lib.robotprovider.TalonSRXFeedbackDevice;
import frc.robot.ElectronicsConstants;
import frc.robot.TuningConstants;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;
import frc.lib.robotprovider.PneumaticsModuleType;
import frc.lib.robotprovider.DoubleSolenoidValue;
import frc.lib.robotprovider.IDoubleSolenoid;
@Singleton
public class ShooterMechanism implements IMechanism{
    private final IDriver driver;
    private final IDoubleSolenoid Kicker;
    private final ITalonSRX HoodMotor;
    private final ITalonSRX FlyWheelMotor;
    private double HoodCurrentPosition;

    @Inject
    public ShooterMechanism(IDriver driver, IRobotProvider provider){
        this.driver = driver;
        this.FlyWheelMotor=provider.getTalonSRX(ElectronicsConstants.SHOOTER_FLYWHEEL_MOTOR_PCMCHANNEL);
        this.HoodMotor=provider.getTalonSRX(ElectronicsConstants.SHOOTER_HOOD_MOTOR_PCMCHANNEL);
        this.HoodMotor.setSensorType(TalonSRXFeedbackDevice.QuadEncoder);
        this.HoodMotor.setControlMode(TalonSRXControlMode.Position);
        this.HoodMotor.setPIDF(
            TuningConstants.SHOOTER_HOOD_MOTOR_KP, 
            TuningConstants.SHOOTER_FLYWHEEL_MOTOR_KI, 
            TuningConstants.SHOOTER_FLYWHEEL_MOTOR_KD, 
            TuningConstants.SHOOTER_FLYWHEEL_MOTOR_KF,
            1
        );
        this.FlyWheelMotor.setSensorType(TalonSRXFeedbackDevice.QuadEncoder);
        this.FlyWheelMotor.setControlMode(TalonSRXControlMode.Velocity);
        this.FlyWheelMotor.setPIDF(
            TuningConstants.SHOOTER_FLYWHEEL_MOTOR_KP, 
            TuningConstants.SHOOTER_FLYWHEEL_MOTOR_KI, 
            TuningConstants.SHOOTER_FLYWHEEL_MOTOR_KD, 
            TuningConstants.SHOOTER_FLYWHEEL_MOTOR_KF, 
            0
        );
        this.Kicker = provider.getDoubleSolenoid(PneumaticsModuleType.PneumaticsControlModule, ElectronicsConstants.SHOOTER_FORWARD_KICKER_PCMCHANNEL, ElectronicsConstants.SHOOTER_REVERSE_KICKER_PCMCHANNEL);
    }
    @Override
    public void readSensors() {
        
    }
    @Override
    public void update(RobotMode mode) {
        this.HoodCurrentPosition=this.driver.getAnalog(AnalogOperation.HoodAnglePosition);
        if (this.driver.getDigital(DigitalOperation.FireButton) == true){
            this.Kicker.set(DoubleSolenoidValue.Forward);
        }
        else{    
            this.Kicker.set(DoubleSolenoidValue.Reverse);
        }
        this.HoodMotor.set(HoodScaledPosition(HoodCurrentPosition));
        while (this.driver.getDigital(DigitalOperation.SpinButton) == true){
            this.FlyWheelMotor.set(200);
            if (this.driver.getDigital(DigitalOperation.SpinButton)){
                this.FlyWheelMotor.set(0);
                break;
            }
        }
    }

    @Override
    public void stop() {
        this.FlyWheelMotor.set(0);
        this.HoodMotor.set(0);
        this.Kicker.set(DoubleSolenoidValue.Off);
    
    }
    private double HoodScaledPosition(double HoodPosition){
        return HoodPosition *90;
    }


}
