package team1318.GarageDoorExample.IncompleteVersion;
import java.util.ArrayList;


public class VirtualGarageDoor {
	private static ArrayList<Sensor> sensors = new ArrayList<Sensor>();
	private static ArrayList<Motor> motors = new ArrayList<Motor>();
	private static double position = 0;
	
	private static final int CLOSED = 0;
	private static final int OPENED = 5;
	
	
	public static void addSensor(Sensor sensor){
		sensors.add(sensor);
	}
	
	public static void addMotor(Motor motor){
		motors.add(motor);
	}
	
	public static void updateSensors(String input){
		
	
		for(Sensor sensor: sensors){
			
			if(sensor.getPort() == 1 && position >= OPENED){
				sensor.setTrigger(true);
			} else if(sensor.getPort() == 2 && position <= CLOSED){
				sensor.setTrigger(true);
			} else if (sensor.getPort() == 3 && input.equals("t")){
				sensor.setTrigger(true);
			}
						
		}
	}
	
	public static void updateMotors(){
		for(Motor motor : motors){
			if(motor.getPort() == 1){
				position += motor.getSpeed();
			}
		}
		
		for(Sensor sensor : sensors){
			sensor.setTrigger(false);
		}
		
	}
	
	public static double getPosition(){
		return position;
	}
	
}
