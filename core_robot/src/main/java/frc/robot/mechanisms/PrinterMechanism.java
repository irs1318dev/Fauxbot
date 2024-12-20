package frc.robot.mechanisms;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.mechanisms.LoggingManager;
import frc.lib.robotprovider.IDoubleSolenoid;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.ITalonSRX;
import frc.lib.robotprovider.RobotMode;
import frc.lib.robotprovider.TalonSRXControlMode;
import frc.lib.robotprovider.TalonSRXFeedbackDevice;

@Singleton
public class PrinterMechanism implements IMechanism{
    private ITalonSRX xMotor;
    private ITalonSRX yMotor;
    private IDoubleSolenoid pen;
    private IDriver driver;

@Inject
public PrinterMechanism(IDriver driver, IRobotProvider provider, LoggingManager logger)
{
    this.driver = driver;
    this.xMotor.setControlMode(TalonSRXControlMode.Position);
    this.yMotor.setControlMode(TalonSRXControlMode.Position);
    this.xMotor.setSensorType(TalonSRXFeedbackDevice.QuadEncoder);
    this.yMotor.setSensorType(TalonSRXFeedbackDevice.QuadEncoder)
    this.xMotor.setPIDF(0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7);
    this.yMotor.setPIDF(1, 2, 3, 4, 0);


}

    @Override
    public void readSensors() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'readSensors'");
    }

    @Override
    public void update(RobotMode mode) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'stop'");
    }
    
    }
}
