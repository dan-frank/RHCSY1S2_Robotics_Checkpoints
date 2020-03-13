package checkpoint_4;

import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.geometry.Line;
import lejos.robotics.geometry.Rectangle;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.mapping.LineMap;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Pose;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.pathfinding.Path;
import lejos.robotics.pathfinding.PathFinder;
import lejos.robotics.pathfinding.ShortestPathFinder;

public class PathFinding {
	final static float WHEELDIAMETER = 56; // The diameter (mm) of the wheels
	final static float WHEELTHICKNESS = 26; // The diameter (mm) of the wheels
	final static float AXLELENGTH = (147 - WHEELTHICKNESS) / 2; // The distance (mm) your two driven wheels
	final static float ANGULARSPEED = 100; // How fast around corners (degrees/sec)
	final static float LINEARSPEED = 150; // How fast in a straight line (mm/sec)

	public static void main(String[] args) throws Exception {
		RegulatedMotor left = new EV3LargeRegulatedMotor(MotorPort.A);
		RegulatedMotor right = new EV3LargeRegulatedMotor(MotorPort.B);
		Wheel wheelLeft = WheeledChassis.modelWheel(left, WHEELDIAMETER).offset(-AXLELENGTH);
		Wheel wheelRight = WheeledChassis.modelWheel(right, WHEELDIAMETER).offset(AXLELENGTH);
		Chassis chassis = new WheeledChassis(new Wheel[]{wheelRight, wheelLeft}, WheeledChassis.TYPE_DIFFERENTIAL); 
		
		MovePilot robot = new MovePilot(chassis);
		PoseProvider posep = new OdometryPoseProvider(robot);
		
		// Create a rudimentary map:
		Line [] lines = new Line[4];
		lines [0] = new Line(-200f, 200f, 1000f, 200f);
		lines [1] = new Line(-200f, 400f, 200f, 400f);
		lines [2] = new Line(-200f, 600f, 200f, 600f);
		lines [3] = new Line(-200f, 800f, 200f, 800f);
		Rectangle bounds = new Rectangle(-500, -500, 2500, 2500);
		LineMap myMap = new LineMap(lines, bounds);
		
		PathFinder pf = new ShortestPathFinder(myMap);
		
		Navigator nav = new Navigator(robot, posep) ;
		System.out.println("Planning path...");
		Path route = pf.findRoute(new Pose(0, 0, 0), new Waypoint(0, 1000));
		System.out.println("Planned path...");
		System.out.println(route.toString());
		Button.ENTER.waitForPressAndRelease();
		nav.followPath(route);
		nav.waitForStop();
	}		
}