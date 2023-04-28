package frc.lib.robotprovider;

public interface IGenericEntry
{
    double getDouble(double defaultValue);
    double[] getDoubleArray(double[] defaultValue);
    boolean getBoolean(boolean defaultValue);
    boolean[] getBooleanArray(boolean[] defaultValue);
    long getInteger(long defaultValue);
    long[] getIntegerArray(long[] defaultValue);
    String getString(String defaultValue);
    String[] getStringArray(String[] defaultValue);
    void setDouble(double value);
    void setDoubleArray(double[] value);
    void setBoolean(boolean value);
    void setBooleanArray(boolean[] value);
    void setInteger(long value);
    void setIntegerArray(long[] value);
    void setString(String value);
    void setStringArray(String[] value);
    void setDefaultDouble(double defaultValue);
    void setDefaultDoubleArray(double[] defaultValue);
    void setDefaultBoolean(boolean defaultValue);
    void setDefaultBooleanArray(boolean[] defaultValue);
    void setDefaultInteger(long defaultValue);
    void setDefaultIntegerArray(long[] defaultValue);
    void setDefaultString(String defaultValue);
    void setDefaultStringArray(String[] defaultValue);
}
