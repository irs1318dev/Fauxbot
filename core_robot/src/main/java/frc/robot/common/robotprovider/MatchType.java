package frc.robot.common.robotprovider;

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