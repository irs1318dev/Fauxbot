package frc.lib.helpers;

/**
 * A generic class to hold two values, or a 2-tuple, that can't be changed after creation.
 */
public class ImmutablePair<T1, T2>
{
    public final T1 first;
    public final T2 second;

    public ImmutablePair(T1 first, T2 second)
    {
        this.first = first;
        this.second = second;
    }
}
