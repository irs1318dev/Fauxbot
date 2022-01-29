package frc.robot.common.robotprovider;

public interface IRobotProvider
{
    public IAnalogInput getAnalogInput(int channel);
    public IDigitalInput getDigitalInput(int channel);
    public IDigitalOutput getDigitalOutput(int channel);
    public ICounter getCounter(int channel);
    public IDutyCycle getDutyCycle(int digitalInputChannel);
    public ITalonSRX getTalonSRX(int deviceNumber);
    public ITalonFX getTalonFX(int deviceNumber);
    public IVictorSPX getVictorSPX(int deviceNumber);
    public ISparkMax getSparkMax(int deviceID, SparkMaxMotorType motorType);
    public ICompressor getCompressor(PneumaticsModuleType moduleType);
    public ICompressor getCompressor(int module, PneumaticsModuleType moduleType);
    public IDoubleSolenoid getDoubleSolenoid(PneumaticsModuleType moduleType, int forwardChannel, int reverseChannel);
    public IDoubleSolenoid getDoubleSolenoid(int module, PneumaticsModuleType moduleType, int forwardChannel, int reverseChannel);
    public IEncoder getEncoder(int channelA, int channelB);
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
    public IVideoStream getMJPEGStream(String name, int width, int height);
    public IUsbCamera getUsbCamera(String name, int dev);
    public IDriverStation getDriverStation();
    public IOpenCVProvider getOpenCVProvider();
    public INetworkTableProvider getNetworkTableProvider();
    public IPreferences getPreferences();
}
