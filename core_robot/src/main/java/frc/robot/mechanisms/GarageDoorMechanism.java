package frc.robot.mechanisms;

import java.io.IOException;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import frc.lib.controllers.PIDHandler;
import frc.lib.driver.IDriver;
import frc.lib.driver.IOperation;

import frc.lib.driver.buttons.IButton;
import frc.lib.mechanisms.IMechanism;
import frc.lib.robotprovider.DoubleSolenoidValue;
import frc.lib.robotprovider.IDigitalInput;
import frc.lib.robotprovider.IDoubleSolenoid;
import frc.lib.robotprovider.IMotor;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.PneumaticsModuleType;
import frc.lib.robotprovider.RobotMode;
import frc.robot.ElectronicsConstants;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;

@Singleton
public class GarageDoorMechanism implements IMechanism
{

    private IDriver driver;
    private IMotor garageMotor;
    private IDigitalInput throughbeamSensor;

    private IDigitalInput openSensor;
    private IDigitalInput closeSensor;

    private boolean isGarageOpen;
    private boolean isGarageClosed;

    private boolean GarageSwitched;

    private boolean throughBeamBroken;

    private boolean ButtonPressed;

    public enum GarageDoorState
    {
        Opened, Opening, Closed, Closing
    }

    private GarageDoorState state;

    @Inject
    public GarageDoorMechanism(IDriver driver, IRobotProvider provider)
    {
        //Initializations
        this.driver = driver;
        boolean ButtonPressed = driver.getDigital(DigitalOperation.Button);
    
        this.garageMotor = provider.getTalon(ElectronicsConstants.GARAGEDOOR_MOTOR);
        this.state = GarageDoorState.Closed;

        this.closeSensor = provider.getDigitalInput(ElectronicsConstants.GARAGEDOOR_CLOSE_CHANNEL);
        this.openSensor = provider.getDigitalInput(ElectronicsConstants.GARAGEDOOR_OPEN_CHANNEL);
        this.throughbeamSensor = provider.getDigitalInput(ElectronicsConstants.GARAGEDOOR_THROUGHBEAM);

        this.isGarageOpen = false;
        this.isGarageClosed = true;
        this.throughBeamBroken = false;

    }

    @Override
    public void readSensors()
    {
        this.isGarageOpen = this.openSensor.get();
        this.isGarageClosed = this.closeSensor.get();
        this.throughBeamBroken = this.throughbeamSensor.get();

        // throw new UnsupportedOperationException("Unimplemented method 'readSensors'");

    }

    // If Click Button
    // If garage closed - open
    // If garage opened - close

    // If sensor passed AND garage door closing
    // Open Garage

    @Override
    public void update(RobotMode mode)
    {
        if (ButtonPressed != driver.getDigital(DigitalOperation.Button))
        {
            ButtonPressed = driver.getDigital(DigitalOperation.Button);

            GarageSwitched = false;
        }

        // boolean ButtonPressed;
        // boolean SwitchState = driver.getDigital(DigitalOperation.Button);


        // If the garage door is moving
        if (state == GarageDoorState.Opening || state == GarageDoorState.Closing)
        {

            if (throughBeamBroken && state == GarageDoorState.Closing)
            {
                state = GarageDoorState.Opening;
            }

            // If the garage door has updated according to the button
            if (GarageSwitched == false)
            {
                if (state == GarageDoorState.Opening)
                {
                    state = GarageDoorState.Closing;
                    GarageSwitched = true;
                }
                else
                {
                    state = GarageDoorState.Opening;
                    GarageSwitched = true;
                }
            }
        }
        // If the garage door is not moving
        else if (state == GarageDoorState.Closed || state == GarageDoorState.Opened)
        {

            if (ButtonPressed)
            {
                if (state == GarageDoorState.Opened)
                {
                    state = GarageDoorState.Closing;
                }
                else
                {
                    state = GarageDoorState.Opening;
                }
            }
        }

        if (state == GarageDoorState.Opened || state == GarageDoorState.Closed)
        {
            this.garageMotor.set(0);
        }
        if (state == GarageDoorState.Opening)
        {
            this.garageMotor.set(1);
        }
        if (state == GarageDoorState.Closing)
        {
            this.garageMotor.set(-1);
        }
        
    }

    @Override
    public void stop()
    {
        // TODO Auto-generated method stub
        garageMotor.set(0.0);
        // throw new UnsupportedOperationException("Unimplemented method 'stop'");
    }

}
