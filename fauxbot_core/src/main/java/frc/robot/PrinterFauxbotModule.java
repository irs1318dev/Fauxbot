package frc.robot;

import frc.robot.simulation.*;

public class PrinterFauxbotModule extends FauxbotModule
{
    @Override
    protected void configure()
    {
        super.configure();

        this.bind(SimulatorBase.class).to(PrinterSimulator.class);
    }
}
