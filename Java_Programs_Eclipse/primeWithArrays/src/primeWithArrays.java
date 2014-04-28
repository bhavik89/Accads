
public class primeWithArrays {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int n = 10;
		printPrimes(n);
	}

	private static void printPrimes(int n) {
		boolean primes[] = new boolean[n+1];
		int i;
		for (i=2; i<=n; i++){
			primes[i] = true;
		}
		
		for (int divisor = 2; divisor*divisor <=n; divisor++){
			if(primes[divisor]){
				for(i=2*divisor; i<=n; i=i+divisor){
					primes[i] = false;
					
				}
			}
		}
		
		for (i=2; i<=n; i++){
			if(primes[i]){
				System.out.print(" "+i);
			}
		}
	}

}
