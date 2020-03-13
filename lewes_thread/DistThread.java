package lewes_thread;

import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

public class DistThread extends Thread {
	private EV3UltrasonicSensor us;
	private int count = 0;
	private boolean[] pass;

	public DistThread(EV3UltrasonicSensor _us, boolean[] _pass) {
		us = _us;
		pass = _pass;
	}

	public void run() {
		SampleProvider sp = us.getDistanceMode();
		float[] samples = new float[1];
		sp.fetchSample(samples, 0);
		while (!stop(samples[0])) {
			sp.fetchSample(samples, 0);
			count++;

			LCD.drawString("" + samples[0], 1, 1);
			LCD.drawString("Count: ", 1, 2);
			LCD.drawString("" + count, 4, 3);

		}
		LCD.clear();
		LCD.drawString("Tot. Count: ", 1, 1);
		LCD.drawString("" + count, 4, 2);
		LCD.drawString("Term. Dist:", 1, 3);
		LCD.drawString("" + samples[0], 4, 4);
		pass[0] = true;
	}

	public boolean stop(float sample) {
		return (sample < 0.2f);
	}
}