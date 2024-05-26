package frc.lib.helpers;

public class PoseHelpers
{
    /**
     * Calculate the pose exponential according to the algorithm described in
     * https://file.tavsys.net/control/controls-engineering-in-frc.pdf theorem 10.2.1
     * solving a differential equation moving the pose forward in time.
     * @param v the x velocity, y velocity, and angular velocity (vx, vy, omega) - in inches, degrees --> updated to the result of the operation in-place
     */
    public static void poseExponential(Triple<Double, Double, Double> v)
    {
        double vx = v.getFirst();
        double vy = v.getSecond();
        double omega = v.getThird();
        double omegaRad = omega * Helpers.DEGREES_TO_RADIANS;

        double sinOmega = Helpers.sind(omega);
        double cosOmega = Helpers.cosd(omega);

        double sinOmegaOverOmega;
        double oneMinusCosOmegaOverOmega;
        if (Math.abs(omega) < 1E-8)
        {
            // replacement for values when omega is close to 0
            sinOmegaOverOmega = 1.0 - omegaRad * omegaRad / 6.0;
            oneMinusCosOmegaOverOmega = omegaRad / 2.0;
        }
        else
        {
            sinOmegaOverOmega = sinOmega / omegaRad;
            oneMinusCosOmegaOverOmega = (1.0 - cosOmega) / omegaRad;
        }

        v.setFirst(sinOmegaOverOmega * vx - oneMinusCosOmegaOverOmega * vy);
        v.setSecond(oneMinusCosOmegaOverOmega * vx + sinOmegaOverOmega * vy);
        // v.setThird(omega);
    }

    /**
     * Calculate the pose exponential according to the algorithm described in
     * https://file.tavsys.net/control/controls-engineering-in-frc.pdf theorem 10.2.1
     * solving a differential equation moving the pose forward in time.
     * @param v the x velocity, y velocity, and angular velocity (vx, vy, omega) - in inches, degrees
     * @return the updated x velocity, y velocity, and angular velocity (vx, vy, omega) - in inches, degrees
     */
    public static ImmutableTriple<Double, Double, Double> poseExponential(ImmutableTriple<Double, Double, Double> v)
    {
        double vx = v.first;
        double vy = v.second;
        double omega = v.third;
        double omegaRad = omega * Helpers.DEGREES_TO_RADIANS;

        double sinOmega = Helpers.sind(omega);
        double cosOmega = Helpers.cosd(omega);

        double sinOmegaOverOmega;
        double oneMinusCosOmegaOverOmega;
        if (Math.abs(omega) < 1E-8)
        {
            // replacement for values when omega is close to 0
            sinOmegaOverOmega = 1.0 - omegaRad * omegaRad / 6.0;
            oneMinusCosOmegaOverOmega = omegaRad / 2.0;
        }
        else
        {
            sinOmegaOverOmega = sinOmega / omegaRad;
            oneMinusCosOmegaOverOmega = (1.0 - cosOmega) / omegaRad;
        }

        return new ImmutableTriple<Double, Double, Double>(
            sinOmegaOverOmega * vx - oneMinusCosOmegaOverOmega * vy,
            oneMinusCosOmegaOverOmega * vx + sinOmegaOverOmega * vy,
            omega);
    }


    /**
     * Calculate the inverse of the pose exponential according to the algorithm used
     * by WPILib's Pose2d.log() function.
     * @param v the x velocity, y velocity, and angular velocity (vx, vy, omega) - in inches, degrees --> updated to the result of the operation in-place
     */
    public static void inversePoseExponential(Triple<Double, Double, Double> v)
    {
        double vx = v.getFirst();
        double vy = v.getSecond();
        double omega = v.getThird();
        double omegaRad = omega * Helpers.DEGREES_TO_RADIANS;
        double halfOmegaRad = omegaRad / 2.0;

        double cosMinusOne = Helpers.cosd(omega) - 1.0;

        double halfOmegaByTanOfHalfOmega;
        if (Math.abs(cosMinusOne) < 1E-8)
        {
            // replacement for values when cosMinusOne is close to 0
            halfOmegaByTanOfHalfOmega = 1.0 - omegaRad * omegaRad / 12.0;
        }
        else
        {
            halfOmegaByTanOfHalfOmega = -(halfOmegaRad * Helpers.sind(omega)) / cosMinusOne;
        }

        double magnitude = Math.sqrt(halfOmegaByTanOfHalfOmega * halfOmegaByTanOfHalfOmega + halfOmegaRad * halfOmegaRad);
        double angle;
        if (magnitude < 1E-8)
        {
            // when magnitude is basically 0, this is negligable
            angle = 0.0;
        }
        else
        {
            angle = Helpers.atan2d(-halfOmegaRad / magnitude, halfOmegaByTanOfHalfOmega / magnitude);
        }

        v.setFirst(magnitude * (vx * Helpers.cosd(angle) - vy * Helpers.sind(angle)));
        v.setSecond(magnitude * (vx * Helpers.sind(angle) + vy * Helpers.cosd(angle)));
        // v.setThird(omega);
    }

    /**
     * Calculate the inverse of the pose exponential according to the algorithm used
     * by WPILib's Pose2d.log() function.
     * @param pose the x velocity, y velocity, and angular velocity (vx, vy, omega) - in inches, degrees
     * @return the updated x velocity, y velocity, and angular velocity (vx, vy, omega) - in inches, degrees
     */
    public static ImmutableTriple<Double, Double, Double> inversePoseExponential(ImmutableTriple<Double, Double, Double> v)
    {
        double vx = v.first;
        double vy = v.second;
        double omega = v.third;
        double omegaRad = omega * Helpers.DEGREES_TO_RADIANS;
        double halfOmegaRad = omegaRad / 2.0;

        double cosMinusOne = Helpers.cosd(omega) - 1.0;

        double halfOmegaByTanOfHalfOmega;
        if (Math.abs(cosMinusOne) < 1E-8)
        {
            // replacement for values when cosMinusOne is close to 0
            halfOmegaByTanOfHalfOmega = 1.0 - omegaRad * omegaRad / 12.0;
        }
        else
        {
            halfOmegaByTanOfHalfOmega = -(halfOmegaRad * Helpers.sind(omega)) / cosMinusOne;
        }

        double magnitude = Math.sqrt(halfOmegaByTanOfHalfOmega * halfOmegaByTanOfHalfOmega + halfOmegaRad * halfOmegaRad);
        double angle;
        if (magnitude < 1E-8)
        {
            // when magnitude is basically 0, this is negligable
            angle = 0.0;
        }
        else
        {
            angle = Helpers.atan2d(-halfOmegaRad / magnitude, halfOmegaByTanOfHalfOmega / magnitude);
        }

        return new ImmutableTriple<Double, Double, Double>(
            magnitude * (vx * Helpers.cosd(angle) - vy * Helpers.sind(angle)),
            magnitude * (vx * Helpers.sind(angle) + vy * Helpers.cosd(angle)),
            omega);
    }
}
