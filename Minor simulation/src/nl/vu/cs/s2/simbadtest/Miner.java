package nl.vu.cs.s2.simbadtest;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

// The Miner robot observes the Explorer robot, so that it can mine minerals.
public class Miner extends Robot implements Observer, TaskHandler {
	private Point3d mineral_location; // This attribute gets updated when the update() method is called.
	private Point3d goal_location; // This attribute gets updated and i used when a Miner accepts the task.
	private Subject subject; // Attribute needed for the Observer Design Pattern.
	private TaskHandler successor; // Attribute needed for the Chain of Responsibility Design Pattern.
		
	public Miner(Vector3d position, String name, Subject subject) {
		super(position, name);
		this.subject = subject;
		this.subject.attach(this);
	}
	    
	@Override
	public void operate() {
		Boolean mineral_found = new Utility().equals(this.getPosition(), goal_location);
		if (! mineral_found)
			this.move();
		else if (mineral_found) {
			System.out.println("The mineral has been mined.");
			this.busy = false;
		}
	}
		
	// This method is used for the Observer design pattern.
	public void update(Point3d position) {
		if (this.subject.getState() == 0) {
			System.out.println("The location has been received by the Miner robot.");
			this.mineral_location = position;
		}
		else if (this.subject.getState() == 1) {
			this.busy = false;
			this.stop();
		}
	}
		
	// These methods are used for the Chain of Responsibility Design Pattern:
		
	public void setSuccessor(TaskHandler successor) {
		this.successor = successor;
	}
		
	public void handleRequest() {
		if (! this.busy) {
			System.out.println("The mineral is going to be mined.");
			this.busy = true;
			this.goal_location = this.mineral_location;
		}
		else {
			if (this.successor != null)
				this.successor.handleRequest();
		}
	}
}