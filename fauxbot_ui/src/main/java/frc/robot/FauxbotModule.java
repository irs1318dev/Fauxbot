package frc.robot;

import javax.inject.Singleton;

import frc.robot.common.*;
import frc.robot.common.robotprovider.*;
import frc.robot.driver.*;
import frc.robot.driver.common.*;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;

public abstract class FauxbotModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        this.bind(IDriver.class).to(Driver.class);
        this.bind(IRobotProvider.class).to(FauxbotProvider.class);
        this.bind(ITimer.class).to(FauxbotTimer.class);
        this.bind(IButtonMap.class).to(ButtonMap.class);
        this.bind(ISmartDashboardLogger.class).to(FauxbotSmartDashboardLogger.class);
        this.bind(IFile.class).to(FauxbotFile.class);
    }

    @Singleton
    @Provides
    public MechanismManager getMechanismManager(Injector injector)
    {
        return new MechanismManager(SettingsManager.getActiveMechanisms(injector));
    }
}
