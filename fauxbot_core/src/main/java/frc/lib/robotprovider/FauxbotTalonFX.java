package frc.lib.robotprovider;

import frc.lib.controllers.PIDHandler;
import frc.robot.simulation.SimulatorBase;

public class FauxbotTalonFX extends FauxbotAdvancedMotorBase implements ITalonFX
{
    private final SimulatorBase simulator;

    private FauxbotEncoder innerEncoder;
    private PIDHandler pidHandler;

    private TalonFXControlMode currentMode;
    private double kp;
    private double ki;
    private double kd;
    private double kf;

    public FauxbotTalonFX(int deviceNumber, SimulatorBase simulator)
    {
        super(deviceNumber);

        this.simulator = simulator;
        this.currentMode = TalonFXControlMode.PercentOutput;

        this.innerEncoder = new FauxbotEncoder(new FauxbotSensorConnection(FauxbotSensorConnection.SensorConnector.CAN, FauxbotTalonFX.class, this.connection.getPort()));
    }

    @Override
    public void follow(ITalonFX talonFX)
    {
    }

    @Override
    public void follow(ITalonFX talonFX, boolean invertDirection)
    {
    }

    @Override
    public void setControlMode(TalonFXControlMode mode)
    {
        this.currentMode = mode;
        this.resetPID();
    }

    @Override
    public void clearRemoteSensor()
    {
    }

    @Override
    public void setRemoteSensor(int sensorId, double ratio)
    {
    }

    @Override
    public void setFeedbackUpdateRate(double frequencyHz)
    {
    }

    @Override
    public void setErrorUpdateRate(double frequencyHz)
    {
    }

    @Override
    public void setOutputUpdateRate(double frequencyHz)
    {
    }

    @Override
    public void setForwardLimitSwitchUpdateRate(double frequencyHz)
    {
    }

    @Override
    public void setReverseLimitSwitchUpdateRate(double frequencyHz)
    {
    }

    @Override
    public void optimizeCanbus()
    {
    }

    @Override
    public void updateLimitSwitchConfig(boolean forwardEnabled, boolean forwardNormallyOpen, boolean forwardReset, double forwardResetPosition, boolean reverseEnabled, boolean reverseNormallyOpen, boolean reverseReset, double reverseResetPosition)
    {
    }

    @Override
    public void setCurrentLimit(boolean enabled, double currentLimit, double triggerThresholdCurrent, double triggerThresholdTime)
    {
    }

    @Override
    public void setCurrentLimit(boolean enabled, double currentLimit, double triggerThresholdCurrent, double triggerThresholdTime, boolean statorLimiting, double statorCurrentLimit)
    {
    }

    @Override
    public void setSelectedSlot(int slotId)
    {
    }

    @Override
    public void setPIDF(double p, double i, double d, double f, int slotId)
    {
        this.kp = p;
        this.ki = i;
        this.kd = d;
        this.kf = f;
        this.resetPID();
    }

    @Override
    public void setMotionMagicPIDVS(double p, double i, double d, double v, double s, double cruiseVelocity, double maxAcceleration, double maxJerk, int slotId)
    {
    }

    @Override
    public void setMotionMagicExpoPIDVS(double p, double i, double d, double v, double s, double cruiseVelocity, double velocityVoltage, double accelerationVoltage, int slotId)
    {
    }

    @Override
    public void set(double value)
    {
        this.set(this.currentMode, 0, value, 0.0);
    }

    @Override
    public void set(TalonFXControlMode mode, double value)
    {
        this.set(mode, 0, value, 0.0);
    }

    @Override
    public void set(double value, double feedForward)
    {
        this.set(this.currentMode, 0, value, feedForward);
    }

    @Override
    public void set(TalonFXControlMode mode, double value, double feedForward)
    {
        this.set(mode, 0, value, feedForward);
    }

    @Override
    public void set(TalonFXControlMode mode, int slotId, double value)
    {
        this.set(mode, slotId, value, 0.0);
    }

    @Override
    public void set(TalonFXControlMode mode, int slotId, double value, double feedForward)
    {
        if (mode == TalonFXControlMode.Follower)
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
        else if (mode == TalonFXControlMode.Velocity && this.pidHandler != null)
        {
            super.set(this.pidHandler.calculateVelocity(value, innerEncoder.getRate()) + feedForward);
        }
        else if (mode == TalonFXControlMode.Position && this.pidHandler != null)
        {
            super.set(this.pidHandler.calculatePosition(value, innerEncoder.get()) + feedForward);
        }
        else
        {
            super.set(value + feedForward);
        }
    }

    @Override
    public void updateLimitSwitchConfig(boolean forwardEnabled, boolean forwardNormallyOpen, boolean reverseEnabled, boolean reverseNormallyOpen)
    {
    }

    @Override
    public void setMotorOutputSettings(boolean invert, MotorNeutralMode neutralMode)
    {
    }

    @Override
    public void setVoltageCompensation(boolean enabled, double maxVoltage)
    {
    }

    @Override
    public void stop()
    {
    }

    @Override
    public void setPosition(double position)
    {
    }

    @Override
    public void reset()
    {
    }

    @Override
    public double getPosition()
    {
        return this.innerEncoder.getDistance();
    }

    @Override
    public double getVelocity()
    {
        return 0.0;
    }

    @Override
    public double getError()
    {
        return 0.0;
    }

    @Override
    public double getOutput()
    {
        return 0.0;
    }

    @Override
    public boolean getForwardLimitSwitchClosed()
    {
        return false;
    }

    @Override
    public boolean getReverseLimitSwitchClosed()
    {
        return false;
    }

    @Override
    public TalonXLimitSwitchStatus getLimitSwitchStatus()
    {
        return new TalonXLimitSwitchStatus(false, false);
    }

    private void resetPID()
    {
        if (this.simulator.shouldSimulatePID() &&
            (this.currentMode == TalonFXControlMode.Position || this.currentMode == TalonFXControlMode.Velocity))
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
