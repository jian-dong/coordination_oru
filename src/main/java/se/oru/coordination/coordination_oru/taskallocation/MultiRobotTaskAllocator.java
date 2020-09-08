package se.oru.coordination.coordination_oru.taskallocation;

import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import org.apache.commons.collections.comparators.ComparatorChain;

import se.oru.coordination.coordination_oru.SimpleNonCooperativeTask;
import se.oru.coordination.coordination_oru.util.StringUtils;

/**
 * This class provides task allocation for a fleet of robots. An instantiatable {@link MultiRobotTaskAllocator}
 * must provide a comparator for queuing tasks. Default ordering is EDF (Earliest Deadline First) when a deadline is provided, FIFO otherwise.
 * 
 * @author anmi, pf, fpa
 *
 */
public class MultiRobotTaskAllocator {
	
	public static String TITLE = "coordination_oru - Robot-agnostic online coordination for multiple robots";
	public static String COPYRIGHT = "Copyright \u00a9 2017-2020 Federico Pecora";

	//null -> public (GPL3) license
	public static String LICENSE = null;

	public static String PUBLIC_LICENSE = "This program comes with ABSOLUTELY NO WARRANTY. "
			+ "This program is free software: you can redistribute it and/or modify it under the "
			+ "terms of the GNU General Public License as published by the Free Software Foundation, "
			+ "either version 3 of the License, or (at your option) any later version. see LICENSE for details.";
	public static String PRIVATE_LICENSE = "This program comes with ABSOLUTELY NO WARRANTY. "
			+ "This program has been licensed to " + LICENSE + ". The licensee may "
			+ "redistribute it under certain conditions; see LICENSE for details.";

	//Force printing of (c) and license upon class loading
	static { printLicense(); }
	
	//A task queue (whenever a new task is posted, it is automatically added to the task queue).
	TreeSet<SimpleNonCooperativeTask> taskQueue = new TreeSet<SimpleNonCooperativeTask>();
	
	//Mission dispatcher for each robot (where to put the output of each instance).
	
	//Coordinator (to get informations about the current paths in execution and status of the robots).
	//what's the info required?
	//tec.getMotionPlanner(robotID) -> gestire conflitto sulla risorsa (synchronized? plan and doPlanning yes!).
	//tec.getRobotReport(robotID) -> getLastRobotReport
	//tec.getRobotType(robotID)
	//tec.getRobotFootprint(robotID)
	//tec.getFootprintPolygon(robotID)
	//tec.getDrivingEnvelope(robotID)
	//tec.getRobot
	//tec.getForwardModel(robotID)
	//tec.getIdleRobots(robotID)
	
	//Fleetmaster: use a local instance instead of the coordination one.
	
	//Visualization on Rviz? (Non completely useful).
	
	//Parameters: use_scenario, weights for the B function, alpha, period of the main loop
	
	//Start a periodic thread which checks for current posted goals and solves the MRTA problem at each instance.
	//flow:
	//
	
	protected ComparatorChain comparators = new ComparatorChain();
	/**
	 * Add a criterion for determining the order of robots through critical sections
	 * (comparator of {@link AbstractTrajectoryEnvelopeTracker}s). 
	 * Comparators are considered in the order in which they are added.
	 * @param c A new comparator for determining robot ordering through critical sections.
	 */
	public void addComparator(Comparator<SimpleNonCooperativeTask> c) {
		this.comparators.addComparator(c);
	}
	
	private static void printLicense() {
		System.out.println("\n"+MultiRobotTaskAllocator.TITLE);
		System.out.println(MultiRobotTaskAllocator.COPYRIGHT+"\n");
		if (MultiRobotTaskAllocator.LICENSE != null) {
			List<String> lic = StringUtils.fitWidth(MultiRobotTaskAllocator.PRIVATE_LICENSE, 72, 5);
			for (String st : lic) System.out.println(st);
		}
		else {
			List<String> lic = StringUtils.fitWidth(MultiRobotTaskAllocator.PUBLIC_LICENSE, 72, 5);
			for (String st : lic) System.out.println(st);
		}
		System.out.println();
	}
	
}
