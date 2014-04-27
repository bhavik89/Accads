package generic;

import java.util.*;

public class TimeStamps {
	
	    private static final int TIMESTAMP_EXPIRY = 60 * 1000; 		
		public static long timestamp;
		
		/*Get UTC TimeStamp*/
		public TimeStamps() {
			Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
			timestamp = calendar.getTimeInMillis();
		}
		
		public long getTimestamp(){			
			return timestamp;
		}
		
		public boolean verifyTimeStamps(long recTimestamp){
			return ((recTimestamp + TIMESTAMP_EXPIRY) >= timestamp);
		}
		
}
