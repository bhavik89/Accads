package codingInterview;

public class TriNaryTree {
	
	/**
	 * Node Class, has three nodes within it
	 * left, right and middle.
	 * 
	 * Each node has a value field and 
	 * a boolean field, visited - used for tree traversal
	 * */
	static class Node {
	    Node left;
	    Node middle;
	    Node right;
	    
	    int value;
	    boolean visited;
	    
	    // Constructor for the Node class
	    public Node(int value) {
	      this.value = value;
	      this.visited = false;
	    }
	  }
	
	/** Method to insert a node for the given root.
	 *  
	 *  - Node is inserted as the left child, if the node value is less
	 *  than it's parent
	 *  
	 *  - Node is inserted as the right child, if the node value is greater
	 *  than it's parent
	 *  
	 *  - Node is inserted as the middle child, if the node value is equal
	 *  to it's parent
	 *  
	 *  Tress is traversed recursively, until a empty position for inserting node 
	 *  is encountered 
	 **/
	public static void insert(Node node, int value) {
	    
		  if (value < node.value) {
			  if (node.left != null) 
				  insert(node.left, value);
			  else {
				  System.out.println("Inserted " + value + " to left of " + node.value);
				  node.left = new Node(value);
			  	}
		  } 
	    
		  else if (value == node.value) {
		      if (node.middle != null) {
		        insert(node.middle, value);
		      } else {
		        System.out.println("Inserted " + value + " at center node of " + node.value);
		        node.middle = new Node(value);
		      }
		    }  
		  
		  
	     else if (value > node.value) {
	      if (node.right != null) {
	        insert(node.right, value);
	      } else {
	        System.out.println("Inserted " + value + " to right of " + node.value);
	        node.right = new Node(value);
	      }
	    }
	  }
	
	 /**
	  * Method to delete the node and return the new root node
	  * 
	  * Deletion is done recursively by searching the node for given value
	  * and deleting it's reference if it is the leaf node,
	  * 
	  * If the node has the subtree under it, replace it with the 
	  * minimum node in left subtree
	  * 
	  * Else, if the node has only right child or subtree, replace it with
	  * minimum value node in right subtree
	  *  
	  * */	
	 public static Node delete(Node node, int value)
	  {
	      if (node.value > value)	      
	          node.left = delete(node.left, value);
	      
	      else if(node.value < value)	      
	          node.right = delete(node.right, value);
	      
	      else
	      {
	          if (node.middle != null)	          
	              node.middle = delete(node.middle, value);
	          
	          else if(node.right != null)
	          {
	              int min = getMinNode(node.right).value;
	              node.value = min;
	              node.right = delete(node.right, min);
	          }
	          else
	             node = node.left;	          
	      }
	      return node;
	  }
	 
	 // Method to search for the minimum value node in the subtree of given node
	 static Node getMinNode(Node node)
	 {
	      if(node != null)
	      {
	          while (node.left != null)	          
	              return getMinNode(node.left);	          
	      }

	      return node;
	  }

	 //Print Inorder traversal of the tree with rootnode as given node
	 public static void printInOrder(Node node) {
		    if (node != null) {
		      printInOrder(node.left);
		      System.out.println("  Traversed " + node.value);
		      printInOrder(node.middle);
		      printInOrder(node.right);
		    }
		  }
		
	
	// Test Method
	static void test(int[] nodes) {
	    
	    Node root = new Node(nodes[0]);
	    System.out.println("Trinary Tree Example");
	    System.out.println("Building tree with root value " + root.value);
	    
	    for(int i = 1; i < nodes.length; i++)
	    	insert(root, nodes[i]);
	    
	    root = delete(root, 2);	    
	   
	    printInOrder(root);
	  }
	
	//Main Method to call test function
	public static void main(String[] args) {
		
		int nodes[] = {5,4,9,5,7,2,2,2};
		test(nodes);
	}

}


