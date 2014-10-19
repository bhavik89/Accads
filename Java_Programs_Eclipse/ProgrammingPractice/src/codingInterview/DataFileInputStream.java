package codingInterview;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.util.zip.InflaterInputStream;

public class DataFileInputStream {



	  public static void main(String[] args) throws Exception {
	    FileInputStream fin = new FileInputStream("C:/fft2.dat");
	    DataInputStream din = new DataInputStream(new FileInputStream("C:\\fft2.dat"));
	    int num = 0;
	    
	    
	    while(true){
	    try{	
	    	float f = din.readFloat();
	    	System.out.println("float : " + f);
	    	num++;
	    	
	    }catch(EOFException e){
	    	din.close();
	    	break;
	    }
	    
	  }
	    System.out.println(num);
	}
}


