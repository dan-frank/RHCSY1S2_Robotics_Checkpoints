package checkpoint_5;

import java.util.Random;

import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Behavior;

public class Backup implements Behavior {
	private MovePilot turner;
	private SampleProvider sp;
	private Random rgen = new Random();
	private float[] samples = new float[1];

	Backup(MovePilot p, EV3UltrasonicSensor us) {
		turner = p;
		this.sp = us.getDistanceMode();
	}

	public void action() {
		LCD.clear(5);
		LCD.drawString("Backup", 1, 5);
		turner.setAngularSpeed(100);
		turner.travel(-200);
		turner.rotate((2 * rgen.nextInt(2) - 1) * 30);
	}

	// It is not sensible to suppress this Behavior. Just let it finish.
	public void suppress() {
	}

	// Is it my turn?
	public boolean takeControl() {
		sp.fetchSample(samples, 0);
		return (samples[0] < 0.20f);
	}
}
