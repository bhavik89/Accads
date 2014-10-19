package codingInterview;

import java.util.ArrayList;
import java.util.Arrays;

import com.sun.corba.se.spi.legacy.connection.GetEndPointInfoAgainException;
import com.sun.org.apache.bcel.internal.generic.FNEG;

public class MainClass {
	
	public static void main(String[] args) {
	
		int [] nums = {1, 1, 1, 1, 1, 0, 0};
		
		//System.out.println(DivideAndConquer.searchZeros(nums));
		//System.out.println(DivideAndConquer.binarySearchFirstOccur(nums, 0, 0, 7));
		//PatternSearch.naiveSearch("bannana", "an");
		
		//System.out.println(DigitsCharCombination.letterCombinations("32"));
		
		//SmallProgs.towerOfHanoi(3, 'A', 'B', 'C');
		
		//SmallProgs.printFiboRecursive(10);
		//SmallProgs.printIterFibo(10);
		
		//System.out.println(SmallProgs.recursiveFact(3));
		//SmallProgs.iterFact(1);
//		int[] binArr = {1,0,0,1};
//		SmallProgs.decToBin(15);
//		SmallProgs.binToDec(binArr);
		
		//System.out.println(NumbersOperations.getIntegerLength(1238));
		//NumbersOperations.interLeveNumbers(123499, 56780);
		
		//SmallProgs.reverseSentence("the sky is blue");
		//Hill.returnHill();
		
		//System.out.println(Math.abs(-10 + 10));
		
		//System.out.println(SmallProgs.printSquaresNum(5, 17));
		int[] A = {0,3,3,3,3,3,9};
		//System.out.println(SmallProgs.maxGap(A));
		
		//ExpressionInterpreter expr = new ExpressionInterpreter();
		
		//System.out.println(expr.exprInterpret("(+ (- 3 2) (+ 5 25) (* 1 2 3))"));
		//Arrays.sort(A);
		//System.out.println(FindFrequencyInArray.getFrequency(A, 3));
		
		UnionAndIntersections.printUnionAndIntersections();
		
		//System.out.println(StringToLong.stringToLong("2"));
		
		//TriNaryTree.test(A);
		
		//MainClass.getEqualSumSubtring("986561517416921217551395112859219257312");
		//SmallProgs.compareStrings();
		//System.out.println(SmallProgs.printIterFiboString(2));
	}
	
	public static int getEqualSumSubtring(String s){

	    // run through all possible length combinations of the number string on left and right half
	    // append sorted versions of these into new ArrayList

	    ArrayList<String> temp_a = new ArrayList<String>();
	    ArrayList<String> temp_b = new ArrayList<String>();

	    int end; // s.length()/2 is an integer that rounds down if length is odd, account for this later 

	    for( int i=0; i<=s.length()/2; i++ ){
	        for( int j=i; j<=s.length()/2; j++ ){
	            // only account for substrings with a length of 2 or greater
	            if( j-i > 1 ){ 
	                char[] tempArr1 = s.substring(i,j).toCharArray();
	                Arrays.sort(tempArr1);
	                String sorted1 = new String(tempArr1);
	                temp_a.add(sorted1);
	                //System.out.println(sorted1);

	                if( s.length() % 2 == 0 )
	                    end = s.length()/2+j;
	                else // odd length so we need the extra +1 at the end
	                    end = s.length()/2+j+1; 
	                char[] tempArr2 = s.substring(i+s.length()/2, end).toCharArray();
	                Arrays.sort(tempArr2);
	                String sorted2 = new String(tempArr2);
	                temp_b.add(sorted2);
	                //System.out.println(sorted2);
	            }

	        }

	    }

	    // For reference
	    System.out.println(temp_a);
	    System.out.println(temp_b);

	    // If the substrings match, it means they have the same sum

	    // Keep track of longest substring
	    int tempCount = 0 ;
	    int count = 0;
	    String longestSubstring = "";

	    for( int i=0; i<temp_a.size(); i++){
	        for( int j=0; j<temp_b.size(); j++ ){
	            if( temp_a.get(i).equals(temp_b.get(j)) ){

	                tempCount = temp_a.get(i).length();

	                if( tempCount > count ){
	                    count = tempCount;
	                    longestSubstring = temp_a.get(i);

	                }
	            }

	        }
	    }

	    System.out.println(longestSubstring);
	    return count*2;
	}
	
}
