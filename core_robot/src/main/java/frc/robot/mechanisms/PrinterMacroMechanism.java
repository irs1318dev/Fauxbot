package frc.robot.mechanisms;

import frc.lib.driver.IDriver;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.ITimer;
import frc.robot.driver.AnalogOperation;

public class PrinterMacroMechanism extends PrinterMechanism{

    public PrinterMacroMechanism(IDriver driver, IRobotProvider provider, ITimer timer) {
        super(driver, provider, timer);
    }
    
}
