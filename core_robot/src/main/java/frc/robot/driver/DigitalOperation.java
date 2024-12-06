package frc.robot.driver;

import frc.lib.driver.AnalogAxis;
import frc.lib.driver.IOperation;
import frc.lib.driver.descriptions.UserInputDevice;
import frc.robot.ElectronicsConstants;
import frc.robot.TuningConstants;

public enum DigitalOperation implements IOperation
{
    ExampleA,
    ExampleB,

    // GarageDoor operations:
    Button,
    // Forklift operations: 
    ForkLiftUp,ForkLiftDown, 

    // Elevator operations:
    ElevatorFirstFloorButton, ElevatorSecondFloorButton, ElevatorThirdFloorButton, ElevatorFourthFloorButton, ElevatorFifthFloorButton, 

    // Printer operations:

    // Shooter operations:
}
