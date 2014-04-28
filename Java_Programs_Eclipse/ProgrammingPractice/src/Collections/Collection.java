package Collections;

import java.util.*;

import java.util.concurrent.ArrayBlockingQueue;

import com.sun.org.apache.bcel.internal.generic.IINC;

public class Collection {


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		arrayList();
		linkedList();
		hashMap();
		linkedHashMap();
		treeMap();
		sets();
		sortLists();
	}
	
	 public static void arrayList(){
		 ArrayList<Integer> intArrList = new ArrayList<Integer>();
			
			intArrList.add(10);
			intArrList.add(12);
			intArrList.add(100);
			
			System.out.println("\nArrayList Iter: ");
			
			//Iteration
			for(Integer val: intArrList){
				System.out.println(val);
			} 
	 }
	
	public static void linkedList(){
		
		LinkedList<Integer> ll = new LinkedList<Integer>();
		
		ll.add(100);
		ll.add(90);
		ll.add(2103);
		
		System.out.println("\nLinked List Iter: ");
		for(Integer in: ll){
			System.out.println(in);
		}
		
	}
	
	public static void hashMap(){
		
		HashMap<Integer, String> exMap = new HashMap<Integer, String>();
		
		exMap.put(1, "One");
		exMap.put(2, "Two");
		exMap.put(213, "Two One Three");
		exMap.put(56, "Five Six");
		
		System.out.println("\nMap Iter:");
		for(Map.Entry<Integer, String> entry: exMap.entrySet()){
			int key = entry.getKey();
			String val = entry.getValue();			
			System.out.println(key + " : " + val );
		}
		
	}
	
	//Keys are ordered as they are put
	public static void linkedHashMap() {
		
		LinkedHashMap<Integer, String> linkedHashMap = new LinkedHashMap<Integer, String>();
		
		linkedHashMap.put(2, "Hello");
		linkedHashMap.put(3, "wassup");
		linkedHashMap.put(23, "how");
		linkedHashMap.put(34, "are");
		linkedHashMap.put(2342, "are");
		
		System.out.println("\nLinked hashMap iter: ");
		for(Integer key: linkedHashMap.keySet()){
			String val = linkedHashMap.get(key);		
			System.out.println(key +" : "+ val);
		}		
	}

//Keys are sorted	
public static void treeMap() {
		
		TreeMap<Integer, String> treeMap = new TreeMap<Integer, String>();
		
		treeMap.put(2, "Hello");
		treeMap.put(33, "wassup");
		treeMap.put(3, "how");
		treeMap.put(1, "are");
		treeMap.put(2342, "are");
		
		System.out.println("\ntreeMap iter: ");
		for(Integer key: treeMap.keySet()){
			String val = treeMap.get(key);		
			System.out.println(key +" : "+ val);
		}
		
	}

public static void sets(){
	
	Set<String> strSet = new HashSet<String>();
	//stores in order they are put
	Set<String> linkedHashSet = new LinkedHashSet<String>();
	//Aphabetical order
	Set<String> treeSet = new TreeSet<String>();
	
	strSet.add("Hello");
	strSet.add("Wassup");
	strSet.add("How are you");
	strSet.add("Wassup");
	strSet.add("How are you");
	
	System.out.println("\nSets iter");
	System.out.println(strSet);
	
	linkedHashSet.add("Hello");
	linkedHashSet.add("Wassup");
	linkedHashSet.add("How are you");
	linkedHashSet.add("Wassup");
	linkedHashSet.add("How are you");
	
	System.out.println("\nLinked Hash Set Iter:");
	for(String ele: linkedHashSet){
		System.out.println(ele);
	}
	
	treeSet.add("Hello");
	treeSet.add("Wassup");
	treeSet.add("How are you");
	treeSet.add("Wassup");
	treeSet.add("How are you");
	
	System.out.println("\nTree Set Iter:");
	for(String ele: treeSet){
		System.out.println(ele);
	}
	
	//Intersection
	Set<String> exSet = new TreeSet<String>();
	
	exSet.add("Hello");
	exSet.add("asda");
	exSet.add("How as you");
	
	Set<String> intesect = new TreeSet<String>(treeSet);
	
	intesect.retainAll(exSet);
	System.out.println("Intersection: " + intesect);
	
	//Difference
	Set<String> diff = new TreeSet<String>(treeSet);
	diff.removeAll(exSet);
	
	System.out.println("Difference: " + diff);
	
	
}

public static void sortLists(){
	
	List<String> strList = new ArrayList<String>();
	
	strList.add("one");
	strList.add("Two");
	strList.add("Three");
	strList.add("Four");
	strList.add("Five");
	strList.add("Six");
	
	Collections.sort(strList);
	
	System.out.println("\nSorted Arrary List");
	for(String str: strList)
		System.out.println(str);
	
}








}
