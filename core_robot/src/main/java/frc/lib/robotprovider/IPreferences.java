package frc.lib.robotprovider;

import java.util.Collection;

/**
 * Represents a Preferences dictionary from NetworkTables
 */
public interface IPreferences
{
    /**
     * Returns whether or not there is a key with the given name.
     */
    boolean containsKey(String key);

    /**
     * Returns the boolean at the given key.
     */
    boolean getBoolean(String key, boolean backup);

    /**
     * Returns the double at the given key.
     */
    double getDouble(String key, double backup);

    /**
     * Returns the float at the given key.
     */
    float getFloat(String key, float backup);

    /**
     * Returns the int at the given key.
     */
    int getInt(String key, int backup);

    /**
     * Gets the preferences keys.
     */
    Collection<String> getKeys();

    /**
     * Returns the long at the given key.
     */
    long getLong(String key, long backup);

    /**
     * Returns the string at the given key.
     */
    String getString(String key, String backup);

    /**
     * Puts the given boolean into the preferences table if it doesn't already exist.
     */
    void initBoolean(String key, boolean value);

    /**
     * Puts the given double into the preferences table if it doesn't already exist.
     */
    void initDouble(String key, double value);

    /**
     * Puts the given float into the preferences table if it doesn't already exist.
     */
    void initFloat(String key, float value);

    /**
     * Puts the given int into the preferences table if it doesn't already exist.
     */
    void initInt(String key, int value);

    /**
     * Puts the given long into the preferences table if it doesn't already exist.
     */
    void initLong(String key, long value);

    /**
     * Puts the given string into the preferences table if it doesn't already exist.
     */
    void initString(String key, String value);

    /**
     * Puts the given boolean into the preferences table.
     */
    void setBoolean(String key, boolean value);

    /**
     * Puts the given double into the preferences table.
     */
    void setDouble(String key, double value);

    /**
     * Puts the given float into the preferences table.
     */
    void setFloat(String key, float value);

    /**
     * Puts the given int into the preferences table.
     */
    void setInt(String key, int value);

    /**
     * Puts the given long into the preferences table.
     */
    void setLong(String key, long value);

    /**
     * Puts the given string into the preferences table.
     */
    void setString(String key, String value);

    /**
     * Remove a preference.
     */
    void remove(String key);

    /**
     * Remove all preferences.
     */
    void removeAll();
}
