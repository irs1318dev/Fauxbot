package org.usfirst.frc.team1318.robot.fauxbot;

public interface IRealWorldSimulator
{
    public String getSensorName(int channel);
    public String getMotorName(int channel);
    public void update();
}
