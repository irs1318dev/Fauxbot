package main;

import main.GarageDoor.GarageDoorComponent;

public class ComponentManager
{
	private GarageDoorComponent garageDoor;

	public ComponentManager()
	{
		this.garageDoor = new GarageDoorComponent();
	}

    public GarageDoorComponent getGarageDoor()
    {
        return this.garageDoor;
    }
}
