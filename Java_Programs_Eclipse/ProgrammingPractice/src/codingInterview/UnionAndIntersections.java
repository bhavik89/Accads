package codingInterview;
import java.util.ArrayList;


public class UnionAndIntersections {
	
	static void printUnionAndIntersections(){
		int[] A = {1,4,6,7,9,10,11};
		int[] B = {1,2,3,4,5,6,7,8,9,10};
		int[] C = {1,3,4,6,8,9,11,12};
		
		FindUnionOfTwo(A, B);
		FindIntersectionOfTwo(A, B);
		FindCommonInThree(A, B, C);
	}
	

	static void FindUnionOfTwo(int[] A, int[] B){
		int i = 0;
		int j = 0;
		
		ArrayList<Integer> result = new ArrayList<>();
		
		while(i<(A.length) && j < (B.length )){
			if(A[i] < B[j]){
				result.add(A[i]);
				i++;
			}else if (A[i] > B[j]){
				result.add(B[j]);
				j++;
			}else{
				result.add(B[j]);
				i++; j++;
			}				
		}
		
		while(i<(A.length)){
			result.add(A[i++]);
		}
		while(j < (B.length )){
			result.add(B[j++]);
		}
		System.out.println("Union: " + result);		
	}
	
	static void FindIntersectionOfTwo(int[] A, int[] B){
		int i = 0;
		int j = 0;
		
		ArrayList<Integer> result = new ArrayList<>();
		
		while(i<(A.length) && j < (B.length )){
			if(A[i] < B[j]){
				i++;
			}else if (A[i] > B[j]){
				j++;
			}else{
				result.add(B[j]);
				i++; j++;
			}				
		}
		
		System.out.println("Intersection: " + result);		
	}
	
	static void FindCommonInThree(int[] A, int[] B, int[] C){
		int i = 0;
		int j = 0;
		int k = 0;
		
		ArrayList<Integer> result = new ArrayList<>();
		
		while(i<(A.length) && j < (B.length ) && k < (C.length)){
			
			if(A[i] == B[j] && B[j] == C[k]){
				result.add(A[i]);
				i++; j++; k++;
			}if(A[i] < B[j]){
				i++;
			}else if (B[j] < C[k]){
				j++;
			}else{
				k++;
			}				
		}
		
		System.out.println("Common in Three: " + result);		
	}
}
