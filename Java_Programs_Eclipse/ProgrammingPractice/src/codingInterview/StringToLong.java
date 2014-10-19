package codingInterview;


public class StringToLong {
        
	    /**
	     * Conversion function to calculate the value from given string
	     * 
	     * Limitations of Code:
	     * 	- If any non-integer values is present in the string it will return 0
	     * 		and not the rest of resulted long number
	     *  
	     *  - If the result value is larger than Max value of Long, but has same number
	     *   	of digits, it will return the Max value of Long
	     **/
		static long stringToLong(String s){
			
			/** 
			 *  Calculate the length of the input string and 
			 * 	perform calculations based upon the length
			 **/
			int len = s.length();
			
			// If length is 0 i.e. null string, return 0
			if(len == 0)
				return 0;
			
			/** 
			 *  If length of input string is 1, convert the string 
			 *  to equivalent integer by subtracting 48 from its ASCII 
			 *  character value
			 **/
			else if(len == 1)				
				return s.charAt(0) - 48;
			
			// Initialize variable for final result 
			long result = 0;			
			
			/**
			 * Convert given string to character array,
			 * helpful for traversal 
			**/
			char[] chrArr = s.toCharArray();
			
			/** 
			 * Get the max value of string to compare, in case the converted 
			 * value goes out of bounds
			 **/ 
			long max = Long.MAX_VALUE;
			
			// Iterate on each character of string and store converted value in 'result'
			for(Character c: chrArr){
				int num = c - 48;
				
				//If string encounters non-number, returns 0
				if(num > 9)
					return 0;
				
				/**
				 *  If the resulting number is within bounds,
				 *  construct result by multiplying the number to 
				 *  equivalent power of 10 based upon it's position in string
				 *  and adding it to stored result
				 **/				
				if((double)(result + (Math.pow(10, len-1) * num)) <= (double)max)
					result += (Math.pow(10, --len) * num);
				
				// If result goes out of bounds, return 0
				else					
					return 0;
			}		
			
			//return final result, if conversion is successful
			return result;
		}
		
		//Test Function
		static void test() { 

		 long i = stringToLong("123");
		 if (i == 123)
			 System.out.println("Successfully converted given string to Long: " + i);
		 else
			 System.out.println("Failed to conver given string to Long");		 

		}
		
		//Main function to call the test function
		public static void main(String[] args) {
			test();
		}
}
