package frc.robot.common;

import java.util.List;

import frc.robot.TuningConstants;
import frc.robot.driver.common.Driver;

public class MechanismManager implements IMechanism
{
    public final List<IMechanism> mechanismList;

    public MechanismManager(List<IMechanism> mechanismList)
    {
        this.mechanismList = mechanismList;
    }

    @Override
    public void readSensors()
    {
        for (IMechanism mechanism : this.mechanismList)
        {
            try
            {
                mechanism.readSensors();
            }
            catch (Exception ex)
            {
                System.out.println("Encountered exception: " + ex.toString());
                if (TuningConstants.THROW_EXCEPTIONS)
                {
                    throw ex;
                }
            }
        }
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
                System.out.println("Encountered exception: " + ex.toString());
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
                System.out.println("Encountered exception: " + ex.toString());
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
