package frc.robot.vision.common;

import frc.robot.common.robotprovider.*;

public class ImageUndistorter
{
    private static final int IMGPROC_INTER_LINEAR = 1;
    private static final int IMGPROC_WARP_FILL_OUTLIERS = 8;
    private static final int CVTYPE_CV_32FC1 = 5;

    private final IOpenCVProvider provider;

    private IMat mapX;
    private IMat mapY;

    /**
     * Initializes a new instance of the ImageUndistorter class.
     * For background, see http://docs.opencv.org/3.1.0/d4/d94/tutorial_camera_calibration.html
     */
    public ImageUndistorter(IOpenCVProvider provider)
    {
        this.provider = provider;

        ISize size;
        IMat intrinsicMatrix;
        intrinsicMatrix = ImageUndistorter.build320x240Intrinsic(provider);
        size = provider.newSize(320, 240);

        IMat distCoeffs = ImageUndistorter.buildDistortion(provider);

        // initialize mapX and mapY
        IMat mapX = provider.newMat();
        IMat mapY = provider.newMat();

        // unused:
        IMat R = provider.newMat();
        IMat newCameraMatrix = provider.newMat();

        provider.initUndistortRectifyMap(intrinsicMatrix, distCoeffs, R, newCameraMatrix, size, ImageUndistorter.CVTYPE_CV_32FC1, mapX, mapY);

        this.mapX = mapX;
        this.mapY = mapY;

        intrinsicMatrix.release();
        distCoeffs.release();

        R.release();
        newCameraMatrix.release();
    }

    /**
     * Undistort the frame so that straight lines appear straight in the image
     * @param frame to undistort
     * @return an non-distorted version of the provided frame
     */
    public IMat undistortFrame(IMat frame)
    {
        IMat source = frame.clone();

        this.provider.remap(source, frame, this.mapX, this.mapY, ImageUndistorter.IMGPROC_INTER_LINEAR, ImageUndistorter.IMGPROC_WARP_FILL_OUTLIERS, provider.newScalar(0));
        source.release();

        return frame;
    }

    /**
     * Build an intrinsic matrix for the Axis M1011 camera at 320x240 resolution.
     * @return an intrinsic matrix
     */
    private static IMat build320x240Intrinsic(IOpenCVProvider provider)
    {
        IMat intrinsicMatrix = provider.newMat(3, 3, ImageUndistorter.CVTYPE_CV_32FC1);

        intrinsicMatrix.put(0, 0, 160); // focal length x
        intrinsicMatrix.put(0, 1, 0.0);
        intrinsicMatrix.put(0, 2, 160.0); // center x

        intrinsicMatrix.put(1, 0, 0.0);
        intrinsicMatrix.put(1, 1, 120); // focal length y [= x * h / w]
        intrinsicMatrix.put(1, 2, 120.0); // center y

        intrinsicMatrix.put(2, 0, 0.0);
        intrinsicMatrix.put(2, 1, 0.0);
        intrinsicMatrix.put(2, 2, 1.0); // flat z

        return intrinsicMatrix;
    }

    /**
     * Build an intrinsic matrix for the Axis M1011 camera at 640x480 resolution.
     * @return an intrinsic matrix
     */
    @SuppressWarnings("unused")
    private static IMat build640x480Intrinsic(IOpenCVProvider provider)
    {
        IMat intrinsicMatrix = provider.newMat(3, 3, ImageUndistorter.CVTYPE_CV_32FC1);

        intrinsicMatrix.put(0, 0, 320); // focal length x
        intrinsicMatrix.put(0, 1, 0.0);
        intrinsicMatrix.put(0, 2, 320.0); // center x

        intrinsicMatrix.put(1, 0, 0.0);
        intrinsicMatrix.put(1, 1, 240); // focal length y [= x * h / w]
        intrinsicMatrix.put(1, 2, 240.0); // center y

        intrinsicMatrix.put(2, 0, 0.0);
        intrinsicMatrix.put(2, 1, 0.0);
        intrinsicMatrix.put(2, 2, 1.0); // flat z

        return intrinsicMatrix;
    }

    /**
     * Build an distortion coefficient matrix for the Axis M1011 camera.
     * @return an distortion matrix
     */
    private static IMat buildDistortion(IOpenCVProvider provider)
    {
        IMat distortionCoeffs = provider.newMat(4, 1, ImageUndistorter.CVTYPE_CV_32FC1);

        double p_factor = 0.00;

        distortionCoeffs.put(0, 0, -0.055); // k1 * r^2
        distortionCoeffs.put(1, 0, 0.0); // k2 * r^4
        distortionCoeffs.put(2, 0, -p_factor); // tangential p1
        distortionCoeffs.put(3, 0, p_factor); // tangential p2

        return distortionCoeffs;
    }
}
