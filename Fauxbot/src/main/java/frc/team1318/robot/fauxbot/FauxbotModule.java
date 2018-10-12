package frc.team1318.robot.fauxbot;

import frc.team1318.robot.fauxbot.simulation.*;

import com.google.inject.AbstractModule;

public class FauxbotModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        //this.bind(IRealWorldSimulator.class).to(GarageDoorSimulator.class);
        //this.bind(IRealWorldSimulator.class).to(ElevatorSimulator.class);
        this.bind(IRealWorldSimulator.class).to(ForkliftSimulator.class);
    }
}
