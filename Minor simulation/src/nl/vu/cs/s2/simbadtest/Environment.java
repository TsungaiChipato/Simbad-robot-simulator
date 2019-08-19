package nl.vu.cs.s2.simbadtest;

import java.util.Map;
import java.util.HashMap;

import java.awt.Color;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import simbad.sim.Box;
import simbad.sim.EnvironmentDescription;
import simbad.sim.Wall;

public class Environment extends EnvironmentDescription
{
	public Map<Integer, Integer> object_positions;
    
    public Environment() {
    	this.object_positions = new HashMap<Integer, Integer>();
        this.setWorldSize(25.0f);
        this.buildScene();
        this.buildBorderWalls();
        this.addMinerals(new Utility().getRandomInt(10, 15));
        this.addMountains(new Utility().getRandomInt(15, 30));
    }
    
    private void buildScene() {
        this.light1IsOn = true;
        this.light2IsOn = true;
        this.ambientLightColor = new Color3f(Color.getHSBColor(0.0f, 200.0f, 100.0f));
        this.floorColor = new Color3f(Color.getHSBColor(0.0f, 360.0f, 100.0f));
        this.setUsePhysics(true);
        this.showAxis(false);
    }
    
    private void buildBorderWalls() {
        final Wall w1 = new Wall(new Vector3d(12.0, 0.0, 0.0), 25.0f, 2.0f, (EnvironmentDescription)this);
        w1.setColor(new Color3f(Color.DARK_GRAY));
        w1.rotate90(1);
        this.add((Object)w1);
        final Wall w2 = new Wall(new Vector3d(-12.0, 0.0, 0.0), 25.0f, 2.0f, (EnvironmentDescription)this);
        w2.setColor(new Color3f(Color.DARK_GRAY));
        w2.rotate90(1);
        this.add((Object)w2);
        final Wall w3 = new Wall(new Vector3d(0.0, 0.0, 12.0), 25.0f, 2.0f, (EnvironmentDescription)this);
        w3.setColor(new Color3f(Color.DARK_GRAY));
        this.add((Object)w3);
        final Wall w4 = new Wall(new Vector3d(0.0, 0.0, -12.0), 25.0f, 2.0f, (EnvironmentDescription)this);
        w4.setColor(new Color3f(Color.DARK_GRAY));
        this.add((Object)w4);
    }
    
    private void addMinerals(final int number_of_minerals) {
        for (int i = 0; i < number_of_minerals; ++i) {
            final Vector3d coordinates = new Utility().getRandomCoordinates(object_positions);
            final Box minerals = new Box(coordinates, new Vector3f(1.0f, 1.0f, 1.0f), (EnvironmentDescription)this);
            minerals.setColor(new Color3f(Color.getHSBColor(0.55f, 1.0f, 0.4f)));
            this.add((Object)minerals);
            this.object_positions.put((int)coordinates.x, (int)coordinates.z);
        }
    }
    
    private void addMountains(final int number_of_mountains) {
        for (int i = 0; i < number_of_mountains; ++i) {
            final Vector3d coordinates = new Utility().getRandomCoordinates(object_positions);
            final Box mountains = new Box(coordinates, new Vector3f((float)new Utility().getRandomInt(1, 3), 1.0f, (float)new Utility().getRandomInt(2, 4)), (EnvironmentDescription)this);
            mountains.setColor(new Color3f(Color.getHSBColor(0.0f, 0.5f, 0.4f)));
            this.add((Object)mountains);
            this.object_positions.put((int)coordinates.x, (int)coordinates.z);
        }
    }
}
