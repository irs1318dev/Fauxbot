package frc.lib.robotprovider;

import javax.inject.Inject;
import javax.inject.Singleton;

import frc.robot.simulation.SimulatorBase;

@Singleton
public class FauxbotProvider implements IRobotProvider
{
    private final SimulatorBase simulator;

    @Inject
    public FauxbotProvider(SimulatorBase simulator)
    {
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
    public IDutyCycle getDutyCycle(int channel)
    {
        return new FauxbotDutyCycle(channel);
    }

    @Override
    public IDutyCycleEncoder getDutyCycleEncoder(int channel)
    {
        return new FauxbotDutyCycleEncoder(channel);
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
    public ITalonFX getTalonFX(int deviceNumber, String canbus)
    {
        return new FauxbotTalonFX(deviceNumber, this.simulator);
    }

    @Override
    public IVictorSPX getVictorSPX(int deviceNumber)
    {
        return new FauxbotVictorSPX(deviceNumber);
    }

    @Override
    public ISparkMax getSparkMax(int deviceId, SparkMotorType motorType)
    {
        return new FauxbotSparkMax(deviceId, motorType);
    }

    @Override
    public ISparkFlex getSparkFlex(int deviceId, SparkMotorType motorType)
    {
        return new FauxbotSparkFlex(deviceId, motorType);
    }

    @Override
    public ICompressor getCompressor(PneumaticsModuleType moduleType)
    {
        return new FauxbotCompressor(moduleType);
    }

    @Override
    public ICompressor getCompressor(int module, PneumaticsModuleType moduleType)
    {
        return new FauxbotCompressor(module, moduleType);
    }

    @Override
    public IDoubleSolenoid getDoubleSolenoid(PneumaticsModuleType moduleType, int forwardChannel, int reverseChannel)
    {
        return new FauxbotDoubleSolenoid(moduleType, forwardChannel, reverseChannel);
    }

    @Override
    public IDoubleSolenoid getDoubleSolenoid(int module, PneumaticsModuleType moduleType, int forwardChannel, int reverseChannel)
    {
        return new FauxbotDoubleSolenoid(module, moduleType, forwardChannel, reverseChannel);
    }

    @Override
    public IEncoder getEncoder(int channelA, int channelB)
    {
        return new FauxbotEncoder(channelA, channelB);
    }

    @Override
    public ICANCoder getCANCoder(int deviceNumber)
    {
        return new FauxbotCANCoder(deviceNumber);
    }

    @Override
    public ICANCoder getCANCoder(int deviceNumber, String canbus)
    {
        return new FauxbotCANCoder(deviceNumber);
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
    public IPowerDistribution getPowerDistribution()
    {
        return new FauxbotPowerDistribution();
    }

    @Override
    public IPowerDistribution getPowerDistribution(int module, PowerDistributionModuleType moduleType)
    {
        return new FauxbotPowerDistribution(module, moduleType);
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
    public ISolenoid getSolenoid(PneumaticsModuleType moduleType, int channel)
    {
        return new FauxbotSolenoid(moduleType, channel);
    }

    @Override
    public ISolenoid getSolenoid(int module, PneumaticsModuleType moduleType, int channel)
    {
        return new FauxbotSolenoid(module, moduleType, channel);
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
    public IPigeon2 getPigeon2(int deviceNumber)
    {
        return new FauxbotPigeon2(deviceNumber);
    }

    @Override
    public IPigeon2 getPigeon2(int deviceNumber, String canbus)
    {
        return new FauxbotPigeon2(deviceNumber);
    }

    @Override
    public ICANdle getCANdle(int deviceNumber)
    {
        return new FauxbotCANdle(deviceNumber);
    }

    @Override
    public ICANdle getCANdle(int deviceNumber, String canbus)
    {
        return new FauxbotCANdle(deviceNumber);
    }

    @Override
    public IDriverStation getDriverStation()
    {
        return FauxbotDriverStation.Instance;
    }

    @Override
    public INetworkTableProvider getNetworkTableProvider()
    {
        return new FauxbotNetworkTableProvider();
    }

    @Override
    public IPathPlanner getPathPlanner()
    {
        return new FauxbotPathPlanner();
    }

    @Override
    public IPreferences getPreferences()
    {
        return new FauxbotPreferences();
    }
}
