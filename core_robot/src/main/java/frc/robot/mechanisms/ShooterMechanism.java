package frc.robot.mechanisms;
import java.beans.Encoder;
import java.util.Timer;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import frc.lib.controllers.PIDHandler;
import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.IDigitalInput;
import frc.lib.robotprovider.IEncoder;
import frc.lib.robotprovider.IMotor;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.ISolenoid;
import frc.lib.robotprovider.ITalonSRX;
import frc.lib.robotprovider.ITimer;
import frc.lib.robotprovider.RobotMode;
import frc.lib.robotprovider.TalonFXFeedbackDevice;
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
    // private final IDoubleSolenoid Kicker;
    
    public ShooterMechanism(IDriver driver, IRobotProvider provider){
        this.driver = driver;
        
    }
    @Override
    public void readSensors() {

    }
    @Override
    public void update(RobotMode mode) {
       
    }

    @Override
    public void stop() {
        
    }
    
}
