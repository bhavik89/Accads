package codingInterview;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class SmallProgs {

	static void towerOfHanoi(int n, char src, char aux, char dest){
		
		if(n==1){
			System.out.printf("Moved disk 1 from %c to %c",src,dest);
			System.out.println();
			//return;
		}else{
		
		towerOfHanoi(n-1, src, dest, aux);
		System.out.printf("Moved disk %d form %c to %c", n, src, dest);
		System.out.println();		
		towerOfHanoi(n-1, aux, src, dest);
		}
		
	}
	
	static void printFiboRecursive(int num){
		for (int i = 0; i <= num; i++){
			System.out.println(recursiveFibo(i));
		}
	}
	
	//Funtion to return Nth fibo number (recursive function)
	static int recursiveFibo(int n){
		
		if(n==0)
			return 0;
		else if (n == 1)
			return 1;
		else
			return (recursiveFibo(n -1) + recursiveFibo(n-2));
		
	}
	
	//Function to print Fibo for n numbers iteratively
	static void printIterFibo(int n){
		
		int first = 0;
		int second = 1;
		
		if(n == 1){
			System.out.println(first);
			return;
		}
		
		for (int i = 0; i<n; i ++){
			
			if(i == 0){
				System.out.println(first);
				
			}else{
			
				System.out.println(second);
				int tmp = second;
				second = second + first;
				first = tmp;
			}
			
		}
	}
	
	static long recursiveFact(long num){
		
		if(num == 0 || num == 1)
			return 1;
		else
			return (num * recursiveFact(num - 1));
		
	}
	
	static void iterFact(int num){
		long fact = 1;
		if(num == 0 || num == 1){
			System.out.println(fact);
			return;
		}else{
			for (int i = 2; i <= num; i++){
				fact *= i;
			}
		}
		System.out.println(fact);
	}
	
	static void decToBin(int num){
		ArrayList<Integer> binArray = new ArrayList<>();
		
		while (num >= 1){
			binArray.add(num%2);
			num /= 2;
		}
		Collections.reverse(binArray);
		System.out.println(binArray);
	}
	
	static void binToDec(int[] num){
		int decNum = 0;
		
		int index = 0;
		for(Integer bit: num){
			if(bit == 1)
				decNum += Math.pow(2, index);
			
			index++;
		}
		
		System.out.println(decNum);
	}
	
	static void reverseSentence(String sent){
		
		//Remove leading or trailing Spaces
		sent = sent.trim();		
		
		//Convert Sentence to String array 
		String[] words = sent.split("\\s+");
		
		//Remove all punctuations
		for (int i = 0; i < words.length; i++) {		   
		    words[i] = words[i].replaceAll("[^\\w]", "");
		}
		
		//New result String
		String result = new String();
		
		//Iterate from end of array to construct result string
		for (int j = (words.length-1); j >= 0; j--){
			if(j>0)
				result += (words[j] + " ");
			else
				result += (words[j]);
		}	
		
		System.out.println(result);

	}
	
	
	
	
}
