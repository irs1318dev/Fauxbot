package frc.lib.controllers;

import frc.lib.helpers.ExceptionHelpers;
import frc.lib.helpers.Helpers;

/**
 * TrapezoidProfile is a class that can be used to create a trapezoidal motion profile.
 * 
 * Pieces of the algorithm were inspired by WPILib's approach, but this is a complete rewrite that also supports
 * no-allocation updates and different acceleration/deceleration rates.
 */
public class TrapezoidProfile
{
    private final double maxVel;
    private final double maxAccel;
    private final double maxDecel;
    private final double accelToDecelRatio; // ratio of max accel to max decel
    private final double accelMaxDuration; // amount of time it takes to accelerate to max velocity
    private final double accelMaxDist; // distance traveled while accelerating to max velocity
    private final double decelMaxDuration; // amount of time it takes to decelerate from max velocity
    private final double decelMaxDist; // distance traveled while decelerating from max velocity

    /**
     * Initializes a new instance of the TrapezoidProfile class.
     * 
     * @param maxVel the maximum velocity that should be used, in units/second
     * @param maxAccel the maximum acceleration/deceleration that should be used, in units/second/second
     */
    public TrapezoidProfile(double maxVel, double maxAccel)
    {
        this(maxVel, maxAccel, maxAccel);
    }

    /**
     * Initializes a new instance of the TrapezoidProfile class.
     * 
     * @param maxVel the maximum velocity that should be used, in units/second
     * @param maxAccel the maximum acceleration that should be used, in units/second/second
     * @param maxDecel the maximum deceleration that should be used, in units/second/second
     */
    public TrapezoidProfile(double maxVel, double maxAccel, double maxDecel)
    {
        ExceptionHelpers.Assert(maxVel > 0.0, "Max velocity must be positive");
        ExceptionHelpers.Assert(maxAccel > 0.0, "Max acceleration must be positive");
        ExceptionHelpers.Assert(maxDecel > 0.0, "Max deceleration must be positive");

        this.maxVel = maxVel;
        this.maxAccel = maxAccel;
        this.maxDecel = maxDecel;

        this.accelToDecelRatio = this.maxAccel / this.maxDecel;
        this.accelMaxDuration = this.maxVel / this.maxAccel;
        this.accelMaxDist = this.accelMaxDuration * this.maxVel * 0.5;
        this.decelMaxDuration = this.maxVel / this.maxDecel;
        this.decelMaxDist = this.decelMaxDuration * this.maxVel * 0.5;
    }

    /**
     * Updates the curr position into the desired position after a given time delta since the last update.
     * It modifies and returns the curr (instead of allocating another one) for performance reasons.
     * @param timeDelta from the last update cycle, in seconds
     * @param curr current position, velocity
     * @param goal desired final/end position, velocity
     * @return true if we still have more progress to make, false if it was already at the goal
     */
    public boolean update(double timeDelta, State curr, State goal)
    {
        ExceptionHelpers.Assert(timeDelta >= 0.0, "timeDelta must be non-negative");

        // NOTE: similar to WPILib's appraoch, we will negate the position and velocity if the goal is behind the current position
        // and then reverse it back at the end
        boolean reverse;
        double currPosition;
        double currVelocity;
        double goalPosition;
        double goalVelocity;
        if (curr.position > goal.position)
        {
            reverse = true;
            currPosition = -curr.position;
            currVelocity = -curr.velocity;
            goalPosition = -goal.position;
            goalVelocity = -goal.velocity;
        }
        else
        {
            reverse = false;
            currPosition = curr.position;
            currVelocity = curr.velocity;
            goalPosition = goal.position;
            goalVelocity = goal.velocity;
        }

        double coastDuration;

        // NOTE: to make it simpler, we will use a similar approach to WPILib which makes a full trapezoid or triangle using pretend
        // pieces that happen before the current time, and after the end velocity time.
        double cutoffBeginDuration = currVelocity / this.maxAccel;
        double cutoffBeginDist = cutoffBeginDuration * currVelocity * 0.5;
        double cutoffEndDuration = goalVelocity / this.maxDecel;
        double cutoffEndDist = cutoffEndDuration * goalVelocity * 0.5;

        double distRemaining = goalPosition - currPosition;

        // NOTE: full* includes the extra triangles in the beginning/end
        double fullDist = cutoffBeginDist + distRemaining + cutoffEndDist;
        double coastDist = fullDist - this.accelMaxDist - this.decelMaxDist;

        double fullAccelDuration;
        double fullDecelDuration;

        // triangle profile:
        if (coastDist <= 0.0)
        {
            // Notes:
            // peak velocity is the same for both accel and decel half ==> maxAccel * fullAccelDuration = maxDecel * fullDecelDuration
            // thus, fullDecelDuration = fullAccelDuration * maxAccel / maxDecel
            // we want dist to equal half the peak velocity time the sum of fullAccelDuration and fullDecelDuration ==> dist = 0.5 * peakVelocity * (fullAccelDuration + fullDecelDuration)
            // ==> dist = 0.5 * maxAccel * fullAccelDuration * (fullAccelDuration + fullDecelDuration)
            // ==> dist = 0.5 * maxAccel * fullAccelDuration * (fullAccelDuration + fullAccelDuration * maxAccel / maxDecel)
            // ==> dist = 0.5 * maxAccel * fullAccelDuration * fullAccelDuration * (1 + maxAccel / maxDecel)
            // ==> fullAccelDuration * fullAccelDuration = 2 * dist / (maxAccel * (1 + maxAccel / maxDecel))
            // ==> fullAccelDuration = sqrt(2 * dist / (maxAccel * (1 + maxAccel / maxDecel)))
            coastDuration = 0.0;
            fullAccelDuration = Math.sqrt(2.0 * fullDist / (this.maxAccel * (1.0 + this.accelToDecelRatio)));
            fullDecelDuration = fullAccelDuration * this.accelToDecelRatio;
        }
        else
        {
            fullAccelDuration = this.accelMaxDuration;
            fullDecelDuration = this.decelMaxDuration;
            coastDuration = coastDist / this.maxVel;
        }

        double accelDuration = fullAccelDuration - cutoffBeginDuration;
        double decelDuration = fullDecelDuration - cutoffEndDuration;

        // accel end time is accelDuration
        // coast end time is accelDuration + coastDuration;
        // decel end time is accelDuration + coastDuration + decelDuration;
        boolean inProgress = true;
        if (timeDelta < accelDuration)
        {
            // accelerating
            currPosition += (0.5 * this.maxAccel * timeDelta + currVelocity) * timeDelta;
            currVelocity += this.maxAccel * timeDelta;
        }
        else if (timeDelta < accelDuration + coastDuration)
        {
            // coasting
            currPosition += (0.5 * this.maxAccel * accelDuration + currVelocity) * accelDuration + this.maxVel * (timeDelta - accelDuration);
            currVelocity = this.maxVel;
        }
        else if (timeDelta < accelDuration + coastDuration + decelDuration)
        {
            // decelerating
            double decelTimeToGoal = decelDuration - (timeDelta - accelDuration - coastDuration);
            currPosition = goalPosition - (0.5 * this.maxDecel * decelTimeToGoal + goalVelocity) * decelTimeToGoal;
            currVelocity = goalVelocity + this.maxDecel * decelTimeToGoal;
        }
        else
        {
            // done
            currPosition = goalPosition;
            currVelocity = goalVelocity;
            inProgress = false;
        }

        // now, (potentially swap the direction back and) put them back into curr (to avoid new allocations)
        if (reverse)
        {
            curr.position = -currPosition;
            curr.velocity = -currVelocity;
        }
        else
        {
            curr.position = currPosition;
            curr.velocity = currVelocity;
        }

        return inProgress;
    }

    /**
     * Object containing the position and velocity at a given moment of time
     */
    public static class State
    {
        private double position;
        private double velocity;

        /**
         * Initializes a new instance of the State class.
         * @param position at the current moment of time
         * @param velocity at the current moment of time
         */
        public State(double position, double velocity)
        {
            this.position = position;
            this.velocity = velocity;
        }

        /**
         * Retrieve the position
         * @return the position
         */
        public double getPosition()
        {
            return this.position;
        }

        /**
         * Retrieve the velocity
         * @return the velocity
         */
        public double getVelocity()
        {
            return this.velocity;
        }

        /**
         * Updates the position for this state
         * @param position to apply, in units
         * @return true if the position has meaningfully changed
         */
        public boolean updatePosition(double position)
        {
            if (Helpers.roughEquals(this.position, position, 1e-5))
            {
                return false;
            }

            this.position = position;
            return true;
        }

        /**
         * Updates the velocity for this state
         * @param velocity to apply, in units/sec
         */
        public void setVelocity(double velocity)
        {
            this.velocity = velocity;
        }

        @Override
        public String toString()
        {
            return "(p:" + this.position + " v:" + this.velocity + ")";
        }
    }
}
