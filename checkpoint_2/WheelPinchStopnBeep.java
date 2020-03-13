package checkpoint_2;

import lejos.hardware.Sound;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;

public class WheelPinchStopnBeep {

	public static void main(String[] args) {
		BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor( MotorPort.A );
		BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor( MotorPort.B );
		mLeft.setSpeed(720); // 2 Revolutions Per Second( RPS )
		mRight.setSpeed(720);
		mLeft.synchronizeWith( new BaseRegulatedMotor[] { mRight });
		mLeft.startSynchronization();
		mLeft.forward();
		mRight.forward();
		mLeft.endSynchronization();
		Delay.msDelay(500);

		Boolean running = true;
		while (running) {
			int Speed1 = mLeft.getRotationSpeed();
			int Speed2 = mRight.getRotationSpeed();

			if (Speed1 == 0 || Speed2 == 0) {
				Sound.beepSequenceUp();
				mLeft.stop();
				mRight.stop();
				running = false;
			}
		}

		mLeft.close();
		mRight.close();
	}

}
