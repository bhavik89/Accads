public class List {

	private ListElement firstnode;
	private ListElement lastnode;
	private String name;
	
	public List(){
		this("List");
	}

	public List(String listname) {
		this.name  = listname;
		firstnode = lastnode = null;
	}
	
	public boolean isEmpty(){
		return firstnode == null;
	}
	
	public ListElement getHead(){
		return firstnode;
	}
	
	public void insertAtFront(Object item){
		if (isEmpty())
			firstnode = lastnode = new ListElement(item);
		else
			firstnode = new ListElement(item, firstnode);		
	}
	
	public void insertAtBack(Object item){
		if (isEmpty())
			firstnode = lastnode = new ListElement(item);
		else
			lastnode = lastnode.next = new ListElement(item);
	}
	
	public Object removeFromFront() throws EmptyListException{
		if(isEmpty())
			throw new EmptyListException(name);
		
		Object removed  = firstnode.node_data;
		if(firstnode == lastnode)
			firstnode = lastnode = null;
		else
			firstnode = firstnode.next;
		
		return removed;
	}
	
	public Object removeFromEnd() throws EmptyListException{
		if(isEmpty())
			throw new EmptyListException(name);
		
		Object removed = lastnode.node_data;
		if(firstnode == lastnode)
			firstnode = lastnode = null;
		else {
			ListElement current = firstnode;
			
			while(current.next != lastnode)
				current = current.next;
			
			lastnode = current;
			current.next = null;
			
		}
			return removed;		
	}
	
	public void print(){
		if (isEmpty())
			System.out.println("List is Empty");
		else
			{
			ListElement current = firstnode;
			
			while(current != null){				
				System.out.printf("%s---> ", current.node_data);
				current = current.next;
				}
			System.out.println();
			}
	}
	
	public int length(){
		int len = 0;
		if (isEmpty())
			return len;
		else
		{
			ListElement current = firstnode;
			while (current.next != null)
				len++;
		}		
		return len;
	}
	
	public int recursiveSize(){
        return recursiveLength(firstnode);
    }
     
    public int recursiveLength(ListElement current){
         
        if (isEmpty()){
            return 0;
        }
         
        if (current == null)
            return 0;
         
        return (1 + recursiveLength(current.next));
    }
    
    public void reverseList(ListElement curr) {
        if(isEmpty()) { return;}     //curr == null
        if(curr.next == null) {
            firstnode = curr;
            return;
        }
        reverseList(curr.next);
        curr.next.next = curr;
        curr.next = null;
    }
    
    public ListElement  FindnthToLast(ListElement head,  int  n)  {

    	if (head == null)
    	  return null;

    	ListElement pntr1 = head;
    	ListElement pntr2 = head;

    	//advance pntr2 by n-1 nodes;    
    	for  (int  i  =  0;  i  <  n ;  i++)  {  
    	
    	if  (pntr2  ==  null)  {
    	  /*this is an error condition to check to see if
    	     the linked list is less than n nodes long, in which
    	     case we just return null indicating an error
    	  */
    	  return null; 
    	  }

    	else //go to the next node
    	   pntr2 = pntr2.next;

    	}

    	/*Now, keep going until you hit a null node,
    	  and then you've reached the end, and
    	  pntr1 will be pointing to the nth from
    	 last node
    	*/

    	while(pntr2.next != null)
    	{
    	  pntr1 = pntr1.next;
    	  pntr2 = pntr2.next;
    	}

    	return pntr1;

    	}

	public void removeNode(ListElement node){
		if(isEmpty())
			{
			System.out.println("List is Empty!!");
			return;
			}
		else
		{
			ListElement curr = firstnode;
			
			while(curr.next.getObject() != node.getObject())
				curr = curr.next;
			
			curr.next = curr.next.next;
			
		}
		
	}
	
	public void isPalindromeLL(List ll){
		ListElement slowptr = ll.getHead();
		ListElement fastptr = ll.getHead();
		
		while (fastptr.next != null){
			
			if (fastptr.next.next != null){
				fastptr = fastptr.next.next;
				slowptr = slowptr.next;
			}else{
				fastptr = fastptr.next;
			}			
		}
		
		List tmpList = ll;
		ll.reverseList(slowptr.next);
		slowptr.next = ll.getHead();
		
	}
	
	 
    
}
