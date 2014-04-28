
 // http://java.sun.com/j2se/1.5.0/docs/api/java/util/ArrayList.html

import java.util.*;

public class arrayList
{
  
  public static void main(String[] args)
  {
    
    System.out.println("Demo program for java.util.ArrayList");
    System.out.println();
    
    
    // arraylist is FIFO (First In First Out)
    // ArrayList myList = new ArrayList();
    // above code will still work in Java 1.5/1.6 but will
    // give warnings
    
    ArrayList<String> myList = new ArrayList<String>();
    
    // add initial items
    System.out.println("Adding items to the list");
        
    myList.add("item1");
    myList.add("item2");
    myList.add("item3");
    myList.add("item4");
    
    System.out.println();
    System.out.println("There are " + myList.size() + " items in the list."); 
    
    System.out.println();
    System.out.println("Here is the list, output method 1");
    
    // output list
    for (Iterator<String> iter = myList.iterator(); iter.hasNext();) 
    {
        System.out.println(iter.next());
    }
    
    
    System.out.println();
    System.out.println("Here is the list, output method 2");  
      
    for (int i = 0; i < myList.size(); i++) 
    {
        System.out.println(myList.get(i));
    }
    
    System.out.println();
    
    System.out.println();
    System.out.println("Here is the list, output method 3, using toString()");  
      
    // remember that you do not have to explicitly call the toString()
    // method since java is "smart" enough to know when to call it
    // automatically
      
    System.out.println(myList); // calls toString() of ArrayList
    System.out.println();
    
    System.out.println();
    System.out.println("Removing 2nd Item");

    
    // remove 2nd item
    // the remove method takes in a 0 based index
    int indexOfItemToRemove = 1;
    
    String removedItem = (String)myList.remove(indexOfItemToRemove);
    
    System.out.println();
    System.out.println("Removed : " + removedItem);
    
    System.out.println();
    System.out.println("Here is the list after removal of an item");
    
    for (Iterator<String> iter = myList.iterator(); iter.hasNext();) 
    {
        System.out.println(iter.next());
    }
  
    System.out.println();
    System.out.println("Adding (inserting) item back to it's position");
    myList.add(indexOfItemToRemove, removedItem);
    
    
    System.out.println();
    System.out.println("Here is the list after additon of item");
    
    for (Iterator<String> iter = myList.iterator(); iter.hasNext();) 
    {
        System.out.println(iter.next());
    }
    
    System.out.println();  
    System.out.println("Adding several more items");    
    
    // adding several more items in an unsorted manner
    
    myList.add("item9");
    myList.add("item7");
    myList.add("item8");
    myList.add("item6");
    
    System.out.println();
    System.out.println("Here is the list after additon of several items, unsorted");
    
    for (Iterator<String> iter = myList.iterator(); iter.hasNext();) 
    {
        System.out.println(iter.next());
    }
    
    System.out.println();
    System.out.println("Sorting items");
    
    Collections.sort(myList);
    
    System.out.println();
    System.out.println("Here is the list after sorting");
    
    for (Iterator<String> iter = myList.iterator(); iter.hasNext();) 
    {
        System.out.println(iter.next());
    }

    
    // searching on item that doesn't exist
    
    String searchItem = "Hello";
    
    System.out.println();
    System.out.println("Searching for " + searchItem);
  
    
    boolean searchResult = myList.contains(searchItem);
    
    if(searchResult == true)
      System.out.println("Item found at position " + myList.indexOf(searchItem));
    else
      System.out.println("Item NOT found");
      
    
    // searching on item that does exist
      
    searchItem = "item8";
      
    System.out.println();
    System.out.println("Searching for " + searchItem);
      
      
    searchResult = myList.contains(searchItem);
    
    if(searchResult == true)
      System.out.println("Item found at position " + myList.indexOf(searchItem));
    else
      System.out.println("Item NOT found");

    System.out.println();
  }  
}