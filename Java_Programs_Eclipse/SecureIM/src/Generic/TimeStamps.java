package Generic;

import java.util.*;

public class TimeStamps {
	
	    private static final int TIMESTAMP_LIMIT = 60 * 1000;
 		
		public static long timestamp = 0;
		
		
		
		public TimeStamps() {
			Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
			timestamp = calendar.getTimeInMillis();
		}
		
		public static long getTimestamp(){
			return timestamp;
		}
		/*
		public static void main(String[] args) {
			
			TimeStamps time = new TimeStamps();
			System.out.println(time.getTimestamp());
			System.out.println(System.currentTimeMillis());
			
		}
		*/
	

}
