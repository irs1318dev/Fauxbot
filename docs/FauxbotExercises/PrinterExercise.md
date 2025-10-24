# Printer Exercise

## Introduction
The goal of the Printer exercise is to get students used to writing code for a mechanism that has a lot of imprecision while running positional PID loops on TalonSRX motor controolers.  This printer behaves closest to how 3D printers works, but only in 2 dimensions - it has two motors to control the printer head travelling over the X and Y axis, plus a double-solenoid that controls pressing a pen to the surface of the paper.

## Difficulty
Programming: 2
Robotics: 3 (Position PID control on motor controller)

## Implementing PrinterMechanism
We'll now cover the basics that will need to be understood for implementing the PrinterMechanism.  More specific instructions exist in the [Robot Code Instructions](#/RobotCodeInstructions/RobotCodeInstructions.md)

## Well-known information
I would recommend adding constants to HardwareConstants describing the Canvas on which the printer will be writing.  The top-left position will be at (0,0), and the bottom-right position will be at (200, 200).  This is typical in simple 2D computer graphics - the origin of an image is the top-left of that image.

### Actuators
#### X-axis Motor (TalonSRX)
This motor can change the position of the pen along the X axis by changing the Position setpoint, assuming that proper PID gains are used.  You can expect that an encoder reading of "0" means that the pen is at the leftmost position, and that an encoder reading of "200" means that the pen is at the rightmost position.  It should be noted that the simulation is designed such that it will cause a poorly-tuned PID system to overshoot or undershoot its desired position.  The x-axis motor is connected to the RoboRIO's CAN interface, using ID 0.

#### Y-axis Motor (TalonSRX)
This motor can change the position of the pen along the X axis by changing the Position setpoint, assuming that proper PID gains are used.  You can expect that an encoder reading of "0" means that the pen is at the topmost position, and that an encoder reading of "200" means that the pen is at the bottommost position.  It should be noted that the simulation is designed such that it will cause a poorly-tuned PID system to overshoot or undershoot its desired position.  The x-axis motor is connected to the RoboRIO's CAN interface, using ID 1.

#### Pen (DoubleSolenoid)
This double-solenoid is used to press the pen to the paper.  You can assume that the "forward" direction should be used when pressing the pen to the paper, and the "reverse" direction lifts the pen back into its resting position.  The forward direction is connected to PCM channel 7, and reverse direction is connected to PCM channel 8.

### Sensors
The Printer does not have independent sensors to check because the encoders are plugged directly into the corresponding TalonSRX motor controllers.

### Operations
#### X-axis Position (Analog)
The X-axis slider controls the desired position of the pen from being all the way left (min extent) to all the way right (max extent).

#### Y-axis Position (Analog)
The Y-axis slider controls the desired position of the pen from being all the way up (min extent) to all the way down (max extent).

#### Pen Down Button (Digital)
The pen down button should be used to move the pen into the down position.

#### Pen Up Button (Digital)
The pen up button should be used to move the pen into the upn position.

### Updating State
There's no specific state diagram for the Printer.  However, there are multiple possibilities for how it can be implemented.  To start out, you can use the default "PercentOutput" mode on the TalonSRX to control how the motor moves the pen location.  That can be directly controlled by the corresponding Analog operations.  For a more advanced implementation, switch to the "Position" mode on the TalonSRX and configure PID constants.  You will also need to convert the values that you set as the desired value on the motor - instead of the range being a motor power in range \[-1, 1\], you will want to use the desired position \[0, 200\].
