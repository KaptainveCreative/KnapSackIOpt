import java.beans.JavaBean;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class Main {
    static Integer[][] m;
    static boolean[][] s;
    static Integer[] p = new Integer[10]; //profit
    static Integer[] w = new Integer[10]; //weight

    static int Gcounter = 0;
    static int Rcounter = 0;
    /**
     * start time in nanoseconds
     */
    private static long startNS;
    /**
     * end time in nanoseconds
     */
    private static long endNS;

    public static void main(String[] args) throws IOException {
        Random rand = new Random(); //Random generator
        FileWriter writer = new FileWriter("Results.csv");
        writer.append("Algorithm ,Capacity ,# of Iterations ,MaxValue, Time(in NS), Experiment Weight Range\n");

        int[] capacity = {50, 100, 1000, 5000, 10000, 100000,1000000};
        for (int C : capacity) {
            int length = p.length;

            // initialize and fills m and s tables
            m = new Integer[length][C + 1];
            for (int i = 0; i < length; i++) {
                for (int j = 0; j < C + 1; j++) {
                    m[i][j] = -1;
                }
            }
            s = new boolean[length][C + 1];
            ArrayList<Integer> avgIterative = new ArrayList<Integer>();
            // initialize all elements of w to 0
            Arrays.fill(w, 0);
            Arrays.fill(p, 0);
            int maxValIterative = 0;
            long timeIterative = 0;
            for (int i = 0; i < p.length; i++) {
                p[i] = rand.nextInt(50) + 1;
//                w[i] = rand.nextInt((int) (C*.25)) + (int) (C*.75);
                w[i] = rand.nextInt(C-1) + 1;  //generate numbers from 1 - C-1
//                w[i] = rand.nextInt((int) (C * .25)) + 1;  //generate numbers from 1 - .1*C
//                  w[i] = rand.nextInt( (C -1)) +(int) (.50* C);  //generate numbers from 1 - .1*C
//                w[i] = rand.nextInt((int) (.1* C)) +1;  //generate numbers from 1 - .1*C
//                w[i] = rand.nextInt((C - 1)) + (int) (0.5 * C);  //generate numbers from 1 - .1*C
//                long startIterative = System.nanoTime();
                avgIterative.get(i);
                int avg = avgIterative.get(i)/ avgIterative.size();
                maxValIterative = KnapSackOpt.KSIterative(p, w, C); // save the results to a file

//                long endIterative = System.nanoTime();
//                timeIterative = endIterative - startIterative;

            }

////// define start time for iterative
//            long startIterative = System.nanoTime();
//            int maxValIterative = KnapSackOpt.KSIterative(p, w, C); // save the results to a file
//            long endIterative = System.nanoTime();
//            long timeIterative = endIterative - startIterative;
//
//// define start time for recursive
//            long startRecursive = System.nanoTime();
//            int maxValRecursive = KSRecursive(p.length - 1, C); // save the results to a file
//            long endRecursive = System.nanoTime();
//            long timeRecursive = endRecursive - startRecursive;
//
//// define start time for greedy
//            long startGreedy = System.nanoTime();
//            int maxValGreedy = KSGreedy(C); // save the results to a file
//            long endGreedy = System.nanoTime();
//            long timeGreedy = endGreedy - startGreedy;


            // record the time. get total ops count.
            endNS = System.nanoTime();
            long timeNS = endNS - startNS;
            writer.append("Iterative,  " + C + ",  " + KnapSackOpt.count + ",  " + maxValIterative + ", " + (long) (timeIterative / 1000 / 1000) + ",  " + "( C - 1)) +(int)(0.5 *C)" + "\n");
//            writer.append("Recursive,  " + C + ",  " + Rcounter + ",  "  + maxValRecursive + ", "+(long)(timeRecursive/1000/1000)+",  " +"( C - 1)) +(int)(0.5 *C)"+"\n");
//            writer.append("Greedy,  " + C + ",  " + Gcounter + ",  "  + maxValGreedy + ", "+(long)(timeGreedy/1000/1000)+",  " +"( C - 1)) +(int)(0.5 *C)"+"\n");


        }
        writer.flush();
        writer.close();
        System.out.println("Results saved to Results.csv");
    }

    public static int KSRecursive(int i, int j) {
        // i ---> current item
        // j ---> current capacity of the knapsack
        Rcounter++;
        // avoid exponential time
        if (m[i][j] != -1) return m[i][j];

        // Are we at the stopping condition?
        if ((i == 0) || j == 0) return 0;

        else if (w[i] > j) {
            //   count++;
            m[i][j] = KSRecursive(i - 1, j); // dont take current item
            return m[i][j];
        } else {
            // Determine the best choice
            //count++;
            int dontPickItem = KSRecursive(i - 1, j);
            int pickItem = KSRecursive(i - 1, j - w[i]) + p[i];// add the profit of the current item
            // Store the best profit in the memoization table
            m[i][j] = Math.max(dontPickItem, pickItem);

            // Store the choice in the solution table
            if (pickItem > dontPickItem) {

                m[i][j] = pickItem;
                s[i][j] = true;
            } else {
                m[i][j] = dontPickItem;
            }
        }

        return m[i][j]; // return the best profit
    }


    public static int KSGreedy(int C) {


    // Step 1: Calculate p/w ratios and sort in descending order
    List<Integer> indices = new ArrayList<>(); // store the indicies
    List<Double> profitByWeight = new ArrayList<>(); // store the p/w ratios
        for(int i = 0; i<p.length;i++)

    {
        indices.add(i);
        double ratio = (double) p[i] / w[i];
        profitByWeight.add(ratio);
        System.out.print("Profit:" + p[i] + " Weight: " + w[i] + " " + " profitByWeight: " + profitByWeight.get(i) + "\n");

    }

    // this uses the comparator class and comparing method to sort based on the p/w arraylist created
    Comparator<Integer> sortBasedOnPByW = Comparator.comparing(profitByWeight::get).reversed();
        indices.sort(sortBasedOnPByW);// now, sort the indices such that it can be accessed in descending order
        System.out.println("Profit by Weight ratios"+profitByWeight);
        System.out.println("indices"+ indices);

    // Step 2: fill the bag based on the biggest ratio until capacity =0
    ArrayList<Integer> knapsack = new ArrayList<Integer>();
    int currentCapacity = C;

            for(int index :indices) {
        Gcounter++;
        if (w[index] <= currentCapacity) {
            knapsack.add(index); //collect the indicies of the weights to get the corresponding profit
            currentCapacity = currentCapacity - w[index];
        }
        if (currentCapacity == 0) {
            break;
        }
    }

    // Step 3: Calculate maximum profit
        System.out.println(knapsack);
    int maxProfit = 0;
        for(
    Integer index :knapsack)

    { // loop through to get indices then sum the corresponding profit
        maxProfit = p[index] + maxProfit;

    }

    // Step 4: Print results
        System.out.println("Items in Knapsack: "+knapsack);
        System.out.println("Best profit is: "+maxProfit);
        System.out.println("# of iterations for Greedy: "+Gcounter);
        return maxProfit;
}
}

