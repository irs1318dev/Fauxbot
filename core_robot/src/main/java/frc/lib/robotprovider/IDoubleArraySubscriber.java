package frc.lib.robotprovider;

/**
 * Represents a Double Array that can be read from Network Tables
 */
public interface IDoubleArraySubscriber
{
    /**
     * Retrieve the current values
     * 
     * @return current values
     */
    double[] get();

    /**
     * Gets the last time the entry's value was changed
     * 
     * @return last change time in seconds
     */
    double getLastChange();

    /**
     * Retrieve all of the values that have been received from the network tables since last time this was called
     * 
     * @return array of values received from the network tables, with timestamps
     */
    DoubleArrayValue[] getValues();
}
