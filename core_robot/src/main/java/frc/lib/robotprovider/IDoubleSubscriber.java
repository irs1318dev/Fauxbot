package frc.lib.robotprovider;

/**
 * Represents a Double that can be read from Network Tables
 */
public interface IDoubleSubscriber
{
    /**
     * Retrieve the current value
     * @return current value
     */
    double get();
}
