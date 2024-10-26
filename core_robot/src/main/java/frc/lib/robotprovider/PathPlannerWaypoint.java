package frc.lib.robotprovider;

import java.util.OptionalDouble;

/**
 * Represents a waypoint along a trajectory that the robot should drive through
 */
public class PathPlannerWaypoint implements IPathPlannerGoal
{
    public final double x;
    public final double y;
    public final double heading;
    public final OptionalDouble orientation;

    /**
     * Creates a waypoint at position (x, y), assuming that the robot should be heading forward at this point and oriented forward.
     * @param x position (in inches)
     * @param y position (in inches)
     */
    public PathPlannerWaypoint(double x, double y)
    {
        this(x, y, 0.0);
    }

    /**
     * Creates a waypoint at position (x, y), traveling in the direction of the heading, facing the orientation, with an overridden velocity
     * @param x position (in inches)
     * @param y position (in inches)
     * @param heading travel direction (tangent, in degrees)
     */
    public PathPlannerWaypoint(double x, double y, double heading)
    {
        this(x, y, heading, OptionalDouble.empty());
    }

    /**
     * Creates a waypoint at position (x, y), traveling in the direction of the heading, facing the orientation, with an overridden velocity
     * @param x position (in inches)
     * @param y position (in inches)
     * @param heading travel direction (tangent, in degrees)
     * @param orientation facing direction (in degrees)
     */
    public PathPlannerWaypoint(double x, double y, double heading, double orientation)
    {
        this(x, y, heading, OptionalDouble.of(orientation));
    }

    /**
     * Creates a waypoint at position (x, y), traveling in the direction of the heading, facing the orientation, with an overridden velocity
     * @param point position (in inches)
     * @param heading travel direction (tangent, in degrees)
     * @param orientation facing direction (in degrees)
     */
    public PathPlannerWaypoint(Point2d point, double heading, double orientation)
    {
        this(
            point.x,
            point.y,
            heading,
            OptionalDouble.of(orientation));
    }

    /**
     * Creates a waypoint at position (x, y), traveling in the direction of the heading, facing the orientation, with an overridden velocity
     * @param x position (in inches)
     * @param y position (in inches)
     * @param heading travel direction (tangent, in degrees)
     * @param orientation facing direction (in degrees)
     */
    private PathPlannerWaypoint(
        double x,
        double y,
        double heading,
        OptionalDouble orientation)
    {
        this.x = x;
        this.y = y;
        this.heading = heading;
        this.orientation = orientation;
    }
}