# Macros

Macros are groupings of different Operations that need to happen in a certain order and with certain conditions between the various operations.  This is typically done by defining a bunch of individual "tasks" that perform one operation until it has completed, and then composing them together using different types of logic.  One example of a macro from 2019 would be the climb macro, which moved the robot forwards, engaged the arms, rotated the cam, drove forward, and finally lifted the elevator and reset the arms and cam.  Another example of a macro from 2019 would be the Vision-based alignment and approach of the rocket and cargo ship.

[Understanding Existing Tasks](UnderstandingExistingTasks.md) can help explain the way that common, pre-existing tasks can be joined together to create macros that can accomplish complex tasks.
