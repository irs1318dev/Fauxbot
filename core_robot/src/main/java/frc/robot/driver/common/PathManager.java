package frc.robot.driver.common;

import java.util.HashMap;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import frc.robot.common.robotprovider.ITrajectory;

@Singleton
public class PathManager
{
    private HashMap<String, ITrajectory> map;

    /**
     * Initializes a new PathManager
     */
    @Inject
    public PathManager()
    {
        this.map = new HashMap<String, ITrajectory>();
    }

    public ITrajectory getTrajectory(String name)
    {
        return map.getOrDefault(name, null);
    }

    public void addPath(String name, ITrajectory path)
    {
        map.put(name, path);
    }
}
