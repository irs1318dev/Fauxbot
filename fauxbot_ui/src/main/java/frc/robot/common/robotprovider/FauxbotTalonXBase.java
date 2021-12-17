package frc.robot.common.robotprovider;

import frc.robot.IRealWorldSimulator;
import frc.robot.common.PIDHandler;

public abstract class FauxbotTalonXBase extends FauxbotAdvancedMotorBase implements ITalonSRX
{
    private final IRealWorldSimulator simulator;

    private FauxbotEncoder innerEncoder;
    private PIDHandler pidHandler;

    private TalonXControlMode currentMode;
    private double kp;
    private double ki;
    private double kd;
    private double kf;

    FauxbotTalonXBase(int deviceNumber, IRealWorldSimulator simulator)
    {
        super(deviceNumber);

        this.simulator = simulator;
        this.currentMode = TalonXControlMode.PercentOutput;
    }

    public void follow(ITalonSRX talonSRX)
    {
    }

    public void follow(ITalonFX talonFX)
    {
    }

    public void follow(IVictorSPX victorSPX)
    {
    }

    public void setControlMode(TalonXControlMode mode)
    {
        this.currentMode = mode;
        this.resetPID();
    }

    public void setSensorType(TalonXFeedbackDevice feedbackDevice)
    {
        if (feedbackDevice == TalonXFeedbackDevice.QuadEncoder ||
            feedbackDevice == TalonXFeedbackDevice.IntegratedSensor)
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

    public void configureVelocityMeasurements(int periodMS, int windowSize)
    {
    }

    public void configureAllowableClosedloopError(int slotId, int error)
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
        if (this.currentMode == TalonXControlMode.Follower)
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
        else if (this.currentMode == TalonXControlMode.Velocity && this.pidHandler != null)
        {
            super.set(this.pidHandler.calculateVelocity(newValue, innerEncoder.getRate()));
        }
        else if (this.currentMode == TalonXControlMode.Position && this.pidHandler != null)
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

    public void setNeutralMode(MotorNeutralMode neutralMode)
    {
    }

    public void setVoltageCompensation(boolean enabled, double maxVoltage)
    {
    }

    public void stop()
    {
    }

    public void setPosition(double position)
    {
    }

    public void reset()
    {
    }

    public double getPosition()
    {
        return this.innerEncoder.getDistance();
    }

    public double getVelocity()
    {
        return 0.0;
    }

    public double getError()
    {
        return 0.0;
    }

    public TalonXLimitSwitchStatus getLimitSwitchStatus()
    {
        return new TalonXLimitSwitchStatus(false, false);
    }

    private void resetPID()
    {
        if (this.simulator.shouldSimulatePID() &&
            (this.currentMode == TalonXControlMode.Position || this.currentMode == TalonXControlMode.Velocity))
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
