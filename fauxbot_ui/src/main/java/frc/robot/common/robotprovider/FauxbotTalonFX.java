package frc.robot.common.robotprovider;

import frc.robot.IRealWorldSimulator;

public class FauxbotTalonFX extends FauxbotTalonXBase implements ITalonFX
{
    public FauxbotTalonFX(int deviceNumber, IRealWorldSimulator simulator)
    {
        super(deviceNumber, simulator);
    }

    @Override
    public void setSupplyCurrentLimit(boolean enabled, double currentLimit, double triggerThresholdCurrent, double triggerThresholdTime)
    {
    }
}
