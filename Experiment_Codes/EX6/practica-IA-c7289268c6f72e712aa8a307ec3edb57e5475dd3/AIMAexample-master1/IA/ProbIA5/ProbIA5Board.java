package IA.ProbIA5;

/**
 * Created by bejar on 17/01/17.
 */
public class ProbIA5Board {
    /* Class independent from AIMA classes
       - It has to implement the state of the problem and its operators
     *

    /* State data structure
        vector with the parity of the coins (we can assume 0 = heads, 1 = tails
     */

    private int [] board;
    private static int [] solution;

    /* Constructor */
    public ProbIA5Board(int []init, int[] goal) {

        board = new int[init.length];
        solution = new int[init.length];

        for (int i = 0; i< init.length; i++) {
            board[i] = init[i];
            solution[i] = goal[i];
        }

    }

    /* vvvvv TO COMPLETE vvvvv */
    public void flip_it(int i){
        // flip the coins i and i + 1
        if(board[i]==1) board[i]=0;
        else board[i]=1;
        if(board[i+1]==1) board[i+1]=0;
        else board[i+1]=1;
    }

    /* Heuristic function */
    public double heuristic(){
        // compute the number of coins out of place respect to solution
        int sum = 0;
        for (int i = 0; i< solution.length; i++) {
            if(board[i]!=solution[i]) sum++;
        }
        return sum;
    }

     /* Goal test */
     public boolean is_goal(){
         boolean isSolution=true;
         for (int i=0; i<5;i++){
             if(board[i]!=solution[i])isSolution=false;
         }
         return isSolution;
     }
    public int getSize1(){
        return board.length;
    }

     /* auxiliary functions */

     // Some functions will be needed for creating a copy of the state

    /* ^^^^^ TO COMPLETE ^^^^^ */
    public ProbIA5Board copy(){
        int[] copiedBoardData = new int[board.length];

        for (int i=0; i<board.length; ++i) {
            copiedBoardData[i] = board[i];
        }
        ProbIA5Board copiedboard = new ProbIA5Board(copiedBoardData, solution);

        return copiedboard;
    }

}
