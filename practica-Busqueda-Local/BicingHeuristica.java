import aima.search.framework.HeuristicFunction;

public class BicingHeuristica implements HeuristicFunction  {
  
  public double getHeuristicValue(Object state) {

   Estado board=(Estado)state;
   //System.out.println("Heuristica: " + board.valorHeuristica());
    double m=board.valorHeuristica();
   return m;
  }
  
}