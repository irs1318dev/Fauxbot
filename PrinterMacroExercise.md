# Printer Macro Exercise

## Introduction
The goal of the Printer Macro exercise is to introduce students to writing code for control tasks and macros in order to have the printer "robot" automatically draw something.

## Implementing Printer Macros
First, make sure that you have implemented PrinterMechanism as a part of the PrinterMechanism exercise.  Also make sure that you indeed used the Positional PID mode on the X- and Y-axis TalonSRX motors.  I'll recommend a few pieces that you can implement in order to make this easier.  Some more specific instructions exist in the [Robot Programming Guide](#/Robot%20Programming%20Guide.md)

## Update to PrinterMechanism
You will want to update PrinterMechanism to have functions that return the current X and Y position from the encoders that are connected to the TalonSRX motor controllers.  Please make sure that the positions are read during readSensor(), stored in a class member variable, and returned by these functions.  Also, you should ensure that it is using Positional PID mode when controlling the Talon SRX motors.

### ControlTasks
#### Pen Write task (extending from CompositeOperationTask)
The first task will be the simplest both conceptually and to implement.  In the PrinterMechanism exercise, you should have added two DigitalOperations, named something like "PenDown" and "PenUp".  In your PenWrite task, you will control these two operations with the ability to select which operation to perform based on the constructor.  The only thing that needs to be implemented within your class will be the constructor.  Your constructor can take a boolean parameter which tells you whether to put the pen down or to pull the pen up.  Based on that parameter, you will change the parameters you send to the super-class's constructor.  The CompositeOperationTask constructor takes 3 arguments: you should specify a short time (e.g. 0.1 seconds), the desired operation (PenUp or PenDown), and the list of possible operations (an array containing just PenUp and PenDown).

#### Pen Move task (extending directly from ControlTaskBase)
The next task will be much more complicated.  You will want to control the x-axis position and y-axis position based on the desired x and y pixel locations that are provided to the constructor.  The update loop should set the x and y analog operations to a value that will control the system to move the pen to the correct location.  Remember that if your Mechanism is expecting joystick input in the range \[-1, 1\], then you will need to adjust from pixel position to joystick input.  The hasCompleted function should check whether the PrinterMechanism's x position and y position are currently within a very-small range of the desired pixel position (e.g. 1 pixel).  You should implement a begin function that retrieves the PrinterMechanism from the injector and saves it into a class member variable.

#### Draw MacroOperation
The last piece that you will need is to add a MacroOperation and to describe it in the ButtonMap.  Within the button map, you can add a new description that uses a Toggle button type, constructs a SequentialTask containing a sequence of Pen Write tasks and Pen Move tasks, and provides a list of analog and digital operations that the tasks will execute.

## Links
[Robot Programming Guide](/Robot%20Programming%20Guide.md)
