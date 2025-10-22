# Adding a new Electronics Constant

To add a new constant that describes how the robot is wired/configured electronically, first open the ```ElectronicsConstants``` class (ElectronicsConstants.java under core_robot\src\main\java\frc\robot) and add a new constant value.  We try to keep the various constants organized, so we keep them listed in a different section for each Mechanism.  Each constant is of the form:
```java
    public static final Type NAME_YELLING_SNAKE_CASE = value;
```

The Type is almost always an integer "```int```" for electronics constants, representing the port where something is plugged in or an identity that has been assigned to the component.  The type may depend on the interface that is being used. 

The naming convention for our electronics constants is "MECHANISMNAME_COMPONENTNAME_INTERFACE".  The Name uses "yelling snake case", which is an all-caps form of snake case, where each word or compound-word is separated by the underscore character "_".  Within the name, "MECHANISMNAME" is a form of the Mechanism's name (e.g. "ELEVATOR" or "DRIVETRAIN").  The "COMPONENTNAME" is a form of the component's name (e.g. "ENCODER" or "LEFT_MOTOR").  The "INTERFACE" is one of a few possible values that describe which interface the component is connected through.  Most components are connected using a single interface, such as Sensors connected to a "DIO_CHANNEL" for digital inputs or an "ANALOG_CHANNEL" for analog inputs, Motors connected to a "PWM_CHANNEL", or Motors using a specific "CAN_ID" when connected through the CAN bus.  But some components use two interfaces, such as "FORWARD_PCM_CHANNEL" and "REVERSE_PCM_CHANNEL" for DoubleSolenoids (pneumatics) and "DIO_CHANNEL_A" and "DIO_CHANNEL_B" for simple quadrature Encoders.

Lastly, the value is the specific port the component was plugged into or id that was assigned to the component.
