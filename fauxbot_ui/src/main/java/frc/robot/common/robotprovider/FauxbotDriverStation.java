package frc.robot.common.robotprovider;

public class FauxbotDriverStation implements IDriverStation
{
    public static FauxbotDriverStation Instance = new FauxbotDriverStation();

    private RobotMode currentMode;

    private FauxbotDriverStation()
    {
        this.currentMode = RobotMode.Disabled;
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
    public RobotMode getMode()
    {
        return this.currentMode;
    }

    @Override
    public String getGameSpecificMessage()
    {
        return "LLL";
    }

    public void setMode(RobotMode mode)
    {
        this.currentMode = mode;
    }
}