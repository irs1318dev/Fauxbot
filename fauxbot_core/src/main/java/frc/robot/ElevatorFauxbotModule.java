package frc.robot.mechanisms;

// idk what this does
import com.google.inject.Inject;
import com.google.inject.Singleton;

// i think most of this stuff is here for every mechanism
import frc.lib.driver.IDriver;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.IDigitalInput;
import frc.lib.robotprovider.IMotor;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.RobotMode;
import frc.robot.ElectronicsConstants;
import frc.robot.driver.DigitalOperation;

@Singleton // hiiiiiii!!!!!

public class ElevatorFauxbotModule implements IMechanism {
    private final IMotor elevatorMotor;
    private final IDigitalInput encoder;
    private final IDriver driver;
} // are there more of these? idk

{ // what is this stuff?
    @Override
    protected void configure()
    {
        super.configure();

        this.bind(SimulatorBase.class).to(ElevatorSimulator.class);
    }
}
/* this comment can be very long
look!
see how long it is!
yay!!!!!!!!!












this is really long i'm gonna stop for now
 */