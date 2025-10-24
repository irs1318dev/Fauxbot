# Adding Autonomous Routines

Like Macros, Autonomous routines involve a series of control tasks working together to accomplish game objectives.

To add a new Autonomous Routine, you should add a new function to the ```AutonomousRoutineSelector``` class that returns an ```IControlTask``` that performs the set of tasks that make up the routine.

```java
    private static IControlTask GetMyFavoriteRoutine()
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

[Click here](ComposingTasksTogether.md) to learn can see more about composing tasks together.

```java
    if (routine == AutoRoutine.MySpecialRoutine)
    {
        return AutonomousRoutineSelector.GetMyFavoriteRoutine();
    }
```

Additionally, you will want to change the decision logic in the ```selectRoutine()``` function and possibly add new entries to the ```StartPosition``` and/or ```AutoRoutine``` enumerations.  When the criteria are met for running your routine, the function should return the task you decided upon above.
