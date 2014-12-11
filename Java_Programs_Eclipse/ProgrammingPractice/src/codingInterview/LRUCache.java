package codingInterview;

import java.util.HashMap;

public class LRUCache {
	
	private HashMap<Integer, DoublyLinkedListNode> cacheMap 
					= new HashMap<>();
	private DoublyLinkedListNode head;
	private DoublyLinkedListNode end;
	private int capacity;
	private int len;
	
	public LRUCache(int capacity){
		this.capacity = capacity;
		len = 0;
	}
	
	public void set(int key, int value){
		if (cacheMap.containsKey(key)){
			DoublyLinkedListNode old = cacheMap.get(key);
			old.val = value;
			removeNode(old);
			setHead(old);			
		} else {
			DoublyLinkedListNode newNode = new DoublyLinkedListNode(key, value);
			
			if(len < capacity){
				setHead(newNode);
				cacheMap.put(key, newNode);
				len++;
			}else{
				cacheMap.remove(end.key);
				end = end.pre;
				if(end != null)
					end.next = null;
					
				setHead(newNode);
				cacheMap.put(key, newNode);
			}
			
		}
	}
	
	public int get(int key){
		if(cacheMap.containsKey(key)){
			DoublyLinkedListNode latest = cacheMap.get(key);
			removeNode(latest);
			setHead(latest);
			return latest.val;
		}else
			return -1;
	}
	
	
	
	
	
	private void setHead(DoublyLinkedListNode node) {
		
		node.next = head;
		node.pre = null;
		
		if(head != null)
			head.pre = node;
		
		head = node;
		
		if(end == null)
			end = node;
		
	}

	private void removeNode(DoublyLinkedListNode node) {
		
		DoublyLinkedListNode curr = node;
		DoublyLinkedListNode pre = curr.pre;
		DoublyLinkedListNode next = curr.next;
		
		if(pre != null)
			pre.next = next;
		else
			head = next;
		
		if(next != null)
			next.pre = pre;
		else
			end = pre;
		
	}





	public class DoublyLinkedListNode{
		public int key;
		public int val;
		public DoublyLinkedListNode pre;
		public DoublyLinkedListNode next;
		
		public DoublyLinkedListNode(int key, int value){
			val = value;
			this.key = key;
		}		
	}
	
	
	
	

	public static void main(String[] args) {
		LRUCache myCache = new LRUCache(5);
		myCache.set(1, 11);

	}

}
