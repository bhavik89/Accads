import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class SortDates implements Runnable {

	// Static TreeMap to keep the dates sorted
	static Map<Long, String> sortedDates = new TreeMap<Long, String>();

	// Class variables for thread-id and thread
	private String thId;
	private Thread thread;
	
	// Name of file to be parsed
	public final static String FILENAME = "input.txt";

	// Constructor
	public SortDates(String threadID) {
		thId = threadID;
	}

	// Start method to create thread and start running it
	// Each thread is synchronized, so that other thread has to wait
	// until the first is over
	public void start() {
		thread = new Thread(this, thId);
		thread.start();
		try {
			thread.join();
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	// Method to display results
	// Basically it iterates over each entry of the tree map and prints the
	// result
	private void displaySortedResults() {

		System.out.println("\nThread ID  Person ID  Month     Day     Year");

		for (Entry<Long, String> entry : sortedDates.entrySet()) {

			Long key = entry.getKey();
			String val = entry.getValue();
			String date = key.toString();

			String year = date.substring(0, 4);
			String month = date.substring(4, 6).replaceFirst("^0+", "");
			String day = date.substring(6, 8).replaceFirst("^0+", "");

			System.out.println(thId + ":\t" + val + "\t" + month + "\t" + day
					+ "\t" + year);
		}
	}

	// Run method of the thread
	@Override
	public void run() {
		displaySortedResults();
	}
	
	//Method to parse input file and put the contents in the map
	//Converts the date from file into long string by concatenating it
	//and then converting it into Long for putting in sorted order
	private static void populateMap() {
		try {
			FileReader inputReader = new FileReader(FILENAME);

			BufferedReader br = new BufferedReader(inputReader);
			String s;

			while ((s = br.readLine()) != null) {
				if (s.split("").length > 1) {
					String[] strArr = s.split("[ \t]+");

					String year = strArr[strArr.length - 1];
					String day = strArr[strArr.length - 2];
					String month = strArr[strArr.length - 3];

					if (day.length() == 1)
						day = "0" + day;
					if (month.length() == 1)
						month = "0" + month;

					String date = year + month + day;

					sortedDates.put(Long.parseLong(date), strArr[0]);

				}
			}
			inputReader.close();

		} catch (FileNotFoundException e) {
			System.out.println("Cannot locate file!");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}
	
	//Main method
	public static void main(String[] args) {

		populateMap();

		SortDates sd1 = new SortDates("Thread 1");
		sd1.start();

		SortDates sd2 = new SortDates("Thread 2");
		sd2.start();
	}

}
