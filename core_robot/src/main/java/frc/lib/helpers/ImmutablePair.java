package frc.lib.helpers;

/**
 * A generic class to hold two values, or a 2-tuple, that can't be changed after creation.
 */
public class ImmutablePair<T1, T2>
{
    /**
     * the first value in the pair
     */
    public final T1 first;

    /**
     * the second value in the pair
     */
    public final T2 second;

    /**
     * Initializes a new instance of the ImmutablePair class, holding two values and not allowing them to be changed.
     * @param first value to hold
     * @param second value to hold
     */
    public ImmutablePair(T1 first, T2 second)
    {
        this.first = first;
        this.second = second;
    }
}
