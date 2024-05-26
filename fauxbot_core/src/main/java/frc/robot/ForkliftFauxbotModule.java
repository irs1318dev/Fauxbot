package frc.robot;

import frc.robot.simulation.*;

public class ForkliftFauxbotModule extends FauxbotModule
{
    @Override
    protected void configure()
    {
        super.configure();

        this.bind(SimulatorBase.class).to(ForkliftSimulator.class);
    }
}
