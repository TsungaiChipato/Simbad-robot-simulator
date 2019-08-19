package nl.vu.cs.s2.simbadtest;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

// The Explorer robot searches for minerals (exploring) and it keeps track of the mission.
public class Explorer extends Robot implements Subject {
	private final static int MINERAL_DETECTION_RATE = 5;
	private final static int CHANGE_POSITION_MOVEMENTS = 10; // Needed to move away from the last found mineral position.
	private static Explorer exploring_robot = new Explorer(); // Attribute needed for the Singleton Design Pattern.
	private MissionConfiguration mission; // Instance to keep track of the mission.
	// These are the attributes that are needed for the Observer Design pattern:
	private List<Observer> observers;
	private int state;
	private TaskHandler chain; // Attribute needed for the Chain of Responsibility Design Pattern.
	
	private Explorer() {
		super(new Vector3d(0, 0, 0), "Explorer Robot");
		this.busy = true;
		this.setColor(new Color3f(1.0f, 0.0f, 0.0f));
		this.mission = new MissionConfiguration();
		this.observers = new ArrayList<Observer>();
	}
	
	public static Explorer getInstance() {
		return exploring_robot;
	}
	
	public void setChain(TaskHandler chain) {
		this.chain = chain;
	}
	
	// This function detects after a certain amount of time whether there is a mineral
	private void searchMinerals() {
		if (this.getCounter() % MINERAL_DETECTION_RATE == 0 && this.collisionDetected()) {
			System.out.println("Mineral found.");
			this.setState(0); // State 0 implies that a mineral has been found.
			this.chain.handleRequest();
			// Keep track of the mined minerals. (Assuming the Mining Robot has mined the mineral.):
			this.mission.collected_minerals += 1;
		}
	}
	
	// This function returns true if at least one Mining Robots is busy.
	private Boolean minersBusy() {
		for (Observer observer : observers) {
			Miner robot = (Miner)observer;
			if (robot.busy)
				return true;
		}
		return false;
	}
	
	@Override
	public void operate() {
		if (MissionConfiguration.stop_mission) {
			this.setState(1);
			this.busy = false;
			this.stop();
		}
		else {
			if (! (this.mission.collected_minerals >= this.mission.objective)) { // True if the mission has not been completed yet.
				this.move();
				this.searchMinerals();
			}
			else {
				for (int i = 0; i < CHANGE_POSITION_MOVEMENTS; i++) // Move away from the last mineral position.
					this.move();
				if (! this.minersBusy()) {
					this.busy = false;
					System.out.println("The mission has been completed.");
				}
			}
		}
    }
	
	// These are the functions and methods that are needed for the Observer Design Pattern:
	
	public void setState(int state) {
	   this.state = state;
	   notifyAllObservers();
	}
	
	public void attach(Observer observer) {
	   observers.add(observer);		
	}
	
	public int getState() {
		   return state;
	}
	
	public void notifyAllObservers() {
	   for (Observer observer : observers)
		   observer.update(this.getPosition());
	}
}
