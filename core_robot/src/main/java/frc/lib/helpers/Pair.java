package frc.lib.helpers;

/**
 * A generic class to hold two values, or a 2-tuple.
 */
public class Pair<T1, T2>
{
    private T1 first;
    private T2 second;

    /**
     * Initializes a new instance of the Pair class, holding two values.
     * @param first value to hold
     * @param second value to hold
     */
    public Pair(T1 first, T2 second)
    {
        this.first = first;
        this.second = second;
    }

    /**
     * Retrieve the first value of the pair
     * @return first value
     */
    public T1 getFirst()
    {
        return this.first;
    }

    /**
     * Retrieve the second value of the pair
     * @return second value
     */
    public T2 getSecond()
    {
        return this.second;
    }

    /**
     * Set the two values in the pair
     * @param first value to set
     * @param second value to set
     */
    public void set(T1 first, T2 second)
    {
        this.first = first;
        this.second = second;
    }

    /**
     * Set the first value in the pair
     * @param first value to set
     */
    public void setFirst(T1 first)
    {
        this.first = first;
    }

    /**
     * Set the second value in the pair
     * @param second value to set
     */
    public void setSecond(T2 second)
    {
        this.second = second;
    }
}
