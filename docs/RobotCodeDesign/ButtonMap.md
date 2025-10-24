# ButtonMap

The ```ButtonMap``` contains the mapping of various joystick/controller buttons and axes to the corresponding digital and analog operations.  The ```Driver``` class is in charge of reading from the joysticks and button pads during the teleop mode, and it uses the ```ButtonMap``` schemas to translate the individual actions taken on the joystick into ```DigitalOperation```s, ```AnalogOperation```s, and ```MacroOperation```s.

[Shifts](Shifts.md) are also used for enhancing the ability for.

[Operation Contexts](OperationContext.md)
