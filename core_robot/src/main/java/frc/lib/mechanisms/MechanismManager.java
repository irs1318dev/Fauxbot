package frc.lib.mechanisms;

import java.util.List;

import frc.lib.helpers.Tracer;
import frc.lib.robotprovider.RobotMode;
import frc.robot.TuningConstants;

/**
 * Manager of mechanisms - runs each mechanism in order and individually so that they can be interacted with in-bulk
 */
public class MechanismManager implements IMechanism
{
    public final List<IMechanism> mechanismList;

    /**
     * Initializes a new instance of the MechanismManager class.
     * @param mechanismList list of mechanisms to be managed
     */
    public MechanismManager(List<IMechanism> mechanismList)
    {
        this.mechanismList = mechanismList;
    }

    /**
     * Reads all of the sensors for each mechanism that will be used in macros/autonomous mode and records their values.
     * This function is called every ~20ms whenever the robot is enabled, shortly before the update function is called.
     */
    @Override
    public void readSensors()
    {
        for (IMechanism mechanism : this.mechanismList)
        {
            Tracer.trace("Reading sensors for mechanism %s", mechanism.getClass().getName());

            try
            {
                mechanism.readSensors();
            }
            catch (Exception ex)
            {
                System.err.println("Encountered exception: " + ex.toString());
                if (TuningConstants.THROW_EXCEPTIONS)
                {
                    throw ex;
                }
            }

            Tracer.trace("Finished reading sensors for mechanism %s", mechanism.getClass().getName());
        }
    }

    /**
     * Calculates the various states to use for each mechanism based on the sensors/operations and applies them to the outputs (actuators, etc.).
     * This function is called every ~20ms whenever the robot is enabled in some mode, after the update function is called.
     * @param mode the current robot mode
     */
    @Override
    public void update(RobotMode mode)
    {
        for (IMechanism mechanism : this.mechanismList)
        {
            Tracer.trace("Updating mechanism %s", mechanism.getClass().getName());

            try
            {
                mechanism.update(mode);
            }
            catch (Exception ex)
            {
                System.err.println("Encountered exception: " + ex.toString());
                if (TuningConstants.THROW_EXCEPTIONS)
                {
                    throw ex;
                }
            }

            Tracer.trace("Finished update for mechanism %s", mechanism.getClass().getName());
        }
    }

    /**
     * Stops each mechanism by stopping the motion of all actuators, and resetting any helpers (e.g. filters, etc.) that depend on time.
     * This function is called whenever the robot is transitioning from enabled to disabled state.
     */
    @Override
    public void stop()
    {
        for (IMechanism mechanism : this.mechanismList)
        {
            Tracer.trace("Stopping mechanism %s", mechanism.getClass().getName());

            try
            {
                mechanism.stop();
            }
            catch (Exception ex)
            {
                System.err.println("Encountered exception: " + ex.toString());
                if (TuningConstants.THROW_EXCEPTIONS)
                {
                    throw ex;
                }
            }

            Tracer.trace("Finished stop for mechanism %s", mechanism.getClass().getName());
        }
    }
}
