# Operation
An Operation is a single basic action that the robot can perform.  There are typically many operations for each mechanism.  These operations should be thought of as the most basic ways of controlling each mechanism.  Operations are also the building blocks on top of which we will build out Macros and Autonomous Routines.  Operations can be either Analog or Digital.

## Analog Operations
Analog operations are typically operations that happen to a certain extent and are controlled by an axis on the joystick during teleop mode (e.g. the drivetrain is controlled by pushing forward along the Y axis of the joystick).  Analog operations are related to double values (rational number, usually between -1.0 and 1.0).  Analog operations are defined in the ```AnalogOperation``` enumeration.

## Digital Operations
Digital operations are operations that are either happening or not happening and are controlled by a button on the joystick or button pad during teleop mode (e.g. a trigger on the joystick to cause a "shoot" action).  Digital operations are related to boolean values (true or false).  Digital operations are defined in the ```DigitalOperation``` enumeration.

There are three main types of digital operations:
* Simple: which is "true" (on) whenever the button is actively being pressed, and "false" (off) otherwise.  A simple button would typically be used for spinning an intake roller while trying to pick up a ball.  A real-world example of a simple button would be something like the Shift key on a computer keyboard.
* Toggle: which is "true" from the time that it is first pressed until the next time it is pressed, and then false until it is pressed again.  A toggle button is typically used for running a macro.  A real-world example of a toggle button would be something like the Caps Lock key on a computer keyboard.
* Click: which is true the first time we run an update after each time the button is pressed, and false until the button has been released and pressed again.  A click button would typically be used for shooting a ball or lifting an arm.  A real-world

**Note**: Although it often feels like toggle buttons make a lot of sense for enabling/disabling certain modes, it can be confusing for a driver who may not be able to tell the current mode.  Much like someone typing with Caps Lock if they aren't paying attention to the screen, a driver could inadvertantly not notice whether or not they were currently in the expected mode if there's not a good indicator on the robot.  For this reason, we typically use two separate "EnableX" and "DisableX" Click operations instead of Toggles.
