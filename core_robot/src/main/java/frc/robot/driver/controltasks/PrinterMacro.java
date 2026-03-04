package frc.robot.driver.controltasks;

import java.util.EnumSet;

import frc.lib.driver.IControlTask;
import frc.lib.driver.IDriver;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;
import frc.robot.mechanisms.PrinterMechanism;


public class PrinterMacro extends ControlTaskBase implements IControlTask {
    private boolean isFinished = false;
    private boolean isAtTarget = false;
    private PrinterMechanism printer;
    double targetX;
    double targetY;
    boolean penDown;
    int pointIndex = 0;

    private class Point {
        public double x;
        public double y;
        public boolean penDown;

        public Point(double x, double y, boolean penDown) {
            this.x = x;
            this.y = y;
            this.penDown = penDown;
        }
    }

    // Define list of points
    private Point[] points = new Point[] {
        new Point(100, 100, false),
        new Point(200, 100, true),
        new Point(200, 200, true),
        new Point(100, 200, true),
        new Point(100, 100, true),
        new Point(150, 150, false)
    };


    public PrinterMacro(IDriver driver) {
    }

    @Override
    public EnumSet<AnalogOperation> getAffectedAnalogOperations() {
        return EnumSet.of(AnalogOperation.XPosition, AnalogOperation.YPosition);
    }

    @Override
    public EnumSet<DigitalOperation> getAffectedDigitalOperations() {
        return EnumSet.of(DigitalOperation.PenUp, DigitalOperation.PenDown);
    }

    private boolean withinTolerance(double a, double b, double tolerance) {
        return Math.abs(a - b) <= tolerance;
    }
    
    private void updateTargets() {
        if (pointIndex < points.length) {
            Point p = points[pointIndex];
            this.targetX = p.x;
            this.targetY = p.y;
            this.penDown = p.penDown;
            pointIndex++;
            this.isAtTarget = false;
        }
        // Set analogOperation inputs for printer targets
        setAnalogOperationState(AnalogOperation.XPosition, targetX);
        setAnalogOperationState(AnalogOperation.YPosition, targetY);
        setDigitalOperationState(DigitalOperation.PenDown, penDown);

    }

    @Override
    public void begin() {
        this.printer = this.getInjector().getInstance(PrinterMechanism.class);
        this.isFinished = false;
    }

    @Override
    public void update() {
        // Check if we are at the target position
        if (withinTolerance(this.printer.getXPosition(), this.targetX, 10) &&
            withinTolerance(this.printer.getYPosition(), this.targetY, 10)) {
            this.isAtTarget = true;
        }

        if (this.isAtTarget && this.penDown) {
            this.isFinished = true;
        }
        updateTargets();
    }

    @Override
    public void end() {
    }

    @Override
    public boolean hasCompleted() {
        return this.isFinished;
    }

}