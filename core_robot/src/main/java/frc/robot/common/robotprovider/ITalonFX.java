package frc.robot.common.robotprovider;

public interface ITalonFX extends ITalonXBase
{
    void setSupplyCurrentLimit(boolean enabled, double currentLimit, double triggerThresholdCurrent, double triggerThresholdTime);
}
