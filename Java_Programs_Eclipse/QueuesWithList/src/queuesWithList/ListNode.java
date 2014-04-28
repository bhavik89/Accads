package queuesWithList;

public class ListNode 
{


public Object element;
public ListNode next;


	public ListNode( Object theElement ) {
    this( theElement, null );
	}


	public ListNode( Object theElement, ListNode n ) {
    element = theElement;
    next    = n;
	}

}
