# External Libraries

The robot code makes use of a number of external libraries in order to make programming the robot more straightforward.

## Guice
[Guice](https://github.com/google/guice) (pronounced "juice") is a dependency injection library, which is responsible for the various "@Inject" and "@Singleton" markup that is seen throughout the code.  The purpose of Guice is to make it easier to plug together the entire system in such a way that it is still unit-testable and able to be simulated in Fauxbot.  You will need to use Guice's @Singleton and @Inject markup when writing a mechanism.

## CTRE Phoenix
[CTRE Phoenix](https://docs.ctr-electronics.com/) is a library that provides the ability to communicate with and control various motors using the TalonSRX, TalonFX, and VictorSPX over CAN.  We use CTRE Phoenix to control the majority of our TalonSRXs/TalonFXs so that we can run PID on the TalonSRX/TalonFX itself for a faster update rate.

## Spark MAX API
The [Spark MAX](http://www.revrobotics.com/sparkmax-software/) has a library that provides the ability to communicate with and control various motors using the SparkMAX over CAN.  We use the Spark MAX to control NEO Motors so that we can use these brushless motors and run PID on the SparkMAX itself for a fast update rate.

## JUnit
[JUnit](https://junit.org/junit4/) is a unit testing library for Java.  JUnit is fairly simple and provides some comparison functions and a framework for running unit tests.

## Mockito
[Mockito](http://site.mockito.org/) is a library for mocking objects for unit testing.  Mockito provides a way to create fake versions of objects that have behaviors that you can describe in a very succinct way.

## OpenCV
[OpenCV](https://opencv.org/) is a computer vision library that is used for fast and efficient processing of images.  We use OpenCV functions to capture images, manipulate them (undistort, HSV filtering), write them, and discover important parts of them (find contours).

## AprilTag
[AprilTag](https://github.com/AprilRobotics/apriltag) is a computer vision library that is used for finding and using AprilTag fiducials in images.  Most of the code directly interacting with the AprilTag library is already written and won't need significant regular updates.
