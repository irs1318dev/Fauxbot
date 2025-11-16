package frc.robot.driver;

import frc.lib.driver.IOperation;

public enum DigitalOperation implements IOperation
{
    ExampleA,
    ExampleB,

    // GarageDoor operations:
    OpenSensor,
    ClosedSensor,
    ThroughBeamSensor,
    Button,
    
    // Forklift operations: 
    ForkliftUp, 
    ForkliftDown,
    

    // Elevator operations:

    // Printer operations:

    // Shooter operations:
}
