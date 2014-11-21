package codingInterview;

import java.util.ArrayList;

public class StringManipulate {
	
	public static void main(String[] args) {
		System.out.println(restoreIpAddresses("25525511135"));
		//System.out.println(numDecodings("12"));
		String str = "BBABCBCAB";
		System.out.println(longestPalindrome(0, str.length() -1 , str.toCharArray()));
	}
	
	public static ArrayList<String> restoreIpAddresses(String s) {
        // Start typing your Java solution below
        // DO NOT write main() function
        ArrayList<String> res = new ArrayList<String>();
        
        if(s.length()>12) 
        	return res;
        
        dfs(res,"",s,0);
        
        return res;
    }
    public static void dfs(ArrayList<String> res,String buff, String s,int count){
    	
    	//System.out.println(buff+ " - "+ isValid(s));
        
    	if(count == 3 && isValid(s)){
            res.add(buff+s);
            return;
        }
        
        for(int i =1;i<4 && i<s.length();i++){
        	        	
        	String substr = s.substring(0,i);
            
            if(isValid(substr))
                dfs(res,buff+substr+".",s.substring(i),count+1);
        }
  
    }
    public static boolean isValid(String s){
        if(s.charAt(0)=='0')
        	return s.equals("0");
        
        long num = Long.parseLong(s);
        return num>=0 && num<=255;
    }
    
    public static int longestPalindrome(int i, int j, char[] chrs){
    	
    	if(i == j)
    		return 1;
    	
    	else if((j == i+1) && (chrs[i] != chrs[j]))
    		return 1;
    	
    	else if ((j == i+1) && chrs[i] == chrs[j])
    		return 2;
    	
    	else if (chrs[i] == chrs[j])
    		return longestPalindrome(i+1, j-1, chrs) + 2;
    	else
    		return Math.max(longestPalindrome(i+1, j, chrs), longestPalindrome(i, j-1, chrs));
    	
    }
    
    


	}