package lewes_thread;

import lejos.hardware.Button;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;

public class Driver {
	public static void main(String[] args) {

		boolean[] pass = new boolean[] {false};
		BaseRegulatedMotor mL = new EV3LargeRegulatedMotor(MotorPort.A);
		BaseRegulatedMotor mR = new EV3LargeRegulatedMotor(MotorPort.B);
		EV3UltrasonicSensor us = new EV3UltrasonicSensor(SensorPort.S1);
		DistThread distCheck = new DistThread(us, pass);
		MoveThread moveThread = new MoveThread(mL, mR, pass);
		distCheck.start();
		moveThread.start();

		while (!Button.ENTER.isDown()) {
		}

		System.exit(0);
	}

}
