package frc.lib.helpers;

/**
 * A generic class to hold two values, or a 2-tuple.
 */
public class Pair<T1, T2>
{
    private T1 first;
    private T2 second;

    public Pair(T1 first, T2 second)
    {
        this.first = first;
        this.second = second;
    }

    public T1 getFirst()
    {
        return this.first;
    }

    public T2 getSecond()
    {
        return this.second;
    }

    public void set(T1 first, T2 second)
    {
        this.first = first;
        this.second = second;
    }

    public void setFirst(T1 first)
    {
        this.first = first;
    }

    public void setSecond(T2 second)
    {
        this.second = second;
    }
}
