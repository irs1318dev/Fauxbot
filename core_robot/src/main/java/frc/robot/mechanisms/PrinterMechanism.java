package frc.robot.mechanisms;

import frc.lib.controllers.PIDHandler;
import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.DoubleSolenoidValue;
import frc.lib.robotprovider.IDoubleSolenoid;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.ITalonSRX;
import frc.lib.robotprovider.PneumaticsModuleType;
import frc.lib.robotprovider.TalonXControlMode;
import frc.robot.ElectronicsConstants;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;

public class PrinterMechanism implements IMechanism{

    private IDriver driver;
    private PIDHandler pidHandler;
    private IDoubleSolenoid penSolenoid;
    private ITalonSRX X_AxisMotor;
    private ITalonSRX Y_AxisMotor;

    private double[] position;

    public PrinterMechanism(IDriver driver, IRobotProvider provider)
    {
        this.driver = driver;
        this.penSolenoid = provider.getDoubleSolenoid(PneumaticsModuleType.PneumaticsControlModule, ElectronicsConstants.PEN_SOLENOID_FORWARD_CHANNEL, ElectronicsConstants.PEN_SOLENOID_REVERSE_CHANNEL);

        this.pidHandler = new PIDHandler(6, 0, 5, 0, 0, null, null, null);

        this.X_AxisMotor = provider.getTalonSRX(0);
        this.Y_AxisMotor = provider.getTalonSRX(1);
        this.position = new double[2];
        this.X_AxisMotor.setControlMode(TalonXControlMode.Position);
    }

    private double convertRange(double input,double scalar, double offset)
    {
        return scalar * ( input + offset);
    }

    @Override
    public void readSensors() {
        // TODO Auto-generated method stub
        position[0] = convertRange(this.X_AxisMotor.getPosition(), 100, 1);
        position[1] = convertRange(this.Y_AxisMotor.getPosition(), 100, 1);
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        drawSquare(100);
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub
        
    }

    private void drawSquare(int sideLength)
    {
        
        if (this.driver.getDigital(DigitalOperation.PenDown))
        {
            this.penSolenoid.set(DoubleSolenoidValue.Forward);
        }
        else
        {
            this.penSolenoid.set(DoubleSolenoidValue.Reverse);
        }

        this.X_AxisMotor.set(convertRange(this.driver.getAnalog(AnalogOperation.X_AxisPositionTalon),100,1));
        this.Y_AxisMotor.set(convertRange(this.driver.getAnalog(AnalogOperation.Y_AxisPositionTalon),100,1));
    }
    
}
