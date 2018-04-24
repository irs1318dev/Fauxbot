package org.usfirst.frc.team1318.robot.drivetrain;

import javax.inject.Singleton;

import org.usfirst.frc.team1318.robot.ElectronicsConstants;
import org.usfirst.frc.team1318.robot.TuningConstants;
import org.usfirst.frc.team1318.robot.common.IMechanism;
import org.usfirst.frc.team1318.robot.common.wpilib.IDigitalInput;
import org.usfirst.frc.team1318.robot.common.wpilib.IMotor;
import org.usfirst.frc.team1318.robot.common.wpilib.IWpilibProvider;
import org.usfirst.frc.team1318.robot.driver.Operation;
import org.usfirst.frc.team1318.robot.driver.common.Driver;

import com.google.inject.Inject;

@Singleton
public class DriveTrainMechanism implements IMechanism
{
    private final IMotor leftMotor;
    private final IMotor rightMotor;
    private Driver driver;

    private double leftPower;
    private double rightPower;

    private double turnAmount;
    private double forwardVelocity;

    // Component Code
    @Inject
    public DriveTrainMechanism(IWpilibProvider provider)
    {
        this.leftMotor = provider.getTalon(ElectronicsConstants.DRIVETRAIN_LEFT_MOTOR_CAN_ID);
        this.rightMotor = provider.getTalon(ElectronicsConstants.DRIVETRAIN_RIGHT_MOTOR_PWM_CHANNEL);
        this.driver = null;

        this.leftPower = 0.0;
        this.rightPower = 0.0;

        this.turnAmount = 0.0;
        this.forwardVelocity = 0.0;
    }

    public void setDriveTrainPower(double leftPower, double rightPower)
    {
        this.leftMotor.set(leftPower);
        this.rightMotor.set(rightPower);
    }

    //Control Code
    @Override
    public void readSensors()
    {
        //read sensors
    }

    @Override
    public void update()
    {
        // get the X and Y values from the operator.  We expect these to be between -1.0 and 1.0,
        // with this value representing the forward velocity percentage and right turn percentage (of max speed)
        this.turnAmount = this.driver.getAnalog(Operation.DriveTrainTurn);
        this.forwardVelocity = this.driver.getAnalog(Operation.DriveTrainMoveForward);

        PowerSetting powerSetting = this.calculateVelocityModePowerSetting();

        this.leftPower = powerSetting.getLeftPower();
        this.rightPower = powerSetting.getRightPower();
        
        // apply the power settings to the drivetrain component
        this.setDriveTrainPower(this.leftPower, this.rightPower);

        boolean resetPressed = this.driver.getDigital(Operation.ResetPower);
        if (resetPressed)
        {
            this.stop();
        }
    }

    // Create a switch with states to make the robot turn
    @Override
    public void stop()
    {
        this.leftMotor.set(0.0);
        this.rightMotor.set(0.0);
    }

    @Override
    public void setDriver(Driver driver)
    {
        this.driver = driver;
    }

    /**
     * Calculate the power setting to use based on the inputs when in velocity mode
     * @return power settings for left and right motor
     */
    private PowerSetting calculateVelocityModePowerSetting()
    {
        // velocity goals represent the desired percentage of the max velocity
        double leftVelocityGoal = 0.0;
        double rightVelocityGoal = 0.0;

        // adjust the intensity of the input
        if (Math.abs(this.forwardVelocity) < Math.abs(this.turnAmount))
        {
            // in-place turn
            leftVelocityGoal = this.turnAmount;
            rightVelocityGoal = -this.turnAmount;
        }
        else
        {
            // forward/backward
            leftVelocityGoal = this.forwardVelocity;
            rightVelocityGoal = this.forwardVelocity;
        }

        // decrease the desired velocity based on the configured max power level
        leftVelocityGoal = leftVelocityGoal * TuningConstants.DRIVETRAIN_MAX_POWER_LEVEL;
        rightVelocityGoal = rightVelocityGoal * TuningConstants.DRIVETRAIN_MAX_POWER_LEVEL;

        // convert velocity goal to power level...

        // Implement PID here
        this.leftPower = leftVelocityGoal;
        this.rightPower = rightVelocityGoal;
        return new PowerSetting(this.leftPower, this.rightPower);
    }

    /**
     * Simple holder of power setting information for the left and right motor
     * (This exists only to allow splitting out common code and have only one return value, because Java doesn't support multi-return)
     */
    private class PowerSetting
    {
        private double leftPower;
        private double rightPower;

        /**
         * Initializes a new PowerSetting
         * @param leftPower to apply
         * @param rightPower to apply
         */
        public PowerSetting(double leftPower, double rightPower)
        {
            this.leftPower = leftPower;
            this.rightPower = rightPower;
        }

        /**
         * gets the left power setting 
         * @return value between -1.0 and 1.0
         */
        public double getLeftPower()
        {
            return this.leftPower;
        }

        /**
         * gets the right power setting 
         * @return value between -1.0 and 1.0
         */
        public double getRightPower()
        {
            return this.rightPower;
        }
    }
}
