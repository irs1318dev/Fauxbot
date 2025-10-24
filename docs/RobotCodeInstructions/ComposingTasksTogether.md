# Composing Tasks together

Tasks can be grouped together in interesting ways to describe more complex tasks.  By having tasks happen in a certain order and sometimes simultaneously, you can end up with a complex task built out of much simpler re-usable tasks.  To do this, you can utilize SequentialTask and ConcurrentTask, and ConditionalTasl-based tasks.

## SequentialTask.Sequence()
Sequential task starts and completes each task in the order they are listed.

```java
SequentialTask.Sequence(
  new WaitTask(3.0),
  new DriveForwardTask(3.5));
```

The example above is a sequence of two tasks, where it will first wait 3 seconds and then will drive 3.5 inches forward.

## ConcurrentTask.AnyTasks()
Concurrent AnyTasks starts all of the tasks at the same time and completes them all when one of them has considered itself to be completed.

```java
ConcurrentTask.AnyTasks(
  new WaitTask(3.0),
  new DriveForwardTask(3.5));
```

The example above is a pair of two tasks that will execute at the same time, completing when either 3 seconds has elapsed OR once the robot has driven 3.5 inches forward.

## ConcurrentTask.AllTasks()
Concurrent AllTasks starts all of the tasks at the same time and completes when all of them have considered themselves to be completed.

```java
ConcurrentTask.AllTasks(
  new WaitTask(3.0),
  new DriveForwardTask(3.5));
```

The example above is a pair of two tasks that will execute at the same time, completing when the task has taken 3 seconds AND the robot has driven 3.5 inches forward.

## ConditionalTask-based tasks
Conditional Tasks have the ability to use branching logic based on conditions within our tasks.  These are implemented as classes inheriting from ```ConditionalTask```, which run perform some logic to evaluate a condition like "elevator is above level 3" and based on that decide which action to perform next.  This allows you to create a decision tree based on different conditions to have the macro or autonomous routine be responsive to the current state of the robot or even the state of the world around it.
