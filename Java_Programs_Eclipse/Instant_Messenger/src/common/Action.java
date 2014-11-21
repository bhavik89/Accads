package src.common;

/* Abstract Action class denotes a action that must
 * be performed with actionInterval frequency.
 */
public abstract class Action implements Runnable {

	/* frequency at which action run in millisecond */
	protected final long actionInterval;

	public Action(long interval) {
		// TODO Auto-generated constructor stub
		this.actionInterval = interval;
	}

	public void run() {
		try {
			while (true) {
				Thread.sleep(actionInterval);
				performAction();
			}
		} catch (Exception e) {
		}
	}

	protected abstract void performAction();
}
