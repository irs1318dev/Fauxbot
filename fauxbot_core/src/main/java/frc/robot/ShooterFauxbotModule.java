package frc.robot;

import frc.robot.simulation.*;

public class ShooterFauxbotModule extends FauxbotModule
{
    @Override
    protected void configure()
    {
        super.configure();

        this.bind(SimulatorBase.class).to(ShooterSimulator.class);
    }
}
