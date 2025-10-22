# PathPlanner

PathPlanner is a library that is used to transform a series of waypoints that make up a path into a trajectory that the robot can automatically follow during an autonomous routine or macro.

Most trajectories need to be defined in the ```PathPlannerTrajectoryGenerator``` (in PathPlannerTrajectoryGenerator.java under core_robot\src\main\java\frc\robot\driver) within the generateTrajectories function, and assigned a name.

Each trajectory is added into a ```trajectoryManager``` which stores all generated paths.  The ```pathPlanner.buildTrajectory()``` function manages generating the trejectory based on a set of constraints.  Each path can have its own maximum acceleration and velocity both for translational movements (forwards, backwars, left, right) and rotation movements (spin clockwise or counterclockwise).  The path then is formed out of a series of ```PathPlannerWaypoint``` and ```PathPlannerRotationTarget``` objects, which represent the desired locations or orientations through which the robot will pass.  The ```PathPlannerWaypoint()``` takes the x-coordinate, y-coordinate, heading, and orientation.  The heading is the direction that the robot will be travelling when moving to/through the waypoint, and the orientation which is the direction the robot will face while traveling to/through the given waypoint.  After the path is described and built into a trajectory, the ```addTrajectory``` function will take parameters for the name of the path and for whether it is specific to the red alliance (```Optional.of(Alliance.Red)```), blue alliance (```Optional.of(Alliance.Blue)```), or can be used for any alliance (```Optional.empty()```).

```java
addTrajectory(
    trajectoryManager,
    pathPlanner.buildTrajectory(
        TuningConstants.MAX_TRANSLATIONAL_VELOCITY,
        TuningConstants.MAX_TRANSLATIONAL_ACCELERATION,
        TuningConstants.MAX_ROTATIONAL_VELOCITY,
        TuningConstants.MAX_ROTATIONAL_ACCELERATION,
        new PathPlannerWaypoint(200, 200, -180, 0),
        new PathPlannerWaypoint(100, 100, -180, 0)),
    "TheBestPathEverWritten",
    Optional.empty());
```
