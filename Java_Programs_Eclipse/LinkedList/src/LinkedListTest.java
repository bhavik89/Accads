
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
        
     	}

}
