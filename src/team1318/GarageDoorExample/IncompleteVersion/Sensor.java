package team1318.GarageDoorExample.IncompleteVersion;

public class Sensor {
	private int port;
	private boolean triggered;
	public Sensor(int port){
		this.port = port;
		VirtualGarageDoor.addSensor(this);
	}
	
	public boolean isTriggered(){
		return triggered;
	}
	
	public void setTrigger(boolean triggered){
		if(triggered != this.triggered && triggered){
			System.out.println("Sensor " + getPort() + " triggered!");
		}
		this.triggered = triggered;
	}
	
	public int getPort(){
		return port;
	}
}
