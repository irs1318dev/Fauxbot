package frc.lib.robotprovider;

public interface ITimer
{
    void start();
    void stop();
    double get();
    void reset();
}
