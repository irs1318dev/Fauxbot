package frc.lib.robotprovider;

/**
 * Represents a Integer that can be read from Network Tables
 */
public interface IIntegerSubscriber
{
    /**
     * Retrieve the current value
     * @return current value
     */
    long get();
}
