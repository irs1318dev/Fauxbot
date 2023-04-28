package frc.lib.robotprovider;

public interface IEncoder
{
    double getRate();
    double getDistance();
    int get();
    void setDistancePerPulse(double distancePerPulse);
    void reset();
}
