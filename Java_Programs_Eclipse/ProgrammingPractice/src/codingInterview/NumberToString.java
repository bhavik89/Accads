package codingInterview;

public class NumberToString {
	
	public static String[] digits = {"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine"};
	public static String[] teens = {"Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
	public static String[] tens = {"Ten", "Twenty", "Thirty", "Fourty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
	public static String[] bigs = {"Hundred", "Thousand", "Million"};
	
	public static String getNumberToString(int num){
		
		if(num > 999999999)
			return "Connot convert such a long number!";
		
		if(num == 0)
			return "Zero";
		else if(num < 0)
			return "Negative" + getNumberToString(-1*num);
		
		int count  = 0;
		String str = "";
		
		while(num > 0){
			
			if(num%1000 != 0){
				
			if(count > 0)  
				str = numberToString100s(num%1000) +  bigs[count] + " " + str;
			else
				str = numberToString100s(num%1000) + " " + str;
			
			num /= 1000;
			count++;		
			}
		}
		return str;
	}

	private static String numberToString100s(int num) {
		
		String str = "";
		
		if(num >= 100){
			str += digits[num/100 -1] + " " + bigs[0] + " ";
			num %= 100;
		}
		
		if(num >= 11 && num <= 19)
			return str + teens[num - 11] + " ";
		else if (num == 10 || num >= 20){
			str += tens[num/10 -1] + " ";
			num %= 10;
		}
		
		if(num >= 1 && num <= 9)
			str += digits[num -1] + " ";
		
		return str;
	}
	
	public static void main(String[] args) {
		System.out.println(getNumberToString(12345));
	}
	
}
