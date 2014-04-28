package hashTable;

public class HashMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		HashMap h1 = new HashMap();
		
		h1.put(1,10);
		h1.put(2,20);
		h1.put(3,30);
//		h1.put(128,100);
//		h1.put(8,78);
//		h1.put(12,89);
//		h1.put(11, 9);
//		h1.put(10,0);
//		int get = h1.get(128);
		
	//	System.out.println(+get);
		
		for(int i=0; i< h1.table.length ; i++){
			Object val = h1.table[i];
			if(val == null)
				System.out.println("NULL");
			else
			System.out.println(h1.table[i].getValue());
		}
		
	}

}
