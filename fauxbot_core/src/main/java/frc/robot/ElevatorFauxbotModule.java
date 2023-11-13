package frc.robot;

import frc.robot.simulation.*;

public class ElevatorFauxbotModule extends FauxbotModule
{
    @Override
    protected void configure()
    {
        super.configure();

        this.bind(SimulatorBase.class).to(ElevatorSimulator.class);
    }
}
