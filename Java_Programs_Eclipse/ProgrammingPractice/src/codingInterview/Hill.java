package codingInterview;

import java.util.Arrays;
import java.util.List;
import java.util.*;

public class Hill {
	
	static void returnHill(){
//		int[] intArr = {5,4,3,2,8};
//		Integer.MIN_VALUE
//		List b = Arrays.asList((intArr));
//		
//		System.out.println(Collections.min(b));
//        System.out.println(Collections.max(b));
		
		int[] array = {4,5,2,3,8};
		int K = 0, last = array[0];
		for (int i = 1; i < array.length; ++i) {
		    if (last >= array[i] + K) {
		        // If we're here, then our value for K wasn't enough: the minimum
		        // possible value of the previous element after transformation is still
		        // not less than the maximum possible value of the current element after
		        // transformation; so, we need to increase K, allowing us to decrease
		        // the former and increase the latter.
		        int correction = (last - (array[i] + K)) / 2 + 1;
		        K += correction;
		        last -= correction;
		        ++last;
		    } else {
		        // If we're here, then our value for K was fine, and we just need to
		        // record the minimum possible value of the current value after
		        // transformation. (It has to be greater than the minimum possible value
		        // of the previous element, and it has to be within the K-bound.)
		        if (last < array[i] - K) {
		            last = array[i] - K;
		        } else {
		            ++last;
		        }
		    }
		}
		System.out.println(K);
	}
}
