package frc.robot.common.robotprovider;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonFXSensorCollection;
import com.ctre.phoenix.motorcontrol.VelocityMeasPeriod;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

public class TalonFXWrapper implements ITalonFX
{
    private static final int pidIdx = 0;
    private static final int timeoutMS = 10;

    final TalonFX wrappedObject;

    private ControlMode controlMode;

    public TalonFXWrapper(int deviceNumber)
    {
        this.wrappedObject = new TalonFX(deviceNumber);
        this.controlMode = ControlMode.PercentOutput;
    }

    public void set(double value)
    {
        this.wrappedObject.set(this.controlMode, value);
    }

    public void follow(ITalonSRX talonSRX)
    {
        this.wrappedObject.follow(((TalonSRXWrapper)talonSRX).wrappedObject);
    }

    public void follow(ITalonFX talonFX)
    {
        this.wrappedObject.follow(((TalonFXWrapper)talonFX).wrappedObject);
    }

    public void follow(IVictorSPX victorSPX)
    {
        this.wrappedObject.follow(((VictorSPXWrapper)victorSPX).wrappedObject);
    }

    public void setControlMode(TalonSRXControlMode mode)
    {
        if (mode == TalonSRXControlMode.PercentOutput)
        {
            this.controlMode = ControlMode.PercentOutput;
        }
        else if (mode == TalonSRXControlMode.Disabled)
        {
            this.controlMode = ControlMode.Disabled;
        }
        else if (mode == TalonSRXControlMode.Follower)
        {
            this.controlMode = ControlMode.Follower;
        }
        else if (mode == TalonSRXControlMode.Position)
        {
            this.controlMode = ControlMode.Position;
        }
        else if (mode == TalonSRXControlMode.MotionMagicPosition)
        {
            this.controlMode = ControlMode.MotionMagic;
        }
        else if (mode == TalonSRXControlMode.Velocity)
        {
            this.controlMode = ControlMode.Velocity;
        }
    }

    public void setSensorType(TalonXFeedbackDevice feedbackDevice)
    {
        FeedbackDevice device;
        if (feedbackDevice == TalonXFeedbackDevice.QuadEncoder)
        {
            device = FeedbackDevice.QuadEncoder;
        }
        else if (feedbackDevice == TalonXFeedbackDevice.PulseWidthEncodedPosition)
        {
            device = FeedbackDevice.PulseWidthEncodedPosition;
        }
        else if (feedbackDevice == TalonXFeedbackDevice.IntegratedSensor)
        {
            device = FeedbackDevice.IntegratedSensor;
        }
        else
        {
            return;
        }

        this.wrappedObject.configSelectedFeedbackSensor(device, TalonFXWrapper.pidIdx, 0);
    }

    public void setPIDFFramePeriod(int periodMS)
    {
        this.wrappedObject.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, periodMS, TalonFXWrapper.timeoutMS);
    }

    public void setFeedbackFramePeriod(int periodMS)
    {
        this.wrappedObject.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, periodMS, TalonFXWrapper.timeoutMS);
    }

    public void configureVelocityMeasurements(int periodMS, int windowSize)
    {
        this.wrappedObject.configVelocityMeasurementPeriod(VelocityMeasPeriod.valueOf(periodMS), TalonFXWrapper.timeoutMS);
        this.wrappedObject.configVelocityMeasurementWindow(windowSize, TalonFXWrapper.timeoutMS);
    }

    public void configureAllowableClosedloopError(int slotId, int error)
    {
        this.wrappedObject.configAllowableClosedloopError(slotId, error, TalonFXWrapper.timeoutMS);
    }

    public void setSelectedSlot(int slotId)
    {
        this.wrappedObject.selectProfileSlot(slotId, TalonFXWrapper.pidIdx);
    }

    public void setPIDF(double p, double i, double d, double f, int slotId)
    {
        this.wrappedObject.config_kP(slotId, p, TalonFXWrapper.timeoutMS);
        this.wrappedObject.config_kI(slotId, i, TalonFXWrapper.timeoutMS);
        this.wrappedObject.config_kD(slotId, d, TalonFXWrapper.timeoutMS);
        this.wrappedObject.config_kF(slotId, f, TalonFXWrapper.timeoutMS);
    }

    public void setMotionMagicPIDF(double p, double i, double d, double f, int velocity, int acceleration, int slotId)
    {
        this.wrappedObject.config_kP(slotId, p, TalonFXWrapper.timeoutMS);
        this.wrappedObject.config_kI(slotId, i, TalonFXWrapper.timeoutMS);
        this.wrappedObject.config_kD(slotId, d, TalonFXWrapper.timeoutMS);
        this.wrappedObject.config_kF(slotId, f, TalonFXWrapper.timeoutMS);
        this.wrappedObject.configMotionCruiseVelocity(velocity, TalonFXWrapper.timeoutMS);
        this.wrappedObject.configMotionAcceleration(acceleration, TalonFXWrapper.timeoutMS);
    }

    public void setPIDF(double p, double i, double d, double f, int izone, double closeLoopRampRate, int slotId)
    {
        this.wrappedObject.config_kP(slotId, p, TalonFXWrapper.timeoutMS);
        this.wrappedObject.config_kI(slotId, i, TalonFXWrapper.timeoutMS);
        this.wrappedObject.config_kD(slotId, d, TalonFXWrapper.timeoutMS);
        this.wrappedObject.config_kF(slotId, f, TalonFXWrapper.timeoutMS);
        this.wrappedObject.config_IntegralZone(slotId, izone, TalonFXWrapper.timeoutMS);
        this.wrappedObject.configClosedloopRamp(closeLoopRampRate, TalonFXWrapper.timeoutMS);
    }

    public void setForwardLimitSwitch(boolean enabled, boolean normallyOpen)
    {
        LimitSwitchSource source = LimitSwitchSource.Deactivated;
        if (enabled)
        {
            source = LimitSwitchSource.FeedbackConnector;
        }

        LimitSwitchNormal type = LimitSwitchNormal.NormallyClosed;
        if (normallyOpen)
        {
            type = LimitSwitchNormal.NormallyOpen;
        }

        this.wrappedObject.configForwardLimitSwitchSource(
            source,
            type,
            TalonFXWrapper.timeoutMS);
    }

    public void setReverseLimitSwitch(boolean enabled, boolean normallyOpen)
    {
        LimitSwitchSource source = LimitSwitchSource.Deactivated;
        if (enabled)
        {
            source = LimitSwitchSource.FeedbackConnector;
        }

        LimitSwitchNormal type = LimitSwitchNormal.NormallyClosed;
        if (normallyOpen)
        {
            type = LimitSwitchNormal.NormallyOpen;
        }

        this.wrappedObject.configReverseLimitSwitchSource(
            source,
            type,
            TalonFXWrapper.timeoutMS);
    }

    public void setInvertOutput(boolean invert)
    {
        this.wrappedObject.setInverted(invert);
    }

    public void setInvertSensor(boolean invert)
    {
        this.wrappedObject.setSensorPhase(invert);
    }

    public void setNeutralMode(MotorNeutralMode neutralMode)
    {
        NeutralMode mode;
        if (neutralMode == MotorNeutralMode.Brake)
        {
            mode = NeutralMode.Brake;
        }
        else
        {
            mode = NeutralMode.Coast;
        }

        this.wrappedObject.setNeutralMode(mode);
    }

    public void setVoltageCompensation(boolean enabled, double maxVoltage)
    {
        this.wrappedObject.configVoltageCompSaturation(maxVoltage, TalonFXWrapper.timeoutMS);
        this.wrappedObject.enableVoltageCompensation(enabled);
    }

    public void setSupplyCurrentLimit(boolean enabled, double currentLimit, double triggerThresholdCurrent, double triggerThresholdTime)
    {
        SupplyCurrentLimitConfiguration config = new SupplyCurrentLimitConfiguration(enabled, currentLimit, triggerThresholdCurrent, triggerThresholdTime);
        this.wrappedObject.configSupplyCurrentLimit(config);
    }

    public void stop()
    {
        this.wrappedObject.set(ControlMode.Disabled, 0.0);
    }

    public void setPosition(int position)
    {
        this.wrappedObject.setSelectedSensorPosition(position, TalonFXWrapper.pidIdx, TalonFXWrapper.timeoutMS);
    }

    public void reset()
    {
        this.wrappedObject.setSelectedSensorPosition(0, TalonFXWrapper.pidIdx, TalonFXWrapper.timeoutMS);
    }

    public int getPosition()
    {
        return this.wrappedObject.getSelectedSensorPosition(TalonFXWrapper.pidIdx);
    }

    public double getVelocity()
    {
        return this.wrappedObject.getSelectedSensorVelocity(TalonFXWrapper.pidIdx);
    }

    public double getError()
    {
        return this.wrappedObject.getClosedLoopError(TalonFXWrapper.pidIdx);
    }

    public TalonXLimitSwitchStatus getLimitSwitchStatus()
    {
        TalonFXSensorCollection collection = this.wrappedObject.getSensorCollection();

        return new TalonXLimitSwitchStatus(
            collection.isFwdLimitSwitchClosed() == 1,
            collection.isRevLimitSwitchClosed() == 1);
    }
}
