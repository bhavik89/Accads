package priorityQueues;

import java.util.ArrayList;
import java.util.List;


public class PriorityQueue {
	
	private ArrayList<Integer> PQ;
	public PriorityQueue() {		
		PQ = new ArrayList<Integer>();
	}
	
	//Method to construct Queue
		static ArrayList<Integer> constructPriorityQueue(ArrayList<Integer> rawPQ){
			
		ArrayList<Integer> constructedQueue = new ArrayList<Integer>();
		
		constructedQueue.add(0, null);
		
		for(Integer i: rawPQ){
			constructedQueue.add(i);
		}
		
		int i = constructedQueue.size() - 1;
		
		while(i > 0){
			int parent = getParent(i);
			if (parent == 0)
				break;
			
			int minChild = getMinChildIndex(constructedQueue, parent);
			
			if (constructedQueue.get(minChild) >= constructedQueue.get(parent)){
				i--;
				continue;				
			}else{
				int temp  = constructedQueue.get(minChild);
				constructedQueue.set(minChild, constructedQueue.get(parent));
				constructedQueue.set(parent, temp);
				i+=2;
			}
				
			i--;
		}
		
		
		
		
		return constructedQueue;
		
	}
	
	
	private static int getMinChildIndex(ArrayList<Integer> que, int parent) {
		
		int parentVal = que.get(parent);
		
		int child1 = (parent*2) < (que.size()-1) ? que.get(parent*2) : 0;
		int child2 = (parent*2+1) < (que.size()-1) ? que.get(parent*2+1) : 0;
		
		if(child1 != 0 && child2 != 0)
			return (child1<child2) ? (parent*2) : (parent*2+1);
		
		else if (child1 == 0 && child2 != 0)
			return (parent*2+1);
		
		else 
			return (parent*2);
		
	}


	private static int getParent(int i) {
		
		return (int) (Math.floor(i/2) > 0 ? Math.floor(i/2) : 1);
		
	}


	private boolean isPriQue(ArrayList<Integer> constructedQueue) {
		// TODO Auto-generated method stub
		return false;
	}


	public static void main(String[] args) {
		ArrayList<Integer> raw = new ArrayList<Integer>();
		
		raw.add(8);
		raw.add(7);
		raw.add(6);
		raw.add(5);
		raw.add(4);
		raw.add(3);
		raw.add(2);
		raw.add(1);
		
		System.out.println(constructPriorityQueue(raw));
		

	}

}
