package checkpoint_2;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;

public class CarRotate {

	public static void main(String[] args) {
		BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor(MotorPort.A);
		BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor(MotorPort.B);
		LCD.drawString(" IRIS 4" , 2 , 2);

		// Tell JVM what the left motor is synchronized with .
		mLeft.synchronizeWith(new BaseRegulatedMotor [] { mRight });
		mLeft.setSpeed(720); // 2 Revolutions Per Second(RPS)
		mRight.setSpeed(720);
		// Do a for loop here to run FOUR times
		int sidesOfASquare = 4;
		for (int i = 0; i < sidesOfASquare; i++) {
		
		mLeft.startSynchronization();
		// these rotates will not begin until we end the synchronization
		mLeft.rotate(2000);
		mRight.rotate(2000);
		// actually begin both motors rotating at exactly the same time
		mLeft.endSynchronization();
		mLeft.waitComplete();
		mRight.waitComplete(); // wait for both motors to finish turning
		Delay.msDelay(500);
		mLeft.rotate(707); // one motor turns to go around a corner
		// start a synchronized edge of the square
		Delay.msDelay(500);
		}
		mLeft.close();
		mRight.close();
	}

}
