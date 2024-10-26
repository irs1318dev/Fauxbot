package frc.lib.helpers;

/**
 * A generic class to hold three values, or a 3-tuple.
 */
public class Triple<T1, T2, T3>
{
    private T1 first;
    private T2 second;
    private T3 third;

    /**
     * Initializes a new instance of the Triple class, holding three values.
     * @param first value to hold
     * @param second value to hold
     * @param third value to hold
     */
    public Triple(T1 first, T2 second, T3 third)
    {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    /**
     * Retrieve the first value of the triple
     * @return first value
     */
    public T1 getFirst()
    {
        return this.first;
    }

    /**
     * Retrieve the second value of the triple
     * @return second value
     */
    public T2 getSecond()
    {
        return this.second;
    }

    /**
     * Retrieve the third value of the triple
     * @return third value
     */
    public T3 getThird()
    {
        return this.third;
    }

    /**
     * Set the three values in the triple
     * @param first value to set
     * @param second value to set
     * @param third value to set
     */
    public void set(T1 first, T2 second, T3 third)
    {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    /**
     * Set the first value in the triple
     * @param first value to set
     */
    public void setFirst(T1 first)
    {
        this.first = first;
    }

    /**
     * Set the second value in the triple
     * @param second value to set
     */
    public void setSecond(T2 second)
    {
        this.second = second;
    }

    /**
     * Set the third value in the triple
     * @param third value to set
     */
    public void setThird(T3 third)
    {
        this.third = third;
    }
}
