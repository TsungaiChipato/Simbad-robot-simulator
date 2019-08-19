package nl.vu.cs.s2.simbadtest;

import javax.vecmath.Point3d;

public interface Observer {
	public abstract void update(Point3d position);
}
