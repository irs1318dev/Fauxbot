package frc.robot.driver.controltasks;

import java.util.EnumSet;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import frc.lib.driver.IDriver;
import frc.lib.driver.IOperationModifier;
import frc.lib.robotprovider.IRobotProvider;
import frc.lib.robotprovider.ITimer;
import frc.robot.driver.AnalogOperation;
import frc.robot.driver.DigitalOperation;
import frc.robot.driver.controltasks.CompositeOperationTask;
import frc.robot.driver.controltasks.ControlTaskBase;
import frc.robot.mechanisms.PrinterMechanism;
@Singleton
public class PrinterMacroTask extends ControlTaskBase{
    @Inject
    private PrinterMechanism printer;
    private boolean pencontrol;
    public PrinterMacroTask(IDriver driver, IRobotProvider provider, ITimer timer) {
        
    }
    @Override
    public void initialize(IOperationModifier operationModifier, Injector injector) {
        
    }
    public class PenControl extends CompositeOperationTask {

        PenControl(DigitalOperation toPerform, EnumSet<DigitalOperation> possibleOperations, boolean pencontrol) {
            super(toPerform, possibleOperations, pencontrol);
        }
        
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
       this.printer = this.getInjector().getInstance(PrinterMechanism.class);
    }
    @Override
    public void update() {
        if (this.pencontrol == true){
            this.setDigitalOperationState(DigitalOperation.PrinterPenDown, true);
        }
        if (this.pencontrol == false){
            this.setDigitalOperationState(DigitalOperation.PrinterPenDown, false);
        }

    }

    @Override
    public void end() {

    }

    @Override
    public boolean hasCompleted() {
        return this.printer.pencontrol;
    }

    @Override
    public boolean shouldCancel() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'shouldCancel'");
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'stop'");
    }
    
}
