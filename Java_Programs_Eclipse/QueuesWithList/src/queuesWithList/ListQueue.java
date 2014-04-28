package queuesWithList;



public class ListQueue {

	private ListNode front;
	private ListNode back;
	
	public ListQueue(){
		front =back = null;
	}

	public boolean isEmpty(){
		return front == null;
	}
	
	public void enqueue(Object x){
		if(isEmpty())
			back = front = new ListNode(x);
		else
			back=back.next = new ListNode(x);
	}
	
	public Object deQueue(){
		if(isEmpty())
			System.out.println("Queue Underflow");
		
		Object returnValue = front.element;
		front = front.next;
		return returnValue;
	}
	
	public Object getFront(){
		if(isEmpty())
			System.out.println("Empty Queue");
		
		return front.element;
	}
	
	
}
