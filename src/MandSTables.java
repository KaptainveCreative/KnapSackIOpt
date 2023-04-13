import java.util.ArrayList;

public class MandSTables {

    // memoization and solution table
    static Integer [][] m;
    static boolean [][] s;
//    static Integer[] p = new Integer[10]; //profit
//    static Integer[] w = new Integer[10]; //weight
//    static Integer C; // item capacity

    static int[] p = {0,1,5,10,2,10,8};
    static int[] w = {0,5,1,10,5,4,10};
    static int C =300;

    static Integer count=0;
    public static void main(String[] args) {


        int length = p.length;

    m = new Integer[length][C+1];
            for(int i=0; i< length; i++){
        for (int j=0; j < C+1; j++) {
            m[i][j] = -1;
        }
    }
            s = new boolean[length][C+1];
           int recursive = KSRecursive(length-1,C);// Recursive version
           int interative = KSIterative(length-1,C);// Recursive version

        System.out.println("Recursive Best profit is: " + recursive);
        System.out.println("Iterative Best profit is: " + interative );
        System.out.println("The number of iterations are: " + count);
    //_______________________________________________________________________________
    // print solution table
    // print columns

        System.out.print("    ");
        for (int i=1; i<C+1; i++) {
        System.out.print("C"+i+" |");
    }
        System.out.println();
        System.out.println("================================================");
        for (int i=0; i<m.length; i++) {
        System.out.print("X"+i+"||");
        for (int j=0; j<C+1; j++) {
            if (s[i][j]) {
                System.out.print(" T |");
            } else {
                System.out.print(" F |");
            }
        }
        System.out.println();
    }


    //_______________________________________________________________________________
    // print profit table
    // print columns

        System.out.print("    ");
        for (int i=1; i<C+1; i++) {
        System.out.print("C"+i+" |");
    }
        System.out.println();
        System.out.println("================================================");
        for (int i=0; i<m.length; i++) {
        System.out.print("X"+i+"||");
        for (int j=0; j<C+1; j++) {
            System.out.print(" "+m[i][j]+"|");
        }
        System.out.println();
    }



    //_______________________________________________________________________________
    // Trace the solution to find the choices
    // Data Structure to store the choices
    ArrayList<Integer> choices = new ArrayList<>();
    int item = p.length-1;
        while (C > 0 && item>0) {
        if (s[item][C]) {
            choices.add(item);
            C = C - w[item];
        }
        item--;
    }
        System.out.println("\n");
        for (Integer c : choices) {
        System.out.print(c+" ");
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
        if ((i==0 ) || j==0 ) return 0;

        else if (w[i] > j){
            //   count++;
            m[i][j] = KSRecursive(i-1, j); // dont take current item
            return m[i][j];
        }
        else {
            // Determine the best choice
            //count++;
            int dontPickItem = KSRecursive(i-1, j);
            int pickItem = KSRecursive(i-1,j-w[i]) + p[i];// add the profit of the current item
            // Store the best profit in the memoization table
            m[i][j]= Math.max(dontPickItem, pickItem);

            // Store the choice in the solution table
            if ( pickItem > dontPickItem){

                m[i][j] = pickItem;
                s[i][j] = true;
            }
            else {
                m[i][j] = dontPickItem;
            }
        }
//        System.out.println("Best profit is: " + m[i][j]);
//        System.out.println("The number of iterations are: " + count);
        return m[i][j]; // return the best profit

    }

    public static int KSIterative( int x, int y){
        // x is the current weight
        // y is the remaining capacity


        for (int i = 0; i < p.length; i++) {
            for (int j = 0; j <= C; j++) {
                count++; // check the # of iterations
                if( i==0 || j == 0) {
                    m[i][j] =0;
                }
                else
                //dont pick the item
                if(w[i] > j){
                    m[i][j] = m[i-1][j];
                    s[i][j] = false;
                }
                else{
                    // pick the item
                    int dontPickItem = m[i-1][j];
                    int pickItem = m[i-1][j-w[i]] + p[i];// add the profit of the current item
                    // Picking the best choice that guarantees the most profit
                    if ( pickItem > dontPickItem){

                        m[i][j] = pickItem;
                        s[i][j] = true;
                    }
                    else {
                        m[i][j] = dontPickItem;
                        s[i][j] = false;
                    }

                }
            }
        }

        return m[p.length-1][y];

    }

}


