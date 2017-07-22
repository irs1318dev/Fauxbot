package org.usfirst.frc.team1318.robot;

import java.util.HashMap;

import org.usfirst.frc.team1318.robot.common.wpilib.IAnalogInput;
import org.usfirst.frc.team1318.robot.common.wpilib.ICANTalon;
import org.usfirst.frc.team1318.robot.common.wpilib.ICompressor;
import org.usfirst.frc.team1318.robot.common.wpilib.IDigitalInput;
import org.usfirst.frc.team1318.robot.common.wpilib.IDoubleSolenoid;
import org.usfirst.frc.team1318.robot.common.wpilib.IEncoder;
import org.usfirst.frc.team1318.robot.common.wpilib.IJoystick;
import org.usfirst.frc.team1318.robot.common.wpilib.IMotor;
import org.usfirst.frc.team1318.robot.common.wpilib.IPowerDistributionPanel;
import org.usfirst.frc.team1318.robot.common.wpilib.IRelay;
import org.usfirst.frc.team1318.robot.common.wpilib.ISolenoid;
import org.usfirst.frc.team1318.robot.common.wpilib.IWpilibProvider;
import org.usfirst.frc.team1318.robot.common.wpilib.RelayDirection;

import static org.mockito.Mockito.mock;

public class TestWpilibProvider implements IWpilibProvider
{
    private HashMap<Integer, IAnalogInput> analogInputMap = new HashMap<Integer, IAnalogInput>();
    private HashMap<Integer, ICANTalon> canTalonMap = new HashMap<Integer, ICANTalon>();
    private HashMap<Integer, ICompressor> compressorMap = new HashMap<Integer, ICompressor>();
    private HashMap<Integer, IDigitalInput> digitalInputMap = new HashMap<Integer, IDigitalInput>();
    private HashMap<Integer, HashMap<Integer, IDoubleSolenoid>> doubleSolenoidModuleMap = new HashMap<Integer, HashMap<Integer, IDoubleSolenoid>>();
    private HashMap<Integer, IEncoder> encoderMap = new HashMap<Integer, IEncoder>();
    private HashMap<Integer, IJoystick> joystickMap = new HashMap<Integer, IJoystick>();
    private HashMap<Integer, IMotor> motorMap = new HashMap<Integer, IMotor>();
    private HashMap<Integer, IPowerDistributionPanel> pdpMap = new HashMap<Integer, IPowerDistributionPanel>();
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
    public ICANTalon getCANTalon(int deviceNumber)
    {
        if (!this.canTalonMap.containsKey(deviceNumber))
        {
            this.canTalonMap.put(deviceNumber, mock(ICANTalon.class));
        }

        return this.canTalonMap.get(deviceNumber);
    }

    @Override
    public ICompressor getCompressor()
    {
        return this.getCompressor(0);
    }

    @Override
    public ICompressor getCompressor(int module)
    {
        if (!this.compressorMap.containsKey(module))
        {
            this.compressorMap.put(module, mock(ICompressor.class));
        }

        return this.compressorMap.get(module);
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
    public IDoubleSolenoid getDoubleSolenoid(int forwardChannel, int reverseChannel)
    {
        return this.getDoubleSolenoid(0, forwardChannel, reverseChannel);
    }

    @Override
    public IDoubleSolenoid getDoubleSolenoid(int module, int forwardChannel, int reverseChannel)
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
    public IPowerDistributionPanel getPDP()
    {
        return this.getPDP(0);
    }

    @Override
    public IPowerDistributionPanel getPDP(int module)
    {
        if (!this.pdpMap.containsKey(module))
        {
            this.pdpMap.put(module, mock(IPowerDistributionPanel.class));
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
    public ISolenoid getSolenoid(int channel)
    {
        return this.getSolenoid(0, channel);
    }

    @Override
    public ISolenoid getSolenoid(int module, int channel)
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
}
