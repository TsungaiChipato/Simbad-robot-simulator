package nl.vu.cs.s2.simbadtest;

public abstract interface TaskHandler {
	public void setSuccessor(TaskHandler successor);
	
	public abstract void handleRequest();
}