package frc.lib.robotprovider;

/**
 * Represents a dropdown selection list that can be used within Shuffleboard/SmartDashboard over NetworkTables
 */
public interface ISendableChooser<V>
{
    /**
     * Add an option to the dropdown that is the default option if no other option is explicitly selected
     * @param name of the option
     * @param object result if selected
     */
    void addDefault(String name, V object);

    /**
     * Add an option to the dropdown
     * @param name of the option
     * @param object result if selected
     */
    void addObject(String name, V object);

    /**
     * Retrieve the selected option's object, fallback to default, fallback to null
     * @return the object associated with the selected option
     */
    V getSelected();
}
