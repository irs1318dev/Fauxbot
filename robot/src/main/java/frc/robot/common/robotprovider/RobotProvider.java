package frc.robot.common.robotprovider;

import javax.inject.Singleton;

import frc.robot.vision.VisionCalculations;

@Singleton
public class RobotProvider implements IRobotProvider
{
    @Override
    public IAnalogInput getAnalogInput(int channel)
    {
        return new AnalogInputWrapper(channel);
    }

    @Override
    public ITalonSRX getTalonSRX(int deviceNumber)
    {
        return new TalonSRXWrapper(deviceNumber);
    }

    @Override
    public IVictorSPX getVictorSPX(int deviceNumber)
    {
        return new VictorSPXWrapper(deviceNumber);
    }

    @Override
    public ICompressor getCompressor()
    {
        return new CompressorWrapper();
    }

    @Override
    public ICompressor getCompressor(int module)
    {
        return new CompressorWrapper(module);
    }

    @Override
    public IDigitalInput getDigitalInput(int channel)
    {
        return new DigitalInputWrapper(channel);
    }

    @Override
    public IDoubleSolenoid getDoubleSolenoid(int forwardChannel, int reverseChannel)
    {
        return new DoubleSolenoidWrapper(forwardChannel, reverseChannel);
    }

    @Override
    public IDoubleSolenoid getDoubleSolenoid(int module, int forwardChannel, int reverseChannel)
    {
        return new DoubleSolenoidWrapper(module, forwardChannel, reverseChannel);
    }

    @Override
    public IEncoder getEncoder(int channelA, int channelB)
    {
        return new EncoderWrapper(channelA, channelB);
    }

    @Override
    public IJoystick getJoystick(int port)
    {
        return new JoystickWrapper(port);
    }

    @Override
    public IMotor getTalon(int channel)
    {
        return new TalonWrapper(channel);
    }

    @Override
    public IMotor getVictor(int channel)
    {
        return new VictorWrapper(channel);
    }

    @Override
    public IServo getServo(int channel)
    {
        return new ServoWrapper(channel);
    }

    @Override
    public IPowerDistributionPanel getPDP()
    {
        return new PowerDistributionPanelWrapper();
    }

    @Override
    public IPowerDistributionPanel getPDP(int module)
    {
        return new PowerDistributionPanelWrapper(module);
    }

    @Override
    public IRelay getRelay(int channel)
    {
        return new RelayWrapper(channel);
    }

    @Override
    public IRelay getRelay(int channel, RelayDirection direction)
    {
        return new RelayWrapper(channel, direction);
    }

    @Override
    public ISolenoid getSolenoid(int channel)
    {
        return new SolenoidWrapper(channel);
    }

    @Override
    public ISolenoid getSolenoid(int module, int channel)
    {
        return new SolenoidWrapper(module, channel);
    }

    @Override
    public INavx getNavx()
    {
        return new NavxWrapper();
    }

    @Override
    public IVideoStream getMJPEGStream(String name, int width, int height)
    {
        return new VideoStreamWrapper(name, width, height);
    }

    @Override
    public IUsbCamera getUsbCamera(String name, int dev)
    {
        return new UsbCameraWrapper(name, dev);
    }

    @Override
    public IDriverStation getDriverStation()
    {
        return new DriverStationWrapper();
    }

    @Override
    public IOpenCVProvider getOpenCVProvider()
    {
        return new OpenCVProvider();
    }

    @Override
    public INetworkTableProvider getNetworkTableProvider()
    {
        return new NetworkTableProvider();
    }

    @Override
    public VisionCalculations getVisionCalculations()
    {
        return new VisionCalculations();
    }
    
    public <V> ISendableChooser<V> getSendableChooser()
    {
        return new SendableChooserWrapper<V>();
    }
}
