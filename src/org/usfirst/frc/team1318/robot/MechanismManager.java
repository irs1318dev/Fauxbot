package org.usfirst.frc.team1318.robot;

import java.util.List;

import org.usfirst.frc.team1318.robot.common.IMechanism;
import org.usfirst.frc.team1318.robot.driver.Driver;

public class MechanismManager implements IMechanism
{
    public final List<IMechanism> mechanismList;

    public MechanismManager(List<IMechanism> mechanismList)
    {
        this.mechanismList = mechanismList;
    }

    @Override
    public void update()
    {
        for (IMechanism mechanism : this.mechanismList)
        {
            try
            {
                mechanism.update();
            }
            catch (Exception ex)
            {
                if (TuningConstants.THROW_EXCEPTIONS)
                {
                    throw ex;
                }
            }
        }
    }

    @Override
    public void stop()
    {
        for (IMechanism mechanism : this.mechanismList)
        {
            try
            {
                mechanism.stop();
            }
            catch (Exception ex)
            {
                if (TuningConstants.THROW_EXCEPTIONS)
                {
                    throw ex;
                }
            }
        }
    }

    @Override
    public void setDriver(Driver driver)
    {
        for (IMechanism mechanism : this.mechanismList)
        {
            mechanism.setDriver(driver);
        }
    }
}
