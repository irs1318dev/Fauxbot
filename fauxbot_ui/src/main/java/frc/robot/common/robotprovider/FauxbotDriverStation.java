package frc.robot.common.robotprovider;

public class FauxbotDriverStation implements IDriverStation
{
    public FauxbotDriverStation()
    {
    }

    @Override
    public String getEventName()
    {
        return null;
    }

    @Override
    public Alliance getAlliance()
    {
        return Alliance.Invalid;
    }

    @Override
    public int getLocation()
    {
        return 0;
    }

    @Override
    public int getMatchNumber()
    {
        return 0;
    }

    @Override
    public MatchType getMatchType()
    {
        return MatchType.None;
    }

    @Override
    public int getReplayNumber()
    {
        return 0;
    }

    @Override
    public double getMatchTime()
    {
        return 0.0;
    }

    @Override
    public boolean isAutonomous()
    {
        return false;
    }

    @Override
    public String getGameSpecificMessage()
    {
        return "LLL";
    }
}