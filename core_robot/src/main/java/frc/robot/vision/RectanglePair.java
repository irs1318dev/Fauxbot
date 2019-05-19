package frc.robot.vision;

import frc.robot.common.robotprovider.IPoint;
import frc.robot.common.robotprovider.IRotatedRect;

public class RectanglePair
{
    private final IRotatedRect left;
    private final IRotatedRect right;

    private IPoint center;

    public RectanglePair(IRotatedRect left, IRotatedRect right)
    {
        this.left = left;
        this.right = right;

        this.center = null;
    }

    public IPoint getCenter()
    {
        if (this.center == null)
        {
            this.center = this.getPreferredRect().getCenter();
        }

        return this.center;
    }

    public double getX()
    {
        return this.getCenter().getX();
    }

    public double getY()
    {
        return this.getCenter().getY();
    }

    public double getXOffset()
    {
        return this.getX() - VisionConstants.LIFECAM_CAMERA_CENTER_WIDTH;
    }

    public double getYOffset()
    {
        return VisionConstants.LIFECAM_CAMERA_CENTER_HEIGHT - this.getY();
    }

    public double getMeasuredAngleX()
    {
        return Math.atan(this.getXOffset() / VisionConstants.LIFECAM_CAMERA_FOCAL_LENGTH_X) * VisionConstants.RADIANS_TO_ANGLE; // - VisionConstants.CAMERA_HORIZONTAL_MOUNTING_ANGLE;
    }

    public double getMeasuredAngleY()
    {
        return Math.atan(this.getYOffset() / VisionConstants.LIFECAM_CAMERA_FOCAL_LENGTH_Y) * VisionConstants.RADIANS_TO_ANGLE;
    }

    private IRotatedRect getPreferredRect()
    {
        if (this.left != null)
        {
            return this.left;
        }

        return this.right;
    }
}
