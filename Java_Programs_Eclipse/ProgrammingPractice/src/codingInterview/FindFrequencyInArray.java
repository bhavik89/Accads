package codingInterview;

public class FindFrequencyInArray {
	
	static int getFrequency(int[] A, int num){
		return (getLastIndexBinarySearch(A, num) - getFirstIndexBinarySearch(A, num));
	}

	
	@SuppressWarnings("null")
	static int getFirstIndexBinarySearch(int[] arr, int num){
		int low = 0, high = (arr.length -1);
		
		
		
		while(low <= high){
			int currPos = (high + low)/2;
			
			
			if (arr[currPos] < num)
				low = currPos + 1;
			else if(currPos > 0 && arr[currPos -1] >= num)
				high = currPos - 1;
			else if(arr[currPos] == num)
				return currPos;
			else	
				return ~currPos;
						
		}
		
		return ~(arr.length);
		
	}	
	
	static int getLastIndexBinarySearch(int[] arr, int num){
		int low = 0, high = (arr.length -1);	
		
		
		while(low <= high){
			int currPos = (high + low)/2;
			
			
			if (currPos < (arr.length -1) && arr[currPos + 1] <= num)
				low = currPos + 1;
			else if(arr[currPos] < num)
				high = currPos - 1;
			else if(arr[currPos] == num)
				return currPos;
			else	
				return ~currPos;
						
		}
		
		if(low <= (arr.length))
				return low;
		else
			 return ~arr.length;
		
	}	
		
}

