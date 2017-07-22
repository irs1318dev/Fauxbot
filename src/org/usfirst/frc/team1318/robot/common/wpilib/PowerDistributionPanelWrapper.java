package org.usfirst.frc.team1318.robot.common.wpilib;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class PowerDistributionPanelWrapper implements IPowerDistributionPanel
{
    private final PowerDistributionPanel wrappedObject;

    public PowerDistributionPanelWrapper()
    {
        this.wrappedObject = new PowerDistributionPanel();
    }

    public PowerDistributionPanelWrapper(int module)
    {
        this.wrappedObject = new PowerDistributionPanel(module);
    }

    public double getBatteryVoltage()
    {
        return this.wrappedObject.getVoltage();
    }

    public double getCurrent(int pdpChannel)
    {
        return this.wrappedObject.getCurrent(pdpChannel);
    }
}
