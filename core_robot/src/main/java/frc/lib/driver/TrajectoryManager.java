package frc.lib.driver;

import java.util.HashMap;

import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import frc.robot.TuningConstants;
import frc.lib.helpers.ExceptionHelpers;
import frc.lib.robotprovider.ITrajectory;

@Singleton
public class TrajectoryManager
{
    private final HashMap<String, TrajectoryBuilder> trajectoryBuilderMap;
    private final HashMap<String, ITrajectory> map;

    /**
     * Initializes a new TrajectoryManager
     */
    @Inject
    public TrajectoryManager()
    {
        this.map = new HashMap<String, ITrajectory>();
        this.trajectoryBuilderMap = new HashMap<String, TrajectoryBuilder>();
    }

    public ITrajectory getTrajectory(String name)
    {
        ITrajectory trajectory = this.map.getOrDefault(name, null);
        if (trajectory == null)
        {
            TrajectoryBuilder trajectoryBuilder = this.trajectoryBuilderMap.getOrDefault(name, null);
            if (trajectoryBuilder != null)
            {
                trajectory = new RoadRunnerTrajectoryWrapper(trajectoryBuilder.build());
                this.map.put(name, trajectory);
                this.trajectoryBuilderMap.remove(name);
            }
        }

        return trajectory;
    }

    public void addTrajectory(String name, TrajectoryBuilder trajectoryBuilder)
    {
        this.addTrajectory(name, trajectoryBuilder, TuningConstants.TRAJECTORY_FORCE_BUILD);
    }

    public void addTrajectory(String name, TrajectoryBuilder trajectoryBuilder, boolean forceBuild)
    {
        if (forceBuild)
        {
            this.addTrajectory(name, new RoadRunnerTrajectoryWrapper(trajectoryBuilder.build()));
        }
        else
        {
            this.trajectoryBuilderMap.put(name, trajectoryBuilder);
        }
    }

    public void addTrajectory(String name, ITrajectory builtTrajectory)
    {
        this.map.put(name, builtTrajectory);
    }

    public void buildAll()
    {
        for (String name : this.trajectoryBuilderMap.keySet())
        {
            TrajectoryBuilder trajectoryBuilder = this.trajectoryBuilderMap.getOrDefault(name, null);
            if (trajectoryBuilder != null)
            {
                this.map.put(name, new RoadRunnerTrajectoryWrapper(trajectoryBuilder.build()));
            }
            else
            {
                ExceptionHelpers.Assert(false, "Unexpected null trajectory named " + name);
            }
        }

        this.trajectoryBuilderMap.clear();
    }
}