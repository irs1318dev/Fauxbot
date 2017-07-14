package org.usfirst.frc.team1318.robot;

import java.util.ArrayList;

import org.usfirst.frc.team1318.robot.common.IController;
import org.usfirst.frc.team1318.robot.garagedoor.GarageDoorController;

public class ControllerManager implements IController
{
    public final ComponentManager components;
    public final ArrayList<IController> controllerList;

    public ControllerManager(ComponentManager components)
    {
        this.components = components;
        this.controllerList = new ArrayList<>();
        this.controllerList.add(new GarageDoorController(this.components.getGarageDoorComponent()));
    }

    @Override
    public void update()
    {
        for (IController controller : this.controllerList)
        {
            controller.update();
        }
    }

    @Override
    public void stop()
    {
        for (IController controller : this.controllerList)
        {
            controller.stop();
        }
    }

    @Override
    public void setDriver(org.usfirst.frc.team1318.robot.driver.Driver driver)
    {
        for (IController controller : this.controllerList)
        {
            controller.setDriver(driver);
        }
    }
}
