package lewes_thread;

import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.utility.Delay;

public class MoveThread extends Thread {
	private BaseRegulatedMotor mL, mR;
	private boolean[] pass;
	private int turnDelay, hSpeed, lSpeed;

	public MoveThread(BaseRegulatedMotor _mL, BaseRegulatedMotor _mR, boolean[] _pass) {
		mL = _mL;
		mR = _mR;
		pass = _pass;
		turnDelay = 2900;
		hSpeed = 720;
		lSpeed = 200;
	}

	public void run() {
//		while (!Button.ENTER.isDown()) { // old for before using pass
		while (!pass[0]) {
			mL.setSpeed(hSpeed);
			mR.setSpeed(lSpeed);
			mL.synchronizeWith(new BaseRegulatedMotor[] { mR });
			mL.startSynchronization();
			mL.forward();
			mR.forward();
			mL.endSynchronization();
			Delay.msDelay(turnDelay);

			mL.setSpeed(lSpeed);
			mR.setSpeed(hSpeed);
			mL.synchronizeWith(new BaseRegulatedMotor[] { mR });
			mL.startSynchronization();
			mL.forward();
			mR.forward();
			mL.endSynchronization();
			Delay.msDelay(turnDelay);
		}
		mL.stop();
		mR.stop();

	}

}
