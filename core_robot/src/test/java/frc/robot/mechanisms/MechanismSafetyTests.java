package frc.robot.mechanisms;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import org.junit.jupiter.api.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

import frc.lib.driver.IButtonMap;
import frc.lib.driver.IDriver;
import frc.lib.driver.descriptions.UserInputDevice;
import frc.lib.mechanisms.IMechanism;
import frc.lib.mechanisms.LoggingManager;
import frc.lib.robotprovider.*;
import frc.robot.LoggingKey;
import frc.robot.SettingsManager;
import frc.robot.driver.*;

public class MechanismSafetyTests
{
    @Test
    public void ensureNoActuationDuringReadSensors()
    {
        Injector injector = Guice.createInjector(new ActuationDisallowedTestModule());

        LoggingManager logger = injector.getInstance(LoggingManager.class);
        logger.refresh(injector);

        List<IMechanism> mechanismList = SettingsManager.getActiveMechanisms(injector);
        for (IMechanism mechanism : mechanismList)
        {
            mechanism.readSensors();
        }
    }

    static class ActuationDisallowedTestModule extends AbstractModule
    {
        @Override
        protected void configure()
        {
            this.bind(IDriver.class).to(TestDriver.class);
            this.bind(IRobotProvider.class).to(TestRobotProvider.class);
            this.bind(ITimer.class).to(TestTimer.class);
            this.bind(IButtonMap.class).to(ButtonMap.class);
            this.bind(IFile.class).to(TestFile.class);
            this.bind(ISmartDashboardLogger.class).to(TestLogger.class);
        }

        @Singleton
        static class TestTimer implements ITimer
        {
            @Override
            public void start()
            {
            }

            @Override
            public void stop()
            {
            }

            @Override
            public double get()
            {
                return 0.0;
            }

            @Override
            public void reset()
            {
            }
        }

        @Singleton
        static class TestLogger implements ISmartDashboardLogger
        {
            @Inject
            public TestLogger()
            {
            }

            @Override
            public void logBoolean(LoggingKey key, boolean value)
            {
            }

            @Override
            public void logBooleanArray(LoggingKey key, boolean[] value)
            {
            }

            @Override
            public void logNumber(LoggingKey key, double value)
            {
            }

            @Override
            public void logNumber(LoggingKey key, Double value)
            {
            }

            @Override
            public void logInteger(LoggingKey key, int value)
            {
            }

            @Override
            public void logInteger(LoggingKey key, Integer value)
            {
            }

            @Override
            public void logInteger(LoggingKey key, int value, String formatString)
            {
            }

            @Override
            public void logString(LoggingKey key, String value)
            {
            }

            @Override
            public void update()
            {
            }

            @Override
            public void flush()
            {
            }
        }

        @Singleton
        static class TestFile implements IFile
        {
            @Inject
            public TestFile()
            {
            }

            @Override
            public void open(String fileName)
            {
                throw new UnsupportedOperationException("Don't expect file.open() to be run during readSensors");
            }

            @Override
            public boolean exists()
            {
                throw new UnsupportedOperationException("Don't expect file.exists() to be run during readSensors");
            }

            @Override
            public long getFreeSpace()
            {
                throw new UnsupportedOperationException("Don't expect file.getFreeSpace() to be run during readSensors");
            }

            @Override
            public void mkdir()
            {
                throw new UnsupportedOperationException("Don't expect file.mkdir() to be run during readSensors");
            }

            @Override
            public IFileWriter openWriter() throws IOException
            {
                throw new UnsupportedOperationException("Don't expect file.openWriter() to be run during readSensors");
            }
        }

        @Singleton
        static class TestDriver implements IDriver
        {
            @Inject
            public TestDriver()
            {
            }

            @Override
            public RobotMode getMode()
            {
                throw new UnsupportedOperationException("Don't expect driver.getMode() to be run during readSensors");
            }

            @Override
            public void update()
            {
                throw new UnsupportedOperationException("Don't expect driver.update() to be run during readSensors");
            }

            @Override
            public void stop()
            {
                throw new UnsupportedOperationException("Don't expect driver.stop() to be run during readSensors");
            }

            @Override
            public void prepAutoMode()
            {
                throw new UnsupportedOperationException("Don't expect driver.prepAutoMode() to be run during readSensors");
            }

            @Override
            public void startMode(RobotMode mode)
            {
                throw new UnsupportedOperationException("Don't expect driver.startMode() to be run during readSensors");
            }

            @Override
            public boolean getDigital(DigitalOperation digitalOperation)
            {
                throw new UnsupportedOperationException("Don't expect driver.getDigital() to be run during readSensors");
            }

            @Override
            public double getAnalog(AnalogOperation analogOperation)
            {
                throw new UnsupportedOperationException("Don't expect driver.getAnalog() to be run during readSensors");
            }

            @Override
            public void setRumble(UserInputDevice device, JoystickRumbleType type, double value)
            {
                throw new UnsupportedOperationException("Don't expect driver.setRumble() to be run during readSensors");
            }

            @Override
            public OperationContext getContext()
            {
                throw new UnsupportedOperationException("Don't expect driver.getContext() to be run during readSensors");
            }

            @Override
            public void setContext(OperationContext context)
            {
                throw new UnsupportedOperationException("Don't expect driver.setContext() to be run during readSensors");
            }
        }

        @Singleton
        static class TestRobotProvider implements IRobotProvider
        {
            @Inject
            public TestRobotProvider()
            {
            }

            @Override
            public IAnalogInput getAnalogInput(int analogChannel)
            {
                return new TestAnalogInput();
            }

            @Override
            public IDigitalInput getDigitalInput(int digitalIOChannel)
            {
                return new TestDigitalInput();
            }

            @Override
            public IDigitalOutput getDigitalOutput(int digitalIOChannel)
            {
                return new TestDigitalOutput();
            }

            @Override
            public ICounter getCounter(int digitalIOChannel)
            {
                return new TestCounter();
            }

            @Override
            public IDutyCycle getDutyCycle(int digitalIOChannel)
            {
                return new TestDutyCycle();
            }

            @Override
            public IDutyCycleEncoder getDutyCycleEncoder(int digitalIOChannel)
            {
                return new TestDutyCycleEncoder();
            }

            @Override
            public ITalonSRX getTalonSRX(int canId)
            {
                return new TestTalonSRX();
            }

            @Override
            public ITalonFX getTalonFX(int canId)
            {
                return new TestTalonFX();
            }

            @Override
            public ITalonFX getTalonFX(int canId, String canbus)
            {
                return new TestTalonFX();
            }

            @Override
            public IVictorSPX getVictorSPX(int canId)
            {
                return new TestVictorSPX();
            }

            @Override
            public ISparkMax getSparkMax(int canId, SparkMotorType motorType)
            {
                return new TestSparkMax();
            }

            @Override
            public ISparkFlex getSparkFlex(int canId, SparkMotorType motorType)
            {
                return new TestSparkFlex();
            }

            @Override
            public ICompressor getCompressor(PneumaticsModuleType moduleType)
            {
                return new TestCompressor();
            }

            @Override
            public ICompressor getCompressor(int moduleId, PneumaticsModuleType moduleType)
            {
                return new TestCompressor();
            }

            @Override
            public IDoubleSolenoid getDoubleSolenoid(PneumaticsModuleType moduleType, int forwardPneumaticsChannel, int reversePneumaticsChannel)
            {
                return new TestDoubleSolenoid();
            }

            @Override
            public IDoubleSolenoid getDoubleSolenoid(int moduleId, PneumaticsModuleType moduleType, int forwardPneumaticsChannel, int reversePneumaticsChannel)
            {
                return new TestDoubleSolenoid();
            }

            @Override
            public IEncoder getEncoder(int digitalIOChannelA, int digitalIOChannelB)
            {
                return new TestEncoder();
            }

            @Override
            public ICANCoder getCANCoder(int canId)
            {
                return new TestCANCoder();
            }

            @Override
            public ICANCoder getCANCoder(int canId, String canbus)
            {
                return new TestCANCoder();
            }

            @Override
            public ICANRange getCANRange(int canId)
            {
                return new TestCANRange();
            }

            @Override
            public ICANRange getCANRange(int canId, String canbus)
            {
                return new TestCANRange();
            }

            @Override
            public IJoystick getJoystick(int port)
            {
                return null;
            }

            @Override
            public IMotor getTalon(int pwmChannel)
            {
                return new TestMotor();
            }

            @Override
            public IServo getServo(int pwmChannel)
            {
                return new TestServo();
            }

            @Override
            public IPowerDistribution getPowerDistribution()
            {
                return new TestPowerDistribution();
            }

            @Override
            public IPowerDistribution getPowerDistribution(int moduleId, PowerDistributionModuleType moduleType)
            {
                return new TestPowerDistribution();
            }

            @Override
            public IRelay getRelay(int relayChannel)
            {
                return new TestRelay();
            }

            @Override
            public IRelay getRelay(int relayChannel, RelayDirection direction)
            {
                return new TestRelay();
            }

            @Override
            public ISolenoid getSolenoid(PneumaticsModuleType moduleType, int pneumaticsChannel)
            {
                return new TestSolenoid();
            }

            @Override
            public ISolenoid getSolenoid(int moduleId, PneumaticsModuleType moduleType, int pneumaticsChannel)
            {
                return new TestSolenoid();
            }

            @Override
            public INavx getNavx()
            {
                return null;
            }

            @Override
            public IPigeonIMU getPigeonIMU(int canId)
            {
                return null;
            }

            @Override
            public IPigeon2 getPigeon2(int canId)
            {
                return new TestPigeon2();
            }

            @Override
            public IPigeon2 getPigeon2(int canId, String canbus)
            {
                return new TestPigeon2();
            }

            @Override
            public ICANdle getCANdle(int canId)
            {
                return null;
            }

            @Override
            public ICANdle getCANdle(int canId, String canbus)
            {
                return null;
            }

            @Override
            public IDriverStation getDriverStation()
            {
                return new TestDriverStation();
            }

            @Override
            public INetworkTableProvider getNetworkTableProvider()
            {
                return new TestNetworkTableProvider();
            }

            @Override
            public IPathPlanner getPathPlanner()
            {
                return null;
            }

            @Override
            public ISwervePoseEstimator getSwervePoseEstimator()
            {
                return null;
            }

            @Override
            public IPreferences getPreferences()
            {
                return null;
            }

            static class TestAnalogInput implements IAnalogInput
            {
                @Override
                public double getVoltage()
                {
                    return 0.0;
                }
            }

            static class TestDigitalInput implements IDigitalInput
            {
                @Override
                public boolean get()
                {
                    return false;
                }

                @Override
                public void setInverted(boolean inverted)
                {
                }
            }

            static class TestDigitalOutput implements IDigitalOutput
            {
                @Override
                public void set(boolean value)
                {
                    throw new UnsupportedOperationException("Don't expect digitalOutput.set() to be run during readSensors");
                }
            }

            static class TestCounter implements ICounter
            {
                @Override
                public int get()
                {
                    return 0;
                }

                @Override
                public void reset()
                {
                }
            }

            static class TestDutyCycle implements IDutyCycle
            {
                @Override
                public double getOutput()
                {
                    return 0.0;
                }

                @Override
                public int getFrequency()
                {
                    return 0;
                }
            }

            static class TestDutyCycleEncoder implements IDutyCycleEncoder
            {
                @Override
                public double get()
                {
                    return 0.0;
                }

                @Override
                public double getDistance()
                {
                    return 0.0;
                }

                @Override
                public double getAbsolutePosition()
                {
                    return 0.0;
                }

                @Override
                public int getFrequency()
                {
                    return 0;
                }

                @Override
                public boolean isConnected()
                {
                    return false;
                }

                @Override
                public void setConnectedFrequencyThreshold(int frequency)
                {
                }

                @Override
                public void setDistancePerRotation(double distancePerRotation)
                {
                }

                @Override
                public void setDutyCycleRange(double min, double max)
                {
                }

                @Override
                public void setInverted(boolean inverted)
                {
                }

                @Override
                public void setPositionOffset(double offset)
                {
                }

                @Override
                public void reset()
                {
                }
            }

            static class TestTalonFX extends TestTalonXBase implements ITalonFX
            {
                @Override
                public void set(double value, double feedForward)
                {
                    throw new UnsupportedOperationException("Don't expect talonFX.set() to be run during readSensors");
                }

                @Override
                public void set(TalonFXControlMode mode, double value)
                {
                    throw new UnsupportedOperationException("Don't expect talonFX.set() to be run during readSensors");
                }

                @Override
                public void set(TalonFXControlMode mode, int slotId, double value)
                {
                    throw new UnsupportedOperationException("Don't expect talonFX.set() to be run during readSensors");
                }

                @Override
                public void set(TalonFXControlMode mode, double value, double feedForward)
                {
                    throw new UnsupportedOperationException("Don't expect talonFX.set() to be run during readSensors");
                }

                @Override
                public void set(TalonFXControlMode mode, int slotId, double value, double feedForward)
                {
                    throw new UnsupportedOperationException("Don't expect talonFX.set() to be run during readSensors");
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
                public void setMotionMagicPIDVS(double p, double i, double d, double v, double s, double cruiseVelocity, double maxAcceleration, double maxJerk, int slotId)
                {
                }

                @Override
                public void setMotionMagicExpoPIDVS(
                    double p,
                    double i,
                    double d,
                    double v,
                    double s,
                    double cruiseVelocity,
                    double velocityVoltage,
                    double accelerationVoltage,
                    int slotId)
                {
                }

                @Override
                public void updateLimitSwitchConfig(
                    boolean forwardEnabled,
                    boolean forwardNormallyOpen,
                    boolean forwardReset,
                    double forwardResetPosition,
                    boolean reverseEnabled,
                    boolean reverseNormallyOpen,
                    boolean reverseReset,
                    double reverseResetPosition)
                {
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
                public void setCurrentLimit(boolean enabled, double currentLimit, double triggerThresholdCurrent, double triggerThresholdTime)
                {
                }

                @Override
                public void setCurrentLimit(
                    boolean enabled,
                    double currentLimit,
                    double triggerThresholdCurrent,
                    double triggerThresholdTime,
                    boolean statorLimiting,
                    double statorCurrentLimit)
                {
                }
            }

            static class TestTalonSRX extends TestTalonXBase implements ITalonSRX
            {
                @Override
                public void set(double value, double feedForward)
                {
                    throw new UnsupportedOperationException("Don't expect talonSRX.set() to be run during readSensors");
                }

                @Override
                public void set(TalonSRXControlMode mode, double value)
                {
                    throw new UnsupportedOperationException("Don't expect talonSRX.set() to be run during readSensors");
                }

                @Override
                public void set(TalonSRXControlMode mode, double value, double feedForward)
                {
                    throw new UnsupportedOperationException("Don't expect talonSRX.set() to be run during readSensors");
                }

                @Override
                public void follow(ITalonSRX talonSRX)
                {
                }

                @Override
                public void follow(IVictorSPX victorSPX)
                {
                }

                @Override
                public void setControlMode(TalonSRXControlMode mode)
                {
                }

                @Override
                public void setMotionMagicPIDF(double p, double i, double d, double f, double velocity, double acceleration, int slotId)
                {
                }

                @Override
                public void setSensorType(TalonSRXFeedbackDevice feedbackDevice)
                {
                }

                @Override
                public void setGeneralFramePeriod(int periodMS)
                {
                }

                @Override
                public void setFeedbackFramePeriod(int periodMS)
                {
                }

                @Override
                public void setPIDFFramePeriod(int periodMS)
                {
                }

                @Override
                public void configureVelocityMeasurements(int periodMS, int windowSize)
                {
                }

                @Override
                public void configureAllowableClosedloopError(int slotId, int error)
                {
                }

                @Override
                public void setPIDF(double p, double i, double d, double f, int izone, double closeLoopRampRate, int slotId)
                {
                }

                @Override
                public void setInvertSensor(boolean flip)
                {
                }
            }

            static class TestTalonXBase implements ITalonXBase
            {
                @Override
                public void set(double power)
                {
                    throw new UnsupportedOperationException("Don't expect talonxbase.set() to be run during readSensors");
                }

                @Override
                public void setSelectedSlot(int slotId)
                {
                }

                @Override
                public void setPIDF(double p, double i, double d, double f, int slotId)
                {
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
                    // allow stop - it is safe
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
                    return 0.0;
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
                public TalonXLimitSwitchStatus getLimitSwitchStatus()
                {
                    return new TalonXLimitSwitchStatus(false, false);
                }
            }

            static class TestVictorSPX implements IVictorSPX
            {
                @Override
                public void set(double power)
                {
                    throw new UnsupportedOperationException("Don't expect sparkBase.set() to be run during readSensors");
                }

                @Override
                public void follow(ITalonSRX talonSRX)
                {
                }

                @Override
                public void follow(IVictorSPX victorSPX)
                {
                }

                @Override
                public void setMotorOutputSettings(boolean invert, MotorNeutralMode neutralMode)
                {
                }

                @Override
                public void setControlMode(TalonSRXControlMode mode)
                {
                }

                @Override
                public void stop()
                {
                    // allow stop - it is safe
                }
            }

            static class TestSparkMax extends TestSparkBase implements ISparkMax
            {
            }

            static class TestSparkFlex extends TestSparkBase implements ISparkFlex
            {
            }

            static class TestSparkBase implements ISparkBase
            {
                @Override
                public void set(double value)
                {
                    throw new UnsupportedOperationException("Don't expect sparkBase.set() to be run during readSensors");
                }

                @Override
                public void set(double value, double feedForward)
                {
                    throw new UnsupportedOperationException("Don't expect sparkBase.set() to be run during readSensors");
                }

                @Override
                public void set(SparkControlMode controlMode, double value)
                {
                    throw new UnsupportedOperationException("Don't expect sparkBase.set() to be run during readSensors");
                }

                @Override
                public void set(SparkControlMode controlMode, double value, double feedForward)
                {
                    throw new UnsupportedOperationException("Don't expect sparkBase.set() to be run during readSensors");
                }

                @Override
                public void stop()
                {
                    // allow stop - it is safe
                }

                @Override
                public void setSelectedSlot(int slotId)
                {
                }

                @Override
                public void setControlMode(SparkControlMode mode)
                {
                }

                @Override
                public double getPosition()
                {
                    return 0.0;
                }

                @Override
                public double getVelocity()
                {
                    return 0.0;
                }

                @Override
                public double getOutput()
                {
                    return 0.0;
                }

                @Override
                public boolean getForwardLimitSwitchStatus()
                {
                    return false;
                }

                @Override
                public boolean getReverseLimitSwitchStatus()
                {
                    return false;
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
                public void applyConfiguration(boolean persistant)
                {
                }

                @Override
                public void configureFollow(ISparkBase sparkBase)
                {
                }

                @Override
                public void configureAbsoluteEncoder()
                {
                }

                @Override
                public void configureRelativeEncoder()
                {
                }

                @Override
                public void configureRelativeEncoder(int resolution)
                {
                }

                @Override
                public void configureSignals(SparkSignalType signalType, boolean alwaysOn)
                {
                }

                @Override
                public void configureSignals(SparkSignalType signalType, int periodMs)
                {
                }

                @Override
                public void configureEncoderAverageDepth(int depth)
                {
                }

                @Override
                public void configureVelocityMeasurementPeriod(int periodMS)
                {
                }

                @Override
                public void configurePIDF(double p, double i, double d, double f, int slotId)
                {
                }

                @Override
                public void configurePIDF(double p, double i, double d, double f, double minOutput, double maxOutput, int slotId)
                {
                }

                @Override
                public void configurePIDF(double p, double i, double d, double f, double izone, int slotId)
                {
                }

                @Override
                public void configurePIDF(double p, double i, double d, double f, double izone, double minOutput, double maxOutput, int slotId)
                {
                }

                @Override
                public void configurePIDFMAXMotion(double p, double i, double d, double f, double izone, double velocity, double acceleration, int slotId)
                {
                }

                @Override
                public void configurePIDFMAXMotion(
                    double p,
                    double i,
                    double d,
                    double f,
                    double izone,
                    double velocity,
                    double acceleration,
                    double minOutput,
                    double maxOutput,
                    int slotId)
                {
                }

                @Override
                public void configureForwardLimitSwitch(boolean enabled, boolean normallyOpen)
                {
                }

                @Override
                public void configureReverseLimitSwitch(boolean enabled, boolean normallyOpen)
                {
                }

                @Override
                public void configureMotorOutputSettings(boolean invert, MotorNeutralMode neutralMode)
                {
                }

                @Override
                public void configureInvertSensor(boolean invert)
                {
                }

                @Override
                public void configureSmartCurrentLimit(int stallLimit, int freeLimit, int limitRPM)
                {
                }

                @Override
                public void configureSecondaryCurrentLimit(double limit, int chopCycles)
                {
                }

                @Override
                public void configureAbsoluteOffset(double zeroOffset)
                {
                }

                @Override
                public void configurePositionConversionFactor(double ratio)
                {
                }

                @Override
                public void configureVelocityConversionFactor(double ratio)
                {
                }

                @Override
                public void configurePositionPIDWrappingSettings(boolean enable, double minInput, double maxInput)
                {
                }
            }

            static class TestCompressor implements ICompressor
            {
                @Override
                public void enableAnalog(double minPressurePSI, double maxPressurePSI)
                {
                }

                @Override
                public void enableHybrid(double minPressurePSI, double maxPressurePSI)
                {
                }

                @Override
                public void enableDigital()
                {
                }

                @Override
                public double getPressure()
                {
                    return 0.0;
                }

                @Override
                public void disable()
                {
                }
            }

            static class TestDoubleSolenoid implements IDoubleSolenoid
            {
                @Override
                public void set(DoubleSolenoidValue value)
                {
                    throw new UnsupportedOperationException("Don't expect motor.set() to be run during readSensors");
                }
            }

            static class TestEncoder implements IEncoder
            {
                @Override
                public double getRate()
                {
                    return 0.0;
                }

                @Override
                public double getDistance()
                {
                    return 0.0;
                }

                @Override
                public int get()
                {
                    return 0;
                }

                @Override
                public void setDistancePerPulse(double distancePerPulse)
                {
                }

                @Override
                public void reset()
                {
                }
            }

            static class TestCANCoder implements ICANCoder
            {
                @Override
                public double getPosition()
                {
                    return 0.0;
                }

                @Override
                public double getVelocity()
                {
                    return 0.0;
                }

                @Override
                public double getAbsolutePosition()
                {
                    return 0.0;
                }

                @Override
                public void setPosition(double newPosition)
                {
                }

                @Override
                public void configSensorDirection(boolean clockwisePositive)
                {
                }
            }

            static class TestCANRange implements ICANRange
            {
                public void setFovParams(double centerX, double centerY, double rangeX, double rangeY)
                {
                }

                public void setProximityParams(double proximityThreshold, double proximityHysteresis, double minSignalStrengthForValidMeasurement)
                {
                }

                public void setToFParams(double updateFrequency, CANRangeUpdateMode updateMode)
                {
                }

                public double getDistance()
                {
                    return 0.0;
                }

                public double getDistanceStdDev()
                {
                    return 0.0;
                }

                public Boolean getIsDetected()
                {
                    return null;
                }
            }

            static class TestMotor implements IMotor
            {
                @Override
                public void set(double power)
                {
                    throw new UnsupportedOperationException("Don't expect motor.set() to be run during readSensors");
                }
            }

            static class TestServo implements IServo
            {
                @Override
                public void set(double value)
                {
                    throw new UnsupportedOperationException("Don't expect servo.set() to be run during readSensors");
                }
            }

            static class TestPowerDistribution implements IPowerDistribution
            {
                @Override
                public double getBatteryVoltage()
                {
                    return 0.0;
                }

                @Override
                public double getCurrent(int channel)
                {
                    return 0.0;
                }

                @Override
                public double getTotalCurrent()
                {
                    return 0.0;
                }

                @Override
                public double getTotalEnergy()
                {
                    return 0.0;
                }

                @Override
                public double getTotalPower()
                {
                    return 0.0;
                }

                @Override
                public double getTemperature()
                {
                    return 0.0;
                }

                @Override
                public void setSwitchableChannel(boolean enabled)
                {
                    throw new UnsupportedOperationException("Don't expect powerDistribution.setSwitchableChannel() to be run during readSensors");
                }
            }

            static class TestRelay implements IRelay
            {
                @Override
                public void set(RelayValue value)
                {
                    throw new UnsupportedOperationException("Don't expect relay.set() to be run during readSensors");
                }

                @Override
                public void configureDirection(RelayDirection direction)
                {
                }
            }

            static class TestSolenoid implements ISolenoid
            {
                @Override
                public void set(boolean on)
                {
                    throw new UnsupportedOperationException("Don't expect solenoid.set() to be run during readSensors");
                }
            }

            static class TestPigeon2 implements IPigeon2
            {
                @Override
                public void getYawPitchRoll(double[] ypr_deg)
                {
                }

                @Override
                public void getRollPitchYawRates(double[] xyz_dps)
                {
                }

                @Override
                public void setYaw(double angleDeg)
                {
                }

                @Override
                public void setYPRUpdateFrequency(double frequencyHz)
                {
                }

                @Override
                public void setRPYRateUpdateFrequency(double frequencyHz)
                {
                }
            }

            static class TestDriverStation implements IDriverStation
            {
                @Override
                public String getEventName()
                {
                    return "";
                }

                @Override
                public Optional<Alliance> getAlliance()
                {
                    return Optional.empty();
                }

                @Override
                public OptionalInt getLocation()
                {
                    return OptionalInt.empty();
                }

                @Override
                public int getMatchNumber()
                {
                    return 0;
                }

                @Override
                public MatchType getMatchType()
                {
                    return MatchType.None;
                }

                @Override
                public int getReplayNumber()
                {
                    return 0;
                }

                @Override
                public double getMatchTime()
                {
                    return 0.0;
                }

                @Override
                public RobotMode getMode()
                {
                    return RobotMode.Disabled;
                }

                @Override
                public boolean isFMSMode()
                {
                    return false;
                }

                @Override
                public String getGameSpecificMessage()
                {
                    return "";
                }
            }

            static class TestNetworkTableProvider implements INetworkTableProvider
            {
                @Override
                public void startShuffleboardRecording()
                {
                }

                @Override
                public void stopShuffleboardRecording()
                {
                }

                @Override
                public IAlert createAlert(String text, AlertType type)
                {
                    return new TestAlert();
                }

                @Override
                public IField2d getField2d(String name)
                {
                    return new TestField2d();
                }

                @Override
                public IDoubleSubscriber getNumberSlider(String title, double initialValue)
                {
                    return new TestDoubleSubscriber();
                }

                @Override
                public IIntegerSubscriber getIntegerSlider(String title, int initialValue)
                {
                    return new TestIntegerSubscriber();
                }

                @Override
                public IBooleanSubscriber getCheckbox(String title, boolean initialValue)
                {
                    return new TestBooleanSubscriber();
                }

                @Override
                public <V> ISendableChooser<V> getSendableChooser(String name)
                {
                    return new TestChooser<V>();
                }

                @Override
                public IDoubleSubscriber getDoubleSubscriber(String key)
                {
                    return new TestDoubleSubscriber();
                }

                @Override
                public IDoubleSubscriber getDoubleSubscriber(String key, double defaultValue)
                {
                    return new TestDoubleSubscriber();
                }

                @Override
                public IDoubleArraySubscriber getDoubleArraySubscriber(String key)
                {
                    return new TestDoubleArraySubscriber();
                }

                @Override
                public IBooleanSubscriber getBooleanSubscriber(String key)
                {
                    return new TestBooleanSubscriber();
                }

                @Override
                public IBooleanSubscriber getBooleanSubscriber(String key, boolean defaultValue)
                {
                    return new TestBooleanSubscriber();
                }

                @Override
                public IIntegerSubscriber getIntegerSubscriber(String key)
                {
                    return new TestIntegerSubscriber();
                }

                @Override
                public IIntegerSubscriber getIntegerSubscriber(String key, int defaultValue)
                {
                    return new TestIntegerSubscriber();
                }

                @Override
                public IStringSubscriber getStringSubscriber(String key)
                {
                    return new TestStringSubscriber();
                }

                @Override
                public IStringSubscriber getStringSubscriber(String key, String defaultValue)
                {
                    return new TestStringSubscriber();
                }

                static class TestAlert implements IAlert
                {
                    @Override
                    public AlertType getType()
                    {
                        return AlertType.Info;
                    }

                    @Override
                    public String getText()
                    {
                        return "";
                    }

                    @Override
                    public void updateText(String newText)
                    {
                    }

                    @Override
                    public void enable()
                    {
                    }

                    @Override
                    public void disable()
                    {
                    }

                    @Override
                    public void set(boolean enabled)
                    {
                    }
                }

                static class TestField2d implements IField2d
                {
                    @Override
                    public void setRobotPose(double xPos, double yPos, double yaw)
                    {
                    }

                    @Override
                    public Pose2d getRobotPose()
                    {
                        return new Pose2d(0.0, 0.0, 0.0);
                    }
                }

                static class TestDoubleSubscriber implements IDoubleSubscriber
                {
                    @Override
                    public double get()
                    {
                        return 0.0;
                    }
                }

                static class TestDoubleArraySubscriber implements IDoubleArraySubscriber
                {
                    @Override
                    public double[] get()
                    {
                        return null;
                    }

                    @Override
                    public double getLastChange()
                    {
                        return 0.0;
                    }

                    @Override
                    public DoubleArrayValue[] getValues()
                    {
                        return new DoubleArrayValue[0];
                    }
                }

                static class TestIntegerSubscriber implements IIntegerSubscriber
                {
                    @Override
                    public long get()
                    {
                        return 0L;
                    }
                }

                static class TestBooleanSubscriber implements IBooleanSubscriber
                {
                    @Override
                    public boolean get()
                    {
                        return false;
                    }
                }

                static class TestStringSubscriber implements IStringSubscriber
                {
                    @Override
                    public String get()
                    {
                        return "";
                    }
                }

                static class TestChooser<V> implements ISendableChooser<V>
                {
                    private V selected = null;

                    @Override
                    public void addDefault(String name, V object)
                    {
                        this.selected = object;
                    }

                    @Override
                    public void addObject(String name, V object)
                    {
                    }

                    @Override
                    public V getSelected()
                    {
                        return this.selected;
                    }
                }
            }
        }
    }
}
