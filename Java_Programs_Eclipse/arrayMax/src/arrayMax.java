
public class arrayMax {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int data[] = {4,6,-5,5,8};

		int largest=data[0];
		for(int x=0; x<data.length; x++){
		if(data[x]>largest){
		largest = data[x];
		}
		}

		System.out.println("Largest no is: "+largest);
		
		String s = "hello world!!";
		String s1 = s.replaceAll("[aeiou]", "");
		System.out.println(s1);

		
		
		}
		
//	public static void vowelRemove(String test) {
//	       int len = test.length();
//	       String vowel;
//	       if (test.length() > 0) {
//	           for (int i = 0; i < len; i++) {
//	               vowel = test.substring(i, 1);
//	               test = test.replaceAll(vowel, "");
//	           }
//	       }
//	       System.out.println(test);
//	    }	
	
	
	
	}



