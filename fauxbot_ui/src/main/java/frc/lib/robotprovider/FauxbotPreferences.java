package frc.lib.robotprovider;

import java.util.Collection;
import java.util.HashMap;

public class FauxbotPreferences implements IPreferences
{
    private HashMap<String, Object> pairs;

    public FauxbotPreferences()
    {
        this.pairs = new HashMap<String, Object>();
    }

    /**
     * Returns whether or not there is a key with the given name.
     * */
    @Override
    public boolean containsKey(String key)
    {
        return this.pairs.containsKey(key);
    }

    /**
     * Returns the boolean at the given key.
     * */
    @Override
    public boolean getBoolean(String key, boolean backup)
    {
        return (boolean)this.pairs.getOrDefault(key, backup);
    }

    /**
     * Returns the double at the given key.
     * */
    @Override
    public double getDouble(String key, double backup)
    {
        return (double)this.pairs.getOrDefault(key, backup);
    }

    /**
     * Returns the float at the given key.
     * */
    @Override
    public float getFloat(String key, float backup)
    {
        return (float)this.pairs.getOrDefault(key, backup);
    }

    /**
     * Returns the int at the given key.
     * */
    @Override
    public int getInt(String key, int backup)
    {
        return (int)this.pairs.getOrDefault(key, backup);
    }

    /**
     * Gets the preferences keys.
     * */
    @Override
    public Collection<String> getKeys()
    {
        return this.pairs.keySet();
    }

    /**
     * Returns the long at the given key.
     * */
    @Override
    public long getLong(String key, long backup)
    {
        return (long)this.pairs.getOrDefault(key, backup);
    }

    /**
     * Returns the string at the given key.
     * */
    @Override
    public String getString(String key, String backup)
    {
        return (String)this.pairs.getOrDefault(key, backup);
    }

    /**
     * Puts the given boolean into the preferences table if it doesn't already exist.
     * */
    @Override
    public void initBoolean(String key, boolean value)
    {
        this.pairs.putIfAbsent(key, value);
    }

    /**
     * Puts the given double into the preferences table if it doesn't already exist.
     * */
    @Override
    public void initDouble(String key, double value)
    {
        this.pairs.putIfAbsent(key, value);
    }

    /**
     * Puts the given float into the preferences table if it doesn't already exist.
     * */
    @Override
    public void initFloat(String key, float value)
    {
        this.pairs.putIfAbsent(key, value);
    }

    /**
     * Puts the given int into the preferences table if it doesn't already exist.
     * */
    @Override
    public void initInt(String key, int value)
    {
        this.pairs.putIfAbsent(key, value);
    }

    /**
     * Puts the given long into the preferences table if it doesn't already exist.
     * */
    @Override
    public void initLong(String key, long value)
    {
        this.pairs.putIfAbsent(key, value);
    }

    /**
     * Puts the given string into the preferences table if it doesn't already exist.
     * */
    @Override
    public void initString(String key, String value)
    {
        this.pairs.putIfAbsent(key, value);
    }

    /**
     * Puts the given boolean into the preferences table.
     * */
    @Override
    public void setBoolean(String key, boolean value)
    {
        this.pairs.put(key, value);
    }

    /**
     * Puts the given double into the preferences table.
     * */
    @Override
    public void setDouble(String key, double value)
    {
        this.pairs.put(key, value);
    }

    /**
     * Puts the given float into the preferences table.
     * */
    @Override
    public void setFloat(String key, float value)
    {
        this.pairs.put(key, value);
    }

    /**
     * Puts the given int into the preferences table.
     * */
    @Override
    public void setInt(String key, int value)
    {
        this.pairs.put(key, value);
    }

    /**
     * Puts the given long into the preferences table.
     * */
    @Override
    public void setLong(String key, long value)
    {
        this.pairs.put(key, value);
    }

    /**
     * Puts the given string into the preferences table.
     * */
    @Override
    public void setString(String key, String value)
    {
        this.pairs.put(key, value);
    }

    /**
     * Remove a preference.
     * */
    @Override
    public void remove(String key)
    {
        this.pairs.remove(key);
    }

    /**
     * Remove all preferences.
     * */
    @Override
    public void removeAll()
    {
        this.pairs.clear();
    }
}