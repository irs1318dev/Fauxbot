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

    /**
     * Initializes a new instance of the Quadruple class, holding four values.
     * @param first value to hold
     * @param second value to hold
     * @param third value to hold
     * @param fourth value to hold
     */
    public Quadruple(T1 first, T2 second, T3 third, T4 fourth)
    {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
    }

    /**
     * Retrieve the first value of the quadruple
     * @return first value
     */
    public T1 getFirst()
    {
        return this.first;
    }

    /**
     * Retrieve the second value of the quadruple
     * @return second value
     */
    public T2 getSecond()
    {
        return this.second;
    }

    /**
     * Retrieve the third value of the quadruple
     * @return third value
     */
    public T3 getThird()
    {
        return this.third;
    }

    /**
     * Retrieve the fourth value of the quadruple
     * @return fourth value
     */
    public T4 getFourth()
    {
        return this.fourth;
    }

    /**
     * Set the four values in the quadruple
     * @param first value to set
     * @param second value to set
     * @param third value to set
     * @param fourth value to set
     */
    public void set(T1 first, T2 second, T3 third, T4 fourth)
    {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
    }

    /**
     * Set the first value in the quadruple
     * @param first value to set
     */
    public void setFirst(T1 first)
    {
        this.first = first;
    }

    /**
     * Set the second value in the quadruple
     * @param second value to set
     */
    public void setSecond(T2 second)
    {
        this.second = second;
    }

    /**
     * Set the third value in the quadruple
     * @param third value to set
     */
    public void setThird(T3 third)
    {
        this.third = third;
    }

    /**
     * Set the fourth value in the quadruple
     * @param fourth value to set
     */
    public void setFourth(T4 fourth)
    {
        this.fourth = fourth;
    }
}
