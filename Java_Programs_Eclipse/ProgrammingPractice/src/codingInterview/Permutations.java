package codingInterview;

import java.util.ArrayList;
import java.util.Arrays;

import sun.security.krb5.internal.PAEncTSEnc;

public class Permutations {
	
	public static void main(String[] args) {
		Object[] arr = {'a','b','c'};
		//getPermutation(arr, 0, arr.length-1);
		genParenthesisList(3);
	}
	
	static void getPermutation(Object[] arr, int current, int len){
		
		if(current== len){
			System.out.println(Arrays.toString(arr));
			return;
		}
		
		for(int j = current; j<= len; j++){
			
			if(j != current)
				swap(arr, j, current);
			
			getPermutation(arr, current+1, len);
			
			if(j != current)
				swap(arr, j, current);
		}
	}

	private static void swap(Object[] arr, int j, int current) {
		
		Object temp = arr[j];
		arr[j] = arr[current];
		arr[current] = temp;
		
		//return arr;
	}
	
	public static void genParenthesisList(int count){
		
		char[] parenStr = new char[count*2];
		ArrayList<String> list = new ArrayList<>();
		addParen(list, count, count, parenStr, 0);
		
		System.out.println(list);
		
	}

	private static void addParen(ArrayList<String> list, 
									int leftRem, int rightRem,
									char[] parenStr, int count) {
		
		if(leftRem < 0 || rightRem < leftRem)
			return;
		
		if(leftRem == 0 && rightRem == 0){
			String s = String.copyValueOf(parenStr);
			list.add(s);
		} else {
		
			if(leftRem > 0){
				parenStr[count] = '(';
				addParen(list, leftRem -1, rightRem, parenStr, count +1);
			}
			
			if(rightRem > leftRem){
				parenStr[count] = ')';
				addParen(list, leftRem, rightRem-1, parenStr, count+1);
			}
			
		}
		
	}
	

}
