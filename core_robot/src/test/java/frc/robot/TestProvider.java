package frc.robot;

import static org.mockito.Mockito.mock;

import java.util.HashMap;

import frc.lib.robotprovider.*;

public class TestProvider implements IRobotProvider
{
    private INavx mockNavx;
    private IPigeonIMU mockPigeon;
    private IPigeon2 mockPigeon2;
    private HashMap<Integer, IAnalogInput> analogInputMap = new HashMap<Integer, IAnalogInput>();
    private HashMap<Integer, IDigitalInput> digitalInputMap = new HashMap<Integer, IDigitalInput>();
    private HashMap<Integer, IDigitalOutput> digitalOutputMap = new HashMap<Integer, IDigitalOutput>();
    private HashMap<Integer, ICounter> counterMap = new HashMap<Integer, ICounter>();
    private HashMap<Integer, IDutyCycleEncoder> dutyCycleEncoderMap = new HashMap<Integer, IDutyCycleEncoder>();
    private HashMap<Integer, IDutyCycle> dutyCycleMap = new HashMap<Integer, IDutyCycle>();
    private HashMap<Integer, ITalonSRX> talonSrxMap = new HashMap<Integer, ITalonSRX>();
    private HashMap<Integer, ITalonFX> talonFxMap = new HashMap<Integer, ITalonFX>();
    private HashMap<Integer, IVictorSPX> victorSpxMap = new HashMap<Integer, IVictorSPX>();
    private HashMap<Integer, ISparkMax> sparkMaxMap = new HashMap<Integer, ISparkMax>();
    private HashMap<Integer, ISparkFlex> sparkFlexMap = new HashMap<Integer, ISparkFlex>();
    private HashMap<Integer, ICompressor> compressorMap = new HashMap<Integer, ICompressor>();
    private HashMap<Integer, HashMap<Integer, IDoubleSolenoid>> doubleSolenoidModuleMap = new HashMap<Integer, HashMap<Integer, IDoubleSolenoid>>();
    private HashMap<Integer, IEncoder> encoderMap = new HashMap<Integer, IEncoder>();
    private HashMap<Integer, ICANCoder> cancoderMap = new HashMap<Integer, ICANCoder>();
    private HashMap<Integer, IJoystick> joystickMap = new HashMap<Integer, IJoystick>();
    private HashMap<Integer, IMotor> motorMap = new HashMap<Integer, IMotor>();
    private HashMap<Integer, IServo> servoMap = new HashMap<Integer, IServo>();
    private HashMap<Integer, IPowerDistribution> pdpMap = new HashMap<Integer, IPowerDistribution>();
    private HashMap<Integer, IRelay> relayMap = new HashMap<Integer, IRelay>();
    private HashMap<Integer, HashMap<Integer, ISolenoid>> solenoidModuleMap = new HashMap<Integer, HashMap<Integer, ISolenoid>>();

    @Override
    public IAnalogInput getAnalogInput(int channel)
    {
        if (!this.analogInputMap.containsKey(channel))
        {
            this.analogInputMap.put(channel, mock(IAnalogInput.class));
        }

        return this.analogInputMap.get(channel);
    }

    @Override
    public IDigitalInput getDigitalInput(int channel)
    {
        if (!this.digitalInputMap.containsKey(channel))
        {
            this.digitalInputMap.put(channel, mock(IDigitalInput.class));
        }

        return this.digitalInputMap.get(channel);
    }

    @Override
    public IDigitalOutput getDigitalOutput(int channel)
    {
        if (!this.digitalOutputMap.containsKey(channel))
        {
            this.digitalOutputMap.put(channel, mock(IDigitalOutput.class));
        }

        return this.digitalOutputMap.get(channel);
    }

    @Override
    public ICounter getCounter(int channel)
    {
        if (!this.counterMap.containsKey(channel))
        {
            this.counterMap.put(channel, mock(ICounter.class));
        }

        return this.counterMap.get(channel);
    }

    @Override
    public IDutyCycle getDutyCycle(int channel)
    {
        if (!this.dutyCycleMap.containsKey(channel))
        {
            this.dutyCycleMap.put(channel, mock(IDutyCycle.class));
        }

        return this.dutyCycleMap.get(channel);
    }

    @Override
    public IDutyCycleEncoder getDutyCycleEncoder(int channel)
    {
        if (!this.dutyCycleEncoderMap.containsKey(channel))
        {
            this.dutyCycleEncoderMap.put(channel, mock(IDutyCycleEncoder.class));
        }

        return this.dutyCycleEncoderMap.get(channel);
    }

    @Override
    public ITalonSRX getTalonSRX(int deviceNumber)
    {
        if (!this.talonSrxMap.containsKey(deviceNumber))
        {
            this.talonSrxMap.put(deviceNumber, mock(ITalonSRX.class));
        }

        return this.talonSrxMap.get(deviceNumber);
    }

    @Override
    public ITalonFX getTalonFX(int deviceNumber)
    {
        if (!this.talonFxMap.containsKey(deviceNumber))
        {
            this.talonFxMap.put(deviceNumber, mock(ITalonFX.class));
        }

        return this.talonFxMap.get(deviceNumber);
    }

    @Override
    public ITalonFX getTalonFX(int deviceNumber, String canbus)
    {
        if (!this.talonFxMap.containsKey(deviceNumber))
        {
            this.talonFxMap.put(deviceNumber, mock(ITalonFX.class));
        }

        return this.talonFxMap.get(deviceNumber);
    }

    @Override
    public IVictorSPX getVictorSPX(int deviceNumber)
    {
        if (!this.victorSpxMap.containsKey(deviceNumber))
        {
            this.victorSpxMap.put(deviceNumber, mock(IVictorSPX.class));
        }

        return this.victorSpxMap.get(deviceNumber);
    }

    @Override
    public ISparkMax getSparkMax(int deviceID, SparkMotorType motorType)
    {
        if (!this.sparkMaxMap.containsKey(deviceID))
        {
            this.sparkMaxMap.put(deviceID, mock(ISparkMax.class));
        }

        return this.sparkMaxMap.get(deviceID);
    }

    @Override
    public ISparkFlex getSparkFlex(int deviceID, SparkMotorType motorType)
    {
        if (!this.sparkFlexMap.containsKey(deviceID))
        {
            this.sparkFlexMap.put(deviceID, mock(ISparkFlex.class));
        }

        return this.sparkFlexMap.get(deviceID);
    }

    @Override
    public ICompressor getCompressor(PneumaticsModuleType moduleType)
    {
        return this.getCompressor(0, moduleType);
    }

    @Override
    public ICompressor getCompressor(int module, PneumaticsModuleType moduleType)
    {
        if (!this.compressorMap.containsKey(module))
        {
            this.compressorMap.put(module, mock(ICompressor.class));
        }

        return this.compressorMap.get(module);
    }

    @Override
    public IDoubleSolenoid getDoubleSolenoid(PneumaticsModuleType moduleType, int forwardChannel, int reverseChannel)
    {
        return this.getDoubleSolenoid(0, moduleType, forwardChannel, reverseChannel);
    }

    @Override
    public IDoubleSolenoid getDoubleSolenoid(int module, PneumaticsModuleType moduleType, int forwardChannel, int reverseChannel)
    {
        if (!this.doubleSolenoidModuleMap.containsKey(module))
        {
            this.doubleSolenoidModuleMap.put(module, new HashMap<Integer, IDoubleSolenoid>());
        }

        HashMap<Integer, IDoubleSolenoid> doubleSolenoidMap = this.doubleSolenoidModuleMap.get(module);
        if (!doubleSolenoidMap.containsKey(forwardChannel))
        {
            doubleSolenoidMap.put(forwardChannel, mock(IDoubleSolenoid.class));
        }

        return doubleSolenoidMap.get(forwardChannel);
    }

    @Override
    public IEncoder getEncoder(int channelA, int channelB)
    {
        if (!this.encoderMap.containsKey(channelA))
        {
            this.encoderMap.put(channelA, mock(IEncoder.class));
        }

        return this.encoderMap.get(channelA);
    }

    @Override
    public ICANCoder getCANCoder(int deviceNumber)
    {
        if (!this.cancoderMap.containsKey(deviceNumber))
        {
            this.cancoderMap.put(deviceNumber, mock(ICANCoder.class));
        }

        return this.cancoderMap.get(deviceNumber);
    }

    @Override
    public ICANCoder getCANCoder(int deviceNumber, String canbus)
    {
        if (!this.cancoderMap.containsKey(deviceNumber))
        {
            this.cancoderMap.put(deviceNumber, mock(ICANCoder.class));
        }

        return this.cancoderMap.get(deviceNumber);
    }

    @Override
    public IJoystick getJoystick(int port)
    {
        if (!this.joystickMap.containsKey(port))
        {
            this.joystickMap.put(port, mock(IJoystick.class));
        }

        return this.joystickMap.get(port);
    }

    @Override
    public IMotor getTalon(int channel)
    {
        return this.getVictor(channel);
    }

    @Override
    public IMotor getVictor(int channel)
    {
        if (!this.motorMap.containsKey(channel))
        {
            this.motorMap.put(channel, mock(IMotor.class));
        }

        return this.motorMap.get(channel);
    }

    @Override
    public IServo getServo(int channel)
    {
        if (!this.servoMap.containsKey(channel))
        {
            this.servoMap.put(channel, mock(IServo.class));
        }

        return this.servoMap.get(channel);
    }

    @Override
    public IPowerDistribution getPowerDistribution()
    {
        return this.getPowerDistribution(0, PowerDistributionModuleType.PowerDistributionHub);
    }

    @Override
    public IPowerDistribution getPowerDistribution(int module, PowerDistributionModuleType moduleType)
    {
        if (!this.pdpMap.containsKey(module))
        {
            this.pdpMap.put(module, mock(IPowerDistribution.class));
        }

        return this.pdpMap.get(module);
    }

    @Override
    public IRelay getRelay(int channel)
    {
        if (!this.relayMap.containsKey(channel))
        {
            this.relayMap.put(channel, mock(IRelay.class));
        }

        return this.relayMap.get(channel);
    }

    @Override
    public IRelay getRelay(int channel, RelayDirection direction)
    {
        return this.getRelay(channel);
    }

    @Override
    public ISolenoid getSolenoid(PneumaticsModuleType moduleType, int channel)
    {
        return this.getSolenoid(0, moduleType, channel);
    }

    @Override
    public ISolenoid getSolenoid(int module, PneumaticsModuleType moduleType, int channel)
    {
        if (!this.solenoidModuleMap.containsKey(module))
        {
            this.solenoidModuleMap.put(module, new HashMap<Integer, ISolenoid>());
        }

        HashMap<Integer, ISolenoid> solenoidMap = this.solenoidModuleMap.get(module);
        if (!solenoidMap.containsKey(channel))
        {
            solenoidMap.put(channel, mock(ISolenoid.class));
        }

        return solenoidMap.get(channel);
    }

    @Override
    public INavx getNavx()
    {
        return this.mockNavx;
    }

    @Override
    public IPigeonIMU getPigeonIMU(int deviceNumber)
    {
        return this.mockPigeon;
    }

    @Override
    public IPigeon2 getPigeon2(int deviceNumber)
    {
        return this.mockPigeon2;
    }

    @Override
    public IPigeon2 getPigeon2(int deviceNumber, String canbus)
    {
        return this.mockPigeon2;
    }

    @Override
    public ICANdle getCANdle(int deviceNumber)
    {
        return mock(ICANdle.class);
    }

    @Override
    public ICANdle getCANdle(int deviceNumber, String canbus)
    {
        return mock(ICANdle.class);
    }
    @Override
    public IDriverStation getDriverStation()
    {
        return null;
    }

    @Override
    public INetworkTableProvider getNetworkTableProvider()
    {
        return null;
    }

    @Override
    public IPathPlanner getPathPlanner()
    {
        return null;
    }

    @Override
    public IPreferences getPreferences()
    {
        return null;
    }

    public void setAnalogInput(int channel, IAnalogInput value)
    {
        this.analogInputMap.put(channel, value);
    }

    public void setDigitalInput(int channel, IDigitalInput value)
    {
        this.digitalInputMap.put(channel, value);
    }

    public void setDigitalOutput(int channel, IDigitalOutput value)
    {
        this.digitalOutputMap.put(channel, value);
    }

    public void setCounter(int channel, ICounter value)
    {
        this.counterMap.put(channel, value);
    }

    public void setDutyCycle(int channel, IDutyCycle value)
    {
        this.dutyCycleMap.put(channel, value);
    }

    public void setTalonSRX(int deviceNumber, ITalonSRX value)
    {
        this.talonSrxMap.put(deviceNumber, value);
    }

    public void setTalonFX(int deviceNumber, ITalonFX value)
    {
        this.talonFxMap.put(deviceNumber, value);
    }

    public void setVictorSPX(int deviceNumber, IVictorSPX value)
    {
        this.victorSpxMap.put(deviceNumber, value);
    }

    public void setSparkMax(int deviceID, SparkMotorType motorType, ISparkMax value)
    {
        this.sparkMaxMap.put(deviceID, value);
    }

    public void setCompressor(ICompressor value)
    {
        this.setCompressor(0, value);
    }

    public void setCompressor(int module, ICompressor value)
    {
        this.compressorMap.put(module, value);
    }

    public void setDoubleSolenoid(int forwardChannel, int reverseChannel, IDoubleSolenoid value)
    {
        this.setDoubleSolenoid(0, forwardChannel, reverseChannel, value);
    }

    public void setDoubleSolenoid(int module, int forwardChannel, int reverseChannel, IDoubleSolenoid value)
    {
        if (!this.doubleSolenoidModuleMap.containsKey(module))
        {
            this.doubleSolenoidModuleMap.put(module, new HashMap<Integer, IDoubleSolenoid>());
        }

        HashMap<Integer, IDoubleSolenoid> doubleSolenoidMap = this.doubleSolenoidModuleMap.get(module);
        doubleSolenoidMap.put(forwardChannel, value);
    }

    public void setEncoder(int channelA, int channelB, IEncoder value)
    {
        this.encoderMap.put(channelA, value);
    }

    public void setCANCoder(int deviceNumber, IEncoder value)
    {
        this.encoderMap.put(deviceNumber, value);
    }

    public void setJoystick(int port, IJoystick value)
    {
        this.joystickMap.put(port, value);
    }

    public void setTalon(int channel, IMotor value)
    {
        this.setVictor(channel, value);
    }

    public void setVictor(int channel, IMotor value)
    {
        this.motorMap.put(channel, value);
    }

    public void setServo(int channel, IServo value)
    {
        this.servoMap.put(channel, value);
    }

    public void setPDP(IPowerDistribution value)
    {
        this.setPDP(0, value);
    }

    public void setPDP(int module, IPowerDistribution value)
    {
        this.pdpMap.put(module, value);
    }

    public void setRelay(int channel, IRelay value)
    {
        this.relayMap.put(channel, value);
    }

    public void setRelay(int channel, RelayDirection direction, IRelay value)
    {
        this.setRelay(channel, value);
    }

    public void setSolenoid(int channel, ISolenoid value)
    {
        this.setSolenoid(0, channel, value);
    }

    public void setSolenoid(int module, int channel, ISolenoid value)
    {
        if (!this.solenoidModuleMap.containsKey(module))
        {
            this.solenoidModuleMap.put(module, new HashMap<Integer, ISolenoid>());
        }

        HashMap<Integer, ISolenoid> solenoidMap = this.solenoidModuleMap.get(module);
        solenoidMap.put(channel, value);
    }

    public void setNavx(INavx value)
    {
        this.mockNavx = value;
    }

    public void setPigeon(IPigeonIMU value)
    {
        this.mockPigeon = value;
    }

    public void setPigeon2(IPigeon2 value)
    {
        this.mockPigeon2 = value;
    }
}
