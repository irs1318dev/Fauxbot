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

    public void setControlMode(TalonSRXControlMode mode)
    {
    }

    public void setMotorOutputSettings(boolean invert, MotorNeutralMode neutralMode)
    {
    }

    public void stop()
    {
    }
}
