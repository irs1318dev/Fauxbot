package frc.lib.robotprovider;

import frc.robot.simulation.SimulatorBase;
import frc.lib.controllers.PIDHandler;

public class FauxbotTalonSRX extends FauxbotAdvancedMotorBase implements ITalonSRX
{
    private final SimulatorBase simulator;

    private FauxbotEncoder innerEncoder;
    private PIDHandler pidHandler;

    private TalonSRXControlMode currentMode;
    private double kp;
    private double ki;
    private double kd;
    private double kf;

    public FauxbotTalonSRX(int deviceNumber, SimulatorBase simulator)
    {
        super(deviceNumber);

        this.simulator = simulator;
        this.currentMode = TalonSRXControlMode.PercentOutput;
    }

    public void follow(ITalonSRX talonSRX)
    {
    }

    public void follow(IVictorSPX victorSPX)
    {
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
            this.innerEncoder = new FauxbotEncoder(new FauxbotSensorConnection(FauxbotSensorConnection.SensorConnector.CAN, FauxbotTalonSRX.class, this.connection.getPort()));
        }
    }

    public void setGeneralFramePeriod(int periodMS)
    {
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

    public void setMotionMagicPIDF(double p, double i, double d, double f, double velocity, double acceleration, int slotId)
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
    public void set(double value)
    {
        this.set(this.currentMode, value, 0.0);
    }

    @Override
    public void set(double value, double feedForward)
    {
        this.set(this.currentMode, value, feedForward);
    }

    @Override
    public void set(TalonSRXControlMode mode, double value)
    {
        this.set(mode, value, 0.0);
    }

    @Override
    public void set(TalonSRXControlMode mode, double value, double feedForward)
    {
        if (mode == TalonSRXControlMode.Follower)
        {
            FauxbotActuatorBase actuator = FauxbotActuatorManager.get(new FauxbotActuatorConnection(FauxbotActuatorConnection.ActuatorConnector.CAN, (int)value));
            if (actuator != null && actuator instanceof FauxbotAdvancedMotorBase)
            {
                FauxbotAdvancedMotorBase advancedMotor = (FauxbotAdvancedMotorBase)actuator;
                // TODO: fix link between primary and follower
                // advancedMotor.currentPowerProperty.addListener(
                //     (observable, oldValue, value) -> { this.currentPowerProperty.set((Double)value); });
            }
            else
            {
                throw new RuntimeException("expected a different actuator type " + actuator == null ? "null" : actuator.toString());
            }
        }
        else if (mode == TalonSRXControlMode.Velocity && this.pidHandler != null)
        {
            super.set(this.pidHandler.calculateVelocity(value, innerEncoder.getRate()) + feedForward);
        }
        else if (mode == TalonSRXControlMode.Position && this.pidHandler != null)
        {
            super.set(this.pidHandler.calculatePosition(value, innerEncoder.get()) + feedForward);
        }
        else
        {
            super.set(value + feedForward);
        }
    }

    public void updateLimitSwitchConfig(boolean forwardEnabled, boolean forwardNormallyOpen, boolean reverseEnabled, boolean reverseNormallyOpen)
    {
    }

    public void setMotorOutputSettings(boolean invert, MotorNeutralMode neutralMode)
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
            (this.currentMode == TalonSRXControlMode.Position || this.currentMode == TalonSRXControlMode.Velocity))
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

    @Override
    public double getOutput()
    {
        return 0.0;
    }
}
