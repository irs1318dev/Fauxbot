package frc.lib.robotprovider;

public interface IAlert
{
    /**
     * Get the type of the alert
     * @return the type of alert (Info, Warning, Error)
     */
    AlertType getType();

    /**
     * Get the text of the alert
     * @return text of the alert
     */
    String getText();

    /**
     * Update the text of the alert, e.g. to add more specific data
     * @param newText to use for the alert
     */
    void updateText(String newText);

    /**
     * Enables/activates the alert
     */
    void enable();

    /**
     * Disables/deactivates the alert
     */
    void disable();

    /**
     * Sets the alert to the given state (enabled or disabled)
     * @param enabled whether the alert should be enabled (true) or disabled (false)
     */
    void set(boolean enabled);
}
