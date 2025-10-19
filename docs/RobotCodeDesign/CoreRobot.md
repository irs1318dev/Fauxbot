# CoreRobot.java

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
