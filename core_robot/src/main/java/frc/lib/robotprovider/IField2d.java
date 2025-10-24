package frc.lib.robotprovider;

public interface IField2d
{
    /**
     * Sets the pose for the robot
     * @param xPos x-position of robot
     * @param yPos y-position of robot
     * @param yaw yaw angle of the robot, in degrees
     */
    void setRobotPose(double xPos, double yPos, double yaw);

    /**
     * Gets the current robot pose
     * @return the Pose2d contained by the object
     */
    Pose2d getRobotPose();
}
