import aima.search.framework.HeuristicFunction;

public class BicingHeuristica implements HeuristicFunction  {
  
  public double getHeuristicValue(Object state) {

   BicingEstado board=(BicingEstado)state;
   double m=board.valorHeuristica();
   return m;
  }
  
}