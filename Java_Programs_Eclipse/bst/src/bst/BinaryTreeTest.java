package bst;

import java.util.LinkedList;
import java.util.Queue;

public class BinaryTreeTest {
	 
	  public static void main(String[] args) {
	    new BinaryTreeTest().run();
	  }
	 
	  static class Node {
	    Node left;
	    Node right;
	 
	    int value;
	    boolean visited;
	    public Node(int value) {
	      this.value = value;
	      this.visited = false;
	    }
	  }
	 
	  public void run() {
	    // build the simple tree from chapter 11.
	    Node root = new Node(10);
	    System.out.println("Binary Tree Example");
	    System.out.println("Building tree with root value " + root.value);
	    insert(root, 5);
	    insert(root, 15);
	    insert(root, 3);
	    insert(root, 7);
	    insert(root, 13);
	    insert(root, 17);
	    System.out.println("Traversing tree in order");
	    printInOrder(root);
	    System.out.println("Traversing tree pre order");
	    //printFrontToBack(root, 7);
	    printPreOrder(root);
	    System.out.println("Traversing tree post order");
	    printPostOrder(root);
	    System.out.println("Traversing tree Level order");
	    printLevelOrder(root);
	    System.out.println("Traversing tree BFS");
	    printBreadthFirstSearch(root);
	    System.out.println(isBST(root));
	    
	  }
	 
	  public void insert(Node node, int value) {
	    
		  if (value < node.value) {
			  if (node.left != null) 
				  insert(node.left, value);
			  else {
				  System.out.println("  Inserted " + value + " to left of " + node.value);
				  node.left = new Node(value);
			  	}
		  } 
	    
	    else if (value > node.value) {
	      if (node.right != null) {
	        insert(node.right, value);
	      } else {
	        System.out.println("  Inserted " + value + " to right of " + node.value);
	        node.right = new Node(value);
	      }
	    }
	  }
	   public void printLevelOrder(Node node){
		   if(node != null)
			   System.out.println("  Traversed " + node.value);		   
			   printPostOrder(node.left);
			   printPostOrder(node.right);		   
	   }
	 
	   
	   	   
	  public void printPostOrder(Node node){
		  if(node != null){
			  printPostOrder(node.left);
			  printPostOrder(node.right);
			  System.out.println("  Traversed " + node.value);
		  }
	  }
	  
	  public void printPreOrder(Node node){
		  if(node != null){
			  System.out.println("  Traversed " + node.value);
			  printPreOrder(node.left);
			  printPreOrder(node.right);
		  }
	  }
	  
	  public void printInOrder(Node node) {
	    if (node != null) {
	      printInOrder(node.left);
	      System.out.println("  Traversed " + node.value);
	      printInOrder(node.right);
	    }
	  }
	  
	  public void printBreadthFirstSearch(Node node){
		  
		  Queue<Node> q = new LinkedList();
		  Node temp = node;
		  
		  while(temp != null){
			  System.out.println("  Traversed " + temp.value);
			  q.add(temp.left);
			  q.add(temp.right);
			  temp  = q.remove();			  
		  }
		  
	  }
	 
	  /**
	   * uses in-order traversal when the origin is less than the node's value
	   *
	   * uses reverse-order traversal when the origin is greater than the node's
	   * order
	   */
	  public void printFrontToBack(Node node, int camera) {
	    if (node == null)
	      return;
	    if (node.value > camera) {
	      // print in order
	      printFrontToBack(node.left, camera);
	      System.out.println("  Traversed " + node.value);
	      printFrontToBack(node.right, camera);
	    } else if (node.value < camera) {
	      // print reverse order
	      printFrontToBack(node.right, camera);
	      System.out.println("  Traversed " + node.value);
	      printFrontToBack(node.left, camera);
	    } else {
	      // order doesn't matter
	      printFrontToBack(node.left, camera);
	      printFrontToBack(node.right, camera);
	    }
	  }
	  
	  boolean isBST(Node root)
	  {
	      //static struct node *prev = NULL;
	      Node prev = null; 
		  
	      // traverse the tree in inorder fashion and keep track of prev node
	      if (root != null)
	      {
	          if (!isBST(root.left))
	            return false;
	   
	          // Allows only distinct valued nodes 
	          if (prev != null && root.value <= prev.value)
	            return false;
	   
	          prev = root;
	   
	          return isBST(root.right);
	      }
	   
	      return true;
	  }
	 
	}
