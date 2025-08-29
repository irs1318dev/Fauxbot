package frc.lib.robotprovider;

/**
 * Represents a Relay plugged into a relay channel
 */
public interface IRelay
{
    /**
     * set the desired state of the relay
     * For relays with RelayDirection "Both", this can be set to any value (Forward, Reverse, On, Off)
     * For relays with RelayDirection "Forward" or "Reverse", one should prefer to use On/Off.
     * @param value state to apply to the relay
     */
    void set(RelayValue value);

    /**
     * configures which states the relay can/should be set in
     * @param direction to use (Both, Forward, Reverse)
     */
    void configureDirection(RelayDirection direction);
}
