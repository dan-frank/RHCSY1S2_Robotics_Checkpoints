package checkpoint_3;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorMode;
//import lejos.utility.Delay;

public class DistanceCheck {

	public static void main(String[] args) {

		float[] level2 = new float[1];
		EV3UltrasonicSensor sensor2 = new EV3UltrasonicSensor(SensorPort.S1);
		SensorMode distance = (SensorMode) sensor2.getDistanceMode();

		while (!Button.ESCAPE.isDown()) {
			distance.fetchSample(level2, 0);
			if (Button.ENTER.isDown()) {
				LCD.clear();
				String display = String.valueOf(level2[0]);
				LCD.drawString(display, 2, 2);
			}

		}
		
		sensor2.close();

	}

}
