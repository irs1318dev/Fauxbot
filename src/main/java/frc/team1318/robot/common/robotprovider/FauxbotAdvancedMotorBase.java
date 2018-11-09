package frc.team1318.robot.common.robotprovider;

public abstract class FauxbotAdvancedMotorBase extends FauxbotMotorBase
{
    protected FauxbotActuatorConnection connection;

    protected FauxbotAdvancedMotorBase(int deviceNumber)
    {
        this.connection = new FauxbotActuatorConnection(FauxbotActuatorConnection.ActuatorConnector.CAN, deviceNumber);
        FauxbotActuatorManager.set(this.connection, this);
    }
}
