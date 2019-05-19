package frc.robot.common.robotprovider;

import edu.wpi.first.wpilibj.DriverStation;

public class DriverStationWrapper implements IDriverStation
{
    private final DriverStation wrappedObject;

    public DriverStationWrapper()
    {
        this.wrappedObject = DriverStation.getInstance();
    }

    @Override
    public String getGameSpecificMessage()
    {
        return this.wrappedObject.getGameSpecificMessage();
    }
}