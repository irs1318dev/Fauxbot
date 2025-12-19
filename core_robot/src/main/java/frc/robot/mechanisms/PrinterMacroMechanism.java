package frc.robot.mechanisms;

import java.util.EnumSet;

import com.google.inject.Injector;

import frc.lib.driver.IControlTask;
import frc.lib.driver.IDriver;
import frc.lib.driver.IOperationModifier;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.ITimer;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;

public class PrinterMacroMechanism extends PrinterMechanism implements IControlTask{

    public PrinterMacroMechanism(IDriver driver, IRobotProvider provider, ITimer timer) {
        super(driver, provider, timer);
    }

    @Override
    public void initialize(IOperationModifier operationModifier, Injector injector) {
        
    }

    @Override
    public EnumSet<AnalogOperation> getAffectedAnalogOperations() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAffectedAnalogOperations'");
    }

    @Override
    public EnumSet<DigitalOperation> getAffectedDigitalOperations() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAffectedDigitalOperations'");
    }

    @Override
    public void begin() {
        
    }

    @Override
    public void update() {
        
    }

    @Override
    public void end() {

    }

    @Override
    public boolean hasCompleted() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hasCompleted'");
    }

    @Override
    public boolean shouldCancel() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'shouldCancel'");
    }
    
}
