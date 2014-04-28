import java.util.Random;
import java.util.Scanner;

public class FiveCardDraw {
	
	/**Random number generator for selecting a random card 
	 * and randomly assigning cards their colors
	**/
	private static final Random RANDOM = new Random();
	
	/* Winning percentage for not switching the cards */
	private static double winByStaying = 0;
	
	/*Winning percentage for switching the cards*/
	private static double winBySwitching = 0;
	
	/* main method */	
	public static void main(String[] args){
		
	    Scanner input = new Scanner(System.in);
		
		/*Initialize Number of rounds to simulate*/		
		String rounds;
		
		/*Initialize total number of cards in the game*/
		int cards = 5;
		
		System.out.println("Enter the number of rounds you want to simulate: ");
		rounds = input.nextLine();	//Scan for user input for number of rounds to be simulated
		
		int num_rounds = 0; // Integer declaration for number of rounds to be simulated
		
		while(!(rounds.equalsIgnoreCase("quit"))) //Simulate until user quits by typing "quit"
		{
		try{
			num_rounds = Integer.parseInt(rounds); //get integers from the user input
			
			if(!(num_rounds < 0 && num_rounds > Integer.MAX_VALUE)){  //validate for the valid rage for input 
				
				/*Call run method passing them total number of cards and number of rounds to simulate*/
				run(num_rounds, cards);
				System.out.println("Enter number of rouds to simulate again or type \"Quit\" to exit"); //get input for next round
				}
			else
				System.out.println("Enter Valid Number or \"Quit\" to exit"); // ask for the valid input
			
		}
		catch (NumberFormatException nfe){ //catch exception if the input is not a number
			System.out.println("Please enter valid number for Number of rounds to be simulated or \"Quit\" to exit"); // ask for the valid input		
		}	
		
		Scanner nextinput = new Scanner(System.in); //input scanner for next input
		rounds = nextinput.nextLine();		
		}
		
		System.out.println("Thank you!!"); //end of program
		System.out.println(Integer.MAX_VALUE);
		input.close();		
	}

	
   /** The run function: takes number of rounds and number of cards as a argument
	 * This loops for the number of rounds given by user and determines 
	 * whether staying or switching the cards results in a correct card to be chosen. 
	 * Here we randomly choose a card from given number of cards and 
	 * also randomly assign a black card from then given card. 
	 * We simulate for given number of rounds and record the winning percentage for
	 * winning by either staying with the card or switching the card for each round.
	 * At the end of all the rounds, the variables winByStaying and winBySwitching 
	 * are updated giving out the percentage of wins by staying and switching.
	 **/
	private static void run(int rounds, int cards) {
		
		if ( cards < 3 ){ //Throw error if number of cards are less than 3 (this cannot be a legitimate problem)
			throw new IllegalArgumentException("Error: Number of cards should not be less than 3");
		}
		
		int winByStayCount = 0; 	//Counter for win by staying 
		int winByChangeCount = 0;	//Counter for win by switching
		
		for ( int i = 0; i < rounds; i++ ){	//Loop for simulating for total number of rounds 
			
			int initialChooseCard = RANDOM.nextInt(cards); //choose a card at random
			int blackCardPosition = RANDOM.nextInt(cards); //choose a random place where the black card will be.
			
			if ( blackCardPosition != initialChooseCard ){ //The chosen card is not black - 
				winByChangeCount++;	// You'll win, if you switch since only two cards are there on the table
			}
			else
				winByStayCount++;	//You'll win if you stay			
		}//end for loop (all rounds simulated)
		
		winByStaying = Math.round((winByStayCount/(double)rounds) * 100.00);		//Percentage of winning by staying with initial card chosen
		winBySwitching = Math.round((winByChangeCount/(double)rounds) * 100.00);	//Percentage of winning by switching the card	
		
		/*Print the results*/
		System.out.println("Simulated the game for " + rounds + " rounds");
		System.out.println("Winning percentage by staying with the initially chosen card: " + winByStaying + "%");
		System.out.println("Winning percentage by swtching the cards: " + winBySwitching + "%");
	}
}
