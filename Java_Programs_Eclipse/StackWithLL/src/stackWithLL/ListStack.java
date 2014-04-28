package stackWithLL;



public class ListStack {

	ListNode top;
	
	public ListStack(){
		top = null;
	}
	
	public boolean isEmpty(){
		return top == null;
	}
	
	public void makeEmpty(){
		top = null;
	}
	
	public void push(ListNode n){
		if(isEmpty())
			top = new ListNode (n, top);
			
	}
	
	public void pop(){
		if(isEmpty())
			System.out.println("Stack is empty!");
		
		top = top.next;
		}
	
	public Object giveTop(){
		if(isEmpty())
			System.out.println("Stack is empty!");
		
		return top.element;
	}
}
