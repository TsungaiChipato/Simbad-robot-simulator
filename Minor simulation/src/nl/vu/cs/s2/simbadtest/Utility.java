package nl.vu.cs.s2.simbadtest;

import java.util.Random;
import java.util.Map;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

public class Utility {
	public int getRandomInt(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
	}
	
	public Boolean equals(Point3d c1, Point3d c2) {
		return c1.x == c2.x && c1.y == c2.y && c1.z == c2.z;
	}
	
	// This function is used to convert coordinates. For example, (1,1; 0,0; 2,2) will become (1,0; 0,0; 2,0).
    public Point3d convertCoordinates(Point3d coordinates) {
        return new Point3d((int)coordinates.x, (int)coordinates.y, (int)coordinates.z);
    }
    
	public Vector3d getRandomCoordinates(Map<Integer, Integer> existing_coordinates) {
		Vector3d coordinates;
		do
			coordinates = new Vector3d(this.getRandomInt(-10, 10), 0.0, this.getRandomInt(-10, 10));
		while ((coordinates.x >= -6.0 && coordinates.x <= 6.0 && coordinates.z >= -3.0 && coordinates.z <= 3.0) || 
				(existing_coordinates.containsKey(coordinates.x) && existing_coordinates.containsValue(coordinates.z)));
		return coordinates;
	}
}
