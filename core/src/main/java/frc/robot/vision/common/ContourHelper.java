package frc.robot.vision.common;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import frc.robot.common.robotprovider.*;

public class ContourHelper
{
    private static final int IMGPROC_RETR_EXTERNAL = 0;
    private static final int IMGPROC_CHAIN_APPROX_TC89_KCOS = 4;

    /**
     * Find the largest contour in the frame
     * @param frame in which to look for contours
     * @return largest contour
     */
    public static IMatOfPoint findLargestContour(IOpenCVProvider provider, IMat frame)
    {
        return ContourHelper.findLargestContour(provider, frame, 0.0);
    }

    /**
     * Find the largest contour in the frame
     * @param frame in which to look for contours
     * @param minContourArea is the minimum contour area allowable
     * @return largest contour
     */
    public static IMatOfPoint findLargestContour(IOpenCVProvider provider, IMat frame, double minContourArea)
    {
        // find the contours using OpenCV API...
        IMat unused = provider.newMat();
        List<IMatOfPoint> contours = new ArrayList<IMatOfPoint>();
        provider.findContours(frame, contours, unused, ContourHelper.IMGPROC_RETR_EXTERNAL, ContourHelper.IMGPROC_CHAIN_APPROX_TC89_KCOS);
        unused.release();

        // find the largest contour...
        double largestContourArea = 0.0;
        IMatOfPoint largestContour = null;
        for (IMatOfPoint contour : contours)
        {
            double area = provider.contourArea(contour);
            if (area >= minContourArea && area > largestContourArea)
            {
                if (largestContour != null)
                {
                    largestContour.release();
                }

                largestContour = contour;
                largestContourArea = area;
            }
            else
            {
                contour.release();
            }
        }

        return largestContour;
    }

    /**
     * Find the two largest contours in the frame
     * @param frame in which to look for contours
     * @return two largest contours, largest then second largest
     */
    public static IMatOfPoint[] findTwoLargestContours(IOpenCVProvider provider, IMat frame)
    {
        return ContourHelper.findTwoLargestContours(provider, frame, 0.0);
    }

    /**
     * Find the two largest contours in the frame
     * @param frame in which to look for contours
     * @param minContourArea is the minimum contour area allowable
     * @return two largest contours, largest then second largest
     */
    public static IMatOfPoint[] findTwoLargestContours(IOpenCVProvider provider, IMat frame, double minContourArea)
    {
        // find the contours using OpenCV API...
        IMat unused = provider.newMat();
        List<IMatOfPoint> contours = new ArrayList<IMatOfPoint>();
        provider.findContours(frame, contours, unused, ContourHelper.IMGPROC_RETR_EXTERNAL, ContourHelper.IMGPROC_CHAIN_APPROX_TC89_KCOS);
        unused.release();

        double largestContourArea = 0.0;
        IMatOfPoint largestContour = null;

        double secondLargestContourArea = 0.0;
        IMatOfPoint secondLargestContour = null;

        // find the two largest contours...
        for (IMatOfPoint contour : contours)
        {
            double area = provider.contourArea(contour);
            if (area >= minContourArea && area > largestContourArea)
            {
                if (largestContour != null)
                {
                    if (secondLargestContour != null)
                    {
                        secondLargestContour.release();
                    }

                    secondLargestContour = largestContour;
                    secondLargestContourArea = largestContourArea;
                }

                largestContour = contour;
                largestContourArea = area;
            }
            else if (area >= minContourArea && area > secondLargestContourArea)
            {
                if (secondLargestContour != null)
                {
                    secondLargestContour.release();
                }

                secondLargestContour = contour;
                secondLargestContourArea = area;
            }
            else
            {
                contour.release();
            }
        }

        return new IMatOfPoint[] { largestContour, secondLargestContour };
    }

    /**
     * Find the two largest contours in the frame
     * @param frame in which to look for contours
     * @param minContourArea is the minimum contour area allowable
     * @param desiredContourHxWRatio is the desired height-to-width ratio for the contours (0.0 or below means ignore this)
     * @param allowableContourHxWRatioRange is the allowable range for the height-to-width ratio for the contours
     * @param allowableContourAreaRatio indicates the max allowable ratio between the area of the contours (0.0 or below means ignore this)
     * @return two largest contours, largest then second largest
     */
    public static IMatOfPoint[] findTwoLargestContours(IOpenCVProvider provider, IMat frame, double minContourArea, double desiredContourHxWRatio, double allowableContourHxWRatioRange, double allowableContourAreaRatio)
    {
        // find the contours using OpenCV API...
        IMat unused = provider.newMat();
        List<IMatOfPoint> contours = new ArrayList<IMatOfPoint>();
        provider.findContours(frame, contours, unused, ContourHelper.IMGPROC_RETR_EXTERNAL, ContourHelper.IMGPROC_CHAIN_APPROX_TC89_KCOS);
        unused.release();

        double largestContourArea = 0.0;
        IMatOfPoint largestContour = null;

        double secondLargestContourArea = 0.0;
        IMatOfPoint secondLargestContour = null;

        // find the two largest contours...
        for (IMatOfPoint contour : contours)
        {
            boolean release = true;
            double area = provider.contourArea(contour);
            if (area >= minContourArea)
            {
                IRect boundingRect = null;
                if (desiredContourHxWRatio >= 0.0)
                {
                    boundingRect = provider.boundingRect(contour);
                }

                if (boundingRect == null || Math.abs(((double)boundingRect.getHeight() / (double)boundingRect.getWidth()) - desiredContourHxWRatio) < allowableContourHxWRatioRange)
                {
                    if (area > largestContourArea)
                    {
                        if (largestContour != null)
                        {
                            if (secondLargestContour != null)
                            {
                                secondLargestContour.release();
                            }

                            secondLargestContour = largestContour;
                            secondLargestContourArea = largestContourArea;
                        }

                        largestContour = contour;
                        largestContourArea = area;
                        release = false;
                    }
                    else if (area >= minContourArea && area > secondLargestContourArea)
                    {
                        if (secondLargestContour != null)
                        {
                            secondLargestContour.release();
                        }

                        secondLargestContour = contour;
                        secondLargestContourArea = area;
                        release = false;
                    }
                }
            }

            if (release)
            {
                contour.release();
            }
        }

        if (allowableContourAreaRatio >= 0.0 &&
            largestContourArea > 0.0 &&
            secondLargestContourArea > 0.0 &&
            secondLargestContourArea / largestContourArea < allowableContourAreaRatio)
        {
            secondLargestContour = null;
            secondLargestContourArea = 0.0;
        }

        return new IMatOfPoint[] { largestContour, secondLargestContour };
    }

    /**
     * Find the contours in the frame, sorted from smallest to largest
     * @param frame in which to look for contours
     * @return sorted largest contour
     */
    public static IMatOfPoint[] findSortedLargestContours(IOpenCVProvider provider, IMat frame)
    {
        return ContourHelper.findSortedLargestContours(provider, frame, 0.0);
    }

    /**
     * Find the contours in the frame, sorted from smallest to largest
     * @param frame in which to look for contours
     * @param minContourArea is the minimum contour area allowable
     * @return sorted largest contour
     */
    public static IMatOfPoint[] findSortedLargestContours(IOpenCVProvider provider, IMat frame, double minContourArea)
    {
        // find the contours using OpenCV API...
        IMat unused = provider.newMat();
        List<IMatOfPoint> contours = new ArrayList<IMatOfPoint>();
        provider.findContours(frame, contours, unused, ContourHelper.IMGPROC_RETR_EXTERNAL, ContourHelper.IMGPROC_CHAIN_APPROX_TC89_KCOS);
        unused.release();

        contours.sort(
            new Comparator<IMatOfPoint>()
            {
                @Override
                public int compare(IMatOfPoint o1, IMatOfPoint o2)
                {
                    double area1 = provider.contourArea(o1);
                    double area2 = provider.contourArea(o2);
                    if (area1 > area2)
                    {
                        return 1;
                    }
                    else if (area1 < area2)
                    {
                        return -1;
                    }
                    else
                    {
                        return 0;
                    }
                }
            });

        return (IMatOfPoint[])contours.toArray();
    }

    public static List<IMatOfPoint> getAllContours(IOpenCVProvider provider, IMat frame, double minContourArea)
    {
        // find the contours using OpenCV API...
        IMat unused = provider.newMat();
        List<IMatOfPoint> contours = new ArrayList<IMatOfPoint>();
        provider.findContours(frame, contours, unused, ContourHelper.IMGPROC_RETR_EXTERNAL, ContourHelper.IMGPROC_CHAIN_APPROX_TC89_KCOS);
        unused.release();

        List<IMatOfPoint> largeContours = new ArrayList<IMatOfPoint>(contours.size());
        for (IMatOfPoint contour : contours)
        {
            double area = provider.contourArea(contour);
            if (area >= minContourArea)
            {
                largeContours.add(contour);
            }
            else
            {
                contour.release();
            }
        }

        return largeContours;
    }

    /**
     * Find the center of mass for a contour using Moments.
     * http://docs.opencv.org/3.1.0/d8/d23/classcv_1_1Moments.html
     * @param contour to use
     * @return point representing the center of the contour
     */
    public static IPoint findCenterOfMass(IOpenCVProvider provider, IMatOfPoint contour)
    {
        IMoments moments = provider.moments(contour);
        if (moments.get_m00() == 0.0)
        {
            return null;
        }

        return provider.newPoint(moments.get_m10() / moments.get_m00(), moments.get_m01() / moments.get_m00());
    }
}
