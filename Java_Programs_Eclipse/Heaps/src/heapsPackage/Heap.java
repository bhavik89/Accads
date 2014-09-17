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
		
		for(int i = 0; i <=10 ;i++){
			int added = (int) (Math.random() * 100);
			h.heap.add(added);
			h.heap = siftUp(h.heap);
			//System.out.println("Added:" + added);
			//System.out.println(h.heap);
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
		
		heapSort(h.heap);
				
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
				//System.out.println("To Shif Down heap: " + heap );
				heap = swap(heap, start, largerChild);
				start = largerChild;
				//System.out.println("Shifted Down heap: " + heap );
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
	
	public static void heapSort(List<Integer> heap){
		
		int size = heap.size() -1;
		long startTime = System.currentTimeMillis();
		//System.out.println(startTime);
		while(size > 0){
			swap(heap, 0, size);
			heap = siftDownToGivenEnd(heap, size-1);	
			size -=1 ;
		}
		
		if(heap.get(0) > heap.get(1))
			swap(heap, 0, 1);
		
		long endTime = System.currentTimeMillis();
		//System.out.println(endTime);
		long timeTaken  = (endTime - startTime);
		
		System.out.println("Sorted Heap: " + heap + " in " + timeTaken + "ms");
		
	}
	
	private static List<Integer> siftDownToGivenEnd(List<Integer> heap, int end) {
		int start = 0;
		//int end = heap.size() - 1;
		//heap = swap(heap, start, end);
		//heap.remove(end);
		//end -= 1;
		while(start < end){
			int largerChild;
			try{
				largerChild = getLargerChildIndex(heap, start);
				if(largerChild > end)
					break;
			}catch(Exception e){
				
				break;
			}
			if(heap.get(largerChild) > heap.get(start)){
				//System.out.println("To Shif Down heap: " + heap );
				heap = swap(heap, start, largerChild);
				start = largerChild;
				//System.out.println("Shifted Down heap: " + heap );
			}
			else 
				break;
		}
		
		return heap;
		
	}
	

}