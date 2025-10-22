# Adding a new Hardware Constant

To add a new constant that describes how the robot is built phyically, first open the ```HardwareConstants``` class (HardwareConstants.java under core_robot\src\main\java\frc\robot) and add a new constant value.  We try to keep the various constants organized, so we keep them listed in a different section for each Mechanism.  Each constant is of the form:
```java
    public static final Type NAME_YELLING_SNAKE_CASE = value;
```

The Type will depend on what is being tracked, usually an  "```int```", "```double```", or "```boolean```".

The naming convention for our hardware constants is "MECHANISMNAME_COMPONENTNAME_DESCRIPTION".  The Name uses "yelling snake-case", which is an all-caps form of snake-case, where each word or compound-word is separated by the underscore character "\_".  Within the name, "MECHANISMNAME" is a form of the Mechanism's name (e.g. "ELEVATOR" or "DRIVETRAIN").  The "COMPONENTNAME" is a form of the component's name (e.g. "ENCODER" or "LEFT_MOTOR" or "ARM").  And the "DESCRIPTION" is some description of what is being measured, such as the number of ticks per rotation in an encoder, or a gear ratio, or the length of an arm segment.
