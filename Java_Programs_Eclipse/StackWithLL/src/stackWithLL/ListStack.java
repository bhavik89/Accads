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
		else{
			//ListNode temp = top;
			ListNode newNode = new ListNode (n, top);
			top = newNode;
			//top.next = temp;
		}
	}
	
	public Object pop(){
		if(isEmpty()){
			System.out.println("Stack is empty!");
			return null;
		}
		
		ListNode temp = top;
		top = top.next;
		return temp.element;
	}
	
	public Object giveTop(){
		if(isEmpty()){
			System.out.println("Stack is empty!");
			return null;
		}
		return top.element;
	}
}
