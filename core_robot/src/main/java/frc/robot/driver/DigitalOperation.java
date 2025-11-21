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
    ElevatorFloor1,
    ElevatorFloor2,
    ElevatorFloor3,
    ElevatorFloor4,
    ElevatorFloor5,

    // Printer operations:

    // Shooter operations:
}

