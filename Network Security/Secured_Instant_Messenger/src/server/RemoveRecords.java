package server;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import generic.*;

/*
 * Abstract class to remove the records from HashMap
 * */
public abstract class RemoveRecords<K,V> extends RunActions {
	private final ConcurrentHashMap<K, V> records;	
	
	protected RemoveRecords(long removalInterval, ConcurrentHashMap<K, V> recordToRemove){
		super(removalInterval);
		this.records = recordToRemove;
	}
	
	//Thread to perform given task
	@Override
	protected void doTask() {
		final long removalTime = System.currentTimeMillis() - runTime;
		
		for (final Entry<K,V> entry: records.entrySet()){
			if(cookieExpired(entry.getValue(), removalTime)){
				records.remove(entry.getKey());
			}
		}
	}
	
	//Method to check if the cookie is expired and can be removed
	protected abstract boolean cookieExpired(V object, long pruneBefore);
}
