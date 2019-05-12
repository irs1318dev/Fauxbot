package frc.robot.common.robotprovider;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Direction;
import edu.wpi.first.wpilibj.Relay.Value;

public class RelayWrapper implements IRelay
{
    private final Relay wrappedObject;

    public RelayWrapper(int channel)
    {
        this.wrappedObject = new Relay(channel);
    }

    public RelayWrapper(int channel, RelayDirection direction)
    {
        this.wrappedObject = new Relay(channel, RelayWrapper.translateDirection(direction));
    }

    public void set(RelayValue value)
    {
        Value wrappedValue;
        if (value == RelayValue.On)
        {
            wrappedValue = Value.kOn;
        }
        else if (value == RelayValue.Forward)
        {
            wrappedValue = Value.kForward;
        }
        else if (value == RelayValue.Reverse)
        {
            wrappedValue = Value.kReverse;
        }
        else // if (value == RelayValue.kOf)
        {
            wrappedValue = Value.kOff;
        }

        this.wrappedObject.set(wrappedValue);
    }

    public void setDirection(RelayDirection direction)
    {
        this.wrappedObject.setDirection(RelayWrapper.translateDirection(direction));
    }

    private static Direction translateDirection(RelayDirection direction)
    {
        Direction wrappedDirection;
        if (direction == RelayDirection.Forward)
        {
            wrappedDirection = Direction.kForward;
        }
        else if (direction == RelayDirection.Reverse)
        {
            wrappedDirection = Direction.kReverse;
        }
        else // if (direction == RelayDirection.kBoth)
        {
            wrappedDirection = Direction.kBoth;
        }

        return wrappedDirection;
    }
}
