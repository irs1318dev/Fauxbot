// package frc.robot.driver.controltasks;
// import java.util.EnumSet;

// import frc.robot.driver.AnalogOperation;
// import frc.robot.driver.DigitalOperation;
// import frc.robot.mechanisms.PrinterMechanism;

// public class PrinterMotorTask extends ControlTaskBase {
//     public PrinterMotorTask(AnalogOperation toPerform, EnumSet<AnalogOperation> possibleOperations){
//         super(toPerform, possibleOperations);
//     }
//     private PrinterMechanism printer;


//     @Override
//     public EnumSet<AnalogOperation> getAffectedAnalogOperations() {
//         // TODO Auto-generated method stub
//         throw new UnsupportedOperationException("Unimplemented method 'getAffectedAnalogOperations'");
//     }
//     @Override
//     public EnumSet<DigitalOperation> getAffectedDigitalOperations() {
//        // TODO Auto-generated method stub
//         throw new UnsupportedOperationException("Unimplemented method 'getAffectedDigitalOperations'");
//     }

//     @Override
//     public void begin() {
//         this.printer = this.getInjector().getInstance(PrinterMechanism.class);
//     }

//     @Override
//     public void update() {
//         // TODO Auto-generated method stub
//         throw new UnsupportedOperationException("Unimplemented method 'update'");
//     }
//     @Override
//     public void end() {
//         // TODO Auto-generated method stub
//         throw new UnsupportedOperationException("Unimplemented method 'end'");
//     }

//     @Override
//     public boolean hasCompleted() {
//         // TODO Auto-generated method stub
//         throw new UnsupportedOperationException("Unimplemented method 'hasCompleted'");
//     }
// }

