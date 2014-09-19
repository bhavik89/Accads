package codingInterview;

import java.util.ArrayList;
import java.util.Stack;

public class EvalExpr {
	static int eval(String str){
		int result = 0;
		
		Stack<Character> expStack = new Stack<>();
		char[] strArr = str.toCharArray();
		int i = 0;
		while(i<str.length()){
			if(strArr[i] != ' ')
				expStack.push(str.charAt(i));
		}
		
		ArrayList<Integer> array = new ArrayList<Integer>();
		
			
		
		return result;
	}
}
