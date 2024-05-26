package frc.lib.helpers;

/**
 * A generic class to hold three values, or a 3-tuple.
 */
public class Triple<T1, T2, T3>
{
    private T1 first;
    private T2 second;
    private T3 third;

    public Triple(T1 first, T2 second, T3 third)
    {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public T1 getFirst()
    {
        return this.first;
    }

    public T2 getSecond()
    {
        return this.second;
    }

    public T3 getThird()
    {
        return this.third;
    }

    public void set(T1 first, T2 second, T3 third)
    {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public void setFirst(T1 first)
    {
        this.first = first;
    }

    public void setSecond(T2 second)
    {
        this.second = second;
    }

    public void setThird(T3 third)
    {
        this.third = third;
    }
}
