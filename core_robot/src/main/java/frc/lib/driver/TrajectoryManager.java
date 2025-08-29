package frc.lib.driver;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import frc.lib.robotprovider.ITrajectory;

@Singleton
public class TrajectoryManager
{
    private final HashMap<String, ITrajectory> map;

    /**
     * Initializes a new TrajectoryManager
     */
    @Inject
    public TrajectoryManager()
    {
        this.map = new HashMap<String, ITrajectory>();
    }

    public ITrajectory getTrajectory(String name)
    {
        return this.map.getOrDefault(name, null);
    }

    public void addTrajectory(String name, ITrajectory builtTrajectory)
    {
        this.map.put(name, builtTrajectory);
    }

    public Set<String> getNames()
    {
        return this.map.keySet();
    }
}