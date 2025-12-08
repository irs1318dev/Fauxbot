# Forklift Exercise

## Introduction
The goal of the Forklift exercise is to introduce students to writing code for a mechanism using inputs from the driver to control motors and pneumatics.

## Difficulty
Programming: 1
Robotics: 1

## Implementing ForkliftMechanism
We'll now cover the basics that will need to be understood for implementing the ForkliftMechanism.  More specific instructions exist in the [Robot Code Instructions](https://irs1318dev.github.io/RobotProgrammingGuide/RobotCodeInstructions/RobotCodeInstructions.html)

### Actuators
#### DriveTrainLeft (Talon)
The left DriveTrain motor controls movement on the left side of the DriveTrain.  You can assume that the positive direction moves forward and negative moves backwards.  The motor is connected to the RoboRIO's PWM channel 0.

#### DriveTrainRight (Talon)
The right DriveTrain motor controls movement on the right side of the DriveTrain.  You can assume that the positive direction moves forward and negative moves backwards.  The motor is connected to the RoboRIO's PWM channel 1.

#### Lifter (DoubleSolenoid)
The lifter for our purposes is a pneumatic piston which controls the movement of the forks.  You can assume that the "forward" direction moves the lifter up and "reverse" direction moves the lifter down.  The forward direction is connected to PCM channel 7, and reverse direction is connected to PCM channel 8.

### Sensors
There are no sensors required for this exercise.

### Operations
#### DriveTrainLeft (Analog)
The left side of our DriveTrain is controlled with an analog operation that can be used as the value provied to the motor.  If the Analog operation is 0.5 (50%), then we should provide 0.5 (50% power) to the left DriveTrain motor.

#### DriveTrainRight (Analog)
The right side of our DriveTrain is controlled with an analog operation that can be used as the value provied to the motor.  If the Analog operation is 0.7 (70%), then we should provide 0.7 (70% power) to the right DriveTrain motor.

#### ForkliftUp (Digital)
Our forklift is very simple and only supports being up or down, not halfway in between.  When the ForkliftUp operation is true, we want to move the lifter into the up position.

#### ForkliftDown (Digital)
When the ForkliftDown operation is true, we want to move the lifter into the down position.
