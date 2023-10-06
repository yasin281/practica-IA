package IA.ProbIA5;

import aima.search.framework.SuccessorFunction;
import aima.search.framework.Successor;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bejar on 17/01/17
 */
public class ProbIA5SuccesorFunction implements SuccessorFunction{

    public List getSuccessors(Object state){
        ArrayList retval = new ArrayList();
        ProbIA5Board board = (ProbIA5Board) state;


        // Some code here
        // (flip all the consecutive pairs of coins and generate new states
        // Add the states to retval as Succesor("flip i j", new_state)
        // new_state has to be a copy of state
        for(int i=0; i<board.getSize1()-1;++i){
            ProbIA5Board new_state;
            new_state=board.copy();
            new_state.flip_it(i);

            String s = i + " " + (i + 1);
            String action= "flip " +s;
            Successor successor = new Successor(action,new_state);
            retval.add(successor);
        }


        return retval;

    }

}
