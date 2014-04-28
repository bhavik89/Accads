package hashTable;

public class HashEntry {

    public int key;
    public int value;

    HashEntry(int key, int value) {
          this.key = key;
          this.value = value;
    }     

    public int getKey() {
          return key;
    }

    public int getValue() {
          return value;
    }

}

