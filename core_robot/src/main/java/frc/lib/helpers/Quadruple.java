package frc.lib.helpers;

/**
 * A generic class to hold four values, or a 4-tuple.
 */
public class Quadruple<T1, T2, T3, T4>
{
    private T1 first;
    private T2 second;
    private T3 third;
    private T4 fourth;

    public Quadruple(T1 first, T2 second, T3 third, T4 fourth)
    {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
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

    public T4 getFourth()
    {
        return this.fourth;
    }

    public void set(T1 first, T2 second, T3 third, T4 fourth)
    {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
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

    public void setFourth(T4 fourth)
    {
        this.fourth = fourth;
    }
}
