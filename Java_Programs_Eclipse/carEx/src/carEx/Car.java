package carEx;

import java.util.*;

public class Car {
	private String carname;
	private String color;
	private int distanceTravelled;
	
	Car(String name, String color){
		this.carname = name;
		this.color = color;
		this.distanceTravelled = 0;
	}
	
	public void drive(){
		this.distanceTravelled += 1;
	}

	public int getDistance() {
		return this.distanceTravelled;
	}
	
	public String getColor() {
		return this.color;
	}
	
	

	
}
