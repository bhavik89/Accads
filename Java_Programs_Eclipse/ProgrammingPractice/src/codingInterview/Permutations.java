package codingInterview;

import java.util.Arrays;

public class Permutations {
	
	public static void main(String[] args) {
		Object[] arr = {'a','b','c'};
		getPermutation(arr, 0, arr.length-1);
	}
	
	static void getPermutation(Object[] arr, int current, int len){
		
		if(current== len){
			System.out.println(Arrays.toString(arr));
			return;
		}
		
		for(int j = current; j<= len; j++){
			
			if(j != current)
				swap(arr, j, current);
			
			getPermutation(arr, current+1, len);
			
			if(j != current)
				swap(arr, j, current);
		}
	}

	private static void swap(Object[] arr, int j, int current) {
		
		Object temp = arr[j];
		arr[j] = arr[current];
		arr[current] = temp;
		
		//return arr;
	}

}
