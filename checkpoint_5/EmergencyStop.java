package checkpoint_5;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class EmergencyStop implements Behavior {
	private MovePilot p;
	
	EmergencyStop(MovePilot p){
		this.p = p;
	}

	@Override
	public boolean takeControl() {
		return Button.ESCAPE.isDown(); // Returns a boolean
	}

	@Override
	public void action() {
		LCD.clear(5);
		LCD.drawString("Stop", 1, 5);
		
		p.stop();
	}

	@Override
	public void suppress() {
	}

}
