package frc.lib.robotprovider;

/**
 * Abstracted provider for retrieving lower-level Robot components
 * Note that we use this approach to allow alternate implementations (Fauxbot, UnitTests, etc.)
 */
public interface IRobotProvider
{
    /**
     * Create an AnalogInput connected to the specified AnalogInput channel
     * @param analogChannel that the device is physically connected to
     * @return AnalogInput object
     */
    public IAnalogInput getAnalogInput(int analogChannel);

    /**
     * Create a DigitalInput connected to the specified Digital IO channel
     * @param digitalIOChannel that the device is physically connected to
     * @return DigitalInput object
     */
    public IDigitalInput getDigitalInput(int digitalIOChannel);

    /**
     * Create a DigitalOutput connected to the specified Digital IO channel
     * @param digitalIOChannel that the device is physically connected to
     * @return DigitalOutput object
     */
    public IDigitalOutput getDigitalOutput(int digitalIOChannel);

    /**
     * Create a Counter connected to the specified Digital IO channel
     * @param digitalIOChannel that the device is physically connected to
     * @return Counter object
     */
    public ICounter getCounter(int digitalIOChannel);

    /**
     * Create a DutyCycle object connected to the specified Digital IO channel
     * @param digitalIOChannel that the device is physically connected to
     * @return DutyCycle object
     */
    public IDutyCycle getDutyCycle(int digitalIOChannel);

    /**
     * Create a DutyCycleEncoder object connected to the specified Digital IO channel
     * @param digitalIOChannel that the device is physically connected to
     * @return DutyCycleEncoder object
     */
    public IDutyCycleEncoder getDutyCycleEncoder(int digitalIOChannel);

    /**
     * Create a TalonSRX object connected to the specified CAN device
     * @param canId that the device has been assigned on the default canbus
     * @return TalonSRX object
     */
    public ITalonSRX getTalonSRX(int canId);

    /**
     * Create a TalonFX object connected to the specified CAN device
     * @param canId that the device has been assigned on the default canbus
     * @return TalonFX object
     */
    public ITalonFX getTalonFX(int canId);

    /**
     * Create a TalonFX object connected to the specified CAN device
     * @param canId that the device has been assigned on the specified canbus
     * @param canbus that specifies which network to check
     * @return TalonFX object
     */
    public ITalonFX getTalonFX(int canId, String canbus);

    /**
     * Create a VictorSPX object connected to the specified CAN device
     * @param canId that the device has been assigned on the default canbus
     * @return VictorSPX object
     */
    public IVictorSPX getVictorSPX(int canId);

    /**
     * Create a SparkMax object connected to the specified CAN device
     * @param canId that the device has been assigned on the default canbus
     * @param motorType that is connected to the SparkMax motor controller (brushed, brushless)
     * @return SparkMax object
     */
    public ISparkMax getSparkMax(int canId, SparkMotorType motorType);

    /**
     * Create a SparkFlex object connected to the specified CAN device
     * @param canId that the device has been assigned on the default canbus
     * @param motorType that is connected to the SparkFlex motor controller (brushed, brushless)
     * @return SparkFlex object
     */
    public ISparkFlex getSparkFlex(int canId, SparkMotorType motorType);

    /**
     * Create a Compressor object for the default pneumatics device (moduleId == 0)
     * @param moduleType of the pneumatics device for controlling the compressor
     * @return Compressor object
     */
    public ICompressor getCompressor(PneumaticsModuleType moduleType);

    /**
     * Create a Compressor object connected to the specified CAN pneumatics device
     * @param moduleId that the pneumatics device has been assigned on the default canbus
     * @param moduleType of the pneumatics device for controlling the compressor
     * @return Compressor object
     */
    public ICompressor getCompressor(int moduleId, PneumaticsModuleType moduleType);

    /**
     * Create a DoubleSolenoid object connected to specified channels on the default pneumatics device (moduleId == 0)
     * @param moduleType of the pneumatics device for controlling the DoubleSolenoid
     * @param forwardPneumaticsChannel for providing forward motion
     * @param reversePneumaticsChannel for providing reverse motion
     * @return DoubleSolenoid object
     */
    public IDoubleSolenoid getDoubleSolenoid(PneumaticsModuleType moduleType, int forwardPneumaticsChannel, int reversePneumaticsChannel);

    /**
     * Create a DoubleSolenoid object connected to specified channels on the specified CAN pneumatics device
     * @param moduleId that the pneumatics device has been assigned on the default canbus
     * @param moduleType of the pneumatics device for controlling the DoubleSolenoid
     * @param forwardPneumaticsChannel for providing forward motion
     * @param reversePneumaticsChannel for providing reverse motion
     * @return DoubleSolenoid object
     */
    public IDoubleSolenoid getDoubleSolenoid(int moduleId, PneumaticsModuleType moduleType, int forwardPneumaticsChannel, int reversePneumaticsChannel);

    /**
     * Create a quadrature Encoder object connected to specified Digital IO channels
     * @param digitalIOChannelA that the device is phyiscally connected to
     * @param digitalIOChannelB that the device is phyiscally connected to
     * @return Encoder object
     */
    public IEncoder getEncoder(int digitalIOChannelA, int digitalIOChannelB);

    /**
     * Create a CANCoder object connected to the specified CAN device
     * @param canId that the device has been assigned on the default canbus
     * @return CANCoder object
     */
    public ICANCoder getCANCoder(int canId);

    /**
     * Create a CANCoder object connected to the specified CAN device
     * @param canId that the device has been assigned on the specified canbus
     * @param canbus that specifies which network to check
     * @return CANCoder object
     */
    public ICANCoder getCANCoder(int canId, String canbus);

    /**
     * Create a Joystick object connected to the specified port in the DriverStation
     * Note: this should not be used in normal mechanism code!
     * @param port that the joystick is associated with in the DriverStation
     * @return Joystick object
     */
    public IJoystick getJoystick(int port);

    /**
     * Create a Talon connected to the specified PWM channel
     * Note: this could be used for other PWM-connected motors in addition to Talons
     * @param pwmChannel that the device is physically connected to
     * @return Talon object
     */
    public IMotor getTalon(int pwmChannel);

    /**
     * Create a Victor connected to the specified PWM channel
     * @param pwmChannel that the device is physically connected to
     * @return Victor object
     */
    public IMotor getVictor(int pwmChannel);

    /**
     * Create a Servo connected to the specified PWM channel
     * @param pwmChannel that the device is physically connected to
     * @return Servo object
     */
    public IServo getServo(int pwmChannel);

    /**
     * Create a PowerDistribution object connected to the default device
     * @return PowerDistribution object
     */
    public IPowerDistribution getPowerDistribution();

    /**
     * Create a PowerDistribution object connected to the specified CAN power distribution device
     * @param moduleId that the power distribution device has been assigned on the default canbus
     * @param moduleType of the power distribution device
     * @return PowerDistribution object
     */
    public IPowerDistribution getPowerDistribution(int moduleId, PowerDistributionModuleType moduleType);

    /**
     * Create a Relay connected to the specified Relay channel
     * @param relayChannel that the device is physically connected to
     * @return Relay object
     */
    public IRelay getRelay(int relayChannel);

    /**
     * Create a Relay connected to the specified Relay channel
     * @param relayChannel that the device is physically connected to
     * @param direction that the relay is configured to use (Both, Forward, Reverse)
     * @return Relay object
     */
    public IRelay getRelay(int relayChannel, RelayDirection direction);

    /**
     * Create a Solenoid object connected to the specified channel on the default pneumatics device (moduleId == 0)
     * @param moduleType of the pneumatics device for controlling the Solenoid
     * @param pneumaticsChannel for providing actuation
     * @return Solenoid object
     */
    public ISolenoid getSolenoid(PneumaticsModuleType moduleType, int pneumaticsChannel);

    /**
     * Create a Solenoid object connected to the specified channel on the specified CAN pneumatics device
     * @param moduleId that the pneumatics device has been assigned on the default canbus
     * @param moduleType of the pneumatics device for controlling the Solenoid
     * @param pneumaticsChannel for providing actuation
     * @return Solenoid object
     */
    public ISolenoid getSolenoid(int moduleId, PneumaticsModuleType moduleType, int pneumaticsChannel);

    /**
     * Create a Navx (IMU) object connected to the MXP port
     * @return Navx object
     */
    public INavx getNavx();

    /**
     * Create a PigeonIMU object connected to the specified CAN device
     * @param canId that the device has been assigned on the default canbus
     * @return PigeonIMU object
     */
    public IPigeonIMU getPigeonIMU(int canId);

    /**
     * Create a Pigeon2 (IMU) object connected to the specified CAN device
     * @param canId that the device has been assigned on the default canbus
     * @return Pigeon2 object
     */
    public IPigeon2 getPigeon2(int canId);

    /**
     * Create a Pigeon2 (IMU) object connected to the specified CAN device
     * @param canId that the device has been assigned on the specified canbus
     * @param canbus that specifies which network to check
     * @return Pigeon2 object
     */
    public IPigeon2 getPigeon2(int canId, String canbus);

    /**
     * Create a CANdle object connected to the specified CAN device
     * @param canId that the device has been assigned on the default canbus
     * @return CANdle object
     */
    public ICANdle getCANdle(int canId);

    /**
     * Create a CANdle object connected to the specified CAN device
     * @param canId that the device has been assigned on the specified canbus
     * @param canbus that specifies which network to check
     * @return CANdle object
     */
    public ICANdle getCANdle(int canId, String canbus);

    /**
     * Retrieve the singleton DriverStation object
     * @return DriverStation object
     */
    public IDriverStation getDriverStation();

    /**
     * Retrieve the singleton NetworkTableProvider object
     * @return NetworkTableProvider object
     */
    public INetworkTableProvider getNetworkTableProvider();

    /**
     * Retrieve the singleton PathPlanner object
     * @return PathPlanner object
     */
    public IPathPlanner getPathPlanner();

    /**
     * Retrieve the singleton Preferences object
     * @return Preferences object
     */
    public IPreferences getPreferences();
}
