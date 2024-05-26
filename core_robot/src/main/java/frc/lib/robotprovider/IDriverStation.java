package frc.lib.robotprovider;

import java.util.Optional;
import java.util.OptionalInt;

public interface IDriverStation
{
    public String getEventName();
    public Optional<Alliance> getAlliance();
    public OptionalInt getLocation();
    public int getMatchNumber();
    public MatchType getMatchType();
    public int getReplayNumber();
    public double getMatchTime();
    public RobotMode getMode();
    public boolean isFMSMode();
    public String getGameSpecificMessage();
}