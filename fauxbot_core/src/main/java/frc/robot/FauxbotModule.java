package frc.robot;

import frc.lib.robotprovider.*;

public abstract class FauxbotModule extends FauxbotCommonModule
{
    @Override
    protected void configure()
    {
        super.configure();

        this.bind(IRobotProvider.class).to(FauxbotProvider.class);
    }
}
