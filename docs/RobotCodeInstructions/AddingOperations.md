# Adding Operations

To add a new action that the robot can take with a mechanism, first open the ```AnalogOperation``` or ```DigitalOperation``` enum (AnalogOperation.java or DigitalOperation.java under core_robot\src\main\java\frc\robot\driver) and add a new value to the list in that file.  We try to keep the various operations organized, so we keep them listed in a different section for each Mechanism.

Digital operations are things that are done all-or-nothing, as a true or false value (type ```boolean```).

Analog operations are things that are done to some extent, as a decimal number (type ```double```, a double-precision floating point number).

The operation should be named starting with the mechanism (e.g. "DriveTrain", "Intake", etc.), and then a description of the action (e.g. "Turn", "RaiseArm", etc.) to make one single pascal-case value (e.g. "DriveTrainTurn", "IntakeRaiseArm", etc.).

Remember that Analog/Digital Operations are a single, simple thing that is done by the robot.  Any more complex action that we want the robot to take will be a Macro which composes these Analog/Digital Operations together.
