package frc.lib.robotprovider;

public interface IBooleanEntry
{
    boolean get();
    boolean get(boolean defaultValue);
    void set(boolean value);
    void setDefault(boolean defaultValue);
}
