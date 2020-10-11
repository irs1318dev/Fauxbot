package frc.robot.common.robotprovider;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.*;
import org.opencv.imgcodecs.*;
import org.opencv.imgproc.*;

public class OpenCVProvider implements IOpenCVProvider
{
  public OpenCVProvider()
  {
  }

  @Override
  public IMat newMat()
  {
        return new MatWrapper(new Mat());
  }

    @Override
  public IMat newMat(int i, int j, int cvType)
  {
        return new MatWrapper(new Mat(i, j, cvType));
    }

  @Override
  public IPoint newPoint(double d, double e)
  {
        return new PointWrapper(new Point(d, e));
    }

  @Override
  public IScalar newScalar(int i)
  {
        return new ScalarWrapper(new Scalar(i));
  }

  @Override
  public IScalar newScalar(int v0, int v1, int v2)
  {
        return new ScalarWrapper(new Scalar(v0, v1, v2));
  }

  @Override
  public ISize newSize(int i, int j)
  {
    return new SizeWrapper(new Size(i, j));
  }

  @Override
  public IMatOfPoint2f convertToMatOfPoints2f(IMatOfPoint points)
  {
    MatOfPoint unwrapped = OpenCVProvider.unwrap(points);
    MatOfPoint2f newMop2f = new MatOfPoint2f();
    unwrapped.convertTo(newMop2f, CvType.CV_32FC2);
    return new MatOfPoint2fWrapper(newMop2f);
  }

  @Override
  public void imwrite(String format, IMat image)
  {
    Imgcodecs.imwrite(format, OpenCVProvider.unwrap(image));
  }

  @Override
  public double contourArea(IMatOfPoint contour)
  {
    return Imgproc.contourArea(OpenCVProvider.unwrap(contour));
  }

  @Override
  public IRect boundingRect(IMatOfPoint points)
  {
    return new RectWrapper(Imgproc.boundingRect(OpenCVProvider.unwrap(points)));
  }

  @Override
  public IRotatedRect minAreaRect(IMatOfPoint2f points)
  {
    return new RotatedRectWrapper(Imgproc.minAreaRect(OpenCVProvider.unwrap(points)));
  }

  @Override
  public IMoments moments(IMatOfPoint array)
  {
    return new MomentsWrapper(Imgproc.moments(OpenCVProvider.unwrap(array)));
  }

  @Override
  public void findContours(IMat image, List<IMatOfPoint> contours, IMat hierarchy, int mode, int method)
  {
    List<MatOfPoint> unwrappedContours = new ArrayList<MatOfPoint>(contours.size());
    for (IMatOfPoint contour : contours)
    {
      unwrappedContours.add(OpenCVProvider.unwrap(contour));
    }

    Imgproc.findContours(OpenCVProvider.unwrap(image), unwrappedContours, OpenCVProvider.unwrap(hierarchy), mode, method);

    for (int i = 0; i < unwrappedContours.size(); i++)
    {
      contours.add(i, new MatOfPointWrapper(unwrappedContours.get(i)));
    }
  }

  @Override
  public void cvtColor(IMat src, IMat dst, int code)
  {
    Imgproc.cvtColor(OpenCVProvider.unwrap(src), OpenCVProvider.unwrap(dst), code);
  }

  @Override
  public void inRange(IMat src, IScalar lowerb, IScalar upperb, IMat dst)
  {
    Core.inRange(OpenCVProvider.unwrap(src), OpenCVProvider.unwrap(lowerb), OpenCVProvider.unwrap(upperb), OpenCVProvider.unwrap(dst));
  }

  @Override
  public void initUndistortRectifyMap(IMat intrinsicMatrix, IMat distCoeffs, IMat r, IMat newCameraMatrix, ISize size, int cvType, IMat mapX, IMat mapY)
  {
    Imgproc.initUndistortRectifyMap(OpenCVProvider.unwrap(intrinsicMatrix), OpenCVProvider.unwrap(distCoeffs), OpenCVProvider.unwrap(r), OpenCVProvider.unwrap(newCameraMatrix), OpenCVProvider.unwrap(size), cvType, OpenCVProvider.unwrap(mapX), OpenCVProvider.unwrap(mapY));
  }

  @Override
  public void remap(IMat src, IMat dst, IMat mapX, IMat mapY, int interpolation, int borderMode, IScalar newScalar)
  {
    Imgproc.remap(OpenCVProvider.unwrap(src), OpenCVProvider.unwrap(dst), OpenCVProvider.unwrap(mapX), OpenCVProvider.unwrap(mapY), interpolation, borderMode, OpenCVProvider.unwrap(newScalar));
  }

  public static Mat unwrap(IMat wrapper)
  {
    if (wrapper == null)
    {
      return null;
    }

    return ((MatWrapper)wrapper).wrappedObject;
  }

  public static MatOfPoint unwrap(IMatOfPoint wrapper)
  {
    if (wrapper == null)
    {
      return null;
    }

    return ((MatOfPointWrapper)wrapper).wrappedObject;
  }

  public static MatOfPoint2f unwrap(IMatOfPoint2f wrapper)
  {
    if (wrapper == null)
    {
      return null;
    }

    return ((MatOfPoint2fWrapper)wrapper).wrappedObject;
  }

  public static Moments unwrap(IMoments wrapper)
  {
    if (wrapper == null)
    {
      return null;
    }

    return ((MomentsWrapper)wrapper).wrappedObject;
  }

  public static Point unwrap(IPoint wrapper)
  {
    if (wrapper == null)
    {
      return null;
    }

    return ((PointWrapper)wrapper).wrappedObject;
  }

  public static Range unwrap(IRange wrapper)
  {
    if (wrapper == null)
    {
      return null;
    }

    return ((RangeWrapper)wrapper).wrappedObject;
  }

  public static Rect unwrap(IRect wrapper)
  {
    if (wrapper == null)
    {
      return null;
    }

    return ((RectWrapper)wrapper).wrappedObject;
  }

  public static Scalar unwrap(IScalar wrapper)
  {
    if (wrapper == null)
    {
      return null;
    }

    return ((ScalarWrapper)wrapper).wrappedObject;
  }

  public static Size unwrap(ISize wrapper)
  {
    if (wrapper == null)
    {
      return null;
    }

    return ((SizeWrapper)wrapper).wrappedObject;
  }
}