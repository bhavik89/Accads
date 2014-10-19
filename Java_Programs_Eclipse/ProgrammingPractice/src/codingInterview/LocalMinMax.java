package codingInterview;

public class LocalMinMax {
	
	static void localMinMax(int[] A){
		
		int maxMin = 0;
		if(A.length <= 1){
			System.out.println(maxMin);
			return;
		}
			
		
		int p = 0, q = 0;
		
		
		boolean gotSub = false;
			
		int i=0;
		
		while(i< A.length){
			
		 if(i+1 <=  A.length - 1){	
			if(A[i] == A[i+1] && !gotSub){
				p = i;
				q = i+1;
				gotSub = true;
			}else if (A[i] == A[i+1] && gotSub){
				q++;
			}else if(gotSub){
				gotSub = false;
				maxMin += getLocalMinMax(p, q, A);				
			}else{
				p = q = i;				
				maxMin += getLocalMinMax(p, q, A);				
			}
		 }else{
			 if(p!= 0)
				 maxMin += 1;
		 }
			i++;
		}
		
		System.out.println(maxMin);
	}
	
	static int getLocalMinMax(int p, int q, int[] a){
		int l = a.length;
		int localMinMax = 0;
		
		if(p > 0 && q < (l -1)){
			if((a[p] > a[p-1] && a[q] > a[q+1]) || (a[p] < a[p-1] && a[q] < a[q+1]))
				localMinMax++;			
		}else if((p == 0 && q < (l-1)) || (p > 0 && q == (l-1))){
				localMinMax++;
		}	
		return localMinMax;
	}
	
	public static void main(String[] args) {
		int[] A = {2,2,3,4,3,5,2,2,1,1,2,2};
		int[] B = {2,2,2,2,2,22};
		localMinMax(A);
		localMinMax(B);
	}

}
