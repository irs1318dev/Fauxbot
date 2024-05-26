package frc.lib.robotprovider;

public interface IDutyCycleEncoder
{
    double get();
    double getDistance();
    double getAbsolutePosition();
    int getFrequency();
    boolean isConnected();
    void setConnectedFrequencyThreshold(int frequency);
    void setDistancePerRotation(double distancePerRotation);
    void setDutyCycleRange(double min, double max);
    void setPositionOffset(double offset);
    void reset();
}