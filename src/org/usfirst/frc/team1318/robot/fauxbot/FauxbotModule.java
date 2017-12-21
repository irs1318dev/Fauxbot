package org.usfirst.frc.team1318.robot.fauxbot;

import org.usfirst.frc.team1318.robot.fauxbot.simulation.DriveTrainSimulator;
import org.usfirst.frc.team1318.robot.fauxbot.simulation.ElevatorSimulator;
import org.usfirst.frc.team1318.robot.fauxbot.simulation.GarageDoorSimulator;

import com.google.inject.AbstractModule;

public class FauxbotModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        this.bind(IRealWorldSimulator.class).to(GarageDoorSimulator.class);
        
    }
}
