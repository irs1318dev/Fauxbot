package frc.robot.common.robotprovider;

public interface IDriverStation
{
    public String getEventName();
    public Alliance getAlliance();
    public int getLocation();
    public int getMatchNumber();
    public MatchType getMatchType();
    public int getReplayNumber();
    public double getMatchTime();
    public boolean isAutonomous();
    public String getGameSpecificMessage();
}