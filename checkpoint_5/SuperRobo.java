package checkpoint_5;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

public class SuperRobo {

	public static void main(String[] args) {		
		float[] level = new float[1];

		EV3ColorSensor sensor = new EV3ColorSensor(SensorPort.S3);
		SampleProvider sensorLight = sensor.getAmbientMode();

		double maxLightLevel = 0;
		double minLightLevel = 1;

		// For print
		String lightLevelMax = "";
		String lightLevelMin = "";

		// Gets the average light levels
		while (!Button.ENTER.isDown()) {
			sensorLight.fetchSample(level, 0);

			if (level[0] > maxLightLevel) { maxLightLevel = level[0]; }
			if (level[0] < minLightLevel) { minLightLevel = level[0]; }

			lightLevelMax = "MAX:" + String.valueOf(maxLightLevel);
			lightLevelMin = "MIN:" + String.valueOf(minLightLevel);

			LCD.clear(2);
			LCD.clear(3);
			LCD.drawString(lightLevelMax, 1, 2);
			LCD.drawString(lightLevelMin, 1, 3);

			Delay.msDelay(200);
		}

		double lightLevelAvg = (maxLightLevel + minLightLevel) / 2;
		String lightLevelAverage = "AVG:" + lightLevelAvg;
		
		EV3UltrasonicSensor us = new EV3UltrasonicSensor(SensorPort.S1);

		LCD.clear(4);
		LCD.drawString(lightLevelAverage, 1, 4);
	
		MovePilot pilot = getPilot(56.0, 73.5);
		
		double[] speed = new double[1];
		speed[0] = 100;
		
		Behavior trundle = new Trundle(pilot, speed);
//		Behavior light = new Light(speed, lightLevelAvg, sensor);
//		Behavior dark = new Dark(speed, lightLevelAvg, sensor);
		Behavior backup = new Backup(pilot, us);
		Behavior batteryLevel = new BatteryLevel();
		Behavior emergencyStop = new EmergencyStop(pilot);
		
		Arbitrator ab = new Arbitrator(new Behavior[]{batteryLevel, trundle, backup, emergencyStop});
        ab.go();
        
        sensor.close();
	}

	private static MovePilot getPilot(double diam , double offset) {
		BaseRegulatedMotor mL = new EV3LargeRegulatedMotor(MotorPort.A);
		BaseRegulatedMotor mR = new EV3LargeRegulatedMotor(MotorPort.B);
		
		Wheel wL = WheeledChassis.modelWheel (mL,diam).offset(-1 * offset);
		Wheel wR = WheeledChassis.modelWheel (mR, diam ).offset(offset);		
		Wheel[] ws = new Wheel[] {wR, wL};
		
		Chassis chassis = new WheeledChassis (ws, WheeledChassis.TYPE_DIFFERENTIAL);
		
		return new MovePilot(chassis);
	}
}
