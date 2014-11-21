package src.common;

import java.util.ArrayList;

/* Manages all the timestamp included inside the messages */
public class TimestampManager {
	private static final int TIMESTAMP_LIMIT = 60 * 1000;
	public static ArrayList<Long> timestamps = new ArrayList<Long>(); 
	
	public static long getTimestamp(){
		Timestamp timestamp = new Timestamp();
		long timestampValue = timestamp.getTimestamp();
		timestamps.add(timestampValue);
		return timestampValue;
	}
	
	public static boolean verifyTimestamp(String timestamp){
		for (Long timestampValue : timestamps) {
			if(Long.valueOf(timestamp).equals(timestampValue + 1)){
				timestamps.remove(timestampValue);
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean isExpired(long timestamp){
		final Long currentTime = System.currentTimeMillis();

		if (Math.abs(timestamp - currentTime) >= TIMESTAMP_LIMIT) {
			return true;
		} 
		
		return false;
	}
	
	public static class Timestamp{
		public long timestamp = 0;
		
		public Timestamp() {
			// TODO Auto-generated constructor stub
			timestamp = System.currentTimeMillis();
		}
		
		public long getTimestamp(){
			return this.timestamp;
		}
	}
	
}
