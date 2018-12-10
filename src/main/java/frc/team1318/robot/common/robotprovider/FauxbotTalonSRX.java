package frc.team1318.robot.common.robotprovider;

import frc.team1318.robot.common.PIDHandler;

public class FauxbotTalonSRX extends FauxbotAdvancedMotorBase implements ITalonSRX
{
    private FauxbotEncoder innerEncoder;
    private PIDHandler pidHandler;

    private TalonSRXControlMode currentMode;
    private double kp;
    private double ki;
    private double kd;
    private double kf;

    public FauxbotTalonSRX(int deviceNumber)
    {
        super(deviceNumber);

        this.currentMode = TalonSRXControlMode.PercentOutput;
    }

    public void setControlMode(TalonSRXControlMode mode)
    {
        this.currentMode = mode;
        this.resetPID();
    }

    public void setSensorType(TalonSRXFeedbackDevice feedbackDevice)
    {
        if (feedbackDevice == TalonSRXFeedbackDevice.QuadEncoder)
        {
            this.innerEncoder = new FauxbotEncoder(new FauxbotSensorConnection(FauxbotSensorConnection.SensorConnector.CAN, this.connection.getPort()));
        }
    }

    public void setFeedbackFramePeriod(int periodMS)
    {
    }

    public void setPIDFFramePeriod(int periodMS)
    {
    }

    public void configureVelocityMeasurements()
    {
    }

    public void setSelectedSlot(int slotId)
    {
    }

    public void setPIDF(double p, double i, double d, double f, int slotId)
    {
        this.kp = p;
        this.ki = i;
        this.kd = d;
        this.kf = f;
        this.resetPID();
    }

    public void setMotionMagicPIDF(double p, double i, double d, double f, int velocity, int acceleration, int slotId)
    {
    }

    public void setPIDF(double p, double i, double d, double f, int izone, double closeLoopRampRate, int slotId)
    {
        this.kp = p;
        this.ki = i;
        this.kd = d;
        this.kf = f;
        this.resetPID();
    }

    @Override
    public void set(double newValue)
    {
        if (this.currentMode == TalonSRXControlMode.Follower)
        {
            FauxbotActuatorBase actuator = FauxbotActuatorManager.get(new FauxbotActuatorConnection(FauxbotActuatorConnection.ActuatorConnector.CAN, (int)newValue));
            if (actuator != null && actuator instanceof FauxbotAdvancedMotorBase)
            {
                FauxbotAdvancedMotorBase advancedMotor = (FauxbotAdvancedMotorBase)actuator;
                advancedMotor.currentPowerProperty.addListener(
                    (observable, oldValue, value) -> { this.currentPowerProperty.set((Double)value); });
            }
            else
            {
                throw new RuntimeException("expected a different actuator type " + actuator == null ? "null" : actuator.toString());
            }
        }
        else if (this.currentMode == TalonSRXControlMode.Velocity)
        {
            super.set(this.pidHandler.calculateVelocity(newValue, innerEncoder.getRate()));
        }
        else if (this.currentMode == TalonSRXControlMode.Position)
        {
            super.set(this.pidHandler.calculatePosition(newValue, innerEncoder.get()));
        }
        else
        {
            super.set(newValue);
        }
    }

    public void setForwardLimitSwitch(boolean enabled, boolean normallyOpen)
    {
    }

    public void setReverseLimitSwitch(boolean enabled, boolean normallyOpen)
    {
    }

    public void setInvertOutput(boolean flip)
    {
    }

    public void setInvertSensor(boolean flip)
    {
    }

    public void setNeutralMode(TalonSRXNeutralMode neutralMode)
    {
    }

    public void setVoltageCompensation(boolean enabled, double maxVoltage)
    {
    }

    public void stop()
    {
    }

    public void setPosition(int position)
    {
    }

    public void reset()
    {
    }

    public int getPosition()
    {
        return (int)this.innerEncoder.getDistance();
    }

    public double getVelocity()
    {
        return 0.0;
    }

    public double getError()
    {
        return 0.0;
    }

    public TalonSRXLimitSwitchStatus getLimitSwitchStatus()
    {
        return null;
    }

    private void resetPID()
    {
        if (this.currentMode == TalonSRXControlMode.Position ||
            this.currentMode == TalonSRXControlMode.Velocity)
        {
            ITimer timer = new FauxbotTimer();
            timer.start();
            this.pidHandler = new PIDHandler(this.kp, this.ki, this.kd, this.kf, 1.0, -4096.0, 4096.0, timer);
        }
        else
        {
            this.pidHandler = null;
        }
    }
}
