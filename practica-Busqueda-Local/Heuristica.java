
//package Bicing;

import aima.search.framework.HeuristicFunction;

public class Heuristica implements HeuristicFunction {
    @Override
    public double getHeuristicValue(Object o) {
        Estado van= (Estado) o;
        double beneficio=0.0;
        for(int i=0; i<van.getNumFurgonetas();++i){
            beneficio += van.getBicicletas(i) + van.getBicicletas2(i); 
        }

        return beneficio;
    }

}
