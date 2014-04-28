package linkedHash;

public class HashMap {

	private final static int TABLE_SIZE = 10;
	
	LinkedHashEntry[] table;
	
	HashMap(){
		table = new LinkedHashEntry[TABLE_SIZE];
		
		for(int i = 0; i < TABLE_SIZE; i++)
			table[i] = null;
	}
	
	public void put(int key, int value){
		int hash = (key % TABLE_SIZE);
		if(table[hash] == null)
			table[hash] = new LinkedHashEntry(key, value);
		
		else{
			LinkedHashEntry entry = table[hash];
			while(entry.getNext() != null && entry.getKey() != key)
				entry = entry.getNext();
			
			if(entry.getKey() == key)
				entry.setValue(value);
			else
				entry.setNext(new LinkedHashEntry(key, value));
		}
		}
	
	
	public int get(int key){
		int hash = (key % TABLE_SIZE);
		if(table[hash] == null)
			return -1;
		else
		{
			LinkedHashEntry entry = table[hash];
			while (entry != null && entry.getKey() != key)
				entry = entry.getNext();
			
			if(entry == null)
				return -1;
			else
				return entry.getValue();
		}			
		}
		
		public void remove (int key){
			
			int hash = (key % TABLE_SIZE);
			
			if(table[hash] != null){
				LinkedHashEntry prevEntry = null;
				LinkedHashEntry currEntry = table[hash];
				
				while (currEntry.getNext() != null && currEntry.getKey() != key){
					prevEntry = currEntry;
					currEntry = currEntry.getNext();
				}
				
				if(currEntry.getKey() == key){
					if(prevEntry ==null)
					table[hash] = currEntry.getNext();
					else
						prevEntry.setNext(currEntry.getNext());
				}
			}
					
		}
		
		
	}


