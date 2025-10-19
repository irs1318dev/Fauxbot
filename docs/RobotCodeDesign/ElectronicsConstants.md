# ElectronicsConstants

```ElectroincsConstants``` is a class that holds constant values describing all of the physical connections (PWM channels, Digital IO channels, Analog IO channels, CAN ids, etc.) that are needed to be known in order to control the correct output device and read the correct sensors.  We keep this information in a separate class (and all in a single file) so that there is only one place to update if the Electronics sub-team needs to re-run the wiring, or in case there are wiring differences between the practice robot and the competition robot.
