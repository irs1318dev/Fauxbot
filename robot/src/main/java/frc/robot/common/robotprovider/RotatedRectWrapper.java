package frc.robot.common.robotprovider;

import org.opencv.core.RotatedRect;

public class RotatedRectWrapper implements IRotatedRect {
    private final RotatedRect wrappedObject;
    private final double[] vals;

    public RotatedRectWrapper(RotatedRect wrappedObject) {
        this.wrappedObject = wrappedObject;
        vals = new double[5];
        exportVals();
    }

    private void exportVals() {
        vals[0] = wrappedObject.center.x;
        vals[1] = wrappedObject.center.y;
        vals[2] = wrappedObject.size.width;
        vals[3] = wrappedObject.size.height;
        vals[4] = wrappedObject.angle;

    }

    @Override
    public IPoint getCenter() {
        return new PointWrapper(this.wrappedObject.center);
    }

    @Override
    public ISize getSize() {
        return new SizeWrapper(this.wrappedObject.size);
    }

    @Override
    public double getAngle() {
        return this.wrappedObject.angle;
    }

    @Override
    public double getAspectRatio() {
        // return the aspect ratio that is always greater than 1.0
        double height = wrappedObject.size.height;
        double width = wrappedObject.size.width;

        // return a large number if either width or height is zero
        if (Math.abs(height) < 0.1 || Math.abs(width) < 0.1) {
            return 10000;
        }
        if (height > width) {
            return height / width;
        } else {
            return width / height;
        }
    }

    @Override
    public void set(double[] vals) {
        this.wrappedObject.set(vals);
        exportVals();
    }

    @Override
    public IRect boundingRect() {
        return new RectWrapper(this.wrappedObject.boundingRect());
    }

    @Override
    public IRotatedRect clone() {
        return new RotatedRectWrapper(this.wrappedObject.clone());
    }

    @Override
    public double[] getRawValues() {
        return this.vals;
    }

}