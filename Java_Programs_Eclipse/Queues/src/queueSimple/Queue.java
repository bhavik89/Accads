package queueSimple;
import java.util.Scanner;

class Queue
{

	int items[] = new int[10];
	int front, rear;
	
	public Queue(){
		front = 0;
		rear = -1;
	}
	
	public void insert(int e)
	{
		if(rear==9)
			System.out.println("Queue Overflow");
		else
			items[++rear] = e;
	}
	
	void remove()
	{/*if queue is not empty remove one element from front */
		if(empty())
			System.out.println("Queue Underflow");
		else
			System.out.println("Removed element : "+items[front++]);
		}
	
	public boolean empty(){
		return(rear < front);
	}
	
	public void display(){
		if (!empty()){
			System.out.println("Queue: ");
			int t = front;
			
			while(t<=rear)
				System.out.println(" "+items[t++]);
			System.out.println();
			
		}
		
	}
		
}