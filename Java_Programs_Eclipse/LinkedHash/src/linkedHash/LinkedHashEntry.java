package linkedHash;

public class LinkedHashEntry {

	private int key;
	private int value;
	private LinkedHashEntry next;
	
	LinkedHashEntry(int key, int value){
		this.key = key;
		this.value = value;
		this.next = null;
	}
	
	public int getValue(){
		return this.value;
	}
	
	public int getKey(){
		return this.key;
	}
	
	public void setValue(int value){
		this.value = value;
	}
	
		public LinkedHashEntry getNext(){
		return this.next;
	}

	public void setNext (LinkedHashEntry entry){
		this.next = entry;
	}
}
