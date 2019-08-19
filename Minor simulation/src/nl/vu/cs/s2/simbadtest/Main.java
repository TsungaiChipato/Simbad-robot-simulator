package nl.vu.cs.s2.simbadtest;

import simbad.gui.*;

public class Main {
	public static void main(String[] args) {
		System.setProperty("j3d.implicitAntialiasing", "true");
	    Simbad frame = new Simbad(new MissionConfiguration().configure(), false);
	    frame.update(frame.getGraphics());
	    new MissionConfiguration().detectStopCommand();
	}
}
