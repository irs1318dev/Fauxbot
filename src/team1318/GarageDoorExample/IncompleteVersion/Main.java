package team1318.GarageDoorExample.IncompleteVersion;
import java.util.Scanner;


public class Main {

	public static void main(String[] args) {
		Driver driver = new Driver();
		DoorController controller = new DoorController(driver);
		Scanner scanner = new Scanner(System.in);
		boolean running = true;
		while(running){
			
			String input = scanner.nextLine();
			if(input.equals("stop")){
				running = false;
			}
			if(input.equals("b")){
				driver.setPressed(true);
			}
			VirtualGarageDoor.updateSensors(input);
			controller.update();
			VirtualGarageDoor.updateMotors();
			VirtualGarageDoor.updateSensors("");
			System.out.println("William's Garage Door is " + VirtualGarageDoor.getPosition() + " meters off the ground!");
			driver.setPressed(false);
			controller.update();
		}
		scanner.close();
	}

}
