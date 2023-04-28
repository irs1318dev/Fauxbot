package frc.lib.mechanisms;

/**
 * The mechanism defines the logic that controls a mechanism given inputs and operator-requested actions, and 
 * translates those into the abstract functions that should be applied to the outputs.
 * 
 * @author Will
 *
 */
public interface IMechanism
{
    /**
     * read all of the sensors for the mechanism that we will use in macros/autonomous mode and record their values
     */
    public void readSensors();

    /**
     * calculate the various outputs to use based on the inputs and apply them to the outputs for the relevant mechanism
     */
    public void update();

    /**
     * stop the relevant mechanism
     */
    public void stop();
}
