package frc.team1318.robot.common.robotprovider;

import java.util.List;

public interface IOpenCVProvider
{
    IMat newMat();
    IMat newMat(int i, int j, int cvType);
    IPoint newPoint(double d, double e);
    IScalar newScalar(int i);
    IScalar newScalar(int lifecamHsvFilterLowV0, int lifecamHsvFilterLowV1, int lifecamHsvFilterLowV2);
    ISize newSize(int i, int j);
    void imwrite(String format, IMat image);
    double contourArea(IMatOfPoint contour);
    IRect boundingRect(IMatOfPoint contour);
    IMoments moments(IMatOfPoint contour);
    void findContours(IMat frame, List<IMatOfPoint> contours, IMat unused, int retrExternal, int chainApproxTc89Kcos);
    void cvtColor(IMat sourceBGR, IMat frame, int imgprocColorBgr2hsv);
    void inRange(IMat sourceHSV, IScalar lowerBound, IScalar upperBound, IMat frame);
    void initUndistortRectifyMap(IMat intrinsicMatrix, IMat distCoeffs, IMat r, IMat newCameraMatrix, ISize size, int cvType, IMat mapX, IMat mapY);
    void remap(IMat source, IMat frame, IMat mapX, IMat mapY, int imgprocInterLinear, int imgprocWarpFillOutliers, IScalar newScalar);
}