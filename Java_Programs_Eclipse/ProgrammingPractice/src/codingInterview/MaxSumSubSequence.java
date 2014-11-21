package codingInterview;

import java.util.Arrays;
import java.util.Stack;

public class MaxSumSubSequence {
	
	public static void main(String[] args) {
		
		int[] arr = {-2,2,0,3,-5};
		
		System.out.println(Arrays.toString(maxSumSequence(arr)));
		System.out.println(maxProductSubSequence(arr));
}
	
	public static int[] maxSumSequence(int [] arr){
		
		int tempSum = 0;
		int maxSum = 0;
		
		int startIndex = 0;
		int maxSumStart = 0;
		int endIndex = 0;
		int[] result = new int[3];
		for(int i= 0; i< arr.length; i++){
			
			int nextSum = tempSum + arr[i];
			
			if(nextSum > tempSum){
				tempSum = nextSum;
				
				if(tempSum > maxSum){
					maxSum = tempSum;
					maxSumStart = startIndex;
					endIndex = i;					
				}
			}
			else{
				tempSum = 0;
				startIndex = i+1;
			}		
		}
		
		result[0] = maxSum;
		result[1] = maxSumStart;
		result[2] = endIndex;
		
		return result;
	}
	
	public static int maxProductSubSequence(int[] A){
		
		    int curr = A[0];
	        int currMaxPos = Math.max(0, A[0]);
	        int currMaxNeg = Math.min(0, A[0]);
	        
	        for (int i = 1; i < A.length; i++) {
	            
	        	if (A[i] == 0) {
	                currMaxPos = 0;
	                currMaxNeg = 0;
	            } else if (A[i] > 0) {
	                currMaxPos = Math.max(1, currMaxPos)*A[i];
	                currMaxNeg = currMaxNeg*A[i];
	            } else {
	                int tempPos = currMaxPos;
	                currMaxPos = currMaxNeg*A[i];
	                currMaxNeg = Math.min(A[i], A[i]*tempPos);
	            }
	            curr = Math.max(curr, currMaxPos);
	        }
	        return curr;
	    }
		
	
}
	


