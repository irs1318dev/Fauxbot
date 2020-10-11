package frc.robot.common.robotprovider;

import frc.robot.IRealWorldSimulator;

public class FauxbotTalonSRX extends FauxbotTalonXBase implements ITalonSRX
{
    public FauxbotTalonSRX(int deviceNumber, IRealWorldSimulator simulator)
    {
        super(deviceNumber, simulator);
    }
}
