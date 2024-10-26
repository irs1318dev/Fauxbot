package frc.lib.helpers;

/**
 * A generic class to hold four values, or a 4-tuple, that can't be changed after creation.
 */
public class ImmutableQuadruple<T1, T2, T3, T4>
{
    /**
     * the first value in the quaduple
     */
    public final T1 first;

    /**
     * the second value in the quaduple
     */
    public final T2 second;

    /**
     * the third value in the quaduple
     */
    public final T3 third;

    /**
     * the fourth value in the quaduple
     */
    public final T4 fourth;

    /**
     * Initializes a new instance of the ImmutableQuadruple class, holding four values and not allowing them to be changed.
     * @param first value to hold
     * @param second value to hold
     * @param third value to hold
     * @param fourth value to hold
     */
    public ImmutableQuadruple(T1 first, T2 second, T3 third, T4 fourth)
    {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
    }
}
