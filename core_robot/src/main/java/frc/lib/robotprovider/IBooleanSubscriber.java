package frc.lib.robotprovider;

/**
 * Represents a Boolean that can be read from Network Tables
 */
public interface IBooleanSubscriber
{
    /**
     * Retrieve the current value
     * @return current value
     */
    boolean get();
}
