package heapsPackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Heap {
 
	private List<Integer> heap;
	public Heap() {		
		heap = new ArrayList<Integer>();
	}
	
	public static void main(String[] args) {
		
		Heap h = new Heap();
		
		for(int i = 0; i <=10;i++){
			int added = (int) (Math.random() * 100);
			h.heap.add(added);
			h.heap = siftUp(h.heap);
			System.out.println("Added:" + added);
			System.out.println(h.heap);
		}
		
//		h.heap.add(10);
//		h.heap = siftUp(h.heap);
//		h.heap.add(20);
//		h.heap = siftUp(h.heap);
//		h.heap.add(11);
//		h.heap = siftUp(h.heap);
//		
		
		System.out.println("Constructed Heap: " + h.heap);
		
		System.out.println("\nRemoved Element: " + h.heap.get(0));
		h.heap = siftDown(h.heap);
		System.out.println("\nNew heap: " + h.heap);
				
	}	

	private static List<Integer> siftUp(List<Integer> heap) {
		//System.out.println(heap);
		int last = heap.size() - 1;
		
		while(last > 0){
			int p = parentIndex(last);
			if(heap.get(p) < heap.get(last)){
				heap = swap(heap, p , last);
				last = p;
			}
			else break;
		}
		//System.out.println(heap);
		return heap;
		
	}
	
	private static List<Integer> siftDown(List<Integer> heap) {
		int start = 0;
		int end = heap.size() - 1;
		heap = swap(heap, start, end);
		heap.remove(end);
		end -= 1;
		while(start < end){
			int largerChild;
			try{
				largerChild = getLargerChildIndex(heap, start);
			}catch(Exception e){
				break;
			}
			if(heap.get(largerChild) > heap.get(start)){
				System.out.println("To Shif Down heap: " + heap );
				heap = swap(heap, start, largerChild);
				start = largerChild;
				System.out.println("Shifted Down heap: " + heap );
			}
			else 
				break;
		}
		
		return heap;
		
	}

	private static int getLargerChildIndex(List<Integer> heap, int start) {
		int left = ((2*start) +1);
		int right = ((2*start) +2);		
		
		
		int largeChild = heap.get(left) > heap.get(right) ? left : right;
		
		if (largeChild == heap.size())
			return largeChild - 1;
		else if (largeChild > heap.size())
			return heap.size() - 1;
		else
			return largeChild;
	}
		

	private static List<Integer> swap(List<Integer> heap, int p, int last) {
		int l = heap.get(last);
		heap.set(last, heap.get(p));
		heap.set(p, l);
		return heap;
	}

	private static int parentIndex(int index) {		
		return (int) Math.floor((index - 1)/2);
		
	}
	
	//A: (4, 2, 8, 5, 9, 1) V: 14

	//A: (5,3) V: 10

	//(5, 5) V:10


	boolean findWhetherTwoInArraySumToValue(ArrayList<Integer> array, int value){

	    if(array==null || array.size() == 0)
	        return false;
	    
	    HashMap<Integer, Integer> arrayMap = new HashMap<Integer, Integer>();
	    
	    //Build Hashmap form the given array, <arrayInt, IntCount>
	    //Complexity: O(n)
	    for(Integer i: array){
	        if(arrayMap.get(i) != null){
	            arrayMap.put(i, arrayMap.get(i)+1);        
	        }else{
	            arrayMap.put(i,1);
	        }
	    }
	    
	    //Iterate over the array to check if (value-arrayElement) exists in map
	    //Complexity O(n)
	    for(Integer i: array){
	        if(array.get(value-i) == 1)
	            return true;
	        
	        else if (array.get(value-i)>1){
	            int add = 0;
	            int count = array.get(value-i);
	            for(int j = 0; j<=count; j++){
	                add += j;
	                if(add == value)
	                    return true;
	            }
	            }            
	    }
	    return false;
	}

}