package frc.lib.helpers;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

/**
 * Helper functions for interacting with sets of various kinds, such as EnumSet and regular Set.
 * https://en.wikipedia.org/wiki/Set_(mathematics)
 */
public class SetHelper
{
    /**
     * Generate a set that represents the intersection of the two provided sets.
     * Selects all of the items that exist in both the left and the right set (inner join)
     * @param left set to perform the inner join
     * @param right set to perform the inner join
     * @return a set of items that exist in both the left and the right set
     */
    public static <T extends Enum<T>> EnumSet<T> Intersection(EnumSet<T> left, EnumSet<T> right)
    {
        EnumSet<T> result = left.clone();
        result.retainAll(right);
        return result;
    }

    /**
     * Generate a set that represents the union of the two provided sets.
     * Selects all of the items that appear in either the left or the right set (outer join)
     * @param left set to perform the outer join
     * @param right set to perform the outer join
     * @return a set of items that exist in either the left or the right set
     */
    public static <T extends Enum<T>> EnumSet<T> Union(EnumSet<T> left, EnumSet<T> right)
    {
        EnumSet<T> result = left.clone();
        result.addAll(right);
        return result;
    }

    /**
     * Generate a set that represents the relative complement of notIn with respect to in.
     * Selects all of the items that appear in "in" but not in "notIn"
     * @param notIn set of items that should not be included in the result
     * @param in set of items that may be included in the result
     * @return a set of items that contain all of the values of "in" that are not in "notIn"
     */
    public static <T extends Enum<T>> EnumSet<T> RelativeComplement(EnumSet<T> notIn, EnumSet<T> in)
    {
        EnumSet<T> result = in.clone();
        result.removeAll(notIn);
        return result;
    }

    /**
     * Generate a set that represents the relative complement of notIn with respect to in.
     * Selects all of the items that appear in "in" but not in "notIn"
     * @param notIn set of items that should not be included in the result
     * @param in set of items that may be included in the result
     * @return a set of items that contain all of the values of "in" that are not in "notIn"
     */
    public static <T extends Enum<T>> EnumSet<T> RelativeComplement(EnumSet<T> notIn, Set<T> in)
    {
        EnumSet<T> result = EnumSet.copyOf(in);
        result.removeAll(notIn);
        return result;
    }

    /**
     * Generate a set that represents the relative complement of notIn with respect to in.
     * Selects all of the items that appear in "in" but not in "notIn"
     * @param notIn set of items that should not be included in the result
     * @param in set of items that may be included in the result
     * @return a set of items that contain all of the values of "in" that are not in "notIn"
     */
    public static <T extends Enum<T>> int Count(EnumSet<T> set)
    {
        int count = 0;
        for (T element : set)
        {
            if (!set.contains(element))
            {
                ExceptionHelpers.Assert(false, "set " + set.toString() + " doesn't contain " + element.toString());
            }

            count++;
        }

        return count;
    }

    /**
     * Generate a set that represents the intersection of the two provided sets.
     * Selects all of the items that exist in both the left and the right set (inner join)
     * @param left set to perform the inner join
     * @param right set to perform the inner join
     * @return a set of items that exist in both the left and the right set
     */
    public static <T> Set<T> Intersection(Set<T> left, Set<T> right)
    {
        Set<T> result = new HashSet<T>();
        for (T item : left)
        {
            if (right.contains(item))
            {
                result.add(item);
            }
        }

        return result;
    }

    /**
     * Generate a set that represents the union of the two provided sets.
     * Selects all of the items that appear in either the left or the right set (outer join)
     * @param left set to perform the outer join
     * @param right set to perform the outer join
     * @return a set of items that exist in either the left or the right set
     */
    public static <T> Set<T> Union(Set<T> left, Set<T> right)
    {
        Set<T> result = new HashSet<T>();
        result.addAll(left);
        result.addAll(right);
        return result;
    }

    /**
     * Generate a set that represents the relative complement of notIn with respect to in.
     * Selects all of the items that appear in "in" but not in "notIn"
     * @param notIn set of items that should not be included in the result
     * @param in set of items that may be included in the result
     * @return a set of items that contain all of the values of "in" that are not in "notIn"
     */
    public static <T> Set<T> RelativeComplement(Set<T> notIn, Set<T> in)
    {
        Set<T> result = new HashSet<T>();
        result.addAll(in);
        result.removeAll(notIn);
        return result;
    }
}
