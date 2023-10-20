
//package Bicing;

import aima.search.framework.HeuristicFunction;

public class Heuristica implements HeuristicFunction {
    @Override
    public double getHeuristicValue(Object o) {
        Estado van= (Estado) o;
        return van.valorHeuristica();
    }

}
