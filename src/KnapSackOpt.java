import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class KnapSackOpt {
    // memoization and solution table
    static Integer[][] m;
    static boolean[][] s;
//    static Integer[] p = new Integer[6]; //profit
//    static Integer[] w = new Integer[6]; //weight
//    static Integer C; // item capacity
//
//    static int[] p = {0, 1, 5, 10, 2, 10, 8};
//    static int[] w = {0, 5, 1, 10, 5, 4, 10};

    static int[] p = {0,2,4,5,8};
    static int[] w = {0,2,3,4,5};
    static int C = 7;

    static Integer count = 0;

    public static void main(String[] args) {
        Random rand = new Random();
        // number of items to choose from
        int length = p.length;

//        for( int i=0; i<length; i++) {
//            C = rand.nextInt(15) + 1;
//            p[i] = rand.nextInt(50) + 1;
//            w[i] = rand.nextInt(10) + 1;
//        }
//         initialize m and s tables
        m = new Integer[length][C + 1];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < C + 1; j++) {
                m[i][j] = -1;
            }
        }
        s = new boolean[length][C + 1];
        int recursive = KSRecursive(length - 1, C);// Recursive version
        int interative = KSIterative(length - 1, C);// Recursive version
        System.out.println("Recursive Best profit is: " + recursive);
        System.out.println("Iterative Best profit is: " + interative);
//        System.out.println("The number of iterations are: " + count);
//        System.out.println("---------------Greedy-----------------");
//        KSGreedy();


        //_______________________________________________________________________________
        // print solution table
        // print columns

        System.out.print("    ");
        for (int i = 0; i < C + 1; i++) {
            System.out.print("C" + i + " |");
        }
        System.out.println();
        System.out.println("================================================");
        for (int i = 0; i < m.length; i++) {
            System.out.print("X" + i + "||");
            for (int j = 0; j < C + 1; j++) {
                if (s[i][j]) {
                    System.out.print(" T |");
                } else {
                    System.out.print(" F |");
                }
            }
            System.out.println();
        }

//
//        //_______________________________________________________________________________
//        // print profit table
//        // print columns
//
        System.out.print("    ");
        for (int i = 0; i < C + 1; i++) {
            System.out.print("C" + i + " |");
        }
        System.out.println();
        System.out.println("================================================");
        for (int i = 0; i < m.length; i++) {
            System.out.print("X" + i + "||");
            for (int j = 0; j < C + 1; j++) {
                System.out.print(" " + m[i][j] + "|");
            }
            System.out.println();
        }
//
//
//        //_______________________________________________________________________________
//        // Trace the solution to find the choices
//        // Data Structure to store the choices
        ArrayList<Integer> choices = new ArrayList<>();
        int item = p.length - 1;
        while (C > 0 && item > 0) {
            if (s[item][C]) {
                choices.add(item);
                C = C - w[item];
            }
            item--;
        }
        System.out.println("\n");
        for (Integer c : choices) {
            System.out.print(c + " ");
        }
        System.out.println();

    }

    public static int KSRecursive(int i, int j) {
        // i ---> current item
        // j ---> current capacity of the knapsack
        count++;
        // avoid exponential time
        if (m[i][j] != -1) return m[i][j];

        // Are we at the stopping condition?
        if ((i == 0) || j == 0) return 0;

        else if (w[i] > j) {
            //   count++;
            m[i][j] = KSRecursive(i - 1, j); // dont take current item
            //return m[i][j];
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

    public static int KSIterative(int x, int y) {
        // x is the current item's weight
        // y is the remaining capacity

        for (int i = 0; i < w.length; i++) {
            for (int j = 0; j <= C; j++) {
                count++; // check the # of iterations
                if (i == 0 || j == 0) {
                    m[i][j] = 0;
                } else
                    //dont pick the item
                    if (w[i] > j) {
                        m[i][j] = m[i - 1][j];
                        s[i][j] = false;
                    } else {
                        // pick the item
                        int dontPickItem = m[i - 1][j];
                        int pickItem = m[i - 1][j - w[i]] + p[i];// add the profit of the current item
                        // Picking the best choice that guarantees the most profit
                        if (pickItem > dontPickItem) {

                            m[i][j] = pickItem;
                            s[i][j] = true;
                        } else {
                            m[i][j] = dontPickItem;
                            s[i][j] = false;
                        }

                    }
            }
        }

        return m[p.length - 1][y];

    }


    public static int KSIterative(Integer[] p, Integer[] w, int C) {
        // x is the current item's weight
        // y is the remaining capacity
        int length = p.length;

        // initialize and fills m and s tables
        m = new Integer[length][C + 1];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < C + 1; j++) {
                m[i][j] = -1;
            }
        }
        s = new boolean[length][C + 1];
        for (int i = 0; i < w.length; i++) {
            for (int j = 0; j <= C; j++) {
                count++; // check the # of iterations
                if (i == 0 || j == 0) {
                    m[i][j] = 0;
                } else
                    //dont pick the item
                    if (w[i] > j) {
                        m[i][j] = m[i - 1][j];
                        s[i][j] = false;
                    } else {
                        // pick the item
                        int dontPickItem = m[i - 1][j];
                        int pickItem = m[i - 1][j - w[i]] + p[i];// add the profit of the current item
                        // Picking the best choice that guarantees the most profit
                        if (pickItem > dontPickItem) {

                            m[i][j] = pickItem;
                            s[i][j] = true;
                        } else {
                            m[i][j] = dontPickItem;
                            s[i][j] = false;
                        }

                    }
            }
        }
        System.out.println("Number of iterations: " + count);
        return m[p.length - 1][C];


    }
}


//    public static void KSGreedy() {
//
//
//    // Step 1: Calculate p/w ratios and sort in descending order
//    List<Integer> indices = new ArrayList<>(); // store the indicies
//    List<Double> profitByWeight = new ArrayList<>(); // store the p/w ratios
//        for(
//    int i = 0;
//    i<p.length;i++)
//
//    {
//        indices.add(i);
//        double ratio = (double) p[i] / w[i];
//        profitByWeight.add(ratio);
//        System.out.print("Profit:" + p[i] + " Weight: " + w[i] + " " + " profitByWeight: " + profitByWeight.get(i) + "\n");
//
//    }
//
//    // this uses the comparator class and comparing method to sort based on the p/w arraylist created
//    Comparator<Integer> sortBasedOnPByW = Comparator.comparing(profitByWeight::get).reversed();
//        indices.sort(sortBasedOnPByW);// now, sort the indices such that it can be accessed in descending order
//        System.out.println("Profit by Weight ratios"+profitByWeight);
//        System.out.println("indices"+ indices);
//
//    // Step 2: fill the bag based on the biggest ratio until capacity =0
//    ArrayList<Integer> knapsack = new ArrayList<Integer>();
//    int currentCapacity = C;
//
//            for(
//    int index :indices)
//
//    {
//        count++;
//        if (w[index] <= currentCapacity) {
//            knapsack.add(index); //collect the indicies of the weights to get the corresponding profit
//            currentCapacity = currentCapacity - w[index];
//        }
//        if (currentCapacity == 0) {
//            break;
//        }
//    }
//
//    // Step 3: Calculate maximum profit
//        System.out.println(knapsack);
//    int maxProfit = 0;
//        for(
//    Integer index :knapsack)
//
//    { // loop through to get indices then sum the corresponding profit
//        maxProfit = p[index] + maxProfit;
//
//    }
//
//    // Step 4: Print results
//        System.out.println("Items in Knapsack: "+knapsack);
//        System.out.println("Best profit is: "+maxProfit);
//        System.out.println("# of iterations for Greedy: "+count);
//}


