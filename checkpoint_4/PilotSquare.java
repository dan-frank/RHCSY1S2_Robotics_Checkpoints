package checkpoint_4;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.geometry.Line;
import lejos.robotics.geometry.Rectangle;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.mapping.LineMap;
import lejos.robotics.navigation.DestinationUnreachableException;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Pose;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.pathfinding.Path;
import lejos.robotics.pathfinding.PathFinder;
import lejos.robotics.pathfinding.ShortestPathFinder;
import lejos.utility.Delay;

public class PilotSquare {
	final static float WHEELDIAMETER = 56; // The diameter (mm) of the wheels
	final static float WHEELTHICKNESS = 26; // The diameter (mm) of the wheels
	final static float AXLELENGTH = (147 - WHEELTHICKNESS) / 2; // The distance (mm) your two driven wheels
	final static float ANGULARSPEED = 100; // How fast around corners (degrees/sec)
	final static float LINEARSPEED = 150; // How fast in a straight line (mm/sec)
	
	public static void main(String[] args) {
		LCD.drawString("VERSION: 0.20", 1,1);
		
		// Wheel Diameter 60mm, both wheels set 29mm from car centre
		BaseRegulatedMotor motorL = new EV3LargeRegulatedMotor(MotorPort.A);
		BaseRegulatedMotor motorR = new EV3LargeRegulatedMotor(MotorPort.B);
	
		Wheel wL = WheeledChassis.modelWheel(motorL, WHEELDIAMETER).offset(-AXLELENGTH);
		Wheel wR = WheeledChassis.modelWheel(motorR, WHEELDIAMETER).offset(AXLELENGTH);
		Wheel[] ws = new Wheel[] {wR, wL};
		
		Chassis chassis = new WheeledChassis(ws, WheeledChassis.TYPE_DIFFERENTIAL);
		MovePilot plt = new MovePilot(chassis);
		
		plt.setAngularSpeed(ANGULARSPEED);
		plt.setLinearSpeed(LINEARSPEED);
	
		PoseProvider poseProvider = new OdometryPoseProvider(plt);
		Navigator navigator = new Navigator(plt, poseProvider);
		
		Line[] lines = new Line[4];
		lines[0] = new Line(250f, 0f, 400f, 0f);
		lines[1] = new Line(280f, -20f, 280f, 300f);
		lines[2] = new Line(250f, 270f, 400f, 270f);
		lines[3] = new Line(370f, 300f, 370f, -20f);
		
		Rectangle bounds = new Rectangle (-50, -50, 800, 700);
		
		LineMap map = new LineMap(lines, bounds);
		PathFinder pf = new ShortestPathFinder(map);
		
		try {
			int posXStart = 140,
				posYStart = 80,
				posAngle  = 0,
				posXEnd   = 600,
				posYEnd   = 200;
			
			LCD.drawString("SRTX:"+posXStart, 1, 2);
			LCD.drawString("SRTY"+posYStart, 1, 3);
			LCD.drawString("ENDX:"+posXEnd, 1, 4);
			LCD.drawString("ENDY:"+posYEnd, 1, 5);
			
			Path path = pf.findRoute(new Pose(posXStart, posYStart, posAngle), new Waypoint(posXEnd, posYEnd));
			navigator.followPath(path);
		} catch (DestinationUnreachableException e) {
			LCD.clear();
			LCD.drawString("Cannot reach", 1, 1);
			LCD.drawString("destination", 1, 2);
		}
		
		navigator.waitForStop();

		LCD.clear();
		LCD.drawString("Path Finished!", 2, 2);
		
		Delay.msDelay(2000);
		
		motorL.close();
		motorR.close();
	}

}
