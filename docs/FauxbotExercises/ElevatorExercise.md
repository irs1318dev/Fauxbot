# Elevator Exercise

## Introduction
The goal of the Elevator exercise is to introduce students to writing code for a mechanism that uses a PID controller.  It is expected that students will have seen an Elevator in the past.  Unlike a real Elevator, this version will only pay attention the the last-pressed floor button and won't schedule visits to several floors.

## Difficulty
Programming: 2 (using inputs from sensors, processing them, using them in outputs)
Robotics: 3 (input from sensors, Position PID control in PIDHandler object)

## Implementing ElevatorMechanism
We'll now cover the basics that will need to be understood for implementing the ElevatorMechanism.  More specific instructions exist in the [Robot Code Instructions](https://irs1318dev.github.io/RobotProgrammingGuide/RobotCodeInstructions/RobotCodeInstructions.html)

### Actuators
#### Motor (Talon)
The Elevator has just one actuator, a Motor.  This motor can either raise or lower the Elevator by spinning either in one direction or the other.  You can assume that the positive direction raises the Elevator and negative lowers the Elevator.  The motor is connected to the RoboRIO's PWM channel 0.

### Sensors
#### Encoder (Encoder)
The Elevator has an encoder that helps to inform how much the elevator has moved up or down.  When the elevator lifts up, the value returned by the encoder will increase.  When the elevator lowers, the value returned by the encoder will decrease.  The encoder is connected to the RoboRIO's Digital I/O channels 0 and 1.

### Operations
#### First Floor Button (Digital)
The First Floor button tells the Elevator that it should move to the height of the First Floor (0.0).

#### Second Floor Button (Digital)
The Second Floor button tells the Elevator that it should move to the height of the Second Floor (50.0).

#### Third Floor Button (Digital)
The Third Floor button tells the Elevator that it should move to the height of the Third Floor (100.0).

#### Fourth Floor Button (Digital)
The Fourth Floor button tells the Elevator that it should move to the height of the Fourth Floor (150.0).

#### Fifth Floor Button (Digital)
The Fifth Floor button tells the Elevator that it should move to the height of the Fifth Floor (200.0).

### Updating State
There's no specific state diagram for the Elevator.  Whenever the user presses one of the buttons, the elevator mechanism should note the new desired height.  It will use that desired height in addition to the current height read from the encoder to provide to a PID controller (PIDHandler class) to calculate the output to send to the motor.
