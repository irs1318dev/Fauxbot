package frc.robot.common.robotprovider;

public class FauxbotVictorSPX extends FauxbotAdvancedMotorBase implements IVictorSPX
{
    public FauxbotVictorSPX(int deviceNumber)
    {
        super(deviceNumber);
    }

    public void setControlMode(TalonSRXControlMode mode)
    {
    }

    public void setInvertOutput(boolean invert)
    {
    }

    public void setInvertSensor(boolean invert)
    {
    }
}
