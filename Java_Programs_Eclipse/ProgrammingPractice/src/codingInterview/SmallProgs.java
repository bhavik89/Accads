package codingInterview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import com.sun.corba.se.impl.oa.poa.AOMEntry;

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
	
	
	static void printFiboRecursiveWords(int num){
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
	
	
static String printIterFiboString(int n){
		
		String first = "0";
		String second = "01";
		
		StringBuilder result = new StringBuilder();
		
		
		if(n == 1){
			result.append(first);
			return result.toString();
		}
		
		for (int i = n; i>1; i--){						
				String tmp = second;
				second += first;
				first = tmp;			
		}
		
		return result.append(first).toString();
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
	
	static int printSquaresNum(int A, int B){
		
		if(B <= 0)
			return 0;
		
		if(A < 0)
			A = 0;
		
		int sqrtA = (int) Math.sqrt(A);
		int sqrtB = (int) Math.sqrt(B);
		
		if(sqrtA * sqrtA != A)
			sqrtA++;
		
		return (sqrtB - sqrtA + 1);		
	}
	
	static int maxGap(int[] A){
		
		Arrays.sort(A);
		
		int maxGap = Integer.MIN_VALUE;
		
		for(int i = 1; i < A.length ; i++){
			if(maxGap < (A[i] - A[i-1]))
				maxGap = (A[i] - A[i-1]);				
		}
		
		return maxGap/2;
	}
	
	static void compareStrings(){
		String str1 = "Hell";
		String str2 = "Hella";
		
		System.out.println(str1.compareTo(str2));	
		
	}
	
	static void numberWithSameIndex(){
		int[] numbers = {-3, -2, 0, 2, 3, 4,5, 7};//{-1,0,2,4,5,6,7,8};
		
		int first = 0;
		int last = numbers.length -1;
		int middle = first + (last - first) / 2;
		boolean matchFound = false;
		
		while(first <= last){
			middle = first + (last - first) / 2;
			
			if(numbers[middle] == middle)
				{ matchFound = true; break; }
			
			if(numbers[middle] < middle)
				first = middle + 1;
			else if(numbers[middle] > middle)
				last = middle - 1;	
			
		}
		
		if(matchFound)
			System.out.println("Matched number with Index: " + middle);
			
		else
			System.out.println("No Match found");
		
 	}
	
	static void medianOfSorted(){
		int[] arr1 = new int[9];//{10, 30, 50, 70, 90};
		int[] arr2 = new int[9];//{20, 40, 60, 80, 90};
		
		for(int i = 0; i < arr1.length; i++){
			arr1[i] = (int) (Math.random() * 100);
			arr2[i] = (int) (Math.random() * 100);
		}
		
		Arrays.sort(arr1);
		Arrays.sort(arr2);
		
		int first1 = 0;
		int first2 = 0;
		int last1 = arr1.length-1;
		int last2 = arr2.length-1;
		
		while(((last1-first1 +1) != 2) || ((last2 - first2 +1)!= 2)){
			
			int middle1 = first1 + (last1 - first1) / 2;
			int middle2 = first2 + (last2 - first2) / 2;
			
			int median1 = arr1[middle1];
			int median2 = arr2[middle2];
			
			if(median1 == median2)
				break;
			
			if(median1 < median2){
				first1 = first1;
				last1 = middle1;
				first2 = middle2;
				last2 = last2;
				
			}				
			else{
				first1 = middle1;
				last1 = last1;
				first2 = first2;
				last2 = middle2;
			}
			
		}
		
		int median = (Math.max(arr1[first1], arr2[first2]) + Math.min(arr1[last1], arr2[last2]))/2;
		
		System.out.println("Sorted Arrays: ");
		System.out.println(Arrays.toString(arr1));
		System.out.println(Arrays.toString(arr2));
		
		
		System.out.println("Median: " + median);
				
		
	}
	
	static void printBits(){
		int res = 0;
		System.out.println(Integer.toString(10, 2));
		int num = 7;
		int count = 0;
		
		BitSet bits = new BitSet();
		bits.set(1);
		bits.set(2);
		System.out.println(bits.get(2));
		
		while(num != 0){
			
			if((num & 1) == 1){
				count++;				
			}
			num >>=1;
		}		
	}
	
	static void printPowerSets(Object[] arr){
		
		ArrayList<String> powerSet = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		
		long totalSets = (long) Math.pow(2, arr.length);
		
		for(int i = 0; i < totalSets; i++){
			
			sb.setLength(0);
			int bit = 0;
			
			int temp = i;
			while(temp != 0){				
				if((temp & 1) == 1){
					sb.append(arr[bit]);				
				}
				temp >>=1;
				bit++;
			}
			
			if(sb.length() == 0)
				powerSet.add(null);
			else
				powerSet.add(sb.toString());
			
		}
		
		System.out.println(powerSet);
		
	}
	
	
	static int additionNoOperator(int a, int b){
		int sum   = 0, carryIn = 0, k = 1, tempA = a, tempB = b;
		 
		while((tempA | tempB) != 0){
			//System.out.println("K: " + k);
			int ak = a&k, bk = b&k;
			int carryOut = (ak&bk) | (ak&carryIn) | (bk&carryIn);
			sum |= (ak ^ bk ^ carryIn);
			carryIn = carryOut << 1;
			k <<= 1;
			tempA >>=1;
			tempB >>= 1;			
		}
		
		return (sum | carryIn);
	}
	
	static void multiplyNoOperator(int a, int b){
		int result = 0;
		
		for(int i=1; i<=b; i++){
			result = additionNoOperator(a,result);
			//System.out.println(result);
		}
		
		System.out.println(result);
		
	}
	
	static void printPrimes(int n){
		
		BitSet primes = new BitSet(n+1);
		
		int maxTraverse = (int) Math.sqrt(n);
		//int maxnums = n/2;
		
		for(int i = 2; i <= maxTraverse; i++){
			
			if(!primes.get(i)){
				for(int j = i; j*i <= n; j++){
					primes.set(i*j);
				}
					
			}			
		}
		
		for(int i= 1; i <= n; i++){
			
			if(!primes.get(i))
				System.out.println(i);
			
		}	
		//System.out.println(primes.toString());	
		
	}
	
	static void countSubBytes(byte[] sourceArr, byte[] target){
		
		if(sourceArr.length < target.length || 
				sourceArr.length == 0 || 
				target.length == 0 ||
				sourceArr == null ||
				target == null ){
			System.out.println("Invalid array!");
			return;
		}
		
		int count = 0;
		boolean matchFound = false;
		int i = 0;
		while( i < sourceArr.length){
			
			int tempI = i;
			if((sourceArr[i] ^ target[0]) == 0){
				matchFound = true;
				
				tempI = i+1;
				for(int j = 1; j < target.length; j++){
					
					if((sourceArr[tempI] ^ target[j]) == 0)
						tempI++;
					
					else{
						matchFound = false;
						break;
					}
				}				
			}
			
			if(matchFound){					
				count++;
				i = tempI;	
				matchFound = false;
			}else
				i++;
		}
			
		System.out.println("Byte Occurences: " + count);
		
		}
	
		
		static void turnOffBit(int num, int k){
			
			System.out.println(num & (~(1 << (k-1))));		
			
		}
		
		
		static boolean isIsomorphic(String str1, String str2){
			
			if((str1.length() != str2.length()) || str1 == null || str2 == null)
				return false;
			
			if(str1.length() < 2)
				return true;
			
			int [] str1Arr = new int[256];
			int [] str2Arr = new int[256];
			
			for(int i = 0; i < str1.length() ; i++){
				str1Arr[(int) str1.charAt(i)]++;
				str2Arr[(int) str2.charAt(i)]++;
			}
			
			Arrays.sort(str1Arr);
			Arrays.sort(str2Arr);
			
					
			for(int i = 0; i < str1Arr.length; i++){
				if(str1Arr[i] != str2Arr[i])
					return false;
			}
			
			return true;
			
		}
		
		static boolean isIsomorphicWithMap(String str1 , String str2){
			
			if((str1.length() != str2.length()) || str1 == null || str2 == null)
				return false;
			
			if(str1.length() < 2)
				return true;
			
			HashMap<Character, Character> isoMap = new HashMap<>();
			
			for(int i = 0; i < str1.length(); i++){
				if((isoMap.containsKey(str1.charAt(i))  && isoMap.get(str1.charAt(i)) != str2.charAt(i)) || 
						(isoMap.containsValue(str2.charAt(i)) && !isoMap.containsKey(str1.charAt(i))))
					return false;
				//else if(isoMap.containsValue(str2.charAt(i)))
				//	return false;
				else
					isoMap.put(str1.charAt(i), str2.charAt(i));				
			}
			
			return true;
			
			
		}
		
		static void removeAll(){
			
			HashSet<Integer> set1 = new HashSet<>();
			HashSet<Integer> set2 = new HashSet<>();
			
			set1.add(1);
			set1.add(2);
			set1.add(3);
			
			set2.add(1);
			set2.add(3);
			
			HashSet<Integer> setx = new HashSet<>(set1);
			setx.removeAll(set2);
			
			System.out.println(setx);			
			
		}
		
		static void countTrailingZeroInFact(int num){
			
			if(num < 0){
				System.out.println("Invalid num");
				return;
			}
				
			
			int count  = 0;
			
			count  = num /5;
			
			int i = 5;
			while(num >0){
				i *= 5;
				count += num/i;
				num /= i;
			}
			
			System.out.println("No. of trailing zeros: " + count);
					
		}
		
		public static void main(String[] args) {
			//countTrailingZeroInFact(29);
			Object[] A = {1,2,3}; 
			printPowerSets(A);
		}
		
	}
	
	

