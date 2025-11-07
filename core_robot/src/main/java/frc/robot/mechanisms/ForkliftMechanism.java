import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.IDoubleSolenoid;
import frc.lib.robotprovider.IMotor;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.RobotMode;

public class ForkliftMechanism implements IMechanism { 

    private final IDriver driver;
    private final IMotor leftMotor;
    private final IMotor rightMotor;
    private final IDoubleSolenoid lifter;

    public ForkliftMechanism(IDriver driver, IRobotProvider provider) {
        this.driver = driver;

        this.leftMotor = provider.getTalon(0);
        
    }

    @Override
    public void readSensors() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void update(RobotMode mode) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub
       
    }


}
