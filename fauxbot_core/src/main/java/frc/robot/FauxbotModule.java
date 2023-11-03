package frc.robot;

import javax.inject.Singleton;

import frc.lib.driver.IButtonMap;
import frc.lib.mechanisms.MechanismManager;
import frc.lib.robotprovider.*;
import frc.lib.driver.*;
import frc.robot.driver.*;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;

public abstract class FauxbotModule extends FauxbotCommonModule
{
    @Override
    protected void configure()
    {
        super.configure();

        this.bind(IRobotProvider.class).to(FauxbotProvider.class);
    }
}
