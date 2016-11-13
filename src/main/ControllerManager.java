package main;

import java.util.ArrayList;

import main.Common.IController;
import main.GarageDoor.GarageDoorController;

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
    public void setDriver(main.Driver.Driver driver)
    {
        for (IController controller : this.controllerList)
        {
            controller.setDriver(driver);
        }
    }
}
