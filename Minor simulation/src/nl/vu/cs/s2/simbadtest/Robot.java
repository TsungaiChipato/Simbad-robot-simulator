package nl.vu.cs.s2.simbadtest;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import simbad.sim.*;
 
public class Robot extends Agent {
	private static final int MIN_COLLISION_DISTANCE = 1;
	private static final int IMMOVABLE_DETECTION_RATE = 400;
	public Boolean busy;
    private RangeSensorBelt sonar;

    public Robot(Vector3d position, String name) {
        super(position, name); // Call the super-constructor from the API.
        this.busy = false;
        this.sonar = RobotFactory.addSonarBeltSensor(this); // API function.
    }
    
    public Point3d getPosition() {
        Point3d position = new Point3d();
        this.getCoords(position);
        return new Utility().convertCoordinates(position);
    }
    
    public void preventImmovable(Point3d previous_position) {
    	if (this.getCounter() > 0 && this.getCounter() % IMMOVABLE_DETECTION_RATE == 0) {
	    	if (new Utility().equals(this.getPosition(), previous_position)) {
	        	this.setRotationalVelocity(Math.PI / 2);
	        	this.setTranslationalVelocity(0);
	        }
    	}
    }
    
    public void move() {
    	if (this.collisionDetected() || this.sonar.getMeasurement(0) <= MIN_COLLISION_DISTANCE) {
    			this.setRotationalVelocity(Math.PI / 4);
    			this.setTranslationalVelocity(0);
    	}
    	else {
    		this.setRotationalVelocity(0);
    		this.setTranslationalVelocity(1);
    	}
    }
    
    public void stop() {
    	this.setRotationalVelocity(0);
		this.setTranslationalVelocity(0);
    }
    
    public void operate() {} // The sub-classes will override this method.
    
    public void performBehavior() {
    	if (this.busy) {
    		this.operate();
    		this.preventImmovable(this.getPosition());
    	}
    }
}