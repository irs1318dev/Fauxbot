package frc.robot;

import javax.inject.Singleton;

import frc.robot.common.*;
import frc.robot.common.robotprovider.*;
import frc.robot.driver.*;
import frc.robot.driver.common.IButtonMap;
import frc.robot.simulation.*;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;

public class GarageDoorFauxbotModule extends FauxbotModule
{
    @Override
    protected void configure()
    {
        super.configure();

        this.bind(IRealWorldSimulator.class).to(GarageDoorSimulator.class);
    }
}
