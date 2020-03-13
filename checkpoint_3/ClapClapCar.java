package checkpoint_3;

import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.NXTSoundSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class ClapClapCar {

	public static void main(String[] args) {
		float[] level = new float[2]; // A sound sample is just one number
		NXTSoundSensor sensor = new NXTSoundSensor(SensorPort.S2);
		SampleProvider sound = sensor.getDBAMode();

		float maxSoundLevel = 0;
		float minSoundLevel = 1;

		boolean running = true;

		// For print
		String soundLevelMax = "";
		String soundLevelMin = "";

		while (running) {
			// Stores sound into level[0]
			sound.fetchSample(level, 0);

			// Gets largest sound
			if (level[0] > maxSoundLevel) {
				maxSoundLevel = level[0];
			}

			// Gets smallest sound
			if (level[0] < minSoundLevel) {
				minSoundLevel = level[0];
			}

			soundLevelMax = "MAX:" + String.valueOf(maxSoundLevel);
			soundLevelMin = "MIN:" + String.valueOf(minSoundLevel);

			LCD.clear(2);
			LCD.clear(3);
			LCD.drawString(soundLevelMax, 1, 2);
			LCD.drawString(soundLevelMin, 1, 3);

			Delay.msDelay(200);
		}

		sensor.close();
	}

}
