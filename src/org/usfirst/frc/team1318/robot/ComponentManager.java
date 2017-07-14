package org.usfirst.frc.team1318.robot;

import org.usfirst.frc.team1318.robot.garagedoor.GarageDoorComponent;

public class ComponentManager
{
    private GarageDoorComponent garageDoor;

    public ComponentManager()
    {
        this.garageDoor = new GarageDoorComponent();
    }

    public GarageDoorComponent getGarageDoorComponent()
    {
        return this.garageDoor;
    }
}
