package codingInterview;

public class NumbersOperations {

	static int getIntegerLength(long num){
		int len = 0;
		
		while(num > 0){
			num /= 10;
			len ++;
		}		
		return len;
	}
	
	static long interLeveNumbers(long num1, long num2){
		
		int len1 = getIntegerLength(num1);
		int len2 = getIntegerLength(num2);
		
		long c1 = num1, c2= num2;
		
		long result = 0;
		
		if(len1 == 0 && len2 == 0){
			return 0;			
		}else if(len1 == 0 && len2 > 0){
			return num2;
		}else if(len1 > 0 && len2 == 0){
			return num1;
		}else{
			int j = 0;
			//int l1 = len1, l2 = len2;
			for(int i = 1; i <= Math.max(len1, len2); i++){
				
				long tmp1 = (long) (c1/(Math.pow(10, (len1 -i))));
				long tmp2 = (long) (c2/(Math.pow(10, (len2 -i))));
				
				System.out.println("Tmp1: "+ tmp1 + ", Tmp2: " + tmp2);
				
				if((len1 - i) >= 0)
					result += tmp1 * (Math.pow(10,(len1 + len2 - (++j))));
				if((len2 - i) >= 0)
					result += tmp2 * (Math.pow(10,(len1 + len2 - (++j)))); 
				//j += 2;
				
				c1 %= Math.pow(10, (len1 -i ));
				c2 %= Math.pow(10, (len2 -i ));
				
				System.out.println(result);
				System.out.println(j);
				
			//	l1--;
			//	l2--;
				
			}
			return result;
		}
			
	}
}
