package frc.lib.robotprovider;

import java.util.Optional;
import java.util.OptionalInt;

/**
 * Represents information from the driver station about the current state of the match, if any
 */
public interface IDriverStation
{
    /**
     * Retrieve the event name from FMS
     * @return event name
     */
    public String getEventName();

    /**
     * Retrieve the current alliance from FMS, fallback to current configuration in DriverStation software
     * @return the current alliance, or empty if invalid
     */
    public Optional<Alliance> getAlliance();

    /**
     * Retrieve the current driver station location from FMS, fallback to the current configuration in DriverStation software
     * @return the current alliance station (1, 2, 3), or empty if invalid
     */
    public OptionalInt getLocation();

    /**
     * Retrieve the current match number from FMS
     * @return the current match number
     */
    public int getMatchNumber();

    /**
     * Retrieve the current match type from FMS
     * @return the type of match (Practice, Qualification, Elimination, None)
     */
    public MatchType getMatchType();

    /**
     * Retrieve the replay number from FMS
     * @return the current replay number
     */
    public int getReplayNumber();

    /**
     * Retrieve the approximate match time from FMS.
     * When connected to the field during a match, this will typically be a whole number, counting down.
     * When connected to the Driver Station in practice mode, this will count down.
     * When conencted to the Driver Station in teleop or autonomous mode, this will count up.
     * @return the current match time remaining
     */
    public double getMatchTime();

    /**
     * Retrieve the current mode of the robot
     * @return the current mode of the robot (Teleop, Test, Autonomous, Disabled)
     */
    public RobotMode getMode();

    /**
     * Retrieves whether the robot is currently attached to FMS
     * @return true if the robot is currently competing on a field controlled by FMS
     */
    public boolean isFMSMode();

    /**
     * Retrieve any game-specific message from FMS, fallback to Driver Station game data setting
     * @return the game-specific message
     */
    public String getGameSpecificMessage();
}
