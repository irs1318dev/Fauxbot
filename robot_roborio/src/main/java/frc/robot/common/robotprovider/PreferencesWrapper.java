package frc.robot.common.robotprovider;

import java.util.Collection;

import edu.wpi.first.wpilibj.Preferences;

public class PreferencesWrapper implements IPreferences
{
    private Preferences wrappedObject;

    public PreferencesWrapper()
    {
        this.wrappedObject = Preferences.getInstance();
    }

    /**
     * Returns whether or not there is a key with the given name.
     * */
    @Override
    public boolean containsKey(String key)
    {
        return this.wrappedObject.containsKey(key);
    }

    /**
     * Returns the boolean at the given key.
     * */
    @Override
    public boolean getBoolean(String key, boolean backup)
    {
        return this.wrappedObject.getBoolean(key, backup);
    }

    /**
     * Returns the double at the given key.
     * */
    @Override
    public double getDouble(String key, double backup)
    {
        return this.wrappedObject.getDouble(key, backup);
    }

    /**
     * Returns the float at the given key.
     * */
    @Override
    public float getFloat(String key, float backup)
    {
        return this.wrappedObject.getFloat(key, backup);
    }

    /**
     * Returns the int at the given key.
     * */
    @Override
    public int getInt(String key, int backup)
    {
        return this.wrappedObject.getInt(key, backup);
    }

    /**
     * Gets the preferences keys.
     * */
    @Override
    public Collection<String> getKeys()
    {
        return this.wrappedObject.getKeys();
    }

    /**
     * Returns the long at the given key.
     * */
    @Override
    public long getLong(String key, long backup)
    {
        return this.wrappedObject.getLong(key, backup);
    }

    /**
     * Returns the string at the given key.
     * */
    @Override
    public String getString(String key, String backup)
    {
        return this.wrappedObject.getString(key, backup);
    }

    /**
     * Puts the given boolean into the preferences table if it doesn't already exist.
     * */
    @Override
    public void initBoolean(String key, boolean value)
    {
        this.wrappedObject.initBoolean(key, value);
    }

    /**
     * Puts the given double into the preferences table if it doesn't already exist.
     * */
    @Override
    public void initDouble(String key, double value)
    {
        this.wrappedObject.initDouble(key, value);
    }

    /**
     * Puts the given float into the preferences table if it doesn't already exist.
     * */
    @Override
    public void initFloat(String key, float value)
    {
        this.wrappedObject.initFloat(key, value);
    }

    /**
     * Puts the given int into the preferences table if it doesn't already exist.
     * */
    @Override
    public void initInt(String key, int value)
    {
        this.wrappedObject.initInt(key, value);
    }

    /**
     * Puts the given long into the preferences table if it doesn't already exist.
     * */
    @Override
    public void initLong(String key, long value)
    {
        this.wrappedObject.initLong(key, value);
    }

    /**
     * Puts the given string into the preferences table if it doesn't already exist.
     * */
    @Override
    public void initString(String key, String value)
    {
        this.wrappedObject.initString(key, value);
    }

    /**
     * Puts the given boolean into the preferences table.
     * */
    @Override
    public void putBoolean(String key, boolean value)
    {
        this.wrappedObject.putBoolean(key, value);
    }

    /**
     * Puts the given double into the preferences table.
     * */
    @Override
    public void putDouble(String key, double value)
    {
        this.wrappedObject.putDouble(key, value);
    }

    /**
     * Puts the given float into the preferences table.
     * */
    @Override
    public void putFloat(String key, float value)
    {
        this.wrappedObject.putFloat(key, value);
    }

    /**
     * Puts the given int into the preferences table.
     * */
    @Override
    public void putInt(String key, int value)
    {
        this.wrappedObject.putInt(key, value);
    }

    /**
     * Puts the given long into the preferences table.
     * */
    @Override
    public void putLong(String key, long value)
    {
        this.wrappedObject.putLong(key, value);
    }

    /**
     * Puts the given string into the preferences table.
     * */
    @Override
    public void putString(String key, String value)
    {
        this.wrappedObject.putString(key, value);
    }

    /**
     * Remove a preference.
     * */
    @Override
    public void remove(String key)
    {
        this.wrappedObject.remove(key);
    }

    /**
     * Remove all preferences.
     * */
    @Override
    public void removeAll()
    {
        this.wrappedObject.removeAll();
    }
}