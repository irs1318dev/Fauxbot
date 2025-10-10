package frc.robot.mechanisms;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import frc.lib.controllers.PIDHandler;
import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.DoubleSolenoidValue;
import frc.lib.robotprovider.IDigitalInput;
import frc.lib.robotprovider.IDoubleSolenoid;
import frc.lib.robotprovider.IEncoder;
import frc.lib.robotprovider.IMotor;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.ITimer;
import frc.lib.robotprovider.PneumaticsModuleType;
import frc.lib.robotprovider.RobotMode;
import frc.robot.ElectronicsConstants;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;

@Singleton
public class ElevatorMechanism implements IMechanism {
    private IMotor motor;
    private IDriver driver;
    private IEncoder encoder;
    private double currentHeight;
    private double targetHeight;
    private final PIDHandler pid;

    public ElevatorMechanism (IDriver driver, IRobotProvider provider, ITimer timer) {
        this.driver = driver;
        this.motor = provider.getTalon(ElectronicsConstants.ELEVATOR_MOTOR_CHANNEL);
        this.encoder = provider.getEncoder(ElectronicsConstants.ELEVATOR_ENCODER_A,ElectronicsConstants.ELEVATOR_ENCODER_B);
        this.pid = new PIDHandler(1, 0, 0.1, 0, 1.0, -1.0, 1.0, timer);
        this.encoder.setDistancePerPulse(1.0);
    }
    @Override
    public void readSensors()
    {
        // TODO Auto-generated method stub
        currentHeight = this.encoder.getDistance();
    }
    @Override
    public void update(RobotMode mode)
    {
        if(driver.getDigital(DigitalOperation.FirstFloor)) {
            targetHeight = 0.0;
        }
        if(driver.getDigital(DigitalOperation.SecondFloor)) {
            targetHeight = 50.0;
        }
        if(driver.getDigital(DigitalOperation.ThirdFloor)) {
            targetHeight = 100.0;
        }
        if(driver.getDigital(DigitalOperation.FourthFloor)) {
            targetHeight = 150.0;
        }
        if(driver.getDigital(DigitalOperation.FifthFloor)) {
            targetHeight = 200.0;
        }
        
    }
    @Override
    public void stop()
    {
        this.motor.set(0.0);
    }

    
}
