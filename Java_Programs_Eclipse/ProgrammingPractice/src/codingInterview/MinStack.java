package codingInterview;

import java.util.Stack;

public class MinStack {
	
	Stack<Integer> myStack;
	Stack<Integer> minStack;
	
	public MinStack(){
		myStack = new Stack<>();
		minStack = new Stack<>();
	}
	
    public void push(int x) {
        
        this.myStack.push(x);
        
        if(minStack.isEmpty() || x <= minStack.peek())
            minStack.push(x);
        
    }

    public void pop() {
        
        if(myStack.isEmpty())
            return;
            
        int x = myStack.pop();
        System.out.println(myStack.size());
        
        if(x == minStack.peek() && !minStack.isEmpty())
            minStack.pop();
        
    }

    public int top() {
        
        if(!myStack.isEmpty())
            return myStack.peek();
            
        else
            return 0;        
    }

    public int getMin() {
        
        if(!minStack.isEmpty())
            return minStack.peek();
        else
            return 0;
    }
    
    public static void main(String[] args) {
    	
    	MinStack ex = new MinStack();
    	
    	System.out.println("myStack: " + ex.myStack.toString());
    	System.out.println("myStack: " + ex.minStack.toString());
    	
    	ex.push(512);
    	System.out.println("myStack: " + ex.myStack.toString());
    	System.out.println("myStack: " + ex.minStack.toString());
    	ex.push(-1024);
    	System.out.println("myStack: " + ex.myStack.toString());
    	System.out.println("myStack: " + ex.minStack.toString());
    	ex.push(-1024);
    	System.out.println("myStack: " + ex.myStack.toString());
    	System.out.println("myStack: " + ex.minStack.toString());
    	ex.push(512);
    	System.out.println("myStack: " + ex.myStack.toString());
    	System.out.println("myStack: " + ex.minStack.toString());
    	
    	System.out.println();    	
    	ex.pop();
    	System.out.println(ex.getMin());
    	System.out.println("myStack: " + ex.myStack.toString());
    	System.out.println("myStack: " + ex.minStack.toString());
    	ex.pop();
    	System.out.println(ex.getMin());
    	System.out.println("myStack: " + ex.myStack.toString());
    	System.out.println("myStack: " + ex.minStack.toString());
    	ex.pop();
    	System.out.println(ex.getMin());
    	System.out.println("myStack: " + ex.myStack.toString());
    	System.out.println("myStack: " + ex.minStack.toString());
    	
    	
    	
	}
}