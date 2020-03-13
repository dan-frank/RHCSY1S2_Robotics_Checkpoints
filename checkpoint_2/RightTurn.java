package checkpoint_2;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;

public class RightTurn {

	final static int TurnDelay = 345;
	final static int MotorSpeed = 720;

	public static void main(String[] args) {
		BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor( MotorPort.A );
		BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor( MotorPort.B );
		LCD.drawString(" IRIS 3", 2, 2);
		mLeft.setSpeed(MotorSpeed); // 2 Revolutions Per Second( RPS )
		mRight.setSpeed(MotorSpeed);
		mLeft.synchronizeWith( new BaseRegulatedMotor[] { mRight });
		mLeft.startSynchronization();
		mLeft.forward();
		mRight.backward();
		mLeft.endSynchronization();
		Delay.msDelay(TurnDelay);
		mLeft.startSynchronization();
		mLeft.stop();
		mRight.stop();
		mLeft.endSynchronization();


		mLeft.close();
		mRight.close();
	}

}
