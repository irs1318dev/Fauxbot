package frc.lib.helpers;

/**
 * The FlagBase class is a base class for a set of flags, also known as a bit field.
 * 
 * The way that flags work is by using bitwise operators to compare which bits are set in the bit field.
 * Beneath the covers, we use an int value that provides 32 bits, meaning 32 possible flags.
 * Each integer can represented like:
 * 0000 0000 0000 0000 0000 0000 0000 0000 = 0
 * 0000 0000 0000 0000 0000 0000 0000 0001 = 1
 * 0000 0000 0000 0000 0000 0000 0000 0010 = 2
 * 0000 0000 0000 0000 0000 0000 0000 0011 = 3
 * ...
 * 
 * The bitwise AND of two values ("Intersect" below) results in just the bits that are set to 1 in both values.
 * Examples:
 * (1 & 0) => 0    OR    (0001 & 0000) => 0000
 * (1 & 1) => 1    OR    (0001 & 0001) => 0001
 * (2 & 1) => 0    OR    (0010 & 0001) => 0000
 * (3 & 1) => 1    OR    (0011 & 0001) => 0001
 * 
 * The bitwise OR of the two values ("Union" below) results in the bits that are set to 1 in either value.
 * (1 | 0) => 1    OR    (0001 | 0000) => 0001
 * (1 | 1) => 1    OR    (0001 | 0001) => 0001
 * (2 | 1) => 3    OR    (0010 | 0001) => 0011
 * (3 | 1) => 3    OR    (0011 | 0001) => 0011
 * 
 * The C# equivalent is an enum that is marked with the Flags attribute
 */
public class FlagBase
{
    protected final int value;
    protected FlagBase(int value)
    {
        this.value = value;
    }

    public boolean hasFlag(FlagBase other)
    {
        return (this.value & other.value) == other.value;
    }

    @Override
    public String toString()
    {
        return "" + this.value;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof FlagBase))
        {
            return false;
        }

        return this.value == ((FlagBase)obj).value;
    }

    public int getValue()
    {
        return this.value;
    }

    protected static int Union(FlagBase... flags)
    {
        if (flags == null || flags.length == 0)
        {
            return 0;
        }

        int result = 0;
        for (FlagBase flag : flags)
        {
            if (flag != null)
            {
                result |= flag.value;
            }
        }

        return result;
    }

    protected static int Intersect(FlagBase... flags)
    {
        if (flags == null || flags.length == 0)
        {
            return 0;
        }

        int result = 0;
        int index;
        for (index = 0; index < flags.length; index++)
        {
            if (flags[index] != null)
            {
                result = flags[index].value;
                break;
            }
        }

        for (index++; index < flags.length; index++)
        {
            FlagBase flag = flags[index];
            if (flag != null)
            {
                result &= flag.value;
            }
        }

        return result;
    }
}
