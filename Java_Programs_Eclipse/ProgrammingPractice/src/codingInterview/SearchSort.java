package codingInterview;

import java.util.Arrays;

public class SearchSort {
	
	public static int searchString(String[] arr, String str, int first, int last){
			
		if(first > last)
			return -1;
		
		int mid = (last+first)/2;
		
		if(arr[mid].isEmpty()){
			int left = mid -1;
			int right = mid +1;
			
			while(true){
				
				if(left < first && right > last)
					return -1;
				else if(right <= last && !arr[right].isEmpty()){
					mid = right;
					break;
				}else if(left >= first && !arr[left].isEmpty()){
					mid = left;
					break;
				}
				
				right++;
				left++;
			}
		}
		
		if(str.equals(arr[mid]))
			return mid;
		else if (arr[mid].compareTo(str) < 0)
			return searchString(arr, str, mid+1, last);
		else
			return searchString(arr, str, first, mid-1);
		
	}
	
	public static int longestIncSubsequence(int[] arr){
		
		int[] lis = new int[arr.length];
		int i,j,max = 0;
		
		Arrays.fill(lis, 1);
		
		for(i = 1; i < arr.length ; i++){
			for(j = 0; j < i; j++)
				if ((arr[i] > arr[j]) && (lis[i] < lis[j] +1))
					lis[i] = lis[j] + 1;
		}
		
		System.out.println(Arrays.toString(lis));
		for(Integer x: lis)
			max = x > max ? x : max;
			
		return max;	
	}
	
	public static int searchIntInRotation(int[] arr, int left, int right, int n){
		
		int mid = (right+left)/2;
		
		if(right < left)
			return -1;		
		
		if(arr[mid] == n)
			return mid;

		if(arr[left] < arr[mid]){ // left is normal
			if(n >= arr[left] && n <= arr[mid])
				return searchIntInRotation(arr, left,mid -1, n); //search left
			else
				return searchIntInRotation(arr, mid+1 , right, n); //search right
			
		}else if (arr[mid] < arr[right]){ // right is normal
			
			if(n >= arr[mid] && n <= arr[right])
				return searchIntInRotation(arr, mid +1 , right, n); //search right
			else
				return searchIntInRotation(arr, left , mid-1, n); //search left
			
		}else if(arr[left] == arr[mid]){
			if(arr[mid] != arr[right])
				return searchIntInRotation(arr, mid +1 , right, n); //search right
			else{
				int res = searchIntInRotation(arr, left , mid-1, n); //search left
				
				if (res == -1)
					return searchIntInRotation(arr, mid +1 , right, n); //search right
				else
					return res;
			}
				
		}
		
		return -1;
			
		
	}

	
	public static void main(String[] args) {
		
		String[] strArr = {"", "bhavik", "example", "hello", "", "string", ""};
		
		int[] ints =  { 10, 22, 9, 33, 21, 50, 41, 60, 80 } ;
		int[] nums =  { 10 ,20 ,3,4,5,6,7,8,9,10};
		
		//System.out.println(longestIncSubsequence(ints));
		
		System.out.println(searchIntInRotation(nums, 0, nums.length-1, 20));
		
		//System.out.println(searchString(strArr, "hello", 0, strArr.length -1));
	}
}
