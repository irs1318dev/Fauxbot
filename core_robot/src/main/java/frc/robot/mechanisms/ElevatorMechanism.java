package frc.robot.mechanisms;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import frc.lib.controllers.PIDHandler;
import frc.lib.driver.IDriver;
import frc.lib.driver.IOperation;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.DoubleSolenoidValue;
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
public class ElevatorMechanism implements IMechanism
{

    private IDriver driver;
    private int targetheight;
    private IMotor motor;
    private IEncoder encoder;
    private PIDHandler pidHandler;
    private double encodingValue;
    
    @Inject
    public ElevatorMechanism(IDriver driver,IRobotProvider provider, ITimer timer) 
    {
        this.driver = driver;
        this.motor = provider.getTalon(ElectronicsConstants.ELEVATOR_CH_MOTOR_ID);

        this.encoder = provider.getEncoder(ElectronicsConstants.ELEVATOR_CH_A_ID, ElectronicsConstants.ELEVATOR_CH_B_ID);
        this.pidHandler = new PIDHandler(1.0, 0.0, 0.1, 0.0, 1.0, -1.0, 1.0, timer);
        encodingValue = 0;
    }
    @Override
    public void readSensors()
    {
       this.encodingValue = this.encoder.get();
       // throw new UnsupportedOperationException("Unimplemented method 'readSensors'");
    
    }

    @Override
    public void update(RobotMode mode)
    {
        if (this.driver.getDigital(DigitalOperation.ElevatorFirstFloorButton))
        {
            targetheight = 0;
        }
        else if (this.driver.getDigital(DigitalOperation.ElevatorSecondFloorButton))
        {
            targetheight = 50;
        }
        else if (this.driver.getDigital(DigitalOperation.ElevatorThirdFloorButton))
        {
            targetheight = 100;
        }
        else if (this.driver.getDigital(DigitalOperation.ElevatorFourthFloorButton))
        {
            targetheight = 150;
        }
        else if (this.driver.getDigital(DigitalOperation.ElevatorFifthFloorButton))
        {
            targetheight = 200;
        }
        this.motor.set(pidHandler.calculatePosition(targetheight, encodingValue));

        System.out.println(targetheight);



    }

    @Override
    public void stop()
    {
        // TODO Auto-generated method stub
        this.motor.set(0);
        //throw new UnsupportedOperationException("Unimplemented method 'stop'");
    }


}