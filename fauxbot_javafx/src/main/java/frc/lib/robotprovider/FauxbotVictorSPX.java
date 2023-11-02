package frc.lib.robotprovider;

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

    public void follow(ITalonFX talonFX)
    {
    }

    public void setControlMode(TalonXControlMode mode)
    {
    }

    public void setNeutralMode(MotorNeutralMode neutralMode)
    {
    }

    public void setInvertOutput(boolean invert)
    {
    }

    public void stop()
    {
    }
}
