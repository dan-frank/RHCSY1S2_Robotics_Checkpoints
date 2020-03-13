package checkpoint_5;

import lejos.hardware.lcd.LCD;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class Trundle implements Behavior {
	private MovePilot p;
//	private double[] s;
//	private double so = 0;

	Trundle(MovePilot pilot, double[] speed) {
		this.p = pilot;
//		this.s = speed;
	}

	// Start trundling and return control immediately.
	public void action() {
		LCD.clear(5);
		LCD.drawString("Trundle", 1, 5);
		
		p.setLinearSpeed(100);
		if (!p.isMoving()) {p.forward();}
//		so = s[0];
	}

	// Since action returns immediately this is probably never called
	public void suppress() {
	}

	// Is it my turn?
	public boolean takeControl() {
		return true;
	}
}
