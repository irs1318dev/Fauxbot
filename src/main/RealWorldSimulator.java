package main;

import main.Common.DigitalInput;
import main.Common.Motor;
import main.Common.MotorManager;
import main.Common.SensorManager;

public class RealWorldSimulator
{
    private GarageState garageState;
    private int numUpdatesSinceStateChange;
    public RealWorldSimulator()
    {
        this.garageState = GarageState.Stopped;
        this.numUpdatesSinceStateChange = 0;
    }

    public void update()
    {
        Motor motor = MotorManager.get(ElectronicsConstants.GARAGEDOOR_MOTOR_CHANNEL);
        if (motor.get() > 0)
        {
            if (this.garageState != GarageState.Opening)
            {
                this.garageState = GarageState.Opening;
                this.numUpdatesSinceStateChange = 0;
            }
            else
            {
                this.numUpdatesSinceStateChange++;
            }
        }
        else if (motor.get() < 0)
        {
            if (this.garageState != GarageState.Closing)
            {
                this.garageState = GarageState.Closing;
                this.numUpdatesSinceStateChange = 0;
            }
            else
            {
                this.numUpdatesSinceStateChange++;
            }
        }
        else // if (motor.get() == 0)
        {
            if (this.garageState != GarageState.Stopped)
            {
                this.garageState = GarageState.Stopped;
                this.numUpdatesSinceStateChange = 0;
            }
        }

        DigitalInput openSensor = (DigitalInput)SensorManager.get(ElectronicsConstants.GARAGEDOOR_OPENSENSOR_CHANNEL);
        DigitalInput closedSensor = (DigitalInput)SensorManager.get(ElectronicsConstants.GARAGEDOOR_CLOSEDSENSOR_CHANNEL);
        
        // if we have been opening for 500 updates (10 seconds), set our open sensor
        if (this.garageState == GarageState.Opening)
        {
            if (this.numUpdatesSinceStateChange > 500)
            {
                openSensor.set(true);
            }
            else if (this.numUpdatesSinceStateChange > 10)
            {
                closedSensor.set(false);
            }
        }
        else if (this.garageState == GarageState.Closing)
        {
            if (this.numUpdatesSinceStateChange > 500)
            {
                closedSensor.set(true);
            }
            else if (this.numUpdatesSinceStateChange > 10)
            {
                openSensor.set(false);
            }
        }
    }
    
    public enum GarageState
    {
        Stopped,
        Opening,
        Closing;
    }
}
