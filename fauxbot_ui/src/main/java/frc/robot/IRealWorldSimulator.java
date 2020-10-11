package frc.robot;

import frc.robot.common.robotprovider.*;
import javafx.scene.canvas.Canvas;

public interface IRealWorldSimulator
{
    public FauxbotSensorConnection[] getSensors();
    public FauxbotActuatorConnection[] getActuators();
    public boolean getSensorTextBox(FauxbotSensorConnection connection);
    public String getSensorName(FauxbotSensorConnection connection);
    public double getSensorMin(FauxbotSensorConnection connection);
    public double getSensorMax(FauxbotSensorConnection connection);
    public String getActuatorName(FauxbotActuatorConnection connection);
    public double getMotorMin(FauxbotActuatorConnection connection);
    public double getMotorMax(FauxbotActuatorConnection connection);
	public boolean shouldSimulatePID();
    public void update();
    public void draw(Canvas canvas);
}
