package nl.vu.cs.s2.simbadtest;

import java.util.ArrayList;
import java.util.Scanner;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

enum MissionType {
	EXPLORING,
	COMMERCIAL;
}

public class MissionConfiguration {
	private final static int MIN_REQUEST_AMOUNT = 1;
	private final static int MAX_REQUEST_AMOUNT = 10;
	private final static int EXPLORING_OBJECTIVE = 5;
	private final static int NUMBER_OF_MINERS = 4;
	private ArrayList<Miner> miners;
	private MissionType type;
	public int objective;
	public int collected_minerals;
	public static Boolean stop_mission; // True when the operator stops the mission.
	
	public MissionConfiguration() {
		this.miners = new ArrayList<Miner>();
		this.type = getRandomMissionType();
		this.objective = setObjective();
		this.collected_minerals = 0;
		MissionConfiguration.stop_mission = false;
	}
	
	private MissionType getRandomMissionType() {
		MissionType[] values = MissionType.values();
		return values[(int) Math.random() * values.length];
	}
	
	private int setObjective() {
		if (this.type == MissionType.EXPLORING)
			return EXPLORING_OBJECTIVE;
		return new Utility().getRandomInt(MIN_REQUEST_AMOUNT, MAX_REQUEST_AMOUNT);
	}
	
	public void detectStopCommand() {
		while (! MissionConfiguration.stop_mission) {
			Scanner scanner = new Scanner(System.in);
			if (scanner.hasNext()) {
				String s = scanner.next();
				if (s.equals("Stop") || s.equals("stop"))
					MissionConfiguration.stop_mission = true;
			}
			scanner.close();
		}
	}
	
	public Environment configure() {
		Environment environment = new Environment();
		Explorer exploring_robot = Explorer.getInstance();
		environment.add(exploring_robot);
		for (int i = 1; i <= NUMBER_OF_MINERS; i++) {
			Miner mining_robot = new Miner(new Vector3d(i, 0, 0), "Mining Robot", (Subject)exploring_robot);
			mining_robot.setColor(new Color3f(i * 0.0625f, i * 0.125f, i * 0.25f));
			this.miners.add(mining_robot);
			environment.add(mining_robot);
		}
		exploring_robot.setChain(setUpChain());
        return environment;
	}
	
	private TaskHandler setUpChain() {
		for (int i = 0; i < NUMBER_OF_MINERS - 1; i++)
			((Miner)this.miners.get(i)).setSuccessor((Miner)this.miners.get(i + 1));
		return (TaskHandler)this.miners.get(0);
	}
}
