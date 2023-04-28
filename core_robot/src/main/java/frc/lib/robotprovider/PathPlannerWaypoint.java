package frc.lib.robotprovider;

public class PathPlannerWaypoint
{
    public final double x;
    public final double y;
    public final double heading;
    public final double orientation;
    public final double velocityOverride;

    public static int setOrientation(boolean isRed, boolean forward)
    {
        if (forward)
        {
            return isRed ? 180 : 0;
        }
        else
        {
            return isRed ? 0 : 180;
        }
    }

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
        this(x, y, heading, 0.0);
    }

    /**
     * Creates a waypoint at position (x, y), traveling in the direction of the heading, facing the orientation, with an overridden velocity
     * @param point position (in inches)
     * @param heading travel direction (tangent, in degrees)
     * @param orientation facing direction (in degrees)
     */
    public PathPlannerWaypoint(Point2d point, double heading, double orientation)
    {
        this(point.x, point.y, heading, orientation, -1.0);
    }

    /**
     * Creates a waypoint at position (x, y), traveling in the direction of the heading, facing the orientation, with an overridden velocity
     * @param point position (in inches)
     * @param heading travel direction (tangent, in degrees)
     * @param orientation facing direction (in degrees)
     * @param velocityOverride while approaching this waypoint (in inches per second)
     */
    public PathPlannerWaypoint(Point2d point, double heading, double orientation, double velocityOverride)
    {
        this(point.x, point.y, heading, orientation, velocityOverride);
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
        this(x, y, heading, orientation, -1.0);
    }

    /**
     * Creates a waypoint at position (x, y), traveling in the direction of the heading, facing the orientation, with an overridden velocity
     * @param x position (in inches)
     * @param y position (in inches)
     * @param heading travel direction (tangent, in degrees)
     * @param orientation facing direction (in degrees)
     * @param velocityOverride while approaching this waypoint (in inches per second)
     */
    public PathPlannerWaypoint(double x, double y, double heading, double orientation, double velocityOverride)
    {
        this.x = x;
        this.y = y;
        this.heading = heading;
        this.orientation = orientation;
        this.velocityOverride = velocityOverride;
    }
}