
public class LinkedListTest {

	public static void main(String[] args) throws EmptyListException {
		List list = new List();
        
        list.insertAtFront( "four" );
        list.print();            
        list.insertAtFront( "two" );
        list.print();         
        
        list.insertAtBack( "six" ); 
        list.print();         
        list.insertAtBack( "nine" ); 
        list.print();  
        
        ListElement nthToLast = list.FindnthToLast(list.getHead(), 2);
        if(nthToLast == null)
        	System.out.println("NULL");
        else
        	System.out.printf("Nth to Last = %s", nthToLast.node_data);	
        System.out.println();
        System.out.println("Reverse...");
        list.reverseList(list.getHead());
        list.print(); 
//        System.out.printf("Length: %d", list.recursiveSize());
//        System.out.printf("Length: %d", list.length());
         
//        try{
//            System.out.printf( "%s removed\n", list.removeFromFront());
//            list.print();
//            System.out.printf( "%s removed\n", list.removeFromFront());
//            list.print();
//            System.out.printf( "%s removed\n", list.removeFromEnd());
//            list.print();
//            System.out.printf( "%s removed\n", list.removeFromEnd());
//            list.print();
//        }catch ( EmptyListException e ){
//            e.printStackTrace();
//        }
        
        System.out.println();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>");
        list.removeNode(list.getHead().next.next);
        list.print();
        
        List ll1 = new List();
        List ll2 = new List();
        
        ll1.insertAtBack(1);
        ll1.insertAtBack(2);
        ll1.insertAtBack(3);
        ll1.insertAtBack(4);
        
        ll2.insertAtBack(5);
        ll2.insertAtBack(6);
        ll2.insertAtBack(7);
        
        swapLL(ll1.getHead(), ll2.getHead());
        
        System.out.println("Printing swapped...");
        ll1.print();
        ll2.print();
        
        //ll2.reverseList(ll2.getHead());
        //ll2.print();
     
	}
	
	static void swapLL(ListElement pt1, ListElement pt2){
		
		while(pt1.next != null){
			ListElement tmp1 = pt1.next, tmp2 = pt2.next;
			pt1.next = pt2;
			pt2.next = tmp1;
			pt1 = tmp1;
			pt2 = tmp2;
		}
		
		
	}

}
