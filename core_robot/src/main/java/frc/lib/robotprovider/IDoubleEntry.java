package frc.lib.robotprovider;

public interface IDoubleEntry
{
    double get();
    double get(double defaultValue);
    void set(double value);
    void setDefault(double defaultValue);
}
