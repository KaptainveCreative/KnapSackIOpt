import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class KnapsackGreedy {

    static int[] p = {0,1,5,10,2,10,8};
    static int[] w = {0,5,1,10,5,4,10};
    static int C = 300;
static int count =0;
    public static void main(String[] args) {

        // Step 1: Calculate p/w ratios and sort in descending order
        List<Integer> indices = new ArrayList<>(); // store the indicies
        List<Double> profitByWeight = new ArrayList<>(); // store the p/w ratios
        for (int i = 0; i < p.length; i++) {
            indices.add(i);
            double ratio = (double)p[i]/w[i];
            profitByWeight.add(ratio);
            System.out.print("Profit:" + p[i]+ " Weight: "+ w[i]+ " "+  " profitByWeight: "+profitByWeight.get(i)+ "\n");

        }

        // this uses the comparator class and comparing method to sort based on the p/w arraylist created
        Comparator<Integer> sortBasedOnPByW = Comparator.comparing(profitByWeight::get).reversed();
        indices.sort(sortBasedOnPByW);// now, sort the indices such that it can be accessed in descending order
        System.out.println(profitByWeight);
        System.out.println(indices);

        // Step 2: fill the bag based on the biggest ratio until capacity =0
            ArrayList<Integer> knapsack = new ArrayList<Integer>();
            int currentCapacity = C;

            for( int index : indices){
                count++;
                if (w[index] <= currentCapacity){
                    knapsack.add(index); //collect the indicies of the weights to get the corresponding profit
                    currentCapacity = currentCapacity - w[index];
                }
                if( currentCapacity ==0){
                    break;
                }
            }

        // Step 3: Calculate maximum profit
        System.out.println(knapsack);
        int maxProfit = 0;
        for (Integer index : knapsack) { // loop through to get indices then sum the corresponding profit
            maxProfit = p[index] + maxProfit;

        }

        // Step 4: Print results
        System.out.println("Items in Knapsack: " + knapsack);
        System.out.println("Best profit is: " + maxProfit);
        System.out.println("# of iterations: " +count);

   }

}
