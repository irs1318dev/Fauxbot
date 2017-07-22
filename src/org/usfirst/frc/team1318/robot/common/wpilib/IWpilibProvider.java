package org.usfirst.frc.team1318.robot.common.wpilib;

public interface IWpilibProvider
{
    public IAnalogInput getAnalogInput(int channel);
    public ICANTalon getCANTalon(int deviceNumber);
    public ICompressor getCompressor();
    public ICompressor getCompressor(int module);
    public IDigitalInput getDigitalInput(int channel);
    public IDoubleSolenoid getDoubleSolenoid(int forwardChannel, int reverseChannel);
    public IDoubleSolenoid getDoubleSolenoid(int module, int forwardChannel, int reverseChannel);
    public IEncoder getEncoder(int channelA, int channelB);
    public IJoystick getJoystick(int port);
    public IMotor getTalon(int channel);
    public IMotor getVictor(int channel);
    public IPowerDistributionPanel getPDP();
    public IPowerDistributionPanel getPDP(int module);
    public IRelay getRelay(int channel);
    public IRelay getRelay(int channel, RelayDirection direction);
    public ISolenoid getSolenoid(int channel);
    public ISolenoid getSolenoid(int module, int channel);
}
