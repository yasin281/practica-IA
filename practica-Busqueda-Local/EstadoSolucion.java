//package Bicing;

import aima.search.framework.GoalTest;

public class EstadoSolucion implements GoalTest {
    @Override
    
        public boolean isGoalState(Object state){

        return((Estado) state).is_goal();
    }
    
}
