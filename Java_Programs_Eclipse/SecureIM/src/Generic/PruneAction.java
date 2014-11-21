package Generic;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;



/* An abstact action that prunes a map 
 * K - key for map
 * V - value for map*/
public abstract class PruneAction<K,V> extends Action {
	private final ConcurrentHashMap<K, V> map;
	
	protected PruneAction(long actionInterval,ConcurrentHashMap<K, V> inputMap){
		super(actionInterval);
		this.map = inputMap;
	}
	
	/* Detemine if given object is prunable from map */
	protected abstract boolean isPrunable(V object, long pruneBefore);
	
	@Override
	protected void performAction() {
		final long pruneBefore = System.currentTimeMillis() - actionInterval;
		
		for (final Entry<K,V> entry: map.entrySet()){
			if(isPrunable(entry.getValue(), pruneBefore)){
				map.remove(entry.getKey());
			}
		}
	}
}
