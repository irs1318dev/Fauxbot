package frc.robot.common.robotprovider;

import javax.inject.Inject;
import javax.inject.Singleton;

import frc.robot.IRealWorldSimulator;

@Singleton
public class FauxbotProvider implements IRobotProvider
{
    private final IRealWorldSimulator simulator;

    @Inject
    public FauxbotProvider(IRealWorldSimulator simulator)
    {
        nu.pattern.OpenCV.loadShared();
        this.simulator = simulator;
    }

    @Override
    public IAnalogInput getAnalogInput(int channel)
    {
        return new FauxbotAnalogInput(channel);
    }

    @Override
    public IDigitalInput getDigitalInput(int channel)
    {
        return new FauxbotDigitalInput(channel);
    }

    @Override
    public IDigitalOutput getDigitalOutput(int channel)
    {
        return new FauxbotDigitalOutput(channel);
    }

    @Override
    public ICounter getCounter(int channel)
    {
        return new FauxbotCounter(channel);
    }

    @Override
    public ITalonSRX getTalonSRX(int deviceNumber)
    {
        return new FauxbotTalonSRX(deviceNumber, this.simulator);
    }

    @Override
    public ITalonFX getTalonFX(int deviceNumber)
    {
        return new FauxbotTalonFX(deviceNumber, this.simulator);
    }

    @Override
    public IVictorSPX getVictorSPX(int deviceNumber)
    {
        return new FauxbotVictorSPX(deviceNumber);
    }

    @Override
    public ISparkMax getSparkMax(int deviceID, SparkMaxMotorType motorType)
    {
        return new FauxbotSparkMax(deviceID, motorType);
    }

    @Override
    public ICompressor getCompressor()
    {
        return new FauxbotCompressor();
    }

    @Override
    public ICompressor getCompressor(int module)
    {
        return new FauxbotCompressor(module);
    }

    @Override
    public IDoubleSolenoid getDoubleSolenoid(int forwardChannel, int reverseChannel)
    {
        return new FauxbotDoubleSolenoid(forwardChannel, reverseChannel);
    }

    @Override
    public IDoubleSolenoid getDoubleSolenoid(int module, int forwardChannel, int reverseChannel)
    {
        return new FauxbotDoubleSolenoid(module, forwardChannel, reverseChannel);
    }

    @Override
    public IEncoder getEncoder(int channelA, int channelB)
    {
        return new FauxbotEncoder(channelA, channelB);
    }

    @Override
    public IJoystick getJoystick(int port)
    {
        return new FauxbotJoystick(port);
    }

    @Override
    public IMotor getTalon(int channel)
    {
        return new FauxbotTalon(channel);
    }

    @Override
    public IMotor getVictor(int channel)
    {
        return new FauxbotVictor(channel);
    }

    @Override
    public IServo getServo(int channel)
    {
        return new FauxbotServo(channel);
    }

    @Override
    public IPowerDistributionPanel getPDP()
    {
        return new FauxbotPowerDistributionPanel();
    }

    @Override
    public IPowerDistributionPanel getPDP(int module)
    {
        return new FauxbotPowerDistributionPanel(module);
    }

    @Override
    public IRelay getRelay(int channel)
    {
        return new FauxbotRelay(channel);
    }

    @Override
    public IRelay getRelay(int channel, RelayDirection direction)
    {
        return new FauxbotRelay(channel, direction);
    }

    @Override
    public ISolenoid getSolenoid(int channel)
    {
        return new FauxbotSolenoid(channel);
    }

    @Override
    public ISolenoid getSolenoid(int module, int channel)
    {
        return new FauxbotSolenoid(module, channel);
    }

    @Override
    public INavx getNavx()
    {
        return new FauxbotNavx();
    }

    @Override
    public IPigeonIMU getPigeonIMU(int deviceNumber)
    {
        return new FauxbotPigeonIMU(deviceNumber);
    }

    @Override
    public IVideoStream getMJPEGStream(String name, int width, int height)
    {
        return new FauxbotVideoStream();
    }

    @Override
    public IUsbCamera getUsbCamera(String name, int dev)
    {
        return new FauxbotUsbCamera();
    }

    @Override
    public IDriverStation getDriverStation()
    {
        return FauxbotDriverStation.Instance;
    }

    @Override
    public IOpenCVProvider getOpenCVProvider()
    {
        return new OpenCVProvider();
    }

    @Override
    public INetworkTableProvider getNetworkTableProvider()
    {
        return new FauxbotNetworkTableProvider();
    }

    @Override
    public IPreferences getPreferences()
    {
        return new FauxbotPreferences();
    }
}
