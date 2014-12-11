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
	
	static void getLargestSum(int[] arr){
		
		int maxSum = Integer.MIN_VALUE; 
		int sum = 0;
		
		
		for (int i = 0; i < arr.length; i ++){
			
			sum += arr[i];
			
			if(maxSum < sum)
				maxSum = sum;
			
			if(sum < 0)
				sum  = 0;		
			
		}
		
		System.out.println("MaxSum: " + maxSum);
	}
	
	public static int longestPalindromeSubSeq(char[] arr, int start, int end){
		
		if(start == end)
			return 1;
		else if (end == (start+1) && arr[start] == arr[end])
			return 2;
		else if(end == (start+1) && arr[start] != arr[end])
			return 1;
		else if(arr[start] == arr[end])
			return longestPalindromeSubSeq(arr, start+1, end-1) + 2;
		else
			return Math.max(longestPalindromeSubSeq(arr, start, end-1), longestPalindromeSubSeq(arr, start+1, end));
		
	}
		


	
	public static void main(String[] args) {
		
		String[] strArr = {"", "bhavik", "example", "hello", "", "string", ""};
		
		int[] ints =  { 10, 22, 9, 33, 21} ;
		int[] nums =  { 10 ,20 ,3,4,5,6,7,8,9,10};
		
		//System.out.println(longestIncSubsequence(ints));
		
		//System.out.println(searchIntInRotation(nums, 0, nums.length-1, 20));
		
		//System.out.println(searchString(strArr, "hello", 0, strArr.length -1));
		int[] a = {-1, -2, -3, 4};
		//getLargestSum(a);
		
		char aa = 'A';
		
		char[] arr = {'a','a'};
		
		System.out.println(longestPalindromeSubSeq(arr, 0, arr.length-1));
		//System.out.println(Integer.toHexString(aa + 20));
		//decodeder("5d347e8987883486798a798687793479827b7d82797986797834887c7d87");
	}
	
	static void decodeder(String in){
		
		for(int i = 0; i< in.length(); i+=2){
			String temp = in.substring(i, i+2);
			
			int x = Integer.parseInt(temp, 16);
			x -= 20;
			System.out.print(Character.toChars(x));
		}
		
	}
}
