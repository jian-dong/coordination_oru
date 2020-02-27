package se.oru.coordination.coordination_oru.fleetmasterinterface.tests;

import org.metacsp.multi.spatioTemporal.paths.Pose;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;

import se.oru.coordination.coordination_oru.fleetmasterinterface.FleetMasterInterface;
import se.oru.coordination.coordination_oru.motionplanning.ompl.ReedsSheppCarPlanner;

public class OneRobotEmptyMapWithoutCoordinator {
	
	public static void main(String[] args) throws InterruptedException {
		System.out.print("Test1: only FleetMasterInterference class");
		FleetMasterInterface flint = new FleetMasterInterface(0., 0., 0., 0.1, 500, 500, true);	
		
		Coordinate footprint1 = new Coordinate(-0.5,0.5);
		Coordinate footprint2 = new Coordinate(-0.5,-0.5);
		Coordinate footprint3 = new Coordinate(0.7,-0.5);
		Coordinate footprint4 = new Coordinate(0.7,0.5);
		flint.setDefaultFootprint(footprint1, footprint2, footprint3, footprint4);
		
		//Test 1: using default robot footprint	
		Pose startRobot1 = new Pose(45.0,5.0,0.0);
		Pose goalRobot11 = new Pose(40.0,7.0,0.0);
		Pose goalRobot12 = new Pose(10.0,7.0,0.0);
		Pose goalRobot13 = new Pose(5.0,5.0,0.0);

		//Set up path planner (using empty map)
		ReedsSheppCarPlanner rsp = new ReedsSheppCarPlanner();
		rsp.setRadius(0.2);
		rsp.setFootprint(flint.getDefaultFootprint());
		rsp.setTurningRadius(4.0);
		rsp.setDistanceBetweenPathPoints(0.1);
		rsp.setStart(startRobot1);
		rsp.setGoals(goalRobot11,goalRobot12,goalRobot13);
		rsp.plan();
		
		//Add the path to the fleetmaster
		flint.addPath(1, rsp.getPath().hashCode(), rsp.getPath(), null);
			
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Delete the path
		flint.clearPath(rsp.getPath().hashCode());		
	}
}

