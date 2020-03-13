package checkpoint_3;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class FollowLine {

	public static void main(String[] args) {
		float[] level = new float[2]; // A sound sample is just one number
		EV3ColorSensor sensor = new EV3ColorSensor(SensorPort.S3);
		SampleProvider light  = sensor.getRedMode();

		float maxLightLevel = 0;
		float minLightLevel = 1;

		// For print
		String lightLevelMax = "";
		String lightLevelMin = "";

		// Gets the average light levels
		while (!Button.ENTER.isDown()) {
			// Stores light into level[0]
			light.fetchSample(level, 0);

			// Gets largest sound
			if (level[0] > maxLightLevel) {
				maxLightLevel = level[0];
			}

			// Gets smallest sound
			if (level[0] < minLightLevel) {
				minLightLevel = level[0];
			}

			lightLevelMax = "MAX:" + String.valueOf(maxLightLevel);
			lightLevelMin = "MIN:" + String.valueOf(minLightLevel);

			LCD.clear(1);
			LCD.clear(2);
			LCD.drawString(lightLevelMax, 1, 1);
			LCD.drawString(lightLevelMin, 1, 2);

			Delay.msDelay(200);
		}

		float lightLevelAvg = (maxLightLevel + minLightLevel) / 2;
		String lightLevelAverage = "AVG:" + lightLevelAvg;

		LCD.clear(3);
		LCD.drawString(lightLevelAverage, 1, 3);

		BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor(MotorPort.A);
		BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor(MotorPort.B);
		mLeft.setSpeed(360); // 1 Revolution Per Second ( RPS )
		mRight.setSpeed(360);
		mLeft.synchronizeWith(new BaseRegulatedMotor[] { mRight });

		// Follows the line
		while (!Button.ENTER.isDown()) {
			// Stores light into level[0]
			light.fetchSample(level, 0);

			mLeft.startSynchronization();
			if (level[0] > lightLevelAvg) {
				mLeft.forward();
				mRight.stop();
			} else {
				mLeft.stop();
				mRight.forward();
			}
			mLeft.endSynchronization();
		}

		sensor.close();
		mLeft.close();
		mRight.close();

		Delay.msDelay(2000);
	}

}
