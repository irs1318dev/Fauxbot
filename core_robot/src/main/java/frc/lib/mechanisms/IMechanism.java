package frc.lib.mechanisms;

import frc.lib.robotprovider.RobotMode;

/**
 * The interface for mechanism classes.
 * Mechanism classes define the logic that controls a mechanism given inputs (sensors) and operator-requested changes, and 
 * translates those into the actions that should be applied to the outputs (actuators, etc.).
 */
public interface IMechanism
{
    /**
     * Reads all of the sensors for the mechanism that will be used in macros/autonomous mode and records their values.
     * This function is called every ~20ms whenever the robot is enabled, shortly before the update function is called.
     */
    public void readSensors();

    /**
     * Calculates the various states to use for the mechanism based on the sensors/operations and applies them to the outputs (actuators, etc.).
     * This function is called every ~20ms whenever the robot is enabled in some mode, after the update function is called.
     * @param mode the current robot mode
     */
    public void update(RobotMode mode);

    /**
     * Stops the mechanism by stopping the motion of all actuators, and resetting any helpers (e.g. filters, etc.) that depend on time.
     * This function is called whenever the robot is transitioning from enabled to disabled state.
     */
    public void stop();
}
