package frc.lib.robotprovider;

public abstract class FauxbotSimpleMotorBase extends FauxbotMotorBase
{
    protected FauxbotActuatorConnection connection;

    protected FauxbotSimpleMotorBase(int port)
    {
        this.connection = new FauxbotActuatorConnection(FauxbotActuatorConnection.ActuatorConnector.PWM, port);
        //if (FauxbotActuatorManager.get(this.connection) == null){
           //FauxbotActuatorManager.set(this.connection, this); 
        //}
        FauxbotActuatorManager.set(this.connection, this);
    }
}
