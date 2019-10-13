# Shooter Exercise

## Introduction
The goal of the Shooter exercise is to get students used to writing code for a mechanism that runs PID loops on TalonSRX motor controolers.  Some students will have seen a flywheel Shooter in the past.  This version will use positional PID for controlling the position of the "hood" which controls direction, will use velocity PID for controlling the speed of the flywheel, and will also have a pneumatic kicker that launches the ball into the flywheel.

## Implementing ShooterMechanism
We'll now cover the basics that will need to be understood for implementing the ShooterMechanism.  More specific instructions exist in the [Robot Programming Guide](#/Robot%20Programming%20Guide.md)

### Actuators
#### Hood Motor (TalonSRX)
This motor can change the position of the hood by changing the Position setpoint, assuming that proper PID gains are used.  You can expect that an encoder reading of "0" means that the hood is all the way up and would cause the ball to be launched parallel to the floor, and that an encoder reading of "90" means that the hood is all the way back and the ball would be launched perpendicularly to the floor.

#### Flywheel Motor (TalonSRX)
This motor can change the velocity of the flywheel by changing the Velocity setpoint, assuming that sensible PID gains are used.

#### Pneumatic Kicker (DoubleSolenoid)
This double-solenoid is used to launch the ball.  You can assume that the "forward" direction should be used when kicking the ball, and the "reverse" direction puts the kicker back into its resting position.

### Sensors
The Shooter does not have independent sensors to check because the encoders are plugged directly into the corresponding TalonSRX motor controllers.

### Operations
#### Hood Angle Position (Analog)
The ShooterAngle slider controls the desired position of the hood from being all the way forward (min extent) to all the way back (max extent).

#### Spin Button (Digital)
The ShooterSpin button should be used to toggle the spinning of the flywheel on or off.  While spinning, the flywheel should try to go at a speed of "200" units.

#### Fire Button (Digital)
The ShooterFilre button tells the kicker to kick momentarily and then return to its regular position.

### Updating State
There's no specific state diagram for the Shooter.

## Links
[Robot Programming Guide](/Robot%20Programming%20Guide.md)
