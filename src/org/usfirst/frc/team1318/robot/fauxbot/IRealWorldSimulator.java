package org.usfirst.frc.team1318.robot.fauxbot;

import javafx.scene.canvas.Canvas;

public interface IRealWorldSimulator
{
    public String getSensorName(int channel);
    public double getEncoderMax(int channel);
    public String getActuatorName(int channel);
    public void update();
    public void draw(Canvas canvas);
}
