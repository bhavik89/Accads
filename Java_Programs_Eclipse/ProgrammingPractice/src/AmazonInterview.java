
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public class AmazonInterview {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//System.out.println(intToString(3));
	    //System.out.println(compressStr("abcd"));
		
		int [] nums = {1,2,3,4,4, 6,2,12,3};
		int[] res = giveNumbers(nums, 9);
		
//		int n1 = 0b1010;
//		int n2 = 0b10;
//		System.out.println(n1 & n2);
		
		//System.out.print(res[0] + "," + res[1]);
		
//		
		//System.out.println(ipow(5,3));
		//System.out.println(getNextLarge(2));
		//System.out.println(getBitCount(16));
		//System.out.println(isPalindromeNum(9));
		//System.out.println(expoSqre(2,5));
		//System.out.println("madam");
		//System.out.println(isPalindromeBinary(9));
		
		int [] nums2 = {1,1,2,4,4,1,2,3,2,3,4,3,1,2,3,4,3};
		int odd = getOddOccurence(nums2);
		//System.out.println(odd);
		
		//System.out.println((int)Math.ceil(Math.random() * 52));
		
		//divideNoDivision(0, 0);
		//getStrCount("ThunderBirdFirefoxt");
		//strNums("ThunderBirdFirefoxt");
		
		int[] sortedArrays = {1,3,5,7,-8,-6,-4,-2};
		int[] sortedArrays1 = {1,3,5,7,2,4,6,8};
		//sortSortedArrays(sortedArrays);
		//sortSortedArrays(sortedArrays1);
		//mergeSorted();
		//sortSortedArrays1();
		//System.out.println(istPalindrom("asdasdsa"));
		
		ArrayList<Integer> intArr = new ArrayList<Integer>();
		intArr.add(5);
		intArr.add(4);
		intArr.add(9);
		intArr.add(8);
		//System.out.println(findWhetherTwoInArraySumToValue(intArr, 10));
		//rightRotate(sortedArrays);
		int[] tempArr = {1, 2, 3, -4, -1, 4};
		//alternatingRearrange(tempArr);
		
		//rearrange(tempArr, 0, false)
		
//		for (Integer i: rearrange(tempArr, 0, false))
//			System.out.println(i);
		System.out.println(isAnagram("aab", "aba"));
	}
	
	
	public static StringBuffer intToString(int value) {
		  StringBuffer buffer = new StringBuffer();
		  if (value < 0) {
		    buffer.append("-");
		  }
		  // MAX_INT is just over 2 billion, so start by finding the number of billions.
		  int divisor = 10000;
		  while (divisor > 0) {
		    int digit = value / divisor;  // integer division, so no remainder.
		    if (digit > 0) {
		      buffer.append('0'+digit);
		      value = value - digit * divisor; // subtract off the value to zero out that digit.
		    }
		    divisor = divisor / 10; // the next loop iteration should be in the 10's place to the right
		  }
		  
		  return buffer;
		}
	
	
	
	//Compresses a string ex: aaaabbbbcc => a4b4c2
	public static String compressStr(String str){
		
		if(str.length() == 0)	
			return null;
		
		String outstr = "";		
		
		char last = str.charAt(0);
		int count = 1;
		
		for (int i = 1; i < str.length(); i ++){
			if(str.charAt(i) == last)
				count++;
		else{
			if(count == 1)
				outstr += last;
			else
				outstr += last + "" + count;
			
			last = str.charAt(i);
			count = 1;
		}
		}
	
	if ((outstr + last + count).length() < str.length())	
			return outstr + last + count;
	else
		return str;
	}
	
	public boolean inRotation(String s1, String s2){
		String concatString = s1 + s1;
		return isSubString(concatString, s2);
	}

	
	
	private boolean isSubString(String concatString, String s2) {
		
		boolean isSub = false;
		
		if (concatString.length() < s2.length())
			return isSub;
		
		int len = concatString.length();
		int str2_pointer = 0;
		
		for(int i = 0; i < len; i ++){
			if (concatString.charAt(i) == s2.charAt(str2_pointer)){
				 isSub = true;	
				 str2_pointer++;
			}
			else{
				isSub = false;	
				 str2_pointer = 0;
			}
		}
		
		return isSub;
	}
	
	//returns the array of 2 numbers from the given array having their sum = given agr sum
	public static int[] giveNumbers(int intArr[], int sum){
		
		int[] result = new int[2];
		
		for(int i = 0; i < intArr.length - 1; i++){
			for (int j = 0; j < intArr.length; j++){
				if((intArr[i] + intArr[j]) == sum){
					
					result[0] = intArr[i];
					result[1] = intArr[j];
				}	
			}
		}		
		return result;		
	}
	
	private static int ipow(int base, int exp)
	{
	    int result = 1;
	    while (exp != 0)
	    {
	        if ((exp & 1) == 1)
	            result *= base; 
	        exp >>= 1;		// divide by 2
			System.out.println(exp);
	        base *= base;
	    }

	    return result;
	}
	
	private static int getNextLarge(int n){
		int mask = 1;
		
			while( !((n & mask) == mask) && (((n & (mask<<1)) == 0))){
				mask <<= 1;
				System.out.println("Mask: " + mask);
			}
			mask <<= 1;
			n |= mask;
			mask >>= 1;
			mask = ~(mask);
			return mask & n;
			
		}
	
	public int getNextSmall(int n){
		int mask = 1;
		
		while (!((n & mask) == 0) && ((n & (mask<<1)) == mask<<1)){
			mask <<= 1;
		}		
		n |= mask;
		mask <<= 1;
		mask = ~(mask);
		
		return n & mask;
		
	}
	
	public static boolean isPalindromeNum(int n){
		int newNum = 0 , aux = n;
		while(aux > 0){
			newNum= (newNum<<1) | (aux & 1);			
			aux >>= 1;			
		}
		System.out.println(newNum);
		return (n == newNum);		
	}
	
	
	public static int getBitCount(int n){
		int count  = 1;
		while(n >0){
			count++;
			n >>=1;
		}		
		return count;
	}
	
	public static int expoSqre(int base, int exp){
		
		int x;
		if (exp == 0)
			return 1;
		
		else if (exp == 1)
			return base;
		
		else if (exp < 0){
			x = expoSqre(base, -exp);
			return 1/x;
		}
		else if (exp%2 != 0){
			x = expoSqre(base, (exp-1)/2);
			return base*x*x;
		}
		else{			
			x = expoSqre(base, exp/2);
			return x*x;		
		}
	}
	
	//Method to determine if the string is palindrome or not
	boolean isPalindrome(String wordOrPhrase){
	    
	    //remove puntuation marks form the string
	    //replace all punctuation mark with "" using regex
	    String noPunc = wordOrPhrase.replaceAll("[^a-zA-Z0-9]", "");
	    
	    //return true, if the new string is equal to the reverse of the same
	    //then the string is palindrome
	    return noPunc.equalsIgnoreCase((new StringBuilder(noPunc)).reverse().toString());
	}
	
	static boolean isPalindromeBinary(int num){
		
		int numBits = getBitCount(num);
		
		
		//System.out.println(getBitCount(num));
		
		int mask1 = 0b1100;
		int mask2 = 0b0011;
		
		int and1 = (num & mask1);
		//System.out.println(and1);
		int and2 = (num & mask2);
		//System.out.println(and2);
		for(int i=0; i <= 2; i++){
			and2 <<= 1;
		}
		//System.out.println(and2);
		
		return (and1 == and2);		
		
	}
	
	
	static int getOddOccurence(int[] arr){
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		
		for (int i=0; i< arr.length; i++){
			int val;
			
			if (map.get(arr[i]) == null)				
				val = 0;
			
			else
				val = map.get(arr[i]);
				
				
			map.put((arr[i]), (val+1));
						
		}		
		
		Iterator iter = map.entrySet().iterator();
		
		while(iter.hasNext()){
			Map.Entry mapEntry = (Map.Entry)iter.next();
			if(map.get(mapEntry.getKey()) % 2 != 0){
					//System.out.println(mapEntry.getKey()+":"+ mapEntry.getValue());
					return (int) mapEntry.getKey();
					
			}
		}
		
		return 0;
		
	}
	
	/* Implementing division without division operator
	 * */
	public static void divideNoDivision(int dividend, int divisor){
		
		if(divisor == 0){
			System.out.println("Infinity");
			return;
		}		
			
		int remainder = 0;
		int quotient = 0;
		int storeDividend = dividend;
		
		while(dividend >= divisor){
			quotient++;
			dividend -= divisor;
		}		
		remainder = storeDividend - divisor*quotient;
		System.out.println("Quotient: " + quotient);
		System.out.println("Remainder: " + remainder);
	}
	
	public static void numToString(int num){
		
		Map<Integer, String> num2str = new HashMap<Integer, String>();
		
		num2str.put(1, "one");
		num2str.put(2, "two");
		num2str.put(3, "three");
		num2str.put(4, "four");
		num2str.put(5, "five");
		num2str.put(6, "six");
		num2str.put(7, "seven");
		num2str.put(8, "eight");
		num2str.put(9, "nine");
		
		StringBuffer numStr = new StringBuffer();
		
		
	}
	
	
	public static void getStrCount(String myString){
	
	Hashtable<String, String> myHash = new Hashtable<String , String>();
	Hashtable<String, String> myHashCount = new Hashtable<String, String>();
	String targetString = "";

	for(int i=0;i<myString.length();i++)	{
	
		if(myHash.containsKey(""+myString.charAt(i))){
	
			int counter = Integer.parseInt(myHashCount.get(myString.charAt(i)+"")+"")+1;
	
			//myHashCount.remove(myString.charAt(i)+"");
	
			myHashCount.put(myString.charAt(i)+"",""+counter);
	}	
		else{
		
			myHash.put(""+myString.charAt(i),""+myString.charAt(i));
	
			myHashCount.put(""+myString.charAt(i),1+"");
	
			targetString += myString.charAt(i);
		}
	}	
	
	int i = 0;
	myString = "";
	
		while(i < targetString.length()){
	
			myString += (targetString.charAt(i) + "" + myHashCount.get(targetString.charAt(i)+"") );	
			i++;
		}
	
		System.out.println(myString);
	}
	
	public static void strNums(String str){
		
		LinkedHashMap<String, Integer> myMap = new LinkedHashMap<String, Integer>();		
		String outStr = "";
		for(int i = 0; i<str.length(); i++){
			
			if(myMap.containsKey(""+str.charAt(i))){
				int count = (myMap.get(""+str.charAt(i))) + 1;
				
				myMap.put(""+str.charAt(i), count);
			}
			else
				myMap.put(""+str.charAt(i), 1);	
				
		}
		
		for(Map.Entry<String, Integer> entry : myMap.entrySet()){
			outStr += (entry.getKey() + (""+entry.getValue()));
		}
		System.out.println(outStr);
	}
	
	public static void sortSortedArrays(int[] arr){
		
		int mid = arr.length/2;
		
		
		for(int i=0; i<mid; i++){
			for(int j = mid; j<arr.length; j++){
				if(arr[i] > arr[j]){
					int temp = arr[i];
					arr[i] = arr[j];
					arr[j] = temp;
				}
				for(Integer x:arr)
					System.out.print(x+", ");
				System.out.println();
			}
							
		}
		
		System.out.println("Sorted Array:");
		for(Integer i:arr)
			System.out.print(i+", ");
		System.out.println();	
		
	}
	
	
	
	
	
	/* Checks if the given string is a anagram of another
	 * Uses hashMap
	 * */
	public static boolean isAnagram(String s, String t){

		if(s.length() != t.length()) return false;   
		   
		HashMap<Character, Integer> anaHash = new HashMap<Character, Integer>();
		int chars_s = 0;
		int chars_t = 0;
		char[] s_array = s.toCharArray();
		char[] t_array = t.toCharArray();
		
		int val;
		for (int i=0; i< s.length(); i++){
		    
		    
		    if(anaHash.get(s_array[i]) == null)
		    	val = 0;
		    else
		        val = anaHash.get(s_array[i]);
		   
		    anaHash.put(s_array[i], (val +1));//(anaHash.get(s_array[i]) + 1));
		    chars_s ++;
		}

		for (int j=0; j<t.length(); j++){
		   
		    if(anaHash.get(t_array[j]) == null){
		    	return false;
		    }
		    else if((anaHash.get(t_array[j])  - 1 )< 0){
		    	return false;
		    }
		    else
		    {
		        anaHash.put(t_array[j], (anaHash.get(t_array[j]))-1);
		        chars_t++;
		    }
		   
		}

		return (chars_s == chars_t);
		}

		/* Checks if a given string is palindrome
		 * */

		public static boolean istPalindrom(String str){
			char[] word = str.toCharArray();
		    int i1 = 0;
		    int i2 = word.length - 1;
		    while (i2 > i1) {
		        if (word[i1] != word[i2]) {
		            return false;
		        }
		        i1++;
		        i2--;
		    }
		    return true;
		}
		
		/* Returns true iff sum of any two ints in Array results into the target value given
		 * else returns false
		 * */
		static boolean findWhetherTwoInArraySumToValue(ArrayList<Integer> array, int value){

		    if(array==null || array.size() == 0)
		        return false;
		    
		    HashMap<Integer, Integer> arrayMap = new HashMap<Integer, Integer>();
		    
		    //Build Hashmap form the given array, <arrayInt, IntCount>
		    //Complexity: O(n)
		    for(Integer i: array){
		        if(arrayMap.get(i) != null){
		            arrayMap.put(i, arrayMap.get(i)+1);        
		        }else{
		            arrayMap.put(i,1);
		        }
		    }
		    
		    //Iterate over the array to check if (value-arrayElement) exists in map
		    //Complexity O(n)
		    for(Integer i: array){
		    	if(arrayMap.get(value-i) == null)
		            return false;
		    	
		    	else if(arrayMap.get(value-i) != null && arrayMap.get(value-i) > 1)
		    		return true;
		    	
		        else {
		        	int add = 0;
		        	for(int j = 0; j<=2; j++){
		                add += j;
		                if(add == value)
		                    return true;
		            }
		            }
		          }            
		    
		    return false;
		}
		
		
		static void alternatingRearrange (int[] arr){
			//int curr, nxtValid;
						
			for (int i = 0; i < arr.length; i++){
				if((i%2 == 0 && arr[i] < 0) || (i%2 != 0 && arr[i] > 0))
					continue;
					
				else if (i%2 == 0 && arr[i] > 0){					
					arr = rearrange(arr, i, false);
				}
				
				else if (i%2 != 0 && arr[i] < 0){					
					arr = rearrange(arr, i, true);
				}
			}
			
			for(Integer i : arr){
				System.out.println(i);
			}
		}
		
		static int[] rearrange(int[] arr, int curr, boolean sign) {
			//int nxtValid;			
		
			for(int i = curr+1; i < arr.length; i++){
				if(sign && (arr[i] >= 0)){
					arr =  rightRotate(arr, curr, i);
					break;
				}
				else if (!sign && (arr[i] < 0)){
					arr = rightRotate(arr, curr, i);
					break;
				}
			}
			
			return arr;
			
		}




		static int[] rightRotate(int[] arr, int start, int end){
			int tmp = arr[end];
			for (int i = end ; i > start ; i--){
				arr[i] = arr[i-1];
			}
			arr[start] = tmp;
			
//			for (Integer x: arr){
//				System.out.println(x);
//			}
			
			return arr;
				
		}


}



	

