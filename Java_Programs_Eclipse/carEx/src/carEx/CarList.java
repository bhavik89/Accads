package carEx;

import java.util.*;

public class CarList {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Car car1 = new Car("honda", "red");
		Car car2 = new Car("Maruti", "white");
		Car car3 = new Car("City", "black");
		Car car4 = new Car("City", "blue");
		Car car5 = new Car("City", "black");
		Car car6 = new Car("City", "blue");
		Car car7 = new Car("City", "black");
		Car car8 = new Car("City", "blue");
		Car[] carArr = {car1, car2, car3, car4, car5, car6, car7, car8};
		
		List<Car> carList= new ArrayList<Car>();
		
		for(Car c: carArr){
			carList.add(c);
		}
		
		Iterator<Car> it = carList.iterator();
		while(it.hasNext()){
			Car c = it.next();
			c.drive();
			System.out.println(c.getDistance());
		}
		
		System.out.println();
		List<Car> noBlues = removeBlues(carList);
		Iterator<Car> it2 = noBlues.iterator();
		while (it2.hasNext()){
			System.out.println(it2.next().getColor());
		}
		
		System.out.println();
		System.out.println(factorial(4));
		
		System.out.println();
		System.out.println(isPalindrome("madam"));
		System.out.println(isPalindrome("live evil"));
		
	}
	
	public static List<Car> removeBlues(List<Car> listCars){
		Iterator<Car> it3 = listCars.iterator();
		
		while(it3.hasNext()){
			if(it3.next().getColor() == "blue")
				it3.remove();			
		}			
		return listCars;	
	}
	
	public static int factorial(int num){
		int result;
		if(num <= 0){
				System.out.println("Factorial does not exists");
				return 0;
		}
		
		if(num ==1)
			return 1;
		else{
			result = num * factorial(num - 1);
			return result;
		}
}
	
	public static boolean isPalindrome(String word){
		char[] wordArr = word.toCharArray();
		int start = 0;
		int end = word.length() - 1;
		
		for(int i = 0; i <= word.length()/2; i++)
		{
			if(wordArr[start] != wordArr[end])
				return false;
			else{
				start += 1;
				end -= 1;						
			}
				
		}
		return true;
	}
}