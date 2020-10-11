package frc.robot.driver.common;

import java.util.ArrayList;
import java.util.List;

import java.io.File;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.kinematics.Kinematics;
import com.acmerobotics.roadrunner.kinematics.TankKinematics;
import com.acmerobotics.roadrunner.path.Path;
import com.acmerobotics.roadrunner.path.PathBuilder;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryGenerator;
import com.acmerobotics.roadrunner.trajectory.constraints.DriveConstraints;
import com.acmerobotics.roadrunner.trajectory.constraints.TankConstraints;

import frc.robot.HardwareConstants;
import frc.robot.TuningConstants;
import frc.robot.common.*;
import frc.robot.driver.PathManager;

public class RoadRunnerTankTranslator
{
    public static final String filePath = System.getProperty("user.home") + File.separator + "path.csv";

    public static void main(String[] args)
    {
        // Initialize path and interpolator (check to see if interpolator is needed)
        Path path =  new PathBuilder(new Pose2d(132, 0, 0)) 
            .splineTo(new Pose2d(0, 200, 90)) // tune y value and angle for shooting position (90 might need to be 270) 
            .build();

        boolean isBackwards = true;

        PathManager.writePathToFile(
            RoadRunnerTankTranslator.filePath,
            RoadRunnerTankTranslator.convert(path, isBackwards));
    }

    public static List<PathStep> convert(Path path, boolean isBackwards)
    {
        // initialize roadrunner constraints
        DriveConstraints constraints = new DriveConstraints(
            0.0, // TuningConstants.ROADRUNNER_MAX_VELOCITY,
            0.0, // TuningConstants.ROADRUNNER_MAX_ACCELERATION,
            0.0, // TuningConstants.ROADRUNNER_MAX_JERK,
            0.0, // TuningConstants.ROADRUNNER_MAX_ANGULAR_VELOCITY,
            0.0, // TuningConstants.ROADRUNNER_MAX_ANGULAR_ACCELERATION,
            0.0); // TuningConstants.ROADRUNNER_MAX_ANGULAR_JERK);

        TankConstraints constraintsTank = new TankConstraints(constraints, 18.0); // HardwareConstants.DRIVETRAIN_WHEEL_SEPARATION_DISTANCE);
        Trajectory traj = TrajectoryGenerator.INSTANCE.generateTrajectory(path, constraintsTank);

        double duration = traj.duration();
        List<PathStep> steps = new ArrayList<PathStep>((int)(duration / 0.1)); // TuningConstants.ROADRUNNER_TIME_STEP));

        double leftWheelPos = 0.0;
        double rightWheelPos = 0.0;

        for (double i = 0; i < duration; i += 0.1) //TuningConstants.ROADRUNNER_TIME_STEP)
        {
            PathStep step = RoadRunnerTankTranslator.buildPathStep(i, traj, leftWheelPos, rightWheelPos, isBackwards);
            leftWheelPos = step.getLeftPosition();
            rightWheelPos = step.getRightPosition();
            steps.add(step);
        }

        return steps;
    }

    public static PathStep buildPathStep(double time, Trajectory traj, double leftWheelPos, double rightWheelPos, boolean isBackwards)
    {
        Pose2d pose = traj.get(time);
        Pose2d poseVel = traj.velocity(time);
        Pose2d poseAcc = traj.acceleration(time);

        // converts the each pose from a field frame of reference to a robot frame of reference
        Pose2d robotVelocity = Kinematics.fieldToRobotVelocity(pose, poseVel);
        Pose2d robotAcceleration = Kinematics.fieldToRobotAcceleration(pose, poseVel, poseAcc);

        // converts unicycle model given by trajectory to left/right wheel velocities and accelerations
        List<Double> velocities = TankKinematics.robotToWheelVelocities(robotVelocity, 18.0);// HardwareConstants.DRIVETRAIN_WHEEL_SEPARATION_DISTANCE);
        double leftWheelVel = velocities.get(0);
        double rightWheelVel = velocities.get(1);

        List<Double> accelerations = TankKinematics.robotToWheelAccelerations(robotAcceleration, 18.0); // HardwareConstants.DRIVETRAIN_WHEEL_SEPARATION_DISTANCE);
        double leftWheelAcc = accelerations.get(0);
        double rightWheelAcc = accelerations.get(1);

        if (isBackwards)
        {
            leftWheelVel *= -1;
            rightWheelVel *= -1;
            leftWheelAcc *= -1;
            rightWheelAcc *= -1;
        }

        // calculates position of each wheel by multiplying the vel at a time step by the time step length
        leftWheelPos += leftWheelVel * 0.1; // TuningConstants.ROADRUNNER_TIME_STEP;
        rightWheelPos += rightWheelVel * 0.1; // TuningConstants.ROADRUNNER_TIME_STEP;
        double heading = pose.getHeading() * Helpers.RADIANS_TO_DEGREES;

        return new PathStep(
            leftWheelPos,
            rightWheelPos,
            leftWheelVel,
            rightWheelVel,
            leftWheelAcc,
            rightWheelAcc,
            heading);
    }
}