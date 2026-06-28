package frc.lib.controllers;

import java.util.OptionalDouble;

import frc.lib.robotprovider.ITimer;

/**
 * This class is an Active Disturbance Rejection Control (ADRC) handler.
 * It utilizes a Linear Extended State Observer (LESO) to estimate state variables 
 * and cancel system disturbances dynamically.
 */
public class ADRCHandler
{
    private final OptionalDouble minOutput;
    private final OptionalDouble maxOutput;

    // ADRC Configuration Parameters
    private final double b0;
    private final double omegaC;
    private final double omegaO;

    // Precalculated Position Gains (2nd-Order Plant -> 3rd-Order LESO)
    private final double kp_p;
    private final double kd_p;
    private final double beta1_p;
    private final double beta2_p;
    private final double beta3_p;

    // Position Observer States
    private double z1 = 0.0; // Estimated position
    private double z2 = 0.0; // Estimated velocity
    private double z3 = 0.0; // Estimated total disturbance (f)

    // Instance variables
    private double prevTime = 0.0;
    private double output = 0.0;
    private boolean firstRun = true;
    private final ITimer timer;

    // Stall Protection State Variables
    private double stallStartTime = 0.0;
    private boolean isStalled = false;
    private static final double STALL_OUTPUT_THRESHOLD = 250.0;
    private static final double STALL_VELOCITY_THRESHOLD = 2.0;
    private static final double STALL_DURATION_SECONDS = 1.5;

    public ADRCHandler(
        double omegaC,
        double omegaO,
        double b0,
        double minOutput,
        double maxOutput,
        ITimer timer)
    {
        this.omegaC = omegaC;
        this.omegaO = omegaO;
        this.b0 = b0;
        this.minOutput = OptionalDouble.of(minOutput);
        this.maxOutput = OptionalDouble.of(maxOutput);
        this.timer = timer;
        this.prevTime = this.timer.get();

        // Parameterize Position ADRC Gains (Second-order system)
        this.kp_p = omegaC * omegaC;
        this.kd_p = 2.0 * omegaC;
        this.beta1_p = 3.0 * omegaO;
        this.beta2_p = 3.0 * omegaO * omegaO;
        this.beta3_p = omegaO * omegaO * omegaO;
    }

    /**
     * Calculates control output for a Position loop with integrated stall protection.
     * * @param profiledSetpoint Current profiled target position (e.g., from a trapezoidal profile)
     * @param measuredValue    Current raw sensor position (degrees)
     * @return Output command restricted to min/max bounds, or 0.0 if stalled.
     */
    public double calculatePosition(double profiledSetpoint, double measuredValue)
    {
        // If the motor is already flagged as stalled, safety cut-off
        if (this.isStalled)
        {
            return 0.0;
        }

        double curTime = this.timer.get();

        // Seed the observer on the very first frame to avoid a violent 0.0 starting jump
        if (this.firstRun)
        {
            this.z1 = measuredValue;
            this.z2 = 0.0;
            this.z3 = 0.0;
            this.prevTime = curTime;
            this.firstRun = false;
            return 0.0;
        }

        double dt = curTime - this.prevTime;

        // Prevent dt explosions (e.g., matching your dt > 0.05f filter)
        if (dt <= 0.0 || dt > 0.05)
        {
            return this.output;
        }

        this.prevTime = curTime;

        // 1. Compute observer tracking error
        double errorEso = this.z1 - measuredValue;

        // 2. Update 3rd-Order LESO States (Euler integration)
        this.z1 += dt * (this.z2 - this.beta1_p * errorEso);
        this.z2 += dt * (this.z3 + (this.b0 * this.output) - this.beta2_p * errorEso);
        this.z3 += dt * (-this.beta3_p * errorEso);

        // 3. Structural Control Law (Virtual PD following the profiled target)
        double u0 = this.kp_p * (profiledSetpoint - this.z1) - this.kd_p * this.z2;

        // 4. Disturbance Rejection
        double result = (u0 - this.z3) / this.b0;

        // 5. Output Saturation
        this.output = clamp(result);

        // 6. Stall Protection Logic
        // Checks if output is maxed out while the estimated velocity (z2) remains stagnant
        if (Math.abs(this.output) >= STALL_OUTPUT_THRESHOLD && Math.abs(this.z2) < STALL_VELOCITY_THRESHOLD)
        {
            if (this.stallStartTime == 0.0)
            {
                this.stallStartTime = curTime;
            }
            else if (curTime - this.stallStartTime > STALL_DURATION_SECONDS)
            {
                this.isStalled = true;
                System.out.println("STALL DETECTED! Motor Disabled via ADRC Protection.");
                this.output = 0.0;
            }
        }
        else
        {
            this.stallStartTime = 0.0;
        }

        return this.output;
    }

    private double clamp(double value) 
    {
        if (this.maxOutput.isPresent() && value > this.maxOutput.getAsDouble()) 
        {
            return this.maxOutput.getAsDouble();
        }
        if (this.minOutput.isPresent() && value < this.minOutput.getAsDouble()) 
        {
            return this.minOutput.getAsDouble();
        }
        return value;
    }

    public boolean isStalled()
    {
        return this.isStalled;
    }

    public double getEstimatedVelocity()
    {
        return this.z2;
    }

    public double getEstimatedDisturbance()
    {
        return this.z3;
    }

    public void reset()
    {
        this.firstRun = true;
        this.isStalled = false;
        this.stallStartTime = 0.0;
        this.output = 0.0;
        this.z1 = 0.0;
        this.z2 = 0.0;
        this.z3 = 0.0;
    }
}