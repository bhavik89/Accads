import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SortDatesPriority2 implements Runnable {

	// Static map to record the entries
	static Map<Long, String> datesMap = new HashMap<Long, String>();

	// An dynamic array to record the dates and later on sort it for the display
	static ArrayList<Long> datesList;

	// Name of file to be parsed
	public final static String FILENAME = "input.txt";

	private String thId;
	private Thread thread;

	// Constructor
	public SortDatesPriority2(String threadID) {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Method to display results
	// Basically it iterates over each entry of the tree map and prints the
	// result
	private void displaySortedResults() {

		System.out.println("\nThread ID  Person ID  Month     Day     Year");

		for (Long date : datesList) {

			String val = datesMap.get(date);
			String dateString = date.toString();
			String year = dateString.substring(0, 4);
			String month = dateString.substring(4, 6).replaceFirst("^0+", "");
			String day = dateString.substring(6, 8).replaceFirst("^0+", "");

			System.out.println(thId + ":\t" + val + "\t" + month + "\t" + day
					+ "\t" + year);

		}

	}

	// Run method for thread
	@Override
	public void run() {
		displaySortedResults();
	}

	// Sorts the given array
	// uses Quick sort algorithm : Time complexity O(nLog(n))
	public static void sortArray(ArrayList<Long> arr, int start, int end) {

		if (arr == null || arr.size() == 0 || start >= end)
			return;

		// choose pivot element for partitioning
		int mid = start + (end - start) / 2;
		Long pivotEle = arr.get(mid);

		// partition array such that
		// the left of array is < pivot &
		// the right of array is > pivot
		int i = start, j = end;
		while (i <= j) {
			while (arr.get(i) < pivotEle)
				i++;

			while (arr.get(j) > pivotEle)
				j--;

			if (i <= j) {
				Long temp = arr.get(i);

				arr.set(i, arr.get(j));
				arr.set(j, temp);
				i++;
				j--;
			}
		}

		// sort both the partitions
		if (start < j)
			sortArray(arr, start, j);

		if (end > i)
			sortArray(arr, i, end);
	}

	// Method to parse input file and put the contents in the map and arrayList
	// Converts the date from file into long string by concatenating it
	// and then converting it into Long for later on sorting the arrayList
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

					datesMap.put(Long.parseLong(date), strArr[0]);
					datesList.add(Long.parseLong(date));

				}
			}
			inputReader.close();

		} catch (FileNotFoundException e) {
			System.out.println("Cannot locate file!");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// Main method
	public static void main(String[] args) {

		// An array list to keep the dates used for sorting after map is
		// populated
		datesList = new ArrayList<Long>();

		populateMap();

		// Sort the arrayList for display in sorted order
		sortArray(datesList, 0, datesList.size() - 1);

		SortDatesPriority2 s1 = new SortDatesPriority2("Thread 1");
		s1.start();

		SortDatesPriority2 s2 = new SortDatesPriority2("Thread 2");
		s2.start();
	}

}
