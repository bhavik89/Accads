
package queueSimple;
import java.util.Scanner;

public class Qsimple {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
Scanner sc = new Scanner(System.in);
Queue q = new Queue();
int ch, n;

while(true)
{
	System.out.println("\n\tQUEUE");
	System.out.println("1.Insert");
	System.out.println("2.Remove");
	System.out.println("3.Exit");
	System.out.print("Enter your choice : ");
	ch=sc.nextInt();
	switch(ch)
	{
		case 1:System.out.print("Enter Number : ");
			   n=sc.nextInt();
			   q.insert(n);
			   q.display();
			   break;
		case 2:q.remove();
			   q.display();
			   break;
		case 3:return;
	}
}
		
	}

}
