package frc.robot;
import java.util.Optional;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import frc.lib.robotprovider.Alliance;
import frc.lib.robotprovider.IDriverStation;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.Point2d;

@Singleton
public class AutonLocManager
{
    private boolean isRed;
    private IDriverStation driverStation;

    public Point2d P1;
    public Point2d P2;

    public AutonLocManager(boolean isRed)
    {
        this.isRed = isRed;
        this.setValues();
    }

    @Inject
    public AutonLocManager(IRobotProvider provider) 
    {
        this.driverStation = provider.getDriverStation();
    }

    public void updateAlliance()
    {
        Optional<Alliance> alliance = driverStation.getAlliance();
        this.isRed = alliance.isPresent() && alliance.get() == Alliance.Red;
        this.setValues();
    }

    public boolean getRedUpdateAlliance()
    {
        Optional<Alliance> alliance = driverStation.getAlliance();
        this.isRed = alliance.isPresent() && alliance.get() == Alliance.Red;
        this.setValues();
        return isRed;
    }

    public double getOrientationOrHeading(double orientationOrHeading)
    {
        return AutonLocManager.getOrientationOrHeading(this.isRed, orientationOrHeading);
    }

    public boolean getIsRed()
    {
        return this.isRed;
    }

    private void setValues()
    {
        // Red is positive
        // Blue is negative
        this.P1 = new Point2d(AutonLocManager.getXPosition(this.isRed, 326.5), 64);
        this.P2 = new Point2d(AutonLocManager.getXPosition(this.isRed, 289 + 4.818761855), 198 - 23.5416793);
    }

    private static double getOrientationOrHeading(boolean isRed, double orientationOrHeading)
    {
        if (isRed)
        {
            return orientationOrHeading;
        }
        else
        {
            return 180.0 - orientationOrHeading;
        }
    }

    private static double getXPosition(boolean isRed, double position)
    {
        if (isRed)
        {
            return position;
        }
        else
        {
            return position * -1.0;
        }
    }
}
