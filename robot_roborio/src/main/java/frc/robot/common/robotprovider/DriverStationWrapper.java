package frc.robot.common.robotprovider;

import com.google.inject.Singleton;

import edu.wpi.first.wpilibj.DriverStation;

@Singleton
public class DriverStationWrapper implements IDriverStation
{
    private final DriverStation wrappedObject;

    public DriverStationWrapper()
    {
        this.wrappedObject = DriverStation.getInstance();
    }

    @Override
    public String getEventName()
    {
        return this.wrappedObject.getEventName();
    }

    @Override
    public Alliance getAlliance()
    {
        switch (this.wrappedObject.getAlliance())
        {
            case Red:
                return Alliance.Red;

            case Blue:
                return Alliance.Blue;

            default:
            case Invalid:
                return Alliance.Invalid;
        }
    }

    @Override
    public int getLocation()
    {
        return this.wrappedObject.getLocation();
    }

    @Override
    public int getMatchNumber()
    {
        return this.wrappedObject.getMatchNumber();
    }

    @Override
    public MatchType getMatchType()
    {
        switch (this.wrappedObject.getMatchType())
        {
            case Practice:
                return MatchType.Practice;

            case Qualification:
                return MatchType.Qualification;

            case Elimination:
                return MatchType.Elimination;

            default:
            case None:
                return MatchType.None;
        }
    }

    @Override
    public int getReplayNumber()
    {
        return this.wrappedObject.getReplayNumber();
    }

    @Override
    public double getMatchTime()
    {
        return this.wrappedObject.getMatchTime();
    }

    @Override
    public boolean isAutonomous()
    {
        return this.wrappedObject.isAutonomous();
    }

    @Override
    public String getGameSpecificMessage()
    {
        return this.wrappedObject.getGameSpecificMessage();
    }
}