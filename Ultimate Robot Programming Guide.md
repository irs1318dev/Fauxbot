Planned chapter guide:

Overview x

Intro to Actuators x

Intro to Sensors x

Intro to Mechanisms x

Intro to Robot Structure x

Intro to Robot Code Design x

Introduce Driver, Mechanisms x

Intro to Mechanism Code x

Structure x

(NOT MENTIONED: LoggingKeys, ControlTasks, Macros, Shifts, Autonomous Routines, External Libraries, Guice, OpenCV, CTRE Phoenix, Spark MAX API, NavX MXP, JUnit, Mockito)

Using Actuators x

Fauxbot Exercise 1 (Forklift) x

Code organization

Tuning, Hardware, Electronics Constants x

Intro to Driver x

ButtonMap, Operations x

Using Sensors

Fauxbot Exercise 2 (Garage Door)

Logging

Intro to PID, Positional PID

Fauxbot Exercise 3 (Elevator)

Velocity PID

Advanced Motor Controllers

Talon SRX, Talon FX, Spark MAX

Fauxbot Exercise 4 (Shooter)

Intro to Macros

Fauxbot Exercise 5 (Printer)

Advanced Macros

Fauxbot Exercise 5b (Printer Macro)

Intro to Autonomous

Intro to Path Planner

Intro to Trapezoidal Motion Profiling

Motion Magic


## Overview
> "Everything should be made as simple as possible, but not simpler." - Albert Einstein

Welcome to the Issaquah Robotics Society's Robot Programming Guide!

The Issaquah Robotics Society’s Robot code is designed to be a good example of a moderately large software project that students of varying levels of experience with programming can contribute to. This guide is meant to help you learn each component of the robot controlled by software, and act as a reference in robot programming projects.

First, it is crucial to understand how different parts of the robot work in tandem to comprise a functional machine.

## General Robot Design

Robots in FRC tend to have a small set of different pieces that we want to utilize, that can be arranged in a large number of ways to make complex mechanisms. These mechanisms are designed before the code for them is written, so you should know what they are composed of by the time you are writing any code. I’ll list out a few of these pieces here and explain what they do and how we typically use them.


### Actuators

Actuators can be defined as any physical component that converts electrical signal to motion. Think of it in the context of "actualizing your dreams," except you are an electric signal and the dream is for a linear actuator to extend.

Examples of actuators include motors and pistons.

Motors are devices that convert electrical power into rotational motion. They can move a variety of parts such as wheels, flywheels (for shooting things), electric fans, elevators, garage doors, linear actuators, and much more.

Pistons are mechanisms powered by pneumatics or hydraulics, and utilize pressure to extend or retract. Pistons have binary states, as they are controlled by pressure of gas or sometimes liquid. So, for example,

Some piston-looking mechanisms may not actually be pistons, however, as there are mechanisms called linear actuators that look similar but have motorized control systems.

### Sensors

The word "sensor" encapsulates many different types of sensors, including limit switches, encoders, through-beam sensors, and distance sensors. Essentially, the word means "something that reports information." They sense certain behaviors of objects. One example a garage door's through beam sensor, that senses if something is in the way of the door closing and stops the door. Another example of a sensor is an encoder inside of a wheel, that counts how many times that wheel has spun. These are numbers that can be accessed in code and used to detect things such as if the robot is carrying cargo, or how far it has travelled.


## RoboRIO

RoboRIO is the name of the computing device that is used to control the robot. It is produced by National Instruments and runs a customized version of Linux on an ARM processor. We write code that uses a library called WPILib (provided by FIRST) to handle interactions between the Robot, Driver Station, etc.

## Intro to Mechanisms and Tasks

A Robot can be thought of as a culmination of numerous different "mechanisms." A mechanism file encapsulates the control of related systems. For example, an intake mechanism may intake some cargo, and control numerous motors and recieve information from sensors at the same time. When given the command, it will start running the intake motors to intake cargo. At the same time, it will constantly check whether or not a through-beam in the intake has been broken to determine if cargo has been recieved, and then stop the motors. These would all be written into a singular mechanism as functions. If these systems need to interact with a human operator, functions can be written and then called from ``ButtonMap``.

Once mechanisms are defined and some functions of those mechanisms are created, the mechanism can then be implemented into "tasks." For example, an arm (which has numerous actuators and encoders) may have to move at the same time as the drivetrain (which has numerous actuators and encoders) to accomplish a certain task. The functions ``armUp(10)`` and ``driveTrainForward(10)`` can be used in a single "task" file without worrying about the specifics of how those mechanisms will move.

## Intro to Robot Code Structure

There are a couple of basic files a programmer will edit that are crucial to the operation of an IRS 1318 robot. These are ``TuningConstants``, ``ElectronicsConstants``, ``ButtonMap``, ``DigitalOperation``, ``AnalogOperation``, and as aforementioned, any ``SomethingMechanism`` or ``SomethingTask`` files.

Digital and Analog operations will be covered later. It is important to note that they are essentially lists of operations that can happen when buttons are pressed on a controller, so for an action to work, it must be registered as one of the operations depending on the type.

### TuningConstants

In order to simplify tuning the settings of the robot, we store any "magic numbers" that we will likely want to change as constants in the ```TuningConstants``` class.  Settings that may need to be tuned include things like the speed at which to run an intake, or the speed at which to turn when the joystick is in a certain position.  These settings are usually things that are hard to know in advance, and the appropriate settings are discovered by testing the robot.  There are many things that aren’t known in advance by the software team, so putting all of these things in ```TuningConstants``` in an organized fashion can help speed up the tuning process of the robot and prevent bugs.

ex: 

```java 
public static final double FLYWHEEL_SHOOT_SPEED = 13.18;
```

This allows for quick editing and tuning of the robot. 

### ElectronicsConstants

```ElectronicsConstants``` is a class that holds constant values describing all of the physical connections (PWM channels, Digital IO channels, Analog IO channels, CAN ids, etc.) that are needed to be known in order to control the correct output device and read the correct sensors.  We keep this information in a separate class (and all in a single file) so that there is only one place to update if the Electronics sub-team needs to re-run the wiring, or in case there are wiring differences between the practice robot and the competition robot.

ex: 

```java 
public static final int JOYSTICK_DRIVER_PORT = 0;
```

This joystick will be plugged into the driver port 0. That is how we will know what port to communicate with this joystick through.

### ButtonMap

The ```ButtonMap``` contains the mapping of various joystick/controller buttons and axes to the corresponding digital and analog operations.  The ```Driver``` class is in charge of reading from the joysticks and button pads during the teleop mode, and it uses the ```ButtonMap``` schemas to translate the individual actions taken on the joystick into ```DigitalOperation```s, ```AnalogOperation```s, and ```MacroOperation```s.

ex: 

```java 
new DigitalOperationDescription(
            DigitalOperation.FireButton,
            UserInputDevice.Driver,
            UserInputDeviceButton.XBONE_B_BUTTON,
            ButtonType.Click
        );
```
		
This is what a button defintion will look like. Once the Xbox One Controller button "B" is clicked, ``FireButton`` will become ``true``. Code in a ``ShooterMechanism`` will have something like this written in its ``update()`` loop:

```java 
boolean fireButtonPressed = driver.getDigital(DigitalOperation.FireButton);
if (fireButtonPressed){
            kicker.set(DoubleSolenoidValue.Forward);
            flywheelMotor.set(100);
            kickerOn = true;
        }
        hoodMotor.set(scaledHoodPos);
```
		
Which will cause the hood to start adjusting and activate the kicker, to fire a ball.

### Driver

This file will not be edited often.
The ```Driver``` is the actor that controls the robots.  The ```Driver``` class triggers different Operations to occur based on the intent of the current actor that is controlling the robot (autonomous or user).

## Intro to Mechanisms
A mechanism is a piece of code (generally a file) that controls a specific part of the robot. 

Each mechanism should control and manage
1. Reading of sensors
2. Control of acuators

For example the intake mechanism could handle the intake of game pieces by using user input, handled by another class, to determine weather to move the acuators to clamp the game object or move them out to release.

## Structure of a mechanism (BEGINNERS MAY SKIP)

Mechanisms handle the interactions with the actuators (e.g. motors, pneumatic solenoids) and sensors (e.g. encoders, limit switches) of each part of the robot, controlling them based on the operations from the Driver. A mechanism is a class that implements the IMechanism interface with a name based on the name of that portion of the robot (e.g. DriveTrain, Intake) combined with "Mechanism", such as ThingMechanism. It should be placed within the mechanisms folder with the other mechanisms and managers.

For example, an intake mechanism may intake some cargo, and control numerous motors and recieve information from sensors at the same time. When given the command, it will start running the intake motors to intake cargo. At the same time, it will constantly check whether or not a through-beam in the intake has been broken to determine if cargo has been recieved, and then stop the motors. These would all be written into a singular mechanism as functions, and called from the ButtonMap.

Define mechanism class and member variables

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

At the top of the class, you should have the driver (``private IDriver driver;``), followed by a list of the definitions of your different actuators and sensors (``private final ISomeActuator nameOfActuator;``) and (``private final ISomeSensor nameOfSensor;``). These will be initialized in the constructor. After the driver and set of actuators and sensors are defined, you will also need to define the logger (``private ILogger logger;``), anything that will be read from the sensors (``private boolean someSetting;``) and any state that needs to be kept for the operation of the mechanism (``private boolean someState;``).

##### Write mechanism constructor
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
```

After defining all of the class's variables, you will define a constructor named like "``public ThingMechanism(IDriver driver, IRobotProvider provider, LoggingManager logger)``". Since 2017 we’ve made use of Google’s Guice to control dependency injection, which is the reason why the special ``@Inject`` markup is required. You will first set the driver to the value that is provided to the constructor by Guice. You will then set the value for each actuator and sensor you defined earlier by calling the corresponding function on the IRobotProvider that is also passed into the constructor. These functions will take some number of arguments based on how the actuators/sensors are physically plugged together in the robot (such as CAN Ids, DIO channel, Analog channel, PCM channel, or PWM channel). 

These arguments should be placed as constants in the ElectronicsConstants file with names such as ``THING_NAMEOFACTUATOR_PWM_CHANNEL`` (The ``ElectronicsConstants`` file will be covered later). We don’t necessarily know in advance how the robot plugs together, so they can be initialized with a value of -1 until we do. After initializing the sensors and actuators, you should set the logger as provided and the settings and states to their default values.

##### Write mechanism readSensors function:

```java
  @Override
  public void readSensors()
  {
    this.someSetting = this.nameOfSensor.get();

    this.logger.logBoolean(LoggingKey.ThingSomeSetting, this.someSetting);
  }
The ``readSensors()`` function reads from the relevant sensors for that mechanism, stores the results in class member variables, and then logs the results to the logger. Most simple sensor types have a simple ``get()`` function or similar to read the current value from that sensor. An entry in the ``LoggingKey`` enum will need to be added to correspond to each setting that we want to log.
```

##### Write mechanism update function:
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

The ``update()`` function examines the inputs that we retrieve from the ``IDriver``, and then calculates the various outputs to use applies them to the outputs for the relevant actuators. For some mechanisms, the logic will be very simple - reading an operation and applying it to an actuator (extend, retract, like a piston). Other mechanisms will involve some internal state and information from the most recent readings from the sensors, and possibly some math in order to determine what the actuator should do. Note that there will often be a "degree" to which something should be done that we don't know in advance (like how linear actuators can extend certain specific amounts). If we are intaking a ball, we may want to carefully choose the correct strength to run the intake motor at. Because we don't know this value in advance and will discover it experimentally, we should put such values into the ``TuningConstants`` file as a constant with a guess for the value. This value should be low as to prevent any accidents.

##### Write mechanism stop function:

```java
  @Override
  public void stop()
  {
    this.nameOfActuator.set(0.0);
  }
```

The stop function tells each of the actuators to stop moving. This typically means setting any ``Motor`` to 0.0 and any ``DoubleSolenoid`` to ``kOff``. It is called when the robot is being disabled, and it is very important to stop everything to ensure that the robot is safe and doesn't make any uncontrolled movements.

Relationship chart of classes:

![image](https://github.com/irs1318dev/irs1318_2023/assets/62030864/e1b65b44-c64c-455c-9a68-13be3637f422)


## IMechanism
Mechanism classes handle the reading of all of the sensors and control of all of the actuators on each mechanism of the robot.  There is one Mechanism class for each individual part of the robot, named using the pattern "ThingMechanism" (where "Thing" is the name of the mechanism, like "DriveTrain").  Mechanisms read from all of the Sensors and translate the Operations from the Driver into the functions that need to be called on the individual Actuators.  This typically involves some math and logic to convert the data from the operations into the particular actions that need to happen.  For example, when using a typical Tank drivetrain, the DriveTrain Mechanism calculates the speed settings to apply to the left and right motors based on the DriveTrainMoveForward operation and the DriveTrainTurn operation.  Also, there may be other concerns to take care of, such as how to respond based on the presence or absence of a setting from another operation or a sensor.

The Mechanisms implement the ```IMechanism``` interface which has the definitions of functions that every Mechanism must implement.  In the mechanism, the ```readSensor()``` and ```update()``` functions are the most important, and are called every ~20 milliseconds.  The ```readSensors()``` function reads the current values from all of the sensors and stores them locally in member variables for that ThingMechanism object.  The ```update()``` function calculates what should be applied to the output devices based on the current Operations and the data we previously read from the sensors.  It is important that these functions execute quickly, so anything that depends on a certain length of time elapsing should be calculated between separate runs of the function and not involve any long-running loops or sleeps.  Most actions that take multiple iterations of the ```update()``` function or depend on time elapsing belong in a macro instead of being hard-coded into the Mechanism, though it is also possible for there to be state kept in member variables to help keep track of what that mechanism should be doing.

## Analog and Digital Operations

Let's clear up what a Digital and Analog Operation is:
An _analog_ operation is an operation that requires a _continuous_ signal. A _digital_ operation is an operation that has a "chunky" signal. Examples of analog operations include Joysticks, pedals, any one button that has numerous continuous states. A digital operation can be a single button, that is either pressed or not pressed. We can know how long it is held for, but nothing more.

![image](https://github.com/irs1318dev/irs1318_2023/assets/62030864/385d43e1-d6c9-4d61-a74d-7a19fa15d7b7)



Now that you understand these fundamental concepts, it's time for the first exercise.

## Begin Fauxbot Exercise One: Forklift

First, you will make sure that you can open the exercise window. Ask your mentor/lead about ``gradlew build``, ``gradlew deploy``.

This task will require you to write ```ForkliftMechanism```. Since this particular machinery is relatively simple, it can be written as a single mechanism. Specific actuators for this exercise can be found in ``ForkliftExercise.md``.

Put simply, here is how ```ForkliftMechanism``` will operate:

The forklift can move left and right, and also move its lifter up and down.

1. First, you will define everything needed for the class to work. This includes the driver, drivetrain motors, and lifter. 

2. In the update loop, you will determine what checks need to be made, and how to act on those checks. This will likely only be checking for button inputs.

3. This means that you must have actions that happen when a button is pressed. 

4. Operation names must be defined in either ``AnalogOperation`` or ``DigitalOperation``, and buttons must be assigned to each operation.

TuningConstants and ElectronicsConstants are optional, but can be used.

First, find or make the folder ``mechanisms`` in ``Fauxbot> core_robot> src> main\java\frc\robot``. Create ``ForkliftMechanism.java`` within the folder. Next to public class ForkliftMechanism, add ``implements IMechanism``. Follow VS Code prompts to fix imports and errors, and you should end up with something like this:

![image](https://github.com/irs1318dev/irs1318_2023/assets/62030864/7eac8cab-e0b8-4bcd-9a7f-22db979db2d9)


Your Forklift will have a left motor, right motor, and lifter. You must also define the driver. All mechanisms must have their components first stated on the class level, and then assigned in the ``@inject``. Keep in mind that our programming team likes to use the ``this.something`` syntax when possible, to avoid confusion between items named similarly. 


For this first exercise, an example is provided below:

![image](https://github.com/irs1318dev/irs1318_2023/assets/62030864/e39b6a2e-1935-470b-ba0e-be89e21e1317)

Apply your imports as you go. When making mechanisms, imports can usually be pasted from similar mechanisms.

In this simulation, each electronic part of the forklift has its own channel, similar to how a real robot would have components plugged into different ports. These are defined in ``ElectronicsConstants``. The forklift motor has a left and right channel, and the double solenoid has a forward and reverse channel. Assign those to 0, 1, 7, and 8, respectively.

This exercise does not utilize ``readSensors()``.

The ``update()`` loop is where you controll all of your actuators. You want your forklift to move when given the command to. Now, think about the two actuators on this component. Are they digital, or are they analog? Assign those actions in ``DigitalOperation`` and ``AnalogOperation``. What you will write in the ``update()`` loop for _digital_ operations is simple ``if`` statements about whether the desired command is ``true``. Your ``update()`` loop must also constantly check whether the desired _analog_ states have changed and update those.

## Begin Fauxbot Exercise Two: Garage Door

This exercise will introduce you to using sensors. 

A garage door has these parts:
motor, through-beam sensor, open sensor, closed sensor.

Your garage door will have numerous states, including moving up, moving down, open, and closed. Button presses and your through-beam sensor will determine what state the garage should be in.

![download](https://github.com/irs1318dev/irs1318_2023/assets/62030864/4c727b72-b986-4eba-a907-72116ab06705)

Begin by creating a new class called GarageDoorMechanism. You may copy imports from Forklift. The ``IMechanism`` structure will always be used.

Below are SOME of the necessary definitions to continue with this exercise:

```java
private final IDigitalInput openSensor;
private final IDigitalInput closedSensor;
private final IDigitalInput throughBeamSensor;

public enum GarageDoorState
    {
        Opened,
        Opening,
        Closed,
        Closing
    }
```
If you are unsure how to define actuators, refer to the Forklift exercise.

After you have defined these sensors and enumerators, you will need to set up three booleans: one for each of the three possible sensor detections: Whether the through beam is broken, whether the door is closed, and whether the door is open.

The ``readSensors()`` loop exists for you to update your sensor statuses (hint: alter booleans status). There are numerous ways to structure robot code for it to run, but it is good practice to standardize sensor updates like this within the project.

Now, in your ``update()`` loop, you will need to write logic for how to act on the information that the sensor provides. Specific actions is written in ``GarageDoorExercise.md``.


## Troubleshooting Visual Studio Code

Visual Studio Code handles many things at the same time, so this can be an ongoing list of issues that you may run into when using it.

The below method works for fixing this:

![brokencode](https://github.com/irs1318dev/irs1318_2023/assets/62030864/e174f449-9de3-48cd-9139-f6a57086654e)

One way that often fixes any problems you may be having regarding synchronizing with the repository, or VSC not recognizing libraries or imports, is to simply clone the repository again. Go to the folder where your cloned files are and delete everything. Make sure that beforehand, you stash your changes in git or manually (manual recommended). Then, clone the repository again. This usually works. If it does not, then that means there is a fundamental issue with the code. Do not commit your code if you are having issues.
