package frc.lib.robotprovider;

import java.util.Optional;
import java.util.OptionalInt;

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
    public Optional<Alliance> getAlliance()
    {
        return Optional.of(Alliance.Red);
    }

    @Override
    public OptionalInt getLocation()
    {
        return OptionalInt.of(0);
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
    public boolean isFMSMode()
    {
        return false;
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