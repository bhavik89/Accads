package generic;

/* Abstract class to perform runs for given interval */
public abstract class RunActions implements Runnable {

	/* Time Interval to execute run */
	protected final long runTime;
	
	/*Constructor*/
	public RunActions(long interval) {		
		this.runTime = interval;
	}
	
	/* Run the thread every interval*/
	public void run() {		
			while (true) {
				try {
					Thread.sleep(runTime);
				} catch (Exception e) {
					//do nothing 
					//NOTE: If the thread is interrupted by Exception, it may lead to runtime error
				}
				doTask();
			}
		}
	protected abstract void doTask();
}
