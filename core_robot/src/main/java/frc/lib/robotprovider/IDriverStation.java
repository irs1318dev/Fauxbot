package frc.lib.robotprovider;

public interface IDriverStation
{
    public String getEventName();
    public Alliance getAlliance();
    public int getLocation();
    public int getMatchNumber();
    public MatchType getMatchType();
    public int getReplayNumber();
    public double getMatchTime();
    public RobotMode getMode();
    public String getGameSpecificMessage();
}