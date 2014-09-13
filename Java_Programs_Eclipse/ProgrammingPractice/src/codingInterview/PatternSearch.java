package codingInterview;

public class PatternSearch {

	static void naiveSearch(String text, String pat){
		char[] txtarr = text.toCharArray();
		char[] patarr = pat.toCharArray();
		
		if(txtarr.length < patarr.length){
			System.out.println("Patter cannot be found!");
			return;
		}else{
			
			for (int i =0; i<= (txtarr.length - patarr.length); i++){
				int j;
				for(j = 0; j < patarr.length; j++){
					if(txtarr[i+j] != patarr[j])
						break;
				}
				if(j == patarr.length)
					System.out.println("Pattern Found at index " + i);
			}
			
			
		}
			
		
		
			
	}
}
