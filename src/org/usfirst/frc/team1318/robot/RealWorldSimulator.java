package org.usfirst.frc.team1318.robot;

import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.MotorBase;
import edu.wpi.first.wpilibj.MotorManager;
import edu.wpi.first.wpilibj.SensorManager;

public class RealWorldSimulator
{
    private static final int ThroughBeamSensorChannel = 0;
    private static final int OpenSensorChannel = 1;
    private static final int ClosedSensorChannel = 2;
    private static final int MotorChannel = 0;
    
    @SuppressWarnings("serial")
    private final Map<Integer, String> sensorNameMap = new HashMap<Integer, String>()
    {
        {
            this.put(RealWorldSimulator.ThroughBeamSensorChannel, "Through-Beam sensor");
            this.put(RealWorldSimulator.OpenSensorChannel, "Open sensor");
            this.put(RealWorldSimulator.ClosedSensorChannel, "Closed sensor");
        }
    };

    @SuppressWarnings("serial")
    private final Map<Integer, String> motorNameMap = new HashMap<Integer, String>()
    {
        {
            this.put(RealWorldSimulator.MotorChannel, "Door motor");
        }
    };

    private static final int GarageFullyOpened = 250;

    private GarageState garageState;
    private int numUpdatesOpened;
    public RealWorldSimulator()
    {
        this.garageState = GarageState.Stopped;
        this.numUpdatesOpened = 0;
    }

    public String getSensorName(int channel)
    {
        if (this.sensorNameMap.containsKey(channel))
        {
            return this.sensorNameMap.get(channel);
        }

        return "Sensor " + channel;
    }

    public String getMotorName(int channel)
    {
        if (this.motorNameMap.containsKey(channel))
        {
            return this.motorNameMap.get(channel);
        }

        return "Motor " + channel;
    }

    public void update()
    {
        MotorBase motor = MotorManager.get(RealWorldSimulator.MotorChannel);
        if (motor != null)
        {
            if (motor.get() > 0)
            {
                if (this.garageState != GarageState.Opening)
                {
                    this.garageState = GarageState.Opening;
                }
                else
                {
                    this.numUpdatesOpened++;
                }
            }
            else if (motor.get() < 0)
            {
                if (this.garageState != GarageState.Closing)
                {
                    this.garageState = GarageState.Closing;
                }
                else
                {
                    this.numUpdatesOpened--;
                }
            }
            else // if (motor.get() == 0)
            {
                if (this.garageState != GarageState.Stopped)
                {
                    this.garageState = GarageState.Stopped;
                }
            }
        }

        DigitalInput openSensor = (DigitalInput)SensorManager.get(RealWorldSimulator.OpenSensorChannel);
        DigitalInput closedSensor = (DigitalInput)SensorManager.get(RealWorldSimulator.ClosedSensorChannel);

        if (openSensor != null && closedSensor != null)
        {
            openSensor.set(false);
            closedSensor.set(false);
            if (this.numUpdatesOpened >= RealWorldSimulator.GarageFullyOpened)
            {
                openSensor.set(true);
            }
            else if (this.numUpdatesOpened <= 0)
            {
                closedSensor.set(true);
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
