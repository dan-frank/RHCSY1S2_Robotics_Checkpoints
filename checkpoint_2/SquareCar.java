package checkpoint_2;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;


public class SquareCar {
	final static int MotorSpeed = 720;
	final static int SquareNum = 4;
	final static int MeterDelay = 2842;
	final static int TurnDelay = 345;
	final static int StopDelay = 300;

	public static void main(String[] args) {
		BaseRegulatedMotor mLeft = new EV3LargeRegulatedMotor( MotorPort.A );
		BaseRegulatedMotor mRight = new EV3LargeRegulatedMotor( MotorPort . B );
		LCD.drawString(" IRIS 7" , 2 , 2);
		mLeft.setSpeed(MotorSpeed); // 2 Revolutions Per Second ( RPS )
		mRight.setSpeed(MotorSpeed);
		mLeft.synchronizeWith (new BaseRegulatedMotor[] { mRight });
		
		//		Button.ENTER.waitForPressAndRelease ();
		for (int i = 0; i < SquareNum; i++) {
			mLeft.startSynchronization();
			mLeft.forward();
			mRight.forward();
			mLeft.endSynchronization();
			Delay.msDelay(MeterDelay);
			mLeft.startSynchronization();
			mLeft.stop();
			mRight.stop();
			mLeft.endSynchronization();
			Delay.msDelay(StopDelay);
			mLeft.startSynchronization();
			mLeft.forward();
			mRight.backward();
			mLeft.endSynchronization();
			Delay.msDelay(TurnDelay);
			mLeft.startSynchronization();
			mLeft.stop();
			mRight.stop();
			mLeft.endSynchronization();
			Delay.msDelay(StopDelay);
		}


		mLeft.close();
		mRight.close();
	}

}
