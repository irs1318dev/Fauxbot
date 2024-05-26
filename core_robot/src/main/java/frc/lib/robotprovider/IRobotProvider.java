package frc.lib.robotprovider;

public interface IRobotProvider
{
    public IAnalogInput getAnalogInput(int channel);
    public IDigitalInput getDigitalInput(int channel);
    public IDigitalOutput getDigitalOutput(int channel);
    public ICounter getCounter(int channel);
    public IDutyCycle getDutyCycle(int digitalInputChannel);
    public IDutyCycleEncoder getDutyCycleEncoder(int digitalInputChannel);
    public ITalonSRX getTalonSRX(int deviceNumber);
    public ITalonFX getTalonFX(int deviceNumber);
    public ITalonFX getTalonFX(int deviceNumber, String canbus);
    public IVictorSPX getVictorSPX(int deviceNumber);
    public ISparkMax getSparkMax(int deviceID, SparkMaxMotorType motorType);
    public ICompressor getCompressor(PneumaticsModuleType moduleType);
    public ICompressor getCompressor(int module, PneumaticsModuleType moduleType);
    public IDoubleSolenoid getDoubleSolenoid(PneumaticsModuleType moduleType, int forwardChannel, int reverseChannel);
    public IDoubleSolenoid getDoubleSolenoid(int module, PneumaticsModuleType moduleType, int forwardChannel, int reverseChannel);
    public IEncoder getEncoder(int channelA, int channelB);
    public ICANCoder getCANCoder(int deviceNumber);
    public ICANCoder getCANCoder(int deviceNumber, String canbus);
    public IJoystick getJoystick(int port);
    public IMotor getTalon(int channel);
    public IMotor getVictor(int channel);
    public IServo getServo(int channel);
    public IPowerDistribution getPowerDistribution();
    public IPowerDistribution getPowerDistribution(int module, PowerDistributionModuleType moduleType);
    public IRelay getRelay(int channel);
    public IRelay getRelay(int channel, RelayDirection direction);
    public ISolenoid getSolenoid(PneumaticsModuleType moduleType, int channel);
    public ISolenoid getSolenoid(int module, PneumaticsModuleType moduleType, int channel);
    public INavx getNavx();
    public IPigeonIMU getPigeonIMU(int deviceNumber);
    public IPigeon2 getPigeon2(int deviceNumber);
    public IPigeon2 getPigeon2(int deviceNumber, String canbus);
    public ICANdle getCANdle(int deviceNumber);
    public ICANdle getCANdle(int deviceNumber, String canbus);
    public IDriverStation getDriverStation();
    public INetworkTableProvider getNetworkTableProvider();
    public IPathPlanner getPathPlanner();
    public IPreferences getPreferences();
}
