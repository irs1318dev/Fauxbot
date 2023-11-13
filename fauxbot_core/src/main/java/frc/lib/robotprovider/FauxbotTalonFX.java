package frc.lib.robotprovider;

import frc.robot.simulation.SimulatorBase;

public class FauxbotTalonFX extends FauxbotTalonXBase implements ITalonFX
{
    public FauxbotTalonFX(int deviceNumber, SimulatorBase simulator)
    {
        super(deviceNumber, simulator);
    }

    @Override
    public void setSupplyCurrentLimit(boolean enabled, double currentLimit, double triggerThresholdCurrent, double triggerThresholdTime)
    {
    }

    @Override
    public void setInvert(TalonFXInvertType invertType)
    {
    }
}
