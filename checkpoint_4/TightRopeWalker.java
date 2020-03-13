package checkpoint_4;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.pathfinding.Path;

public class TightRopeWalker {

	final static float WHEELDIAMETER = 56; // The diameter (mm) of the wheels
	final static float WHEELTHICKNESS = 26; // The diameter (mm) of the wheels
	final static float AXLELENGTH = 147 - WHEELTHICKNESS; // The distance (mm) your two driven wheels
	final static float ANGULARSPEED = 100; // How fast around corners (degrees/sec)
	final static float LINEARSPEED = 50; // How fast in a straight line (mm/sec)

	public static void main (String[] args) {
		BaseRegulatedMotor motorL = new EV3LargeRegulatedMotor(MotorPort.A);
		// Create a "Wheel" with Diameter 51mm and offset 22mm left of centre.
		Wheel wheelL = WheeledChassis.modelWheel(motorL, WHEELDIAMETER).offset(-AXLELENGTH / 2);
		
		BaseRegulatedMotor motorR = new EV3LargeRegulatedMotor(MotorPort.B);
		// Create a "Wheel" with Diameter 51mm and offset 22mm left of centre.
		Wheel wheelR = WheeledChassis.modelWheel(motorR, WHEELDIAMETER).offset(AXLELENGTH / 2);
				
		// Create a "Chassis" with two wheels on it.
		Chassis chassis = new WheeledChassis((new Wheel[] {wheelR, wheelL}), WheeledChassis.TYPE_DIFFERENTIAL);
		
		// Finally create a pilot which can drive its wheels separately.
		MovePilot plt = new MovePilot(chassis);
		
		plt.setAngularSpeed(ANGULARSPEED);
		plt.setLinearSpeed(LINEARSPEED);
		
		PoseProvider poseProvider = new OdometryPoseProvider(plt);
		Navigator navigator = new Navigator(plt, poseProvider);
		Path path = new Path();
		
		path.add(new Waypoint(600, 0));
		path.add(new Waypoint(0, 0));
		
		navigator.followPath(path);
		navigator.waitForStop();

		LCD.clear();
		LCD.drawString("Path Finished!", 2, 2);
		
		motorL.close();
		motorR.close();
	}

}
