package rumens_thread;

import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

public class WatcherThreadRumen extends Thread {

	private BaseRegulatedMotor mL;
	private BaseRegulatedMotor mR;

	public WatcherThreadRumen(BaseRegulatedMotor _mL, BaseRegulatedMotor _mR) {
		mL = _mL;
		mR= _mR;
		
		
	}

	public void run() {
		try (EV3UltrasonicSensor us = new EV3UltrasonicSensor(SensorPort.S1)) {
			SampleProvider sp = us.getDistanceMode();
			float[] samples = new float[1];
			while (true) {

				sp.fetchSample(samples, 0);
				mL.setSpeed(samples[0] < 0.2 ? 1 : 720);
				mR.setSpeed(samples[0] < 0.2 ? 1 : 720);
				Thread.yield();
			}
		}
	}
}
