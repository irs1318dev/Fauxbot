package frc.robot.vision;

import frc.robot.common.robotprovider.*;
import frc.robot.vision.common.VisionResult;

import java.util.*;

public class VisionCalculations
{
    public VisionCalculations()
    {
    }

    public List<Set<IRotatedRect>> groupRotatedRect(List<IRotatedRect> rotatedRect)
    {
        List<Set<IRotatedRect>> rows = new ArrayList<Set<IRotatedRect>>();
        for (IRotatedRect rect : rotatedRect)
        {
            boolean added = false;

            // skip malformed rectangles
            // reject rectangles that are malformed compared to the expected aspect ration of 5.5/2.0
            if (rect.getAspectRatio() > 5.0 || rect.getAspectRatio() < 1.0)
            {
                continue;
            }

            // put the rectangle in a row
            for (Set<IRotatedRect> row : rows)
            {
                IRotatedRect compare = row.stream().findFirst().get();
                if (Math.abs(rect.getCenter().getY() - compare.getCenter().getY()) < 30)
                {
                    row.add(rect);
                    added = true;
                    break;
                }
            }

            if (added)
            {
                continue;
            }

            // no row exists, create a new row
            Set<IRotatedRect> row = new HashSet<IRotatedRect>();
            row.add(rect);
            rows.add(row);
        }

        return rows;
    }

    public Set<IRotatedRect> pickRow(List<Set<IRotatedRect>> rows, VisionResult visionResult)
    {
        if (rows == null || rows.size() == 0)
        {
            return null;
        }

        if (rows.size() == 1)
        {
            return rows.get(0);
        }

        if (VisionResult.HIGH_TARGET.equals(visionResult))
        {
            double lowestY = Double.MAX_VALUE;
            Set<IRotatedRect> lowestRow = null;
            for (Set<IRotatedRect> row : rows)
            {
                IRotatedRect rect = row.stream().findFirst().get();
                if (rect.getCenter().getY() < lowestY)
                {
                    lowestY = rect.getCenter().getY();
                    lowestRow = row;
                }
            }

            return lowestRow;
        }
        else
        {
            double highestY = Double.MIN_VALUE;
            Set<IRotatedRect> highestRow = null;
            for (Set<IRotatedRect> row : rows)
            {
                IRotatedRect rect = row.stream().findFirst().get();
                if (rect.getCenter().getY() > highestY)
                {
                    highestY = rect.getCenter().getY();
                    highestRow = row;
                }
            }

            return highestRow;
        }
    }

    boolean isLeft(IRotatedRect rect)
    {
        double rectAngle = rect.getAngle();
        if (rectAngle < -45.0 && rectAngle > -90.0)
        {
            return true;
        }

        return false;
    }

    boolean isRight(IRotatedRect rect)
    {
        double rectAngle = rect.getAngle();
        if (rectAngle < 0.0 && rectAngle > -44.0)
        {
            return true;
        }

        return false;
    }

    List<IRotatedRect> largestRect(List<IRotatedRect> rect)
    {
        double area = 0;
        List<IRotatedRect> pair = new ArrayList<IRotatedRect>();
        IRotatedRect left = null;
        IRotatedRect right = null;
        int count = rect.size();
        for (int i = 0; i < count; i++)
        {
            if (isLeft(rect.get(i)))
            {
                if (area < (rect.get(i).getSize().area()))
                {
                    area = (rect.get(i).getSize().area());
                    left = rect.get(i);
                    if (i < count - 1)
                    {
                        right = rect.get(i + 1);
                    }
                    else
                    {
                        right = null;
                    }

                    if (pair.isEmpty())
                    {
                        pair.add(left);
                        pair.add(right);
                    }
                    else
                    {
                        pair.set(0, left);
                        pair.set(1, right);
                    }
                }
            }
        }

        return pair;
    }

    public List<RectanglePair> pairRectangles(Set<IRotatedRect> rects)
    {
        List<RectanglePair> rectanglePairs = new ArrayList<RectanglePair>();
        List<IRotatedRect> sortedRectangles = this.sortByCenterX(rects);

        int i = 0;
        int rectCount = sortedRectangles.size();
        IRotatedRect left = null;
        while (i < rectCount)
        {
            IRotatedRect current = sortedRectangles.get(i);

            if (this.isLeft(current))
            {
                if (left != null)
                {
                    // multiple lefts in a row?  right may be obscured...
                    rectanglePairs.add(new RectanglePair(left, null));
                }

                left = current;
            }
            else //if (this.isRight(current))
            {
                rectanglePairs.add(new RectanglePair(left, current));
                left = null;
            }

            i++;
        }

        if (left != null)
        {
            // unpaired rectangle?  may be near the edge of the viewport...
            rectanglePairs.add(new RectanglePair(left, null));
        }

        return rectanglePairs;
    }

    public RectanglePair pickPreferredPair(List<RectanglePair> rectanglePairs, double centerWidth, double maxDistance)
    {
        if (rectanglePairs == null)
        {
            return null;
        }

        RectanglePair bestPair = null;
        double minAngleFromCenter = Double.MAX_VALUE;
        for (int i = 0; i < rectanglePairs.size(); i++)
        {
            RectanglePair current = rectanglePairs.get(i);
            IPoint center = current.getCenter();
            double angleFromCenter = Math.abs(centerWidth - center.getX());

            if (this.getDistanceFromRobot(current) <= maxDistance &&
                angleFromCenter < minAngleFromCenter)
            {
                minAngleFromCenter = angleFromCenter;
                bestPair = current;
            }
        }

        return bestPair;
    }

    public double getDistanceFromCamera(RectanglePair pair)
    {
        return 0.0; // (VisionConstants.DOCKING_CAMERA_MOUNTING_HEIGHT - VisionConstants.ROCKET_TO_GROUND_TAPE_HEIGHT)/(Math.tan((VisionConstants.DOCKING_CAMERA_VERTICAL_MOUNTING_ANGLE - pair.getMeasuredAngleY()) * VisionConstants.ANGLE_TO_RADIANS));
    }

    public double getDistanceFromRobot(RectanglePair pair)
    {
        return 0.0; // this.getDistanceFromCamera(pair) * Math.cos(pair.getMeasuredAngleX() * VisionConstants.ANGLE_TO_RADIANS) - VisionConstants.DOCKING_CAMERA_MOUNTING_DISTANCE;    
    }

    public double getDesiredAngleX(RectanglePair pair)
    {
        return 0.0; // Math.asin((VisionConstants.DOCKING_CAMERA_HORIZONTAL_MOUNTING_OFFSET - VisionConstants.DOCKING_TAPE_OFFSET) / this.getDistanceFromCamera(pair)) * VisionConstants.RADIANS_TO_ANGLE;
    }

    public List<IRotatedRect> sortByCenterX(Collection<IRotatedRect> rects)
    {
        List<IRotatedRect> rectList = new ArrayList<>();
        if (rects != null)
        {
            rectList.addAll(rects);
        }

        Collections.sort(rectList, Comparator.comparingDouble(arg0 -> arg0.getCenter().getX()));
        return rectList;
    }
}