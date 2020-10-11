package frc.robot;

/**
 * All constants describing the important aspects of the vision system being used (Resolution, FOV, Focal Length)
 * 
 * @author Will
 * 
 */
public class VisionConstants
{
    // Information about Microsoft LifeCam HD-3000 USB-based camera:
    public static final int LIFECAM_CAMERA_RESOLUTION_X = 1280;
    public static final int LIFECAM_CAMERA_RESOLUTION_Y = 720;
    public static final double LIFECAM_CAMERA_CENTER_WIDTH = VisionConstants.LIFECAM_CAMERA_RESOLUTION_X / 2.0 - 0.5; // distance from center to left/right sides in pixels
    public static final double LIFECAM_CAMERA_CENTER_HEIGHT = VisionConstants.LIFECAM_CAMERA_RESOLUTION_Y / 2.0 - 0.5; // distance from center to top/bottom in pixels
    public static final double LIFECAM_CAMERA_FIELD_OF_VIEW_X = 61.37272; // 16:9 field of view along x axis https://vrguy.blogspot.com/2013/04/converting-diagonal-field-of-view-and.html to convert from 68.5 degrees diagonal.
    public static final double LIFECAM_CAMERA_FIELD_OF_VIEW_Y = 36.91875; // 16:9 field of view along y axis
    public static final double LIFECAM_CAMERA_CENTER_VIEW_ANGLE = VisionConstants.LIFECAM_CAMERA_FIELD_OF_VIEW_X / 2.0;
    public static final double LIFECAM_CAMERA_FOCAL_LENGTH_X = 1078.4675; // focal_length = res_* / (2.0 * tan (FOV_* / 2.0)
    public static final double LIFECAM_CAMERA_FOCAL_LENGTH_Y = 1078.4675; // focal_length = res_* / (2.0 * tan (FOV_* / 2.0)
}
