package frc.robot.common.robotprovider;

public interface IRelay
{
    void set(RelayValue value);
    void setDirection(RelayDirection direction);
}
