package org.usfirst.frc.team1318.robot.fauxbot;

import javax.inject.Singleton;

import org.usfirst.frc.team1318.robot.fauxbot.simulation.GarageDoorSimulator;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;

public class FauxbotModule extends AbstractModule
{
    @Override
    protected void configure()
    {
    }

    @Singleton
    @Provides
    public IRealWorldSimulator getSimulator(Injector injector)
    {
        return injector.getInstance(GarageDoorSimulator.class);
    }
}
