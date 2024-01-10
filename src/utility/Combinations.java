package utility;

import java.util.ArrayList;

/**
 * Class whose method nChooseK returns an ArrayList of all combinations of k elements from a set of n elements.
 * Each entry in the ArrayList has length k+1, and the first entry is always 0. //TODO: Change the algorithm so that it only returns the combinations of k elements.
 * 
 */
public class Combinations {
    private static ArrayList<int[]> combinations;
    private static int[] sol;
    private static int n;
    private static int k;

    /**
     * Returns an ArrayList of all combinations of k elements from a set of n elements.
     * The format of each entry in the ArrayList is as follows: [0, a1, a2, ..., ak], where a1, a2, ..., ak are the k elements.
     * @param n
     * @param k
     * @return
     */
    public static ArrayList<int[]> nChooseK(int n, int k){
        Combinations.n = n;
        Combinations.k = k;
        combinations = new ArrayList<int[]>();
        sol = new int[k+1];
        comb(1);
        return combinations;
    }

    /**
     * Recursive method that finds all combinations of k elements from a set of n elements.
     * @param p
     */
    private static void comb(int p){
        for(int i = sol[p-1] + 1; i <= n ; ++i)
        {
            sol[p] = i;
            if(p == k)
                combinations.add(sol.clone());
            else
                comb(p + 1);
        }
    }

    /**
     * Test method
     * @param args
     */
    public static void main(String[] args) {
        for(int[] a : nChooseK(6 - 2, 3)){
            for(int i : a)
                System.out.print(i + " ");
            System.out.println();
        }
    }
}