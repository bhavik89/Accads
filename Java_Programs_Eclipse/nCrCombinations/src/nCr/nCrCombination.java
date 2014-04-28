package nCr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class nCrCombination
{
    private final static int LEN = 5;

    //for example: args = [Kennedy, Johnson, Nixon, Ford]
    public static void main(String[] args)
    { //too few data
        //List args = ["Kennedy", "Johnson", "Nixon", "Ford"];

        if (args.length < LEN)
            return;
        
        //collect combinations
        String[][] combinations = collectCombinations(args);

        //print result, one per line
        int count = 0;
        for(String[] comb : combinations)
        {
            System.out.println(Arrays.toString(comb));
            count++;
        }
        System.out.println(count);
    }

    //collects all combinations and returns the result array 
    private static String[][] collectCombinations(String[] list)
    {
        int n = list.length;
        
        java.util.ArrayList<String[]> combinations = new ArrayList<String[]>();

        //the last possible 'i' is list[n-LEN], so no need go further
        for(int i = 0; i <= n - LEN; ++i)
        {
            //the last possible 'j' is list[n-(LEN-1)]
            for(int j = i+1; j <= n - (LEN-1); ++j)
            {
                //the last possible 'k' is list[n-(LEN-2)]
                //n-(LEN-2)==n-(3-2)==n-1, its the last element in the list
                for(int k = j+1; k <= n - (LEN-2); ++k)
                {
                	for(int l = k+1; k <= n - (LEN-3); ++k)
                    {  
                		for(int m = l+1; k <= n - (LEN-4); ++k)
                        { 
                		combinations.add(new String[] {
                            list[i],
                            list[j],
                            list[k],
                            list[l],
                            list[m]});               		
                				 
                        }
                    }
                }               
            }
        }
        return combinations.toArray(new String[0][]);
    }
}
