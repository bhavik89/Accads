package codingInterview;

public class DivideAndConquer {
	
	
	static int searchZeros(int[] arr){
		 
		int firstZeroIndex =  binarySearch(arr, 0);
		
		return firstZeroIndex;
	}
	
	
	static int binarySearchFirstOccur(int[] arr, int target, int start, int end){
		int cent = end/2;
				
		while((start - end)> 0){			
			if (arr[end/2] == 0)
				binarySearchFirstOccur(arr, 0, 0, end);
			else
				binarySearchFirstOccur(arr, 0, end/2, end);
		}
		
		return cent;
			
	}
	
	
	static int binarySearch(int[] arr,int target){
		int first = 0; 
		int last = arr.length -1;
		
		while ((first + 1) < last){
			int test = (first + last)/2;
			
			if (arr[test] > target)
				last = test;
			else
				first = test;
		}
			if(arr[first] == target)
				return first;
			else
				return -1;
	
	}
}
