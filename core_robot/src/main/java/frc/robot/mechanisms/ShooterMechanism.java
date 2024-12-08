package frc.robot.mechanisms;

import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.DoubleSolenoidValue;
import frc.lib.robotprovider.IDoubleSolenoid;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.ITalonSRX;
import frc.lib.robotprovider.PneumaticsModuleType;
import frc.lib.robotprovider.TalonXControlMode;
import frc.robot.ElectronicsConstants;
import frc.robot.driver.DigitalOperation;

public class ShooterMechanism implements IMechanism {

    private IDriver driver;
    private ITalonSRX hoodMotor;
    private ITalonSRX flyWheelMotor;
    private IDoubleSolenoid pneumaticKicker;
    private double velocity;
    private double hoodAngle;

    public ShooterMechanism(IRobotProvider provider, IDriver driver)
    {
        this.driver = driver;
        this.hoodMotor = provider.getTalonSRX(ElectronicsConstants.HOOD_MOTOR_CHANNEL);
        this.flyWheelMotor = provider.getTalonSRX(ElectronicsConstants.FLYWHEEL_MOTOR_CHANNEL);
        this.pneumaticKicker = provider.getDoubleSolenoid(
        PneumaticsModuleType.PneumaticsControlModule,
        ElectronicsConstants.PNEUMATIC_KICKER_FORWARD_CHANNEL, 
        ElectronicsConstants.PNEUMATIC_KICKER_REVERSE_CHANNEL);
        
    }

    @Override
    public void readSensors()
    {
        // TODO Auto-generated method stub
        this.velocity = this.flyWheelMotor.getVelocity();
        this.hoodAngle = this.hoodMotor.getPosition();
        
    }

    @Override
    public void update()
    {
        // TODO Auto-generated method stub
        if (this.driver.getDigital(DigitalOperation.SpinButton))
        {
            this.pneumaticKicker.set(DoubleSolenoidValue.Forward);
            this.flyWheelMotor.set(ElectronicsConstants.FLYWHEEL_ROTATION_RATE);
        }
        if (this.driver.getDigital(DigitalOperation.FireButton))
        {
            this.flyWheelMotor.set(TalonXControlMode.Required, 200);
        }
        
    }

    @Override
    public void stop()
    {
        // TODO Auto-generated method stub
        
    }
    
}
