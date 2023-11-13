package frc.lib.robotprovider;

import frc.robot.simulation.SimulatorBase;

public class FauxbotTalonSRX extends FauxbotTalonXBase implements ITalonSRX
{
    public FauxbotTalonSRX(int deviceNumber, SimulatorBase simulator)
    {
        super(deviceNumber, simulator);
    }
}
