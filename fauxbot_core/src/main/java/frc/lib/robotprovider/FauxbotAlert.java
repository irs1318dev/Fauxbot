package frc.lib.robotprovider;

public class FauxbotAlert implements IAlert
{
    private final AlertType type;
    private String text;

    private boolean isEnabled;

    public FauxbotAlert(String text, AlertType type)
    {
        this.type = type;
        this.text = text;

        this.isEnabled = false;
    }

    @Override
    public AlertType getType()
    {
        return this.type;
    }

    @Override
    public String getText()
    {
        return this.text;
    }

    @Override
    public void updateText(String newText)
    {
        this.text = newText;
    }

    @Override
    public void enable()
    {
        this.isEnabled = true;
    }

    @Override
    public void disable()
    {
        this.isEnabled = false;
    }

    @Override
    public void set(boolean enabled)
    {
        this.isEnabled = enabled;
    }
}
