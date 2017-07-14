package org.usfirst.frc.team1318.robot;

import java.util.List;

import org.usfirst.frc.team1318.robot.common.IController;
import org.usfirst.frc.team1318.robot.driver.Driver;

public class ControllerManager implements IController
{
    public final List<IController> controllerList;

    public ControllerManager(List<IController> controllerList)
    {
        this.controllerList = controllerList;
    }

    @Override
    public void update()
    {
        for (IController controller : this.controllerList)
        {
            try
            {
                controller.update();
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
        for (IController controller : this.controllerList)
        {
            try
            {
                controller.stop();
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
        for (IController controller : this.controllerList)
        {
            controller.setDriver(driver);
        }
    }
}
