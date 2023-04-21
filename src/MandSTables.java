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
    static int C =14;

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


}


