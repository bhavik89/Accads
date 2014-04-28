package prime;

public class primenos {

	public static void main(String[] args) {

	    int n = 100; // the max number for test

	    // 
	    boolean[] sieve = new boolean[n + 1];
	    for (int i = 2; i <= n; i++) {
	        sieve[i] = true;
	    }
	    for (int i = 2; i <= n; i++) {
	        if (sieve[i] != false) {
	            for (int j = i; j * i <= n; j++) {
	                sieve[i * j] = false;
	            }
	        }
	    }

	    // Print prime numbers
	    for (int i = 0; i <= n; i++) {
	        if (sieve[i]) {
	            System.out.println(i);
	        }
	    }

	}
	
}
