package frc.robot.driver;

public enum MacroOperation
{
    // DriveTrain operations:
    AutonomousRoutine,
    PIDBrake,
    TurnInPlaceLeft,
    TurnInPlaceRight,
    FollowSomePath,

    // Vision operations:
    VisionCenterAndAdvance,
    VisionFastCenterAndAdvance,
    DriveForwardTurnRight,
}
