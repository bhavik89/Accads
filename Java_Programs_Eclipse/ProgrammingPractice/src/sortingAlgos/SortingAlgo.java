package sortingAlgos;

import java.util.ArrayList;

public class SortingAlgo {

	public static void main(String[] args) {
	
		ArrayList<Integer> inputArr = new ArrayList<Integer>();
		
		for(int i = 0; i <=3;i++){
			inputArr.add((int) (Math.random() * 100));
		}
		
		System.out.println("Input Array: " + inputArr);
		//bubbleSort(inputArr);
		//insertionSort(inputArr);
		
		int[] intArr = new int[10];
		for(int i = 0; i < 10;i++){
			intArr[i] = (int) (Math.random() * 100);
			//System.out.println(intArr[i]);
		}
		
		
		//quickSort(intArr, 0, intArr.length-1);
		//for(Integer i:intArr)
		//	System.out.println(i);
		//sortSortedArrays1();
		selectionSort();
}	
	//Bubble Sort
	private static void bubbleSort(ArrayList<Integer> inputArr) {
        int temp;
		for(int i= inputArr.size() - 1; i >= 1; i--){
			for (int j = 0; j <= i-1; j++ ){
				if(inputArr.get(j) > inputArr.get(j+1)){
					temp = inputArr.get(j);
					inputArr.set(j, inputArr.get(j+1));
					inputArr.set(j+1, temp);
				}
			}
		}		
		
		System.out.print("Bubble Sorted Array:");
		System.out.println(inputArr);
	}
	
	//Insertion Sort
	private static void insertionSort(ArrayList<Integer> inputArr) {

		int temp;
		for (int i=0;i<inputArr.size(); i++){
			int j = i;
			while(j>0 && (inputArr.get(j) < inputArr.get(j-1))){
				temp = inputArr.get(j);
				inputArr.set(j, inputArr.get(j-1));
				inputArr.set(j-1, temp);
				j -= 1;
			}
					
		}
		
		System.out.print("Insertion Sorted Array:");
		System.out.println(inputArr);
		
	}
	
	/* Merge sort algo
	 * */
	public static void mergeSorted(){
		int A[] = {-1,3,8,9};
		int B[] = {-62,-41,5,6,0,0,0,0}; // last four as place holders for the merge

		// start from the end of the larger array;
		int idx = 7;
		// we also need the indices of the largest elements in both arrays
		int idx_a = 3, idx_b = 3;

		while (idx_a >= 0) { // done when A has been traversed
		    if (idx_b < 0 || A[idx_a] > B[idx_b]) { // if elements of b are exhausted
		        B[idx] = A[idx_a];
		        idx_a--;
		    }
		    else {
		        B[idx] = B[idx_b];
		        idx_b--;
		    }
		    idx--;
		}
		
		System.out.println("Sorted Array:");
		for(Integer a: B)
			System.out.print(a+", ");
	}
	
	public static void quickSort(int[] arr, int low, int up){
		
		if(arr.length<2)
			return;
		
		int pivot = arr[(arr.length-1)/2];
		
		//int low = 0, up = len -1;
		
		while(low<up){
			while(arr[low] <= pivot)
				low++;
			
			while(arr[up] > pivot)
				up--;
			
			int temp = arr[low];
			arr[low] = arr[up];
			arr[up] = temp;			
		}
		
		quickSort(arr, low, pivot-1);
		quickSort(arr, pivot+1, up);
	}
	
	public static void selectionSort(){
		System.out.println("Slection sort: \n");
		int[] intArr = new int[10];
		for(int i = 0; i < 10;i++){
			intArr[i] = (int) (Math.random() * 100);
		}
		System.out.println("Unsorted Array: ");
		
		for(Integer i: intArr)
			System.out.print(i+", ");
		
		for (int i = 0; i < intArr.length ; i++){
			int smallestIndex = i;
			
			for(int j = i+1; j < intArr.length; j++){
				if(intArr[smallestIndex] > intArr[j])
					smallestIndex = j;
			}
			
			int swap = intArr[smallestIndex];
			intArr[smallestIndex] = intArr[i];
			intArr[i] = swap;
			
		}
		
		System.out.println("\nSorted Array: ");
		
		for(Integer i: intArr)
			System.out.print(i+", ");
		
	}
	
	
	/* Sorts the already sorted 2 arrays and merges into one
	 * NOT WORKING CORRECTLY -- NEED TO HAVE A LOOK
	 * */
	public static void sortSortedArrays1(){
		
		int B[] = {-62,-41,5,6,1,2,3,4};
		
		int last = B.length-1;
		int mid1 = (B.length-1)/2;
		int array2Last = (B.length-1);
		System.out.println(last + ":" +mid1 +":" +array2Last);
		while(mid1 >= 0 && last>=0){
			if(array2Last < (B.length-1)/2 || B[mid1] > B[array2Last]){
				int temp  = B[last];
				B[last] = B[mid1];
				B[mid1] = temp;
				mid1--;
			}
			else{
				int temp = B[last];
				B[last] = B[array2Last];
				B[array2Last] = temp;
				array2Last--;
			}
				last--;
		}
		
		System.out.println("Sorted Array:");
		for(Integer a: B)
			System.out.print(a+", ");
		
	}
	
}
