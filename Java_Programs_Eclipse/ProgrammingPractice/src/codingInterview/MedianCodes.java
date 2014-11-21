package codingInterview;

import java.util.Arrays;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

public class MedianCodes {
	
	static int medianOfTwoSorted(int arr1[], int arr2[], int left, int right, int n){
		
		return getMedian(arr1, arr2, left, right, n);
		
		
	}

	private static int getMedian(int[] arr1, int[] arr2, int left, int right, int n) {
		
		int i,j;
		
		if(left > right)
			return getMedian(arr2, arr1, 0, n-1, n);
		
		i = (left+right)/2;
		j = n-i-1;
		
		if(arr1[i] >= arr2[j] && (j == n-1 || arr1[i] <= arr2[j+1])){
			
			if(i==0 || arr2[j] > arr1[i-1])
				return (arr1[i] + arr2[j])/2;
			else
				return (arr1[i] + arr1[i-1])/2;		
			
		} else if(arr1[i] > arr2[j] && j != n-1 && arr1[i] > arr2[j+1])
			return getMedian(arr1, arr2, left, i-1, n);
		
		else
			return getMedian(arr1, arr2, i+1, right, n);	
		
	}
	
	
	static int partition1(int[] A, int m, int p){
		
		int v = A[m];
		int i = m;
		int j = p;
		
		do{
			do{i++;}while(A[i] <= v);
			do{j--;}while(A[j] >= v);
			
			if(i<j)
				A = swap(A, i, j);
			
		}while(i<=j);
		
		A[m] = A[j]; A[j] = v;
		
		System.out.println(Arrays.toString(A));
		
		return j;
		
	}
	
	
	
private static int[] swap(int[] a, int i, int j) {
		int temp  = a[i];
		a[i] = a[j];
		a[j] = a[i];
		return a;
	}

static int[] partition(int[] arr, int p){
	
		int[] arrtmp = {5, 19, 4, 2, 13};
		arr = arrtmp;
		
		p = 1;
		
		int right = arr.length -1;
		int left = 0;
		int temp = arr[left];
		
		while(left <= right){
			
//			while(arr[left] <= arr[p] && (left+1 <= right)){left++;}
//			while(arr[right] >= arr[p]) {right--;};
			if(arr[left] < arr[p]){ left++; continue;}
			if(arr[right] > arr[p]) {right--; continue;}
			
			
			if(left < right){
				arr[left] = arr[left] ^ arr[right];
				arr[right] = arr[left] ^ arr[right];
				arr[left] = arr[left]^arr[right];
			}
		
		}
		System.out.println(left);
		left--;
		if(arr[left] >= arr[p]){
		arr[left] = arr[left] ^ arr[p];
		arr[p] = arr[left] ^ arr[p];
		arr[left] = arr[left]^arr[p];
		}
		
		System.out.println(Arrays.toString(arr));
//		arr[left] = arr[right];
//		arr[right] = temp;
		
		return arr;
	}

	
static int doPartition(int[] arr, int p){
		
		int right = arr.length -1;
		int left = 0;
		int temp = arr[left];
		
		while(left <= right){
			
			do{left++;} while(arr[left] <= arr[p]);
			do{right--;} while (arr[right] >= arr[p]);
			
			if(left < right){
				arr[left] = arr[left] ^ arr[right];
				arr[right] = arr[left] ^ arr[right];
				arr[left] = arr[left]^arr[right];
			}
		
		}
		System.out.println(left);
		left--;
		if(arr[left] >= arr[p]){
		arr[left] = arr[left] ^ arr[p];
		arr[p] = arr[left] ^ arr[p];
		arr[left] = arr[left]^arr[p];
		}
//		arr[left] = arr[right];
//		arr[right] = temp;
		
		return left;
	}
	

}
