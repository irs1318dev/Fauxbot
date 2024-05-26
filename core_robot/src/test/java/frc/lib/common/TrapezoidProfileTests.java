package frc.lib.common;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Assertions;

import frc.lib.controllers.TrapezoidProfile;

public class TrapezoidProfileTests
{
    // @Test
    public void checkGoZero()
    {
        TrapezoidProfile profile = new TrapezoidProfile(10.0, 5.0, 5.0);
        TrapezoidProfile.State curr = new TrapezoidProfile.State(0.0, 0.0);
        TrapezoidProfile.State goal = new TrapezoidProfile.State(0.0, 0.0);

        Assertions.assertFalse(profile.update(0.0, curr, goal));
        Assertions.assertEquals(0.0, curr.getPosition());
        Assertions.assertEquals(0.0, curr.getVelocity());
        Assertions.assertEquals(0.0, goal.getPosition());
        Assertions.assertEquals(0.0, goal.getVelocity());
    }

    // @Test
    public void checkZeroDistance()
    {
        TrapezoidProfile profile = new TrapezoidProfile(10.0, 5.0, 5.0);

        TrapezoidProfile.State curr = new TrapezoidProfile.State(0.0, 0.0);
        TrapezoidProfile.State goal = new TrapezoidProfile.State(0.0, 0.0);
        Assertions.assertFalse(profile.update(0.02, curr, goal));
        Assertions.assertEquals(0.0, curr.getPosition());
        Assertions.assertEquals(0.0, curr.getVelocity());

        curr = new TrapezoidProfile.State(2.0, 0.0);
        goal = new TrapezoidProfile.State(2.0, 0.0);
        Assertions.assertFalse(profile.update(0.02, curr, goal));
        Assertions.assertEquals(2.0, curr.getPosition());
        Assertions.assertEquals(0.0, curr.getVelocity());

        curr = new TrapezoidProfile.State(360.0, 4.0);
        goal = new TrapezoidProfile.State(360.0, 4.0);
        Assertions.assertFalse(profile.update(0.04, curr, goal));
        Assertions.assertEquals(360.0, curr.getPosition());
        Assertions.assertEquals(4.0, curr.getVelocity());

        curr = new TrapezoidProfile.State(0.0, 8.0);
        goal = new TrapezoidProfile.State(0.0, 8.0);
        Assertions.assertFalse(profile.update(1.0, curr, goal));
        Assertions.assertEquals(0.0, curr.getPosition());
        Assertions.assertEquals(8.0, curr.getVelocity());
    }

    // @Test
    public void checkZeroTime()
    {
        TrapezoidProfile profile = new TrapezoidProfile(10.0, 5.0, 5.0);

        TrapezoidProfile.State curr = new TrapezoidProfile.State(0.0, 0.0);
        TrapezoidProfile.State goal = new TrapezoidProfile.State(0.02, 0.0);
        Assertions.assertTrue(profile.update(0.0, curr, goal));
        Assertions.assertEquals(0.0, curr.getPosition());
        Assertions.assertEquals(0.0, curr.getVelocity());

        curr = new TrapezoidProfile.State(0.0, 0.0);
        goal = new TrapezoidProfile.State(0.2, 2.0);
        Assertions.assertTrue(profile.update(0.0, curr, goal));
        Assertions.assertEquals(0.0, curr.getPosition());
        Assertions.assertEquals(0.0, curr.getVelocity());

        curr = new TrapezoidProfile.State(0.0, 0.0);
        goal = new TrapezoidProfile.State(0.4, 4.0);
        Assertions.assertTrue(profile.update(0.0, curr, goal));
        Assertions.assertEquals(0.0, curr.getPosition());
        Assertions.assertEquals(0.0, curr.getVelocity());

        curr = new TrapezoidProfile.State(0.0, 0.0);
        goal = new TrapezoidProfile.State(1.0, 8.0);
        Assertions.assertTrue(profile.update(0.0, curr, goal));
        Assertions.assertEquals(0.0, curr.getPosition());
        Assertions.assertEquals(0.0, curr.getVelocity());

        curr = new TrapezoidProfile.State(0.0, 0.0);
        goal = new TrapezoidProfile.State(10.0, 10.0);
        Assertions.assertTrue(profile.update(0.0, curr, goal));
        Assertions.assertEquals(0.0, curr.getPosition());
        Assertions.assertEquals(0.0, curr.getVelocity());
    }

    // @Test
    public void checkNormalPathFromZero()
    {
        TrapezoidProfile profile = new TrapezoidProfile(3.0, 3.0);
        TrapezoidProfile.State curr = new TrapezoidProfile.State(0.0, 0.0);
        TrapezoidProfile.State goal = new TrapezoidProfile.State(6.0, 0.0);

        for (int i = 0; i < 300; i++)
        {
            Assertions.assertTrue(profile.update(0.01, curr, goal) || i == 299);

            // simple verification of path
            Assertions.assertTrue(curr.getPosition() >= 0.0 && curr.getPosition() <= 6.0, "Position out of range [0,6] -> " + curr.getPosition());
            Assertions.assertTrue(curr.getVelocity() >= 0.0 && curr.getVelocity() <= 3.0, "Velocity out of range [0,3] -> " + curr.getVelocity());
        }

        // should at least roughly equal the goal
        Assertions.assertEquals(6.0, curr.getPosition(), 1e-5);
        Assertions.assertEquals(0.0, curr.getVelocity(), 1e-5);

        // after any subsequent update, should be exactly equal to goal...
        Assertions.assertFalse(profile.update(0.01, curr, goal));

        Assertions.assertEquals(6.0, curr.getPosition());
        Assertions.assertEquals(0.0, curr.getVelocity());
    }

    // @Test
    public void checkNormalChangeGoal()
    {
        TrapezoidProfile profile = new TrapezoidProfile(1.5, 1.5);

        TrapezoidProfile.State curr = new TrapezoidProfile.State(0.0, 0.0);
        TrapezoidProfile.State goal = new TrapezoidProfile.State(1.5, 0);

        // first second will have the system reach max speed at position ~0.75
        for (int i = 0; i < 100; i++)
        {
            Assertions.assertTrue(profile.update(0.01, curr, goal));
        }

        Assertions.assertNotEquals(goal.getPosition(), curr.getPosition());

        // lengthen and we should transform this into a full trapezoid and coast at the max speed for 1 second,
        // but only continue for 0.5 seconds
        goal = new TrapezoidProfile.State(3.0, 0.0);
        for (int i = 0; i < 50; i++)
        {
            Assertions.assertTrue(profile.update(0.01, curr, goal));
        }

        Assertions.assertNotEquals(goal.getPosition(), curr.getPosition());

        // now cut off the end and we should start decreasing velocity automatically,
        // and finish after 1 second
        goal = new TrapezoidProfile.State(2.25, 0.0);
        for (int i = 0; i < 100; i++)
        {
            Assertions.assertTrue(profile.update(0.01, curr, goal) || i == 99);
        }

        // should at least roughly equal the goal
        Assertions.assertEquals(2.25, curr.getPosition(), 1e-5);
        Assertions.assertEquals(0.0, curr.getVelocity(), 1e-5);

        // after any subsequent update, should be exactly equal to goal...
        Assertions.assertFalse(profile.update(0.01, curr, goal));

        Assertions.assertEquals(2.25, curr.getPosition());
        Assertions.assertEquals(0.0, curr.getVelocity());
    }

    // @Test
    public void checkNormalPathFromNonZero()
    {
        // travel 11862 inches over 661 seconds (2s accel, 657s coast, 2s decel)
        TrapezoidProfile profile = new TrapezoidProfile(18.0, 9.0);
        TrapezoidProfile.State curr = new TrapezoidProfile.State(1318.0, 0.0);
        TrapezoidProfile.State goal = new TrapezoidProfile.State(13180.0, 0.0);

        double prevPosition = curr.getPosition();
        double prevVelocity = curr.getVelocity();
        for (int i = 0; i < 200; i++)
        {
            Assertions.assertTrue(profile.update(0.01, curr, goal));

            // verify accel
            Assertions.assertTrue(curr.getPosition() >= prevPosition, "Position should be increasing");
            Assertions.assertTrue(curr.getPosition() - prevPosition <= 0.01 * 18.0 + 1e-5, "Position should be increasing by at most 18.0 in/sec, actual " + prevPosition + " to " + curr.getPosition());
            Assertions.assertTrue(curr.getVelocity() >= prevVelocity, "Velocity should be increasing");

            prevPosition = curr.getPosition();
            prevVelocity = curr.getVelocity();
        }

        for (int i = 0; i < 65700; i++)
        {
            Assertions.assertTrue(profile.update(0.01, curr, goal));

            // verify coast
            Assertions.assertTrue(curr.getPosition() >= prevPosition, "Position should be increasing");
            Assertions.assertEquals(curr.getPosition() - prevPosition, 0.01 * 18.0, 1e-5, "Position should be increasing by roughly 18.0 in/sec, actual " + prevPosition + " to " + curr.getPosition());
            Assertions.assertEquals(18.0, curr.getVelocity(), 1e-5, "Velocity should be consistently at max");

            prevPosition = curr.getPosition();
            prevVelocity = curr.getVelocity();
        }

        for (int i = 0; i < 200; i++)
        {
            Assertions.assertTrue(profile.update(0.01, curr, goal) || i == 199);

            // verify decel
            Assertions.assertTrue(curr.getPosition() >= prevPosition, "Position should be increasing");
            Assertions.assertTrue(curr.getPosition() - prevPosition <= 0.01 * 18.0 + 1e-5, "Position should be increasing by at most 18.0 in/sec, actual " + prevPosition + " to " + curr.getPosition());
            Assertions.assertTrue(curr.getVelocity() <= prevVelocity, "Velocity should be decreasing");

            prevPosition = curr.getPosition();
            prevVelocity = curr.getVelocity();
        }

        // should at least roughly equal the goal
        Assertions.assertEquals(13180.0, curr.getPosition(), 1e-5);
        Assertions.assertEquals(0.0, curr.getVelocity(), 1e-5);

        // after any subsequent update, should be exactly equal to goal...
        Assertions.assertFalse(profile.update(0.01, curr, goal));

        Assertions.assertEquals(13180.0, curr.getPosition());
        Assertions.assertEquals(0.0, curr.getVelocity());
    }

    // @Test
    public void checkNormalPathCrossZero()
    {
        // travel 2636 inches over 667 seconds (8s accel, 651s coast, 8s decel)
        TrapezoidProfile profile = new TrapezoidProfile(4.0, 0.5);
        TrapezoidProfile.State curr = new TrapezoidProfile.State(-1318.0, 0.0);
        TrapezoidProfile.State goal = new TrapezoidProfile.State(1318.0, 0.0);

        double prevPosition = curr.getPosition();
        double prevVelocity = curr.getVelocity();
        for (int i = 0; i < 800; i++)
        {
            Assertions.assertTrue(profile.update(0.01, curr, goal));

            // verify accel
            Assertions.assertTrue(curr.getPosition() >= prevPosition, "Position should be increasing");
            Assertions.assertTrue(curr.getPosition() - prevPosition <= 0.01 * 4.0 + 1e-5, "Position should be increasing by at most 4.0 in/sec, actual " + prevPosition + " to " + curr.getPosition());
            Assertions.assertTrue(curr.getVelocity() >= prevVelocity, "Velocity should be increasing");

            prevPosition = curr.getPosition();
            prevVelocity = curr.getVelocity();
        }

        for (int i = 0; i < 65100; i++)
        {
            Assertions.assertTrue(profile.update(0.01, curr, goal));

            // verify coast
            Assertions.assertTrue(curr.getPosition() >= prevPosition, "Position should be increasing");
            Assertions.assertEquals(curr.getPosition() - prevPosition, 0.01 * 4.0, 1e-5, "Position should be increasing by roughly 4.0 in/sec, actual " + prevPosition + " to " + curr.getPosition());
            Assertions.assertEquals(4.0, curr.getVelocity(), 1e-5, "Velocity should be consistently at max");

            prevPosition = curr.getPosition();
            prevVelocity = curr.getVelocity();
        }

        for (int i = 0; i < 800; i++)
        {
            Assertions.assertTrue(profile.update(0.01, curr, goal) || i == 799);

            // verify decel
            Assertions.assertTrue(curr.getPosition() >= prevPosition, "Position should be increasing");
            Assertions.assertTrue(curr.getPosition() - prevPosition <= 0.01 * 4.0 + 1e-5, "Position should be increasing by at most 4.0 in/sec, actual " + prevPosition + " to " + curr.getPosition());
            Assertions.assertTrue(curr.getVelocity() <= prevVelocity, "Velocity should be decreasing");

            prevPosition = curr.getPosition();
            prevVelocity = curr.getVelocity();
        }

        // should at least roughly equal the goal
        Assertions.assertEquals(1318.0, curr.getPosition(), 1e-5);
        Assertions.assertEquals(0.0, curr.getVelocity(), 1e-5);

        // after any subsequent update, should be exactly equal to goal...
        Assertions.assertFalse(profile.update(0.01, curr, goal));

        Assertions.assertEquals(1318.0, curr.getPosition());
        Assertions.assertEquals(0.0, curr.getVelocity());
    }

    // @Test
    public void checkReversePathFromZero()
    {
        TrapezoidProfile profile = new TrapezoidProfile(3.0, 3.0);
        TrapezoidProfile.State curr = new TrapezoidProfile.State(0.0, 0.0);
        TrapezoidProfile.State goal = new TrapezoidProfile.State(-6.0, 0.0);

        for (int i = 0; i < 300; i++)
        {
            Assertions.assertTrue(profile.update(0.01, curr, goal) || i == 299);

            // simple verification of path
            Assertions.assertTrue(curr.getPosition() >= -6.0 && curr.getPosition() <= 0.0, "Position out of range [-6,0] -> " + curr.getPosition());
            Assertions.assertTrue(curr.getVelocity() >= -3.0 && curr.getVelocity() <= 0.0, "Velocity out of range [-3,0] -> " + curr.getVelocity());
        }

        // should at least roughly equal the goal
        Assertions.assertEquals(-6.0, curr.getPosition(), 1e-5);
        Assertions.assertEquals(0.0, curr.getVelocity(), 1e-5);

        // after any subsequent update, should be exactly equal to goal...
        Assertions.assertFalse(profile.update(0.01, curr, goal));

        Assertions.assertEquals(-6.0, curr.getPosition());
        Assertions.assertEquals(0.0, curr.getVelocity());
    }

    // @Test
    public void checkReverseChangeGoal()
    {
        TrapezoidProfile profile = new TrapezoidProfile(1.5, 1.5);

        TrapezoidProfile.State curr = new TrapezoidProfile.State(0.0, 0.0);
        TrapezoidProfile.State goal = new TrapezoidProfile.State(-1.5, 0);

        // first second will have the system reach max speed at position ~0.75
        for (int i = 0; i < 100; i++)
        {
            Assertions.assertTrue(profile.update(0.01, curr, goal));
        }

        Assertions.assertNotEquals(goal.getPosition(), curr.getPosition());

        // lengthen and we should transform this into a full trapezoid and coast at the max speed for 1 second,
        // but only continue for 0.5 seconds
        goal = new TrapezoidProfile.State(-3.0, 0.0);
        for (int i = 0; i < 50; i++)
        {
            Assertions.assertTrue(profile.update(0.01, curr, goal));
        }

        Assertions.assertNotEquals(goal.getPosition(), curr.getPosition());

        // now cut off the end and we should start decreasing velocity automatically,
        // and finish after 1 second
        goal = new TrapezoidProfile.State(-2.25, 0.0);
        for (int i = 0; i < 100; i++)
        {
            Assertions.assertTrue(profile.update(0.01, curr, goal) || i == 99);
        }

        // should at least roughly equal the goal
        Assertions.assertEquals(-2.25, curr.getPosition(), 1e-5);
        Assertions.assertEquals(0.0, curr.getVelocity(), 1e-5);

        // after any subsequent update, should be exactly equal to goal...
        Assertions.assertFalse(profile.update(0.01, curr, goal));

        Assertions.assertEquals(-2.25, curr.getPosition());
        Assertions.assertEquals(0.0, curr.getVelocity());
    }

    // @Test
    public void checkReversePathFromNonZero()
    {
        // travel -11862 inches over 661 seconds (2s accel, 657s coast, 2s decel)
        TrapezoidProfile profile = new TrapezoidProfile(18.0, 9.0);
        TrapezoidProfile.State curr = new TrapezoidProfile.State(-1318.0, 0.0);
        TrapezoidProfile.State goal = new TrapezoidProfile.State(-13180.0, 0.0);

        double prevPosition = curr.getPosition();
        double prevVelocity = curr.getVelocity();
        for (int i = 0; i < 200; i++)
        {
            Assertions.assertTrue(profile.update(0.01, curr, goal));

            // verify accel
            Assertions.assertTrue(curr.getPosition() <= prevPosition, "Position should be decreasing");
            Assertions.assertTrue(curr.getPosition() - prevPosition >= -0.01 * 18.0 - 1e-5, "Position should be decreasing by at most -18.0 in/sec, actual " + prevPosition + " to " + curr.getPosition());
            Assertions.assertTrue(curr.getVelocity() <= prevVelocity, "Velocity should be decreasing");

            prevPosition = curr.getPosition();
            prevVelocity = curr.getVelocity();
        }

        for (int i = 0; i < 65700; i++)
        {
            Assertions.assertTrue(profile.update(0.01, curr, goal));

            // verify coast
            Assertions.assertTrue(curr.getPosition() <= prevPosition, "Position should be decreasing");
            Assertions.assertEquals(curr.getPosition() - prevPosition, -0.01 * 18.0, 1e-5, "Position should be decreasing by roughly -18.0 in/sec, actual " + prevPosition + " to " + curr.getPosition());
            Assertions.assertEquals(-18.0, curr.getVelocity(), 1e-5, "Velocity should be consistently at max");

            prevPosition = curr.getPosition();
            prevVelocity = curr.getVelocity();
        }

        for (int i = 0; i < 200; i++)
        {
            Assertions.assertTrue(profile.update(0.01, curr, goal) || i == 199);;

            // verify decel
            Assertions.assertTrue(curr.getPosition() <= prevPosition, "Position should be decreasing");
            Assertions.assertTrue(curr.getPosition() - prevPosition >= -0.01 * 18.0 - 1e-5, "Position should be decreasing by at most -18.0 in/sec, actual " + prevPosition + " to " + curr.getPosition());
            Assertions.assertTrue(curr.getVelocity() >= prevVelocity, "Velocity should be increasing");

            prevPosition = curr.getPosition();
            prevVelocity = curr.getVelocity();
        }

        Assertions.assertEquals(-13180.0, curr.getPosition(), 1e-5);
        Assertions.assertEquals(0.0, curr.getVelocity(), 1e-5);

        // after any subsequent update, should be exactly equal to goal...
        Assertions.assertFalse(profile.update(0.01, curr, goal));

        Assertions.assertEquals(-13180.0, curr.getPosition());
        Assertions.assertEquals(0.0, curr.getVelocity());
    }

    // @Test
    public void checkReversePathCrossZero()
    {
        // travel -2636 inches over 667 seconds (8s accel, 651s coast, 8s decel)
        TrapezoidProfile profile = new TrapezoidProfile(4.0, 0.5);
        TrapezoidProfile.State curr = new TrapezoidProfile.State(1318.0, 0.0);
        TrapezoidProfile.State goal = new TrapezoidProfile.State(-1318.0, 0.0);

        double prevPosition = curr.getPosition();
        double prevVelocity = curr.getVelocity();
        for (int i = 0; i < 800; i++)
        {
            Assertions.assertTrue(profile.update(0.01, curr, goal));

            // verify accel
            Assertions.assertTrue(curr.getPosition() <= prevPosition, "Position should be decreasing");
            Assertions.assertTrue(curr.getPosition() - prevPosition >= -0.01 * 4.0 - 1e-5, "Position should be decreasing by at most -4.0 in/sec, actual " + prevPosition + " to " + curr.getPosition());
            Assertions.assertTrue(curr.getVelocity() <= prevVelocity, "Velocity should be decreasing");

            prevPosition = curr.getPosition();
            prevVelocity = curr.getVelocity();
        }

        for (int i = 0; i < 65100; i++)
        {
            Assertions.assertTrue(profile.update(0.01, curr, goal));

            // verify coast
            Assertions.assertTrue(curr.getPosition() <= prevPosition, "Position should be decreasing");
            Assertions.assertEquals(curr.getPosition() - prevPosition, -0.01 * 4.0, 1e-5, "Position should be decreasing by roughly -4.0 in/sec, actual " + prevPosition + " to " + curr.getPosition());
            Assertions.assertEquals(-4.0, curr.getVelocity(), 1e-5, "Velocity should be consistently at max");

            prevPosition = curr.getPosition();
            prevVelocity = curr.getVelocity();
        }

        for (int i = 0; i < 800; i++)
        {
            Assertions.assertTrue(profile.update(0.01, curr, goal) || i == 799);

            // verify decel
            Assertions.assertTrue(curr.getPosition() <= prevPosition, "Position should be decreasing");
            Assertions.assertTrue(curr.getPosition() - prevPosition >= -0.01 * 4.0 - 1e-5, "Position should be decreasing by at most -4.0 in/sec, actual " + prevPosition + " to " + curr.getPosition());
            Assertions.assertTrue(curr.getVelocity() >= prevVelocity, "Velocity should be increasing");

            prevPosition = curr.getPosition();
            prevVelocity = curr.getVelocity();
        }

        Assertions.assertEquals(-1318.0, curr.getPosition(), 1e-5);
        Assertions.assertEquals(0.0, curr.getVelocity(), 1e-5);

        // after any subsequent update, should be exactly equal to goal...
        Assertions.assertFalse(profile.update(0.01, curr, goal));

        Assertions.assertEquals(-1318.0, curr.getPosition());
        Assertions.assertEquals(0.0, curr.getVelocity());
    }

    // @Test
    public void checkCutoffPath()
    {
        // travel 210 inches over 20 seconds (1s accel, 19.5s coast, 1s decel)
        TrapezoidProfile profile = new TrapezoidProfile(10.0, 5.0);
        TrapezoidProfile.State curr = new TrapezoidProfile.State(-10.0, 5.0);
        TrapezoidProfile.State goal = new TrapezoidProfile.State(200.0, 5.0);

        double prevPosition = curr.getPosition();
        double prevVelocity = curr.getVelocity();
        for (int i = 0; i < 100; i++)
        {
            Assertions.assertTrue(profile.update(0.01, curr, goal));

            // verify accel
            double positionChange = curr.getPosition() - prevPosition;
            Assertions.assertTrue(positionChange >= 0.0, "Position should be increasing");
            Assertions.assertTrue(positionChange >= 0.01 * 5.0 - 1e-5 && positionChange <= 0.01 * 10.0 + 1e-5, "Position should be increasing by between 5.0 and 10.0 in/sec, actual " + prevPosition + " to " + curr.getPosition());
            Assertions.assertTrue(curr.getVelocity() >= prevVelocity, "Velocity should be increasing");

            prevPosition = curr.getPosition();
            prevVelocity = curr.getVelocity();
        }

        for (int i = 0; i < 1950; i++)
        {
            Assertions.assertTrue(profile.update(0.01, curr, goal));

            // verify coast
            Assertions.assertTrue(curr.getPosition() >= prevPosition, "Position should be decreasing");
            Assertions.assertEquals(curr.getPosition() - prevPosition, 0.01 * 10.0, 1e-5, "Position should be increasing by roughly 10.0 in/sec, actual " + prevPosition + " to " + curr.getPosition());
            Assertions.assertEquals(10.0, curr.getVelocity(), 1e-5, "Velocity should be consistently at max");

            prevPosition = curr.getPosition();
            prevVelocity = curr.getVelocity();
        }

        for (int i = 0; i < 100; i++)
        {
            Assertions.assertTrue(profile.update(0.01, curr, goal) || i == 99);

            // verify decel
            double positionChange = curr.getPosition() - prevPosition;
            Assertions.assertTrue(positionChange >= 0.0, "Position should be increasing");
            Assertions.assertTrue(positionChange >= 0.01 * 5.0 - 1e-5 && positionChange <= 0.01 * 10.0 + 1e-5, "Position should be increasing by between 5.0 and 10.0 in/sec, actual " + prevPosition + " to " + curr.getPosition());
            Assertions.assertTrue(curr.getVelocity() <= prevVelocity, "Velocity should be decreasing");

            prevPosition = curr.getPosition();
            prevVelocity = curr.getVelocity();
        }

        Assertions.assertEquals(200.0, curr.getPosition(), 1e-5);
        Assertions.assertEquals(5.0, curr.getVelocity(), 1e-5);

        // after any subsequent update, should be exactly equal to goal...
        Assertions.assertFalse(profile.update(0.01, curr, goal));

        Assertions.assertEquals(200.0, curr.getPosition());
        Assertions.assertEquals(5.0, curr.getVelocity());
    }

    // @Test
    public void checkDisjointPathCrossZero()
    {
        // travel 2636 inches over 664 seconds (8s accel, 654s coast, 2s decel)
        TrapezoidProfile profile = new TrapezoidProfile(4.0, 0.5, 2.0);
        TrapezoidProfile.State curr = new TrapezoidProfile.State(-1318.0, 0.0);
        TrapezoidProfile.State goal = new TrapezoidProfile.State(1318.0, 0.0);

        double prevPosition = curr.getPosition();
        double prevVelocity = curr.getVelocity();
        for (int i = 0; i < 800; i++)
        {
            Assertions.assertTrue(profile.update(0.01, curr, goal));

            // verify accel
            Assertions.assertTrue(curr.getPosition() >= prevPosition, "Position should be increasing");
            Assertions.assertTrue(curr.getPosition() - prevPosition <= 0.01 * 4.0 + 1e-5, "Position should be increasing by at most 4.0 in/sec, actual " + prevPosition + " to " + curr.getPosition());
            Assertions.assertTrue(curr.getVelocity() >= prevVelocity, "Velocity should be increasing");

            prevPosition = curr.getPosition();
            prevVelocity = curr.getVelocity();
        }

        for (int i = 0; i < 65400; i++)
        {
            Assertions.assertTrue(profile.update(0.01, curr, goal));

            // verify coast
            Assertions.assertTrue(curr.getPosition() >= prevPosition, "Position should be increasing");
            Assertions.assertEquals(curr.getPosition() - prevPosition, 0.01 * 4.0, 1e-5, "Position should be increasing by roughly 4.0 in/sec, actual " + prevPosition + " to " + curr.getPosition());
            Assertions.assertEquals(4.0, curr.getVelocity(), 1e-5, "Velocity should be consistently at max");

            prevPosition = curr.getPosition();
            prevVelocity = curr.getVelocity();
        }

        for (int i = 0; i < 200; i++)
        {
            Assertions.assertTrue(profile.update(0.01, curr, goal) || i == 199);

            // verify decel
            Assertions.assertTrue(curr.getPosition() >= prevPosition, "Position should be increasing");
            Assertions.assertTrue(curr.getPosition() - prevPosition <= 0.01 * 4.0 + 1e-5, "Position should be increasing by at most 4.0 in/sec, actual " + prevPosition + " to " + curr.getPosition());
            Assertions.assertTrue(curr.getVelocity() <= prevVelocity, "Velocity should be decreasing");

            prevPosition = curr.getPosition();
            prevVelocity = curr.getVelocity();
        }

        // should at least roughly equal the goal
        Assertions.assertEquals(1318.0, curr.getPosition(), 1e-5);
        Assertions.assertEquals(0.0, curr.getVelocity(), 1e-5);

        // after any subsequent update, should be exactly equal to goal...
        Assertions.assertFalse(profile.update(0.01, curr, goal));

        Assertions.assertEquals(1318.0, curr.getPosition());
        Assertions.assertEquals(0.0, curr.getVelocity());
    }
}
