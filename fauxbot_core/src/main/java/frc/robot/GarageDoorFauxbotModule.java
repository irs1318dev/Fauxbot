package frc.robot;

import frc.robot.simulation.*;

public class GarageDoorFauxbotModule extends FauxbotModule
{
    @Override
    protected void configure()
    {
        super.configure();

        this.bind(SimulatorBase.class).to(GarageDoorSimulator.class);
    }
}
