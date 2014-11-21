package codingInterview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class DisJointSet {
	
	HashMap<Character, Character> parent = new HashMap<>();
	
	public DisJointSet(){
		
		char[] sets = {'a','b','c','d','e','f'};
		
		for(Character c: sets)
			parent.put(c, c);
		
	}
	
	char find(char element){
		
		char p = parent.get(element);
		if(p == element)
			return p;
		else
			return find(p);
	}
	
	void union(char set1, char set2){
		parent.put(set1, set2);
	}
	
	int getDisJointSets(){
		
		HashSet<Character> uniqueParents = new HashSet<>();
		
		for(Character c :this.parent.keySet()){
			uniqueParents.add(this.find(c));
		}
		
		return uniqueParents.size();
		
		
	}
	
	
	public static void main(String[] args) {
		
		DisJointSet ds = new DisJointSet();	
		System.out.println("Unique Sets: " + ds.getDisJointSets());
		ds.union('d', 'b');
		
		System.out.println("Unique Sets: " + ds.getDisJointSets());
		
		System.out.println(ds.parent);
		System.out.println(ds.find('c'));
		
		ds.union('c', 'a');
		ds.union('a', 'b');
		System.out.println(ds.parent);	
		System.out.println(ds.find('c'));
		System.out.println("Unique Sets: " + ds.getDisJointSets());
		
	}
	
}
