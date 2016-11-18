package team1318.GarageDoorExample.IncompleteVersion;

public class Motor {
	private int port;
	private double speed;
	
	public Motor(int port){
		this.port = port;
		VirtualGarageDoor.addMotor(this);
	}
	
	public void setSpeed(double speed){
		this.speed = speed;
	}
	
	public double getSpeed(){
		return speed;
	}
	
	public int getPort(){
		return port;
	}
	
}
