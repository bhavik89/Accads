package codingInterview;

import org.apache.commons.math3.*;
import org.apache.commons.math3.complex.Complex;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.zip.InflaterInputStream;

public class DataFileInputStream {



	  public static void main(String[] args) throws Exception {
	    FileInputStream fin = new FileInputStream("C:/fft2.dat");
	    DataInputStream din = new DataInputStream(new FileInputStream("C:\\fft2.dat"));
	    int num = 0;
	    int frame = 0;
	    ArrayList<Double> frames = new ArrayList<>();
	    
	    while(true){
	    	try{ 	
	    	
	    	Float rl = din.readFloat();
	    	Float img = din.readFloat();
	    	
	    	rl = (Float.isNaN(rl)) ? 0 : rl; 
	    	
	    	img = (Float.isNaN(img)) ? 0 : img; 
	    	
	    	Complex c = new Complex(rl, img);
	    		
	    	frames.add(c.abs());	    	
	    	num++;
	    	frame++;
	    	
	    	if(frame == 255){
	    		frame = 0;
	    		System.out.println(frames);
	    		frames.clear();
	    	}
	    		
	    	
	    	
	    }catch(EOFException e){
	    	din.close();
	    	break;
	    }
	    
	  }
	    System.out.println(num);
	}
}


