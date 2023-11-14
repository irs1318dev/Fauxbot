package frc.robot.simulation;

import com.badlogic.gdx.scenes.scene2d.Group;

import frc.lib.robotprovider.FauxbotActuatorConnection;
import frc.lib.robotprovider.FauxbotSensorConnection;

public abstract class SimulatorBase extends Group
{
    public abstract FauxbotSensorConnection[] getSensors();
    public abstract FauxbotActuatorConnection[] getActuators();
    public abstract String getSensorName(FauxbotSensorConnection connection);
    public abstract double getSensorMin(FauxbotSensorConnection connection);
    public abstract double getSensorMax(FauxbotSensorConnection connection);
    public abstract String getActuatorName(FauxbotActuatorConnection connection);
    public abstract double getMotorMin(FauxbotActuatorConnection connection);
    public abstract double getMotorMax(FauxbotActuatorConnection connection);
	public abstract boolean shouldSimulatePID();
}
