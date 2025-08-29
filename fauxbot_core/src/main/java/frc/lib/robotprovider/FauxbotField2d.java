package frc.lib.robotprovider;

public class FauxbotField2d implements IField2d
{
    private final String name;

    private Pose2d pose;

    public FauxbotField2d(String name)
    {
        this.name = name;
    }

    @Override
    public void setRobotPose(double xPos, double yPos, double yaw)
    {
        this.pose.x = xPos;
        this.pose.y = yPos;
        this.pose.angle = yaw;
    }

    @Override
    public Pose2d getRobotPose()
    {
        return this.pose;
    }
}
