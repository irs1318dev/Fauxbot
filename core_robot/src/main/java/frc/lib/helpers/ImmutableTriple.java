package frc.lib.helpers;

/**
 * A generic class to hold three values, or a 3-tuple, that can't be changed after creation.
 */
public class ImmutableTriple<T1, T2, T3>
{
    /**
     * the first value in the triple
     */
    public final T1 first;

    /**
     * the second value in the triple
     */
    public final T2 second;

    /**
     * the third value in the triple
     */
    public final T3 third;

    /**
     * Initializes a new instance of the ImmutableTriple class, holding three values and not allowing them to be changed.
     * @param first value to hold
     * @param second value to hold
     * @param third value to hold
     */
    public ImmutableTriple(T1 first, T2 second, T3 third)
    {
        this.first = first;
        this.second = second;
        this.third = third;
    }
}
