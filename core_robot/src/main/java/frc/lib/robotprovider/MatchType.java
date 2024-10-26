package frc.lib.robotprovider;

/**
 * The type of match the robot may participate in
 */
public enum MatchType
{
    None("N"),
    Practice("P"),
    Qualification("Q"),
    Elimination("E");

    public final String value;

    MatchType(String value)
    {
        this.value = value;
    }
}