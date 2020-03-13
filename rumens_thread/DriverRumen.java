package rumens_thread;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;

public class DriverRumen {
  final static int TOTAL = 20000;
	public static void main(String[] args) {
		BaseRegulatedMotor mL = new EV3LargeRegulatedMotor(MotorPort.A);
		BaseRegulatedMotor mR = new EV3LargeRegulatedMotor(MotorPort.B);
		mL.synchronizeWith(new BaseRegulatedMotor[] {mR});
		
		Thread watcher = new WatcherThreadRumen(mL,mR);
//		watcher.setDaemon(true);
		watcher.start();
		mL.startSynchronization();
		mL.rotate(TOTAL);
		mR.rotate(TOTAL);
		mL.endSynchronization();
		LCD.drawString("Finished "+mL.getTachoCount(), 2, 4);
		Button.ENTER.waitForPressAndRelease();

	}

}
