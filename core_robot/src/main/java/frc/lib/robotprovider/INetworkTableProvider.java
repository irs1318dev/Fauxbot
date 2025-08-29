package frc.lib.robotprovider;

/**
 * Represents a provider for the Network Tables
 */
public interface INetworkTableProvider
{
    /**
     * Requests Shuffleboard to start a recording
     */
    void startShuffleboardRecording();

    /**
     * Requests Shuffleboard to stop recording
     */
    void stopShuffleboardRecording();

    /**
     * Create an alert to display on Shuffleboard/SmartDashboard
     * @param text to display (may be updated later)
     * @param type severity of the alert (info, warning, error)
     * @return alert object to update text and enable/disable the alert
     */
    IAlert createAlert(String text, AlertType type);

    /**
     * Create a Field2d widget in Shuffleboard/SmartDashboard
     * @param name of the widget
     * @return field object to update the robot pose
     */
    IField2d getField2d(String name);

    /**
     * Create a number slider for input on Shuffleboard/SmartDashboard
     * @param title of the slider
     * @param initialValue of the slider
     * @return subscriber to be able to see the changes to the slider published to Network Tables
     */
    IDoubleSubscriber getNumberSlider(String title, double initialValue);
    
    /**
     * Create an integer slider for input on Shuffleboard/SmartDashboard
     * @param title of the slider
     * @param initialValue of the slider
     * @return subscriber to be able to see the changes to the slider published to Network Tables
     */
    IIntegerSubscriber getIntegerSlider(String title, int initialValue);

    /**
     * Create a checkbox for input on Shuffleboard/SmartDashboard
     * @param title of the checkbox
     * @param initialValue of the checkbox
     * @return subscriber to be able to see the changes to the checkbox published to Network Tables
     */
    IBooleanSubscriber getCheckbox(String title, boolean initialValue);

    /**
     * Create a drop-down list for input on Shuffleboard/SmartDashboard
     * @param name of the drop-down
     * @return chooser object to populate and read current setting from
     */
    <V> ISendableChooser<V> getSendableChooser(String name);

    /**
     * Create a listener for doubles from the network tables
     * @param key to listen for
     * @return subscriber to be able to see the current value published to Network Tables (default 0.0)
     */
    IDoubleSubscriber getDoubleSubscriber(String key);

    /**
     * Create a listener for doubles from the network tables
     * @param key to listen for
     * @param defaultValue to read if there's nothing published to NetworkTables
     * @return subscriber to be able to see the current value published to Network Tables
     */
    IDoubleSubscriber getDoubleSubscriber(String key, double defaultValue);

    /**
     * Create a listener for booleans from the network tables
     * @param key to listen for
     * @return subscriber to be able to see the current value published to Network Tables (default false)
     */
    IBooleanSubscriber getBooleanSubscriber(String key);

    /**
     * Create a listener for booleans from the network tables
     * @param key to listen for
     * @param defaultValue to read if there's nothing published to NetworkTables
     * @return subscriber to be able to see the current value published to Network Tables
     */
    IBooleanSubscriber getBooleanSubscriber(String key, boolean defaultValue);

    /**
     * Create a listener for integers from the network tables
     * @param key to listen for
     * @return subscriber to be able to see the current value published to Network Tables (default 0)
     */
    IIntegerSubscriber getIntegerSubscriber(String key);

    /**
     * Create a listener for integers from the network tables
     * @param key to listen for
     * @param defaultValue to read if there's nothing published to NetworkTables
     * @return subscriber to be able to see the current value published to Network Tables
     */
    IIntegerSubscriber getIntegerSubscriber(String key, int defaultValue);

    /**
     * Create a listener for strings from the network tables
     * @param key to listen for
     * @return subscriber to be able to see the current value published to Network Tables (default null)
     */
    IStringSubscriber getStringSubscriber(String key);

    /**
     * Create a listener for strings from the network tables
     * @param key to listen for
     * @param defaultValue to read if there's nothing published to NetworkTables
     * @return subscriber to be able to see the current value published to Network Tables
     */
    IStringSubscriber getStringSubscriber(String key, String defaultValue);
}
