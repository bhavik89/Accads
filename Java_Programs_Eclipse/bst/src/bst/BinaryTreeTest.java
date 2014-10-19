package bst;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

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
	    
	    System.out.println(maxHeight(root));
	    
	    int[] arr  = {0,1,2,3,4,5,6,7,8,9,10};
	    Node n = createMinimalBST(arr, 0, arr.length-1);
	    System.out.println("Traversing new tree in order");
	    printInOrder(n);
	    
	    System.out.println("Max = " + findMax(root));
	    System.out.println("Traversing Inorder iteratively: ");
	    inOrderIterratively(root);
	    
	    Node root1 = new Node(10);
	    insert(root1, 5);
	    insert(root1, 6);
	    insert(root1, 7);
	    insert(root1, 8);
	    insert(root1, 9);
	    insert(root1, 4);
//	    insert(root1, 4);
//	    insert(root1, 1);
//	    insert(root1, 1);
//	    insert(root1, 3);
//	    insert(root1, 35);	   
//	    insert(root1, 34);
//	    insert(root1, 36);
//	    insert(root1, 1);
	    
	    
	    
//	    System.out.println(getMaxSumOfPaths(root1));
//	    getNumOfLeaves(root1);
//	    System.out.println(isBST(root1));
//	    getFullNodes(root1);
//	    System.out.println(getFullNodesRecursive(root1, 0));
	    
	    System.out.println("MaxLeftRight: "  + getMaxLeftRight(root1));
	    
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
	  
	  int maxHeight(Node root){
		  
		  if(root == null) 
			  return 0;
		  
		  int left_height = maxHeight(root.left);
		  int right_height = maxHeight(root.right);
		  
		  return (left_height > right_height) ? left_height +1 : right_height +1;
		  
	  }
	  
	  Node createMinimalBST(int arr[], int start, int end){
		  if(end < start)
			  return null;
		  
		  int mid = (start + end)/2;
		  
		  Node n = new Node(arr[mid]);
		  n.left = createMinimalBST(arr, start, mid-1);
		  n.right = createMinimalBST(arr, mid+1, end);
		  
		  return n;
	  }
	  
	  int findMax(Node node){
		  int max = Integer.MIN_VALUE;
		  int node_val, left, right;
		  if(node != null){
			  node_val = node.value;
			  left = findMax(node.left);
			  right = findMax(node.right);			  
			  max = (left >= right) ? left : right;			  
			  max = (max >= node_val)? max : node_val;
			  
		  }
		  
		  return max;
			  
		  
	  }
	  
	  int findMaxIteratively(Node node){
		  int max = Integer.MIN_VALUE;
		  int node_val, left = Integer.MIN_VALUE, right = Integer.MIN_VALUE;
		  Node root = node;
		  
		  while(root != null){
			  node_val = root.value;
			  root = root.left;				   
			  left = (left >= node_val)? left : node_val;			  
		  }
		  root = node;
		  while(root != null){
			  node_val = root.value;
			  root = root.right;				   
			  max = (max >= node_val)? max : node_val;			  
		  }		  
		  return max;		  
		  
	  }
	  
	  void inOrderIterratively(Node root){
		  if(root == null)
			  return;
		  
		  Stack<Node> nodeStack = new Stack<Node>();
		  while(true){
			  while(root != null){
				  nodeStack.push(root);
				  root = root.left;
			  }
			  if(nodeStack.empty())
				  break;
			  
			  root = (Node) nodeStack.pop();
			  System.out.println("Traversed: " + root.value);
			  root = root.right;
		  }
		  return;		  
	  }
	  
	  int getSumOfNodes(Node root){
		  
		  if(root == null)
			  return 0;
		  if(root.left == null && root.right == null)
			  return root.value;
		  
		  return root.value + getSumOfNodes(root.left) + getSumOfNodes(root.right);		  
		  
	  }
	  
	  void getSumOfNodesIteratively(Node root){
		  
		  long sum = 0;
		  if(root ==null){
			  System.out.println("Sum: " + sum);
			  return;
		  }
		  
		  Queue<Node> nodes = new LinkedList<>();
		  nodes.add(root);
		  
		  while(!nodes.isEmpty()){
			  Node curr = nodes.poll();
			  sum += curr.value;
			  if(curr.left != null)
				  nodes.add(curr.left);
			  
			  if(curr.right != null)
				  nodes.add(curr.right);
		  }
		  
		  System.out.println("Sum:  "+ sum);
		  
		  
	  }
	  
	  int getMaxSumOfPathsFromRoot(Node root){
		  
		  if(root == null)
			  return 0;
		  if(root.left == null && root.right == null)
			  return root.value;
		  
		  return Math.max((root.value + getMaxSumOfPathsFromRoot(root.left)),(root.value + getMaxSumOfPathsFromRoot(root.right)));		  
		  
	  }
	  
	  int getMaxSumOfPaths(Node root){
		  
		  //if(root == null)
		  //	  return 0;
		  if(root.left == null && root.right == null)
			  return root.value;
		  if(root.left == null || root.right == null)
			  return root.value;
		  
		  int left =  getMaxSumOfPaths(root.left);
		  int right  = getMaxSumOfPaths(root.right);
		  int all = root.value + getMaxSumOfPaths(root.left) + getMaxSumOfPaths(root.right);
		  
		  int max = Math.max(left, right);
		  return Math.max(max, all);  
		  		  
		  
	  }
	  
	  void getNumOfLeaves(Node root){
		  
		  int leaves = 0;
		  if(root.left ==null && root.right == null){
			  System.out.println("Leaves: "+leaves);
			  return;
		  }
		  
		  ArrayList<Node> nodeQue = new ArrayList<>();
		  
		  nodeQue.add(root);
		  while(!nodeQue.isEmpty()){
			  Node r = nodeQue.get(0);
			  nodeQue.remove(0);
			  
			  if(r.left == null && r.right == null){
				  leaves++;
			  }
			  if(r.left != null)
				  nodeQue.add(r.left);
			  
			  if(r.right != null)
				  nodeQue.add(r.right);
			  
		  }
		  System.out.println("Leaves: "+leaves);		  
	  }
	  
	  void getFullNodes(Node root){
		  int fullNodes = 0;
		  if(root.left == null && root.right == null){
			  System.out.println("Full Nodes: "+fullNodes);
			  return;
		  }
		  ArrayList<Node> nodes = new ArrayList<>();
		  nodes.add(root);
		  
		  while(!nodes.isEmpty()){
			  Node curr = nodes.get(0);
			  nodes.remove(0);
			  
			  if(curr.left != null && curr.right != null)
				  fullNodes++;
			  
			  if(curr.left != null)
				  nodes.add(curr.left);
			  
			  if(curr.right != null)
				  nodes.add(curr.right);
			  
		  }
		  
		  System.out.println("Full Nodes: "+fullNodes);
			  
	  }
	  
	  int getFullNodesRecursive(Node root, int fullNodes){
		  
		 		  
		  if(root.left != null && root.right != null)
			  fullNodes++;
		  
		  if(root.left != null)
			  fullNodes = getFullNodesRecursive(root.left, fullNodes);
		  
		  if(root.right != null)
			  fullNodes = getFullNodesRecursive(root.right, fullNodes);
		  
		  return fullNodes; 
		 
	  }
	  
	  int getMaxLeftRight(Node root){
		  int max = 0;
		  
		  if(root == null)
			  return max-1;
		  else if(root.left == null && root.right==null)
			  return 0;
		  
		  Queue<Node> nodes = new LinkedList<>();
		  nodes.add(root);
		  
		  while(!nodes.isEmpty()){
			  Node curr = nodes.poll();
			  int leftHeight = getLheight(curr);
			  int rightHeight = getRheight(curr);
			  
			  int tempMax = Math.max(leftHeight, rightHeight);
			  
			  max = (tempMax > max) ?  tempMax : max;
			  
			 if(curr.right != null)
				 nodes.add(curr.right);
			 
			 if(curr.left != null)
				 nodes.add(curr.left);		 
			  
		  }
		  
		  return max -1;
		  
	  }

	private int getRheight(Node curr) {
		
		if(curr == null)
			return 0;
		
		int rHeight = getRheight(curr.right);
		
		
		return rHeight+1;
	}

	private int getLheight(Node curr) {
		if(curr == null)
			return 0;
		
		int lHeight = getLheight(curr.left);
		
		
		return lHeight+1;
	}
	 
	}
