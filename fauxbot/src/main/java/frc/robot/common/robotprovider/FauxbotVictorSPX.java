package frc.robot.common.robotprovider;

public class FauxbotVictorSPX extends FauxbotAdvancedMotorBase implements IVictorSPX
{
    public FauxbotVictorSPX(int deviceNumber)
    {
        super(deviceNumber);
    }

    public void follow(ITalonSRX talonSRX)
    {
    }

    public void follow(IVictorSPX victorSPX)
    {
    }

    public void setControlMode(TalonSRXControlMode mode)
    {
    }

    public void setNeutralMode(TalonSRXNeutralMode neutralMode)
    {
    }

    public void setInvertOutput(boolean invert)
    {
    }
}
