package frc.lib.helpers;

/**
 * A generic class to hold four values, or a 4-tuple, that can't be changed after creation.
 */
public class ImmutableQuadruple<T1, T2, T3, T4>
{
    public final T1 first;
    public final T2 second;
    public final T3 third;
    public final T4 fourth;

    public ImmutableQuadruple(T1 first, T2 second, T3 third, T4 fourth)
    {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
    }
}
