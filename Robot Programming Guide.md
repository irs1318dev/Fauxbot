# Robot Programming Guide

**Table of Contents**
1. [Overview](#overview)
2. [General Robot Design](#general-robot-design)
   1. [RoboRIO](#roborio)
   2. [Actuators](#actuators)
      1. [Motors (Talons, Jaguars, Victors)](#motors-talons-jaguars-victors)
      2. [Pistons/Pneumatic Cylinders (DoubleSolenoids)](#pistonspneumatic-cylinders-doublesolenoids)
   3. [Sensors](#sensors)
      1. [Limit Switches](#limit-switches)
      2. [Encoders](#encoders)
      3. [Through-Beam Sensors](#through-beam-sensors)
      4. [Distance Sensors](#distance-sensors)
   4. [Other](#other)
      1. [Logger](#logger)
      2. [LED Lights](#led-lights)
   5. [User Input Devices](#user-input-devices)
      1. [Joysticks](#joysticks)
      2. [Button Pads](#button-pads)
      3. [Dip Switches](#dip-switches)
   6. [Modes](#modes)
3. [Robot Code Design](#robot-code-design)
   1. [CoreRobot.java](#corerobotjava)
   2. [Mechanisms](#mechanisms)
   3. [TuningConstants](#tuningconstants)
   4. [ElectronicsConstants](#electronicsconstants)
   5. [HardwareConstants](#hardwareconstants)
   6. [LoggingKeys](#loggingkeys)
   7. [Driver](#driver)
      1. [Operation](#operation)
         1. [Analog Operations](#analog-operations)
         2. [Digital Operations](#digital-operations)
      2. [ControlTasks](#controltasks)
      3. [ButtonMap](#buttonmap)
         1. [Macros](#macros)
         2. [Shifts](#shifts)
      4. [Autonomous Routines](#autonomous-routines)
   8. [External Libraries](#external-libraries)
      1. [Guice](#guice)
      2. [OpenCV](#opencv)
      3. [CTRE Phoenix](#ctre-phoenix)
      4. [NavX MXP](#navx-mxp)
      5. [JUnit](#junit)
      6. [Mockito](#mockito)
4. [Instructions](#instructions)
   1. [Setting up your Environment](#setting-up-your-environment)
   2. [Simple Command Line operations and Git usage](#simple-command-line-operations-and-git-usage)
   3. [Your normal end-to-end git workflow](#your-normal-end-to-end-git-workflow)
   4. [So you started coding before creating a topic branch](#so-you-started-coding-before-creating-a-topic-branch)
   5. [Making Simple Operation changes](#making-simple-operation-changes)
   6. [Adding a new Electronics Constant](#adding-a-new-electronics-constant)
   7. [Adding a new Hardware or Tuning Constant](#adding-a-new-hardware-or-tuning-constant)
   8. [Adding a new Logging Key](#adding-a-new-logging-key)
   9. [Writing a new Mechanism](#writing-a-new-mechanism)
      1. [Define mechanism class and member variables](#define-mechanism-class-and-member-variables)
      2. [Write mechanism constructor](#write-mechanism-constructor)
      3. [Write mechanism readSensors function](#write-mechanism-readsensors-function)
      4. [Write mechanism update function](#write-mechanism-update-function)
      5. [Write mechanism stop function](#write-mechanism-stop-function)
      6. [Write any getter functions](#write-any-getter-functions)
   10. [Writing Macros and Autonomous Routines](#writing-macros-and-autonomous-routines)
      1. [Writing Tasks](#writing-tasks)
         1. [Define task class, member variables, and constructor](#define-task-class-member-variables-and-constructor)
         2. [Write task begin function](#write-task-begin-function)
         3. [Write task update function](#write-task-update-function)
         4. [Write task end function](#write-task-end-function)
         5. [Write task hasCompleted function](#write-task-hascompleted-function)
         6. [Write task shouldCancel function (optional)](#write-task-shouldcancel-function-optional)
         7. [Write task stop function (optional)](#write-task-stop-function-optional)
      2. [Adding Macros](#adding-macros)
      3. [Adding Autonomous Routines](#adding-autonomous-routines)
          1. [PathPlanner](#PathPlanner)
      4. [Composing Tasks together](#composing-tasks-together)
         1. [SequentialTask.Sequence()](#sequentialtasksequence)
         2. [ConcurrentTask.AnyTasks()](#concurrenttaskanytasks)
         3. [ConcurrentTask.AllTasks()](#concurrenttaskalltasks)
5. [Advanced Topics](#advanced-topics)
   1. [PID Controllers](#pid-controllers)
   2. [Motion Planning](#motion-planning)
   3. [Vision](#vision)

## Overview
> "Everything should be made as simple as possible, but not simpler." - Albert Einstein

The Issaquah Robotics Society’s Robot code is designed to be a good example of a moderately large software project that students of varying levels of experience with programming can contribute to.  It is aimed at making the programming of the robot easy, so that it can respond well to changes to the physical robot as well as changes in the way that we wish to control the robot.  It achieves this by encapsulating each part in a different area of the code, with the idea that there should be very little duplication of code to ease maintainability.

## General Robot Design
Robots in FRC tend to have a small set of different pieces that we want to utilize, that can be arranged in a large number of ways to make complex mechanisms.  These mechanisms are designed before the code for them is written, so you should know what they are composed of by the time you are writing any code.  I’ll list out a few of these pieces here and explain what they do and how we typically use them.

### RoboRIO
RoboRIO is the name of the computing device that is used to control the robot.  It is produced by National Instruments and runs a customized version of Linux on an ARM processor.  We write code that uses a library called WPILib to handle interactions between the Robot, Driver Station, etc.

### Actuators
#### Motors (Talons, Jaguars, Victors)
Electric motors are typically used to provide movement for the robot.  They provide a rotational force that is dependent on the current setting on them and the amount of voltage that is available.  Motors are useful when a certain amount of motion is needed or when there are motions that need to happen at different speeds (as opposed to all-or-nothing).  Motors are used in places such as drive trains, elevators, and intakes.  In WPILib, they are controlled using a double value (rational number) between -1.0 and 1.0.  Since 2018, we have typically used the Talon SRX which can incorporate the abilities of a motor, an encoder, a top/bottom limit switch, and a PID controller to allow for advanced control.  In 2020, we started using brushless motors, including the Falcon with its built-in TalonFX motor controller as well as the NEO with its corresponding Spark MAX motor controller.

#### Pistons/Pneumatic Cylinders (DoubleSolenoids)
Pneumatic cylinders are also used to provide movement for components on the robot.  They provide a linear force and are controlled very simply by a set of 2 valves (solenoids) that change where air pressure is directed in order to move the rod within the cylinder.  There are 3 settings: "off" which means that no air pressure is applied through either valve, "forward" which means that air pressure is applied through one valve, and "reverse" which means that air pressure is applied through the other valve.  Forward and Reverse typically correlates to whether the piston is actively pushing out or actively pulling in, but it depends on how the pneumatics are set up.  Due to the way that they work, pneumatics controlled by solenoids trigger all-or-nothing movements.  Solenoids are often used in "shooters" such as the kicker on the 2016 robot, or for controlling the position of an intake such as on the 2019 robot.  In WPILib, they are controlled using settings of Value.kOff, Value.kForward, and Value.kReverse.

### Sensors
#### Limit Switches
Limit switches are simple switches that are used to sense when two things are physically touching.  They are simple electronic devices that complete a circuit (or break a circuit) when the switch is pressed, and break a circuit (or complete it) when released.  In WPILib, you would use a DigitalInput, which returns a true or false based on whether the limit switch is pressed or not.

#### Encoders
Encoders are used to measure the amount that an axle has rotated.  There are different types of encoders (optical, magnetic).  We typically use a quadrature encoder, which can detect the amount of rotation and the direction in which the axle has rotated.  Each encoder has a rating for how many "pulses" or ticks it receives in a complete rotation of the axle.  Using some simple math based on the sizes of the wheels (and gears), you can calculate how far something has travelled.  In WPILib, you would typically use an Encoder object, which returns the number of ticks/pulses, the distance (based on the distance per pulse), or the velocity (if you trust the timer on the robot).  In some scenarios, such as when using a TalonSRX, TalonFX, or SparkMAX motor controller, the encoder plugs into the motor controller and is instead used as a part of controlling the motor.  In other scenarios, such as some absolute encoders, the sensor is actually an Analog sensor.  For such encoders, WPILib would use an AnalogInput, which returns a double (rational number) value between 0V (0 degrees) and 5V (360 degrees).

#### Through-Beam Sensors
Through-Beam Sensors are simple infrared sensors and lights that are used to sense whether there is anything between the light and sensor.  They are often used in the real world at the bottom of a garage door to detect if anything is under the garage door so it doesn’t get crushed.  This can be used on a robot to sense whether something is in a given location.  We often use them on robots to detect whether the game piece has been successfully picked up.  In WPILib, you would use an AnalogInput, which returns a double value (rational number) which indicates how many volts were detected by the infrared sensor.  This value will differ based on the through-beam sensor, so you can tell through experimentation whether it is tripped or not for a given value range.

#### Distance Sensors
There are various types of distance sensors, which can use either sound or light to sense how far away the robot is from something else.  In WPILib, you would use an AnalogInput, which would return a double value (rational number) which indicates how many volts were detected by the sensor.  This value will differ based on the sensor and its placement, so you can tell through experimentation what the values mean.  It is also possible to use a more complex sensor that would need to have code written for it to use I2C or another protocol to let the RoboRIO communicate with the sensor.

### Other
#### Logger
A Logger is something that takes information that is used and outputs it to some other form - as output either on the driver station console, in the smart-dashboard, or into a file on the RoboRIO.  We typically want to log all sensor information and most output information so that we can be sure that the robot is doing what we tell it to do.  If the robot is not doing what we are telling it to, this makes it easier to determine whether the problem is a hardware problem (electronics, mechanical) or a software problem (logic, calculation).

#### LED Lights
We also often use LED lights as an indicator of some kind, or in association with a Vision system that takes advantage of retro-reflective tape.  These can be controlled a number of different ways, using DigitalOutput, a Solenoid slot on the PCM, a Relay, or even a Motor controller in WPILib.

### User Input Devices
#### Joysticks
The joystick is a normal computer joystick, much like you’d find for playing a flight simulator game.  The ones that we have used for the past few years is the Logitech Xtreme 3D Pro, which has 12 buttons, the primary X and Y axis, a Throttle axis, and a directional hat.  Our team historically used a single joystick as the Driver’s primary input metho,d through 2019.

#### Button Pads
The button pads we use are PC gaming button pads which have 12 buttons.  Our team historically used a button pad for the Co-Driver’s input method, or as a secondary driver input method.

#### Controllers
Another controller type is an Xbox 360, Xbox One, PS4 controller, or a 3rd-party controller that has USB input.  This is an alternative that can be used by a Driver and/or Operator of the robot if the control scheme is preferable based on that year's challenge.  Since 2020, we have switched to typically having a Driver and Operator each with their own controller to work together to perform tasks.

#### Dip Switches
Dip Switches are simple toggle switches which are used to switch between different modes on the Robot.  Our team historically used dip switches to allow us to select which of several different pre-programmed autonomous routines to use without having to change anything within the code or rely on the smart dashboard.  In 2019, we started using the Smart Dashboard to choose the autonomous routine.

### Modes
During each match, the first portion is usually a part called autonomous mode, where the robot drives itself.  The next portion is called teleop mode, where a user drives the robot.

## Robot Code Design
### CoreRobot.java
The "main loop" of the robot is in CoreRobot.java (and Robot.java), which has a few entry points from the WPILib infrastructure which our code depends on.  The class ```CoreRobot``` typically stays exactly the same from year to year.

The entry points for code execution comes from WPILib at the following times:
* When the robot is first turned on
  * In ```robotInit()```, we initialize the mechanisms, logger, timer, and driver that the robot will use.
* When the robot is enabled in teleop mode
  * In ```teleopInit()```, we run ```generalInit()```.  In ```generalInit()``` we apply the driver to each of the mechanisms and ensure that the timer has been started.
* When the robot is enabled in autonomous mode
  * In ```autonomousInit()```, we tell the driver that it is beginning autonomous mode, and then call ```generalInit()```.
* When the robot is disabled
  * In ```disabledInit()```, we call the stop function for each of our mechanisms, the timer, and the driver.
* Every ~20ms while the robot is enabled in teleop mode
  * In ```teleopPeriodic()```, we simply call our ```generalPeriodic()``` function because we have structured our code to be the same for our teleop and autonomous modes.  In ```generalPeriodic()```, we first call the ```readSensor()``` function for each of our mechanisms, then call ```update()``` on the driver, and finally call the ```update()``` function for each of our mechanisms.
* Every ~20ms while the robot is enabled in autonomous mode
  * In ```autonomousPeriodic()```, we again call ```generalPeriodic()```.

### Mechanisms
Mechanism classes handle the reading of all of the sensors and control of all of the actuators on each mechanism of the robot.  There is one Mechanism class for each individual part of the robot, named using the pattern "ThingMechanism" (where "Thing" is the name of the mechanism, like "DriveTrain").  Mechanisms read from all of the Sensors and translate the Operations from the Driver into the functions that need to be called on the individual Actuators.  This typically involves some math and logic to convert the data from the operations into the particular actions that need to happen.  For example, when using a typical Tank drivetrain, the DriveTrain Mechanism calculates the speed settings to apply to the left and right motors based on the DriveTrainMoveForward operation and the DriveTrainTurn operation.  Also, there may be other concerns to take care of, such as how to respond based on the presence or absence of a setting from another operation or a sensor.

The Mechanisms implement the ```IMechanism``` interface which has the definitions of functions that every Mechanism must implement.  In the mechanism, the ```readSensor()``` and ```update()``` functions are the most important, and are called every ~20 milliseconds.  The ```readSensors()``` function reads the current values from all of the sensors and stores them locally in member variables for that ThingMechanism object.  The ```update()``` function calculates what should be applied to the output devices based on the current Operations and the data we previously read from the sensors.  It is important that these functions execute quickly, so anything that depends on a certain length of time elapsing should be calculated between separate runs of the function and not involve any long-running loops or sleeps.  Most actions that take multiple iterations of the ```update()``` function or depend on time elapsing belong in a macro instead of being hard-coded into the Mechanism, though it is also possible for there to be state kept in member variables to help keep track of what that mechanism should be doing.

### TuningConstants
In order to simplify tuning the settings of the robot, we store any "magic numbers" that we will likely want to change as constants in the ```TuningConstants``` class.  Settings that may need to be tuned include things like the speed at which to run an intake, or the speed at which to turn when the joystick is in a certain position.  These settings are usually things that are hard to know in advance, and the appropriate settings are discovered by testing the robot.  There are many things that aren’t known in advance by the software team, so putting all of these things in ```TuningConstants``` in an organized fashion can help speed up the tuning process of the robot and prevent bugs.

### ElectronicsConstants
```ElectroincsConstants``` is a class that holds constant values describing all of the physical connections (PWM channels, Digital IO channels, Analog IO channels, CAN ids, etc.) that are needed to be known in order to control the correct output device and read the correct sensors.  We keep this information in a separate class (and all in a single file) so that there is only one place to update if the Electronics sub-team needs to re-run the wiring, or in case there are wiring differences between the practice robot and the competition robot.

### HardwareConstants
Similar to the ```ElectronicsConstants``` and ```TuningConstants```, we also store some facts about the dimensions of the robot and the different parts of the robot as constants in the ```HardwareConstants``` class.  This tends to include things like the diameter of the wheels and the width of the robot, which may be useful for calculations that need to be made during autonomous mode or for determining the robot's position and orientation on the field.

### LoggingKeys
Within mechanisms, we have a desire to log various bits of information about a mechanism's state in order to make it easier to set some of the tuning constants as well as debug hardware and software issues on the robot.  This information is sent somewhere based on the robot's configuration, but typically these can be seen in the Smart Dashboard that runs on the Driver Station laptop while the robot is running.  The ```LoggingKey``` enumeration contains the list of every bit of information that is logged during the running of the robot, along with the corresponding name that will be displayed in the log or on the dashboard.

### Driver
The ```Driver``` is the actor that controls the robots.  The ```Driver``` class triggers different Operations to occur based on the intent of the current actor that is controlling the robot (autonomous or user).

#### Operation
An Operation is a single basic action that the robot can perform.  There are typically many operations for each mechanism.  These operations should be thought of as the most basic ways of controlling each mechanism.  Operations are also the building blocks on top of which we will build out Macros and Autonomous Routines.  Operations can be either Analog or Digital.

##### Analog Operations
Analog operations are typically operations that happen to a certain extent and are controlled by an axis on the joystick during teleop mode (e.g. the drivetrain is controlled by pushing forward along the Y axis of the joystick).  Analog operations are related to double values (rational number, usually between -1.0 and 1.0).  Analog operations are defined in the ```AnalogOperation``` enumeration.

##### Digital Operations
Digital operations are operations that are either happening or not happening and are controlled by a button on the joystick or button pad during teleop mode (e.g. a trigger on the joystick to cause a "shoot" action).  Digital operations are related to boolean values (true or false).  Digital operations are defined in the ```DigitalOperation``` enumeration.

There are three main types of digital operations:
* Simple: which is "true" (on) whenever the button is actively being pressed, and "false" (off) otherwise.  A simple button would typically be used for spinning an intake roller while trying to pick up a ball.  A real-world example of a simple button would be something like the Shift key on a computer keyboard.
* Toggle: which is "true" from the time that it is first pressed until the next time it is pressed, and then false until it is pressed again.  A toggle button is typically used for running a macro.  A real-world example of a toggle button would be something like the Caps Lock key on a computer keyboard.
* Click: which is true the first time we run an update after each time the button is pressed, and false until the button has been released and pressed again.  A click button would typically be used for shooting a ball or lifting an arm.  A real-world

**Note**: Although it often feels like toggle buttons make a lot of sense for enabling/disabling certain modes, it can be confusing for a driver who may not be able to tell the current mode.  Much like someone typing with Caps Lock if they aren't paying attention to the screen, a driver could inadvertantly not notice whether or not they were currently in the expected mode if there's not a good indicator on the robot.  For this reason, we typically use two separate "EnableX" and "DisableX" Click operations instead of Toggles.

#### ControlTasks
Tasks are used to control operations or groups of operations that run until a certain condition is met.  They are used within Macros and Autonomous Routines and can be composed together to perform complex actions.  Tasks themselves should aim to be relatively simple and only accomplish on thing if possible - this helps prevent code duplication later.  An example of a single task is "DriveDistanceTask", which drives forward for a specified distance.

Tasks implement the ```IControlTask``` interface, and typically extend from the ```ControlTaskBase``` class.  Tasks work by applying certain values to one or more operations.  When the task first starts, the ```begin()``` function is called so that the task can check the current state of the robot.  Every ~20ms, the ```update()``` function is called to update the settings based on the criteria defined in the task.  Before running ```update()```, the ```hasCompleted()``` function is called to check whether the task should end.  There are also functions such as ```shouldCancel()``` to indicate that the task needs to end even though it hasn't officially completed, ```end()``` to do clean-up after the task has completed, and ```stop()``` to do clean-up if the task was cancelled.

#### ButtonMap
The ```ButtonMap``` contains the mapping of various joystick/controller buttons and axes to the corresponding digital and analog operations.  The ```Driver``` class is in charge of reading from the joysticks and button pads during the teleop mode, and it uses the ```ButtonMap``` schemas to translate the individual actions taken on the joystick into ```DigitalOperation```s, ```AnalogOperation```s, and ```MacroOperation```s.

##### Macros
Macros are groupings of different Operations that need to happen in a certain order and with certain conditions between the various operations.  This is typically done by defining a bunch of individual "tasks" that perform one operation until it has completed, and then composing them together using different types of logic.  One example of a macro from 2019 would be the climb macro, which moved the robot forwards, engaged the arms, rotated the cam, drove forward, and finally lifted the elevator and reset the arms and cam.  Another example of a macro from 2019 would be the Vision-based alignment and approach of the rocket and cargo ship.

##### Shifts
Sometimes there aren't enough buttons on the joystick in order to accomodate the number of operations and macros that we want to have available to the driver and/or operator.  We have the ability to define "shifts" that allow the same button to mean different things depending on when another button is pressed.  These shifts are described in the ```ButtonMap```.

#### Autonomous Routines
Autonomous routines are designed very similarly to macros, except that they are triggered automatically by the Driver when the autonomous mode starts instead of by buttons on the joystick.  Autonomous routine selection occurs in the ```AutonomousRoutineSelector``` class, with the specific routines defined in that class as well. 

### External Libraries
The robot code makes use of a number of external libraries in order to make programming the robot more straightforward.

#### Guice
[Guice](https://github.com/google/guice) (pronounced "juice") is a dependency injection library, which is responsible for the various "@Inject" and "@Singleton" markup that is seen throughout the code.  The purpose of Guice is to make it easier to plug together the entire system in such a way that it is still unit-testable and able to be simulated in Fauxbot.  You will need to use Guice's @Singleton and @Inject markup when writing a mechanism.

#### OpenCV
[OpenCV](https://opencv.org/) is a computer vision library that is used for fast and efficient processing of images.  We use OpenCV functions to capture images, manipulate them (undistort, HSV filtering), write them, and discover important parts of them (find contours).

#### CTRE Phoenix
[CTRE Phoenix](https://github.com/CrossTheRoadElec/Phoenix-Documentation) is a library that provides the ability to communicate with and control various motors using the TalonSRX, TalonFX, and VictorSPX over CAN.  We use CTRE Phoenix to control the majority of our TalonSRXs/TalonFXs so that we can run PID on the TalonSRX/TalonFX itself for a faster update rate.

#### Spark MAX API
The [Spark MAX](http://www.revrobotics.com/sparkmax-software/) has a library that provides the ability to communicate with and control various motors using the SparkMAX over CAN.  We use the Spark MAX to control NEO Motors so that we can use these brushless motors and run PID on the SparkMAX itself for a fast update rate.

#### NavX MXP
The [NavX MXP](http://www.pdocs.kauailabs.com/navx-mxp/software/) has a library that is used to interact with the NavX MXP.  The NavX uses its Gyroscope and Accelerometers in order to provide orientation measurements for field positioning purposes.

#### JUnit
[JUnit](https://junit.org/junit4/) is a unit testing library for Java.  JUnit is fairly simple and provides some comparison functions and a framework for running unit tests.

#### Mockito
[Mockito](http://site.mockito.org/) is a library for mocking objects for unit testing.  Mockito provides a way to create fake versions of objects that have behaviors that you can describe in a very succinct way.

## Instructions
### Setting up your Environment
To prepare your computer for Robot programming with our team, you will need to follow the following steps:
1. Installing everything:
   1. Install development environment.  There are two options, either installing the WPILib VS Code along with other WPILib tools, or just installing and configuring the most recent "Long-Term Support" version of the JDK.
      1. To install WPILib VS Code, run the [WPILib installer](https://github.com/wpilibsuite/allwpilib/releases).  It will install WPI's special version of VS Code, the JDK, WPILib library dependencies, other dependencies, and some special tools.  Be sure to select the version appropriate for your operating system.
      2. To install the latest "Long-Term Support" JDK (17, 21, ...), download the correct installer for your operating system from [Oracle's JDK page](https://www.oracle.com/java/technologies/downloads/).  You will then need to configure the JAVA_HOME environment variable, in "Configuraing things" step 2 below.
   2. Install regular VS Code.  Run the [VS Code Installer](https://code.visualstudio.com/) to install the regular version of VS Code.  Be sure to select the version appropriate for your operating system.
   3. Install Git.  Run the [Git installer](https://git-scm.com/downloads) to install the Git client.  Be sure to select the version appropriate for your operating system.
   4. Install the Java Extension Pack for VS Code.  In VS Code, open the extensions side bar by either clicking on the corresponding icon or clicking View --> Open View..., typing "Extensions", and selecting "Extensions" (side bar).  Within the Extensions side bar, search for the "Java Extension Pack" published by Microsoft, and then click to install it.  Optionally, I would also recommend installing the "Live Share Extension Pack" published by Microsoft.
   5. Install GitHub Desktop (optional).  Our team uses GitHub as the host for our source control system, so if you are more comfortable having a GUI for interacting with it, then GitHub Desktop will be the best supported.  Install the appropriate version of [GitHub Desktop](https://desktop.github.com/) for your operating system.
2. Configuring things:
   1. Git uses VIM as the default text editor for commit messages.  Normal people not very familiar with VIM usage, so it is strongly recommended to change to a more normal windowed application as VIM can be very confusing for beginners.  I would recommend switching to use VS Code as your editor and default diff tool.
      1. Use VS Code as your default text editor by running ```git config --global core.editor "code --wait"``` from a Command Prompt window.
         1. If you are on a non-Windows system, you may need to make sure that running the command "code" from the command line (Terminal) successfully opens VS Code.  If it does not, Open VS Code, then open its command window (CTRL+P on Linux, COMMAND+P on Mac??), type "> path" into the window that appears, and select the option that mentions updating "path" or installing the "code" command for opening VS Code.
      2. Modify your Global settings by running ```git config --global -e```, and then adding the following entries to the end of the file:
      ```
      [diff]
        tool = vscode
      [difftool "vscode"]
        cmd = code --wait --diff \"$LOCAL\" \"$REMOTE\"
      ```
   2. VS Code's Java extension sometimes needs extra hints to find where the Java JDK was installed.  To do this, you will need to add an environment variable on Windows (sorry, don't know what to do for Mac).
      1. In Windows 10, press start and type "environment" in the search bar.
      2. Click the option that says "Edit the system environment variables".  
      3. Click the "Environment Variables..." button at the bottom of the window.
      4. Within the "System variables" section, click the "New..." button.
      5. In the "New System Variable" dialog, use the Variable name "JAVA_HOME" and value "C:\Program Files\Java\jdk-21", then click Ok.
      6. Click ok to close the Environment Variables and System Properties windows.
      7. Restart your computer.
3. Get the code onto your local machine.
   1. Copy the repository's URL.  In GitHub, find the repository you are interested in, click the "Clone or download" button, and then copy the text (e.g. "https://github.com/irs1318dev/Fauxbot.git").
   2. Using commandline:
      1. Open a commandline window.  On Windows, search for "cmd" or "Command Prompt".  Navigate within your directory structure to a directory where you'd like to keep your source files (e.g. "```cd C:\Users\username\git\```").
      2. Run the following git command to clone the repository to your local machine: "```git clone https://github.com/irs1318dev/Fauxbot.git```"
      3. Once the repository has been cloned, navigate into the main directory (e.g. "```cd C:\Users\username\git\Fauxbot```") and tell Gradle to build the code in the directory (type "```gradlew build```").  If gradle hasn't been installed yet, this should trigger it to be installed.  If you are running MacOS or Linux, instead run gradle wrapper with a dot and forward slash in front of it ("```./gradlew```").  If you are running in PowerShell instead of Command Prompt, instead run gradle wrapper with a dot and a backslash in front of it ("```.\gradlew```").
      4. Open VS Code for this project.  In the main directory, type "```code irs1318_Fauxbot.code-workspace```".  This will tell VS Code to open with a reference to the folder you are currently exploring within cmd.
    3. Using GitHub Desktop:
       1. Open GitHub Desktop.  For the best experience, you will need a GitHub user account that has been added to the irs1318dev group.  If you haven't done that, consider doing that first.
       2. Go to File --> Clone Repository.  If you have been added to the irs1318dev group, you can select the repository you want (e.g. "irs1318dev/Fauxbot") from a list of repositories under the GitHub.com tab.  Otherwise, go to the the URL tab and enter the repository you want (e.g. "irs1318dev/Fauxbot") in the text box.  Then choose a local path where this repository will be cloned (e.g. "C:\Users\username\git\Fauxbot") and click the clone button.
       3. Open VS Code for this project.  Open VS Code and open the folder where code is located by going to File --> Open Folder, and selecting the folder within the one where the repository was cloned (e.g. "C:\Users\username\git\Fauxbot").

If you have issues building the code using gradle for the first time, it may be one of the following issues:
1. Insufficient disk space.  If you get a message talking about not being able to copy a file or create a directory, it may be a disk space issue.  Please clear some space so you have enough to build.
2. Insufficient permissions to run gradlew.  On Mac/Linux, gradlew is blocked from running by default.  To allow it, run "```chmod +x gradlew```" and then run "```chmod 755 gradlew```".
3. Forgetting slashes/etc.  On Mac/Linux or in a Powershell window, you may need to run gradlew in a special way.  On Mac/Linux, you may need to run gradlew as "```./gradlew```".  In Powershell, you may need to run gradlew as "```.\gradlew```".

### Simple Command Line operations and Git usage
Starting in the 2019 season, there's a stronger need to use the command-line than in previous years.  Command line interfaces are used often in real world Engineering and Software Development, so learning it is very useful.

#### Opening CMD and Navigating to a directory in Windows
(Note that the first few steps in the instructions are different in Mac/Unix/Linux - please use the internet to figure out specific instructions for your non-Windows operating system.  In Linux/Unix you are looking for a bash or shell window, on Mac you are looking for the Terminal.)  Press the start button (or the Windows key on your keyboard) and type "cmd" and open Command Prompt.  This will open Command Prompt (cmd) scoped to your user home directory (typically "```C:\Users\username\```").

You will need to navigate around in order to do anything useful.  To look at the contents of your current directory, type "```dir```" ("```ls```" on Mac/Linux/Unix).  To navigate to another directory, use the change directory command ("```cd```") and type "```cd directory```".  While using this command, you can use "```..```" to reference the directory above your current scope, and "```.```" to reference the current directory.  You can also use a full name of the directory, such as "```cd C:\Users\username\git\```" to navigate to that directory.

#### Simple git operations in Command Prompt
1. "```git status```" command will tell you all of the files that are different than what has been committed.
2. "```git resotre filename```" command will get rid of any changes to the specified file in your working directory and replace it with the last-committed version of that file in the local repository.
3. "```git add -A```" command will add all of the currently-changed files in your working directory to be staged and ready to commit.
4. "```git commit -m "message"```" command will commit all of the currently staged changes with the provided message.
5. "```git push```" command will push commits from your local repository to the remote repository (you will need to run "```git push origin branchname```" the first time you are pushing a new branch).
6. "```git branch```" command will show you what branches currently exist for the current repository.
7. "```git branch -c master branchname```" command will create a new topic branch off of the master branch, and "```git checkout -b branchname```" command will create a new topic branch off of hte current branch.
8. "```git pull```" command will update your local repository with changes that have been pushed to the remote repository.
9. "```git checkout branchname```" command will switch your working directory to look at a different branch.
10. "```git clone https://github.com/irs1318dev/Fauxbot.git```" command will clone the repository tracked at the provided url, creating a local copy that you can use to make changes.

For more information about Git in command prompt, look here:
[GitHub's Git cheat-sheet](https://services.github.com/on-demand/downloads/github-git-cheat-sheet/)
[GitHub's Git Handbook](https://guides.github.com/introduction/git-handbook/)

### Your normal end-to-end git workflow
When working with branches, you will typically follow a workflow like below:

1. Switch to master branch.  Run "```git checkout master```".  This will fail if you have pending changes.  If you don't have any pending changes that you care about, you can run "```git clean -d -f```".  If that doesn't solve the problem, run "```git stash```".  If you have changes that you cared about from a previous topic branch, see step 5 and come back here after step 7 or 8.  If you started making changes before following these steps, look at the [So you started coding before creating a topic branch](#so-you-started-coding-before-creating-a-topic-branch) section below.
2. Get the latest changes that exist on the server onto your local machine.  Run "```git pull```".
3. Create and switch to a topic branch for your change.  Your topic branch should have a name based on what you're trying to work on.  Run "```git checkout -b topicbranchname```" (don't forget to replace "topicbranchname"!).
4. Make changes to your code.
5. Commit all of your changes to your local topic branch.  Run "```git commit -a -m "description of my change"```".
6. Repeat steps 4 and 5 as necessary.
7. Share your changes with the world. Run "```git push```".  You will probably get a message saying that your topic branch isn't being tracked upstream.  You can either copy and paste the message that it gives you, or run something like "```git push --set-upstream origin topicbranchname```" (don't forget to repalce "topicbranchname"!).
8. Go to [https://www.github.com/irs1318dev], navigate to the repository you are working out of, and create a Pull Request to merge your changes into master.  If you can't figure that out, ask Will.

### So you started coding before creating a topic branch
If you started coding in "the wrong branch", usually you can recover from it as long as you don't have changes from that topic branch mixed in.  You can do something like:

1. Stash your changes.  Run "```git stash```".
2. Switch to master branch.  Run "```git checkout master```".
3. Get the latest changes that exist on the server onto your local machine.  Run "```git pull```".
4. Create and switch to a topic branch for your change.  Your topic branch should have a name based on what you're trying to work on.  Run "```git checkout -b topicbranchname```" (don't forget to repalce "topicbranchname"!).
5. Retrieve your changes from the stash.  Run "```git stash pop```".
6. Continue making changes to your code.  Follow steps 5-8 in the section above ([Your normal end-to-end git workflow](#your-normal-end-to-end-git-workflow)).

### Making Simple Operation changes
To add a new action that the robot can take with a mechanism, first open the ```AnalogOperation``` or ```DigitalOperation``` enum (AnalogOperation.java or DigitalOperation.java) and add a new value to the list in that file.  We try to keep the various operations organized, so we keep them listed in a different section for each Mechanism.  The operation should be named starting with the mechanism (e.g. "DriveTrain", "Intake", etc.), and then a description of the action (e.g. "Turn", "RaiseArm", etc.) to make one single pascal-case value (e.g. "DriveTrainTurn", "IntakeRaiseArm", etc.).  Remember that Analog/Digital Operations are a single, simple thing that is done by the robot.  Any more complex action that we want the robot to take will be a Macro which composes these Analog/Digital Operations together (which we will talk about later).

Next, you will open the ButtonMap.java file and add another mapping into the AnalogOperationSchema/DigitalOperationSchema that describes the AnalogOperation/DigitalOperation that you just added.  Remember that Analog Operations represent things that are done to a certain extent, using double (decimal) values typically between -1.0 and 1.0.  Digital Operations represent things that are either done or not done, using Boolean values (true or false).  Each type of Operation, Analog or Digital, has their own corresponding type of Description.

```java
    new AnalogOperationDescription(
        AnalogOperation.DriveTrainTurn,
        UserInputDevice.Driver,
        AnalogAxis.XBONE_RSX,
        ElectronicsConstants.INVERT_XBONE_RIGHT_X_AXIS,
        TuningConstants.DRIVETRAIN_X_DEAD_ZONE),
```

The Analog description takes parameters describing the User Input Device (Driver or Operator controller) and the axis of the joystick (X, Y, Throttle, etc.).  It also includes the ability to invert the axis (so that the "forward" direction matches positive) and the ability to provie a dead zone (as joysticks are often imperfect at mesauring the middle).

```java
    new DigitalOperationDescription(
        DigitalOperation.IntakeIn,
        UserInputDevice.Operator,
        UserInputDeviceButton.XBONE_A_BUTTON,
        ButtonType.Simple),
```

The Digital description takes arguments describing the User Input Device, the button on the joystick, and the type of button (Simple, Toggle, or Click).  Simple buttons are typically used for continuous actions (such as running an intake), Toggle actions are typically used for macros, and Click actions are typically used for single-shot actions (such as extending an arm).

### Adding a new Electronics Constant
To add a new constant that describes how the robot is wired/configured electronically, first open the ```ElectronicsConstants``` class (ElectronicsConstants.java) and add a new constant value.  We try to keep the various constants organized, so we keep them listed in a different section for each Mechanism.  Each constant is of the form:
```java
    public static final Type NAME = value;
```

The Type is almost always an integer "```int```" for electronics constants, representing the port where something is plugged in or an identity that has been assigned to the component.  The type may depend on the interface that is being used. 

The naming convention for our electronics constants is "MECHANISMNAME_COMPONENTNAME_INTERFACE".  The Name uses "yelling snake case", which is an all-caps form of snake case, where each word or compound-word is separated by the underscore character "_".  Within the name, "MECHANISMNAME" is a form of the Mechanism's name (e.g. "ELEVATOR" or "DRIVETRAIN").  The "COMPONENTNAME" is a form of the component's name (e.g. "ENCODER" or "LEFT_MOTOR").  The "INTERFACE" is one of a few possible values that describe which interface the component is connected through.  Most components are connected using a single interface, such as Sensors connected to a "DIO_CHANNEL" for digital inputs or an "ANALOG_CHANNEL" for analog inputs, Motors connected to a "PWM_CHANNEL", Motors using a specific "CAN_ID" when connected through the CAN bus.  But some components use two interfaces, such as "FORWARD_PCM_CHANNEL" and "REVERSE_PCM_CHANNEL" for DoubleSolenoids (pneumatics) and "DIO_CHANNEL_A" and "DIO_CHANNEL_B" for Encoders.

Lastly, the value is the specific port the component was plugged into or id that was assigned to the component.

### Adding a new Hardware or Tuning Constant
To add a new constant that describes how the robot is built or tuned, first open the ```HardwareConstants``` or ```TuningConstants``` class (HardwareConstants.java or TuningConstants.java) and add a new constant value.  We try to keep the various constants organized, so we keep them listed in a different section for each Mechanism.  Each constant is of the form:
```java
    public static final Type NAME = value;
```

The Type will depend on what is being tracked, usually an  "```int```", "```double```", or "```boolean```".

The naming convention for our tuning constants is that all of them start with "MECHANISMNAME_" and then is followed with a description of what is being kept in the constant.  The Name uses "yelling snake-case", which is an all-caps form of snake-case, where each word or compound-word is separated by the underscore character "_".

### Adding a new Logging Key
To add a new key for logging purposes, first open the ```LoggingKey``` enum (LoggingKey.java) and add a new value to the list in that file.  We try to keep the various logging keys organizated by mechanism, so please keep them sorted in a sensible order.  Each logging key is of the form:
```java
    Name("value"),
```

The name is of the form "MechanismState", where the first part is the name of the mechanism (e.g. "Intake" or "DriveTrain") and the second part is the state that is being logged (e.g. "IsExtended" or "LeftDistance").  The name uses pascal-case, where multiple words are included and separated by capitalizing the first letter of each word.  The value is of the form "m.state", where the first part is a 1- to 2-letter abbreviation for the mechanism (e.g. "i" for intake or "dt" for DriveTrain) that is unique for the mechanisms on the robot and the second part is a camel-case form of the state.  Camel-case is like pascal-case, except the first letter of the element is lower-case.

Put together, an entry for the DriveTrain's Left Distance would look like:
```java
    DriveTrainLeftDistance("dt.leftDistance"),
```

### Writing a new Mechanism
Mechanisms handle the interactions with the actuators (e.g. motors, pneumatic solenoids) and sensors (e.g. encoders, limit switches) of each part of the robot, controlling them based on the operations from the Driver.  A mechanism is a class that implements the ```IMechanism``` interface with a name based on the name of that portion of the robot (e.g. DriveTrain, Intake) combined with "Mechanism", such as ThingMechanism.  It should be placed within the mechanisms folder with the other mechanisms and managers.

#### Define mechanism class and member variables
```java
@Singleton
public class ThingMechanism implements IMechanism
{
  // driver
  private final IDriver driver;

  // sensors and actuators
  private final ISomeSensor nameOfSensor;
  private final ISomeActuator nameOfAcutator;

  // logger
  private final ILogger logger;

  // sensor values
  private boolean someSetting;

  // mechanism state
  private boolean someState;
```

At the top of the class, you should have the driver ("```private IDriver driver;```"), followed by a list of the definitions of your different actuators and sensors ("```private final ISomeActuator nameOfActuator;```" and "```private final ISomeSensor nameOfSensor;```").  These will be initialized in the constructor.  After the driver and set of actuators and sensors are defined, you will also need to define the logger ("```private ILogger logger;```"), anything that will be read from the sensors ("```private boolean someSetting;```"), and any state that needs to be kept for the operation of the mechanism ("```private boolean someState;```").

#### Write mechanism constructor
```java
  @Inject
  public ThingMechanism(IDriver driver, IRobotProvider provider, LoggingManager logger)
  {
    this.driver = driver;

    this.nameOfSensor = provider.GetSomeSensor(ElectronicsConstants.THING_NAMEOFSENSOR_PWM_CHANNEL);
    this.nameOfActuator = provider.GetSomeActuator(ElectronicsConstants.THING_NAMEOFACTUATOR_PWM_CHANNEL);

    this.logger = logger;

    this.someSetting = false;
    this.someState = false;
  }
  ...
```

After defining all of the class's variables, you will define a constructor named like "```public ThingMechanism(IDriver driver, IRobotProvider provider, LoggingManager logger)```".  Since 2017 we’ve made use of Google’s Guice to control dependency injection, which is the reason why the special ```@Inject``` markup is required.  You will first set the driver to the value that is provided to the constructor by Guice.  You will then set the value for each actuator and sensor you defined earler by calling the corresponding function on the ```IRobotProvider``` that is also passed into the constructor.  These functions will take some number of arguments based on how the actuators/sensors are physically plugged together in the robot (such as CAN Ids, DIO channel, Analog channel, PCM channel, or PWM channel).  These arguments should be placed as constants in the ElectronicsConstants file with names such as THING_NAMEOFACTUATOR_PWM_CHANNEL.  We don’t necessarily know in advance how the robot plugs together, so they can be initialized with a value of -1 until we do.  After initializing the sensors and actuators, you should set the logger as provided and the settings and states to their default values.

#### Write mechanism readSensors function
```java
  @Override
  public void readSensors()
  {
    this.someSetting = this.nameOfSensor.get();

    this.logger.logBoolean(LoggingKey.ThingSomeSetting, this.someSetting);
  }
```

The ```readSensors()``` function reads from the relevant sensors for that mechanism, stores the results in class member variables, and then logs the results to the logger.  Most simple sensor types have a simple ```get()``` function or similar to read the current value from that sensor.  An entry in the ```LoggingKey``` enum will need to be added to correspond to each setting that we want to log.

#### Write mechanism update function
```java
  @Override
  public void update()
  {
    boolean shouldThingAction = this.driver.getDigital(DigitalOperation.ThingAction);

    double thingActionAmount = 0.0;
    if (shouldThingAction)
    {
      thingActionAmount = TuningConstants.THING_ACTION_AMOUNT;
    }

    this.nameOfActuator.set(thingActionAmount);
  }
```

The ```update()``` function examines the inputs that we retrieve from the ```IDriver```, and then calculates the various outputs to use applies them to the outputs for the relevant actuators.  For some mechanisms, the logic will be very simple - reading an operation and applying it to an actuator.  Other mechanisms will involve some internal state and information from the most recent readings from the sensors, and possibly some math in order to determine what the actuator should do.  Note that there will often be a "degree" to which something should be done that we don't know in advance.  For example, if we are intaking a ball we may want to carefully choose the correct strength to run the motor at.  Because we don't know this value in advance and will discover it experimentally, we should put such values into the ```TuningConstants``` file as a constant with a guess for the value.

#### Write mechanism stop function
```java
  @Override
  public void stop()
  {
    this.nameOfActuator.set(0.0);
  }
```

The stop function tells each of the actuators to stop moving.  This typically means setting any Motor to 0.0 and any DoubleSolenoid to kOff.  It is called when the robot is being disabled, and it is very important to stop everything to ensure that the robot is safe and doesn't make any uncontrolled movements.

#### Write any getter functions
```java
  public boolean getSomeSetting()
  {
    return this.someSetting;
  }
```

When there are sensors being read, often we will want to incorporate the data that they return into the running of tasks as a part of macros and autonomous routines.  In order to support that, we must add getter functions so that the tasks can access the values that were read from the sensors.  These functions just simply return the value that was read during the readSensors function.  Typically we can skip writing these until a task is being written needs this information.

### Writing Macros and Autonomous Routines
Macros and Autonomous routines both involve control tasks.  These tasks control the robot through setting Analog/Digital Operations.  For more advanced tasks, they can read the current state of the robot by running the functions that expose values that are read from sensors on the Mechanism.

#### Writing Control Tasks
Tasks are used to control operations or groups of operations that run until a certain condition is met.  A task is a class that implements the ```IControlTask``` interface, and typically extends from the ```ControlTaskBase``` or ```TimedTask``` class.  Tasks are named based on the sort of action they perform (e.g. RaiseElevator) combined with "Task", such as RaiseElevatorTask.  It should be placed within the controltasks folder (which is within the driver folder) with the other tasks.

##### Define task class, member variables, and constructor
```java
public class RaiseElevatorTask extends ControlTaskBase implements IMechanism
{
  private ElevatorMechanism elevator;

  public RaiseElevatorTask()
  {
  }
```

At the top of the class, you should declare any member variables that you need.  Some of these member variables may be initialized in the constructor, such as when your task has parameters like a time duration, whereas other member variables will be initialized in the begin function.  Note that the constructor could be called well before the task actually starts, which is why we have the begin function below.

##### Write task begin function
```java
  public void begin()
  {
    this.elevator = this.getInjector().getInstance(ElevatorMechanism.class);
  }
```

The ```begin()``` function is called at the very beginning of the task, and can be used to set some initial state and retrieve any mechanism that we need to reference.  Note that this function is called right before ```hasCompleted()``` and ```update()``` are called for the first time.

##### Write task update function
```java
  public void update()
  {
    this.setDigitalOperationState(DigitalOperation.ElevatorRaise, true);
  }
```

The ```update()``` function is called every ~20ms and should update the relevant operations.

##### Write task end function
```java
  public void end()
  {
    this.setDigitalOperationState(DigitalOperation.ElevatorRaise, false);
  }
```

The ```end()``` function is called when the task has ended.  The function resets the operations that were used to their default value and should clear any state that needs to be cleared.

##### Write task hasCompleted function
```java
  public boolean hasCompleted()
  {
    return this.elevatorMechanism.isRaised();
  }
```

The ```hasCompleted()``` function is called by the ```Driver``` class to check whether the particular task should complete.  Often this is based on either the amount of time has elapsed since the task began, or it could be based on some sensor condition being met.

##### Write task shouldCancel function (optional)
```java
  public boolean shouldCancel()
  {
    return this.elevatorMechanism.isBroken();
  }
```

The ```shouldCancel()``` function is called by the ```Driver``` class to check whether the particular task should be cancelled prematurely.  This is used in a few situations, such as when we are unable to perform an action anymore because a precondition is not met.

##### Write task stop function (optional)
```java
  public void stop()
  {
    this.setDigitalOperationState(DigitalOperation.ElevatorRaise, false);
  }
```

The ```stop()``` function is called when the task has ben cancelled or interrupted.  The function resets the operations that were used to their default value and should clear any state that needs to be cleared.  Typically tasks do the same thing during ```stop()``` as they do during ```end()```, so when that is the case just ```end()``` needs to be implemented/overridden.

#### Adding Macros
To add a new Macro, you should add a new entry to the ```MacroOperation``` enumeration, and a new ```MacroOperationDecription``` to the MacroSchema within the ```ButtonMap``` class.

```java
    new MacroOperationDescription(
        MacroOperation.SomeMacro,
        UserInputDevice.Driver,
        UserInputDeviceButton.XBONE_X_BUTTON,
        ButtonType.Toggle,
        () -> new SomeTask(),
        new Operation[]
        {
            DigitalOperation.ThingAction,
        }),
```

The MacroOperationDescription requires arguments describing the user input device to use, the button that triggers the macro, the type of button to use (either ```Simple``` or ```Toggle```), a supplier for the task that should be used within the macro (```() -> new SomeTask()```), and a list of the different operations that this macro uses.

#### Adding Autonomous Routines
To add a new Autonomous Routine, you should add a new function to the ```AutonomousRoutineSelector``` class that returns an ```IControlTask``` that performs the set of tasks that make up the routine.

```java
    private static IControlTask GetMySpecialRoutine()
    {
        return 
                SequentialTask.Sequence(
                  new WaitTask(3.0),
                  new DriveForwardTask(3.5),
                  new NavxTurnTask(-90),
                  new WaitTask(3.0),
                  new DriveForwardTask(3.5));
    }
```

You can see more about [composing tasks](#composing-tasks-together) below.

```java
    if (routine == AutoRoutine.MySpecialRoutine)
    {
        return AutonomousRoutineSelector.GetMySpecialRoutine();
    }
```

Additionally, you will want to change the decision logic in the ```selectRoutine()``` function and possibly add new entries to the ```StartPosition``` and/or ```AutoRoutine``` enumerations.  When the criteria are met for running your routine, the function should return the task you decided upon above.

#### Composing Tasks together
Tasks can be grouped together in interesting ways to describe more complex tasks.  By having tasks happen in a certain order and sometimes simultaneously, you can end up with a routine that performs very interesting things built out of much simpler re-usable tasks.  To do this, you can utilize SequentialTask and ConcurrentTask.

##### SequentialTask.Sequence()
Sequential task starts and completes each task in the order they are listed.

```java
SequentialTask.Sequence(
  new WaitTask(3.0),
  new DriveForwardTask(3.5));
```

The example above is a sequence of two tasks, where it will first wait 3 seconds and then will drive 3.5 inches forward.

##### ConcurrentTask.AnyTasks()
Concurrent AnyTasks starts all of the tasks at the same time and completes them all when one of them has considered itself to be completed.

```java
ConcurrentTask.AnyTasks(
  new WaitTask(3.0),
  new DriveForwardTask(3.5));
```

The example above is a pair of two tasks that will execute at the same time, completing when either 3 seconds has elapsed OR once the robot has driven 3.5 inches forward.

##### ConcurrentTask.AllTasks()
Concurrent AllTasks starts all of the tasks at the same time and completes when all of them have considered themselves to be completed.

```java
ConcurrentTask.AllTasks(
  new WaitTask(3.0),
  new DriveForwardTask(3.5));
```

The example above is a pair of two tasks that will execute at the same time, completing when the task has taken 3 seconds AND the robot has driven 3.5 inches forward.

## Advanced Topics
### PID Controllers
"PID" stands for **P**roportional **I**ntegral and **D**erivative.  PID is a way of controlling a part of a robot that incorporates feedback from sensors in order to control the operation of the robot.  PID is often used as a way for correcting for _error_ caused by things like friction or other forces pushing back on the robot.  PID takes in values according to put in the current _measured value_ (the value discovered from an encoder or other sensor) and _setpoint_ (the desired value).  We typically use PID for elevators and for Positional control in the drivetrain.  For Velocity control we also use Feed-Forward to provide additional control.

With PID, there are different constant values that need to be discovered experimentally for the P, I, D, and F values.  Typically, F is only used for Velocity control.  P is used for basically all PID controllers.  I is used to correct error from slight overshoots or undershoots over time.  D is used to reduce oscillation around the setpoint.  For more information, Wikipedia has an ok article on PID.

### Motion Planning
[Video](http://www.youtube.com/watch?v=8319J1BEHwM)

### Vision
To-Do.

### PathPlanner
PathPlanner is a library that is used to create autonomus paths that are called in the ```AutonomousRoutineSelector```

Every robot path called trajectory need to have a ```trajecotryManager``` which stores all generated paths and needs to be passed in as the first parameter of the path to store all the paths. ```pathPlanner.buildTrajectory()``` builds the trejectory and takes in a Maximum velocity and maximum acceleration the robot can reach on a given path. It also takes any number of ```PathPlannerWaypoint()``` which are the points the robots travels along and the last paramerter is the name of the Path. The ```PathPlannerWaypoint()``` takes a X-Coordinate, Y-Coordinate, a headding which is the direction the robot will move into the point, and the orientation which is the direction the robot will face in which while traveling to the given point.

```java
addTrajectory(
            trajectoryManager,
            pathPlanner.buildTrajectory(
                TuningConstants.DRIVETRAIN_TRUE_MAX_PATH_TRANSLATIONAL_VELOCITY,
                TuningConstants.DRIVETRAIN_MAX_PATH_TRANSLATIONAL_ACCELERATION,
                new PathPlannerWaypoint(200, 200, -180, 0),
                new PathPlannerWaypoint(100, 100, -180, 0)),
                "TheBestPathEveryWritten");
```
