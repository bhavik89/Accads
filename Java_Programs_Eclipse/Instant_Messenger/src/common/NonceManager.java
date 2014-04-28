package src.common;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Random;

/* Manages the nonces for both server and client */
public class NonceManager {
	private static HashSet<Nonce> nonces = new HashSet<Nonce>();

	public static long generateNonce(){
		boolean isPresent = false;
		Nonce nonce = null;
		while(!isPresent) 
		{
			nonce = new Nonce();
			isPresent = nonces.add(nonce);
		}
		return nonce.getNonce(); 
	}
	
	public static boolean verifyNonce(long nonce){
		if(nonces != null){
			for (Nonce storedNonce : nonces) {
				if(storedNonce.getNonce( ) == nonce){
					nonces.remove(storedNonce);
					return true;
				}
			}
		}
		
		return false;
	}
	
	public static class Nonce {
        private static final Random RND = new SecureRandom();
        private long nonce;

        public Nonce() {
            nonce = RND.nextLong();
        }     

        public long getNonce() {
        	return nonce;
        }
}
}
