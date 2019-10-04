# Printer Exercise

## Introduction
The goal of the Printer exercise is to get students used to writing code for a mechanism that has a lot of imprecision while running PID loops on TalonSRX motor controolers.  It can also be used to teach using macros and autonomous routines.  This printer behaves closest to how 3D printers works - it has two motors to control the printer head travelling over the X and Y axis, plus a double-solenoid that controls pressing a pen to the surface of the paper.

## Implementing PrinterMechanism
We'll now cover the basics that will need to be understood for implementing the PrinterMechanism.  More specific instructions exist in the [Robot Programming Guide](#/Robot%20Programming%20Guide.md)

### Actuators
#### X-axis Motor (TalonSRX)
This motor can change the position of the pen along the X axis by changing the Position setpoint, assuming that proper PID gains are used.  You can expect that an encoder reading of "0" means that the pen is at the leftmost position, and that an encoder reading of "200" means that the pen is at the rightmost position.  It should be noted that the simulation is designed such that it will cause a poorly-tuned PID system to overshoot or undershoot its desired position.

#### Y-axis Motor (TalonSRX)
This motor can change the position of the pen along the X axis by changing the Position setpoint, assuming that proper PID gains are used.  You can expect that an encoder reading of "0" means that the pen is at the topmost position, and that an encoder reading of "200" means that the pen is at the bottommost position.  It should be noted that the simulation is designed such that it will cause a poorly-tuned PID system to overshoot or undershoot its desired position.

#### Pen (DoubleSolenoid)
This double-solenoid is used to press the pen to the paper.  You can assume that the "forward" direction should be used when pressing the pen to the paper, and the "reverse" direction lifts the pen back into its resting position.

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
There's no specific state diagram for the Printer.

## Links
[Robot Programming Guide](/Robot%20Programming%20Guide.md)
