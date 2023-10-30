import IA.Bicing.Estaciones;
import IA.Bicing.Estacion;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;    
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;
import aima.search.framework.GraphSearch;

//import para formulas matematicas
import static java.lang.Math.abs;

import java.util.Scanner;
public class BicingExperimento5 {
    public static void main(String[] args) {
        // Estaciones(int nest, int nbic, int dem, int seed) Constructor
        // cogemos los datos de la entrada
        int nest = 25;
        int nbic = 1250;
        int nfurg = 5;
        int dem = 0;
        int iniTrivial = 1;
        int heuristica = 0;
        int hillClimbing = 1;

        int seed = 1234;

        int seeds[] = {0,3756, 583, 84787, 263, 7085, 25, 2884, 9683, 25654, 4983};
        
        for(int i = 0; i < seeds.length; ++i){
            seed = seeds[i];
            System.out.println("Semilla: " + seed);

            for(int j = 0; j < 2; ++j){
                if(j == 0){
                  heuristica = 0;
                  System.out.println("Heuristica 1: sin coste");
                }
                else{
                  heuristica = 1;
                  System.out.println("Heuristica 2: coste distancia");
                }
                for(int k = 0; k < 2; ++k){
                  if(k == 0) hillClimbing = 1;
                  else hillClimbing = 0;
                  BicingExperimento(nest, nbic, nfurg, dem, seed, iniTrivial, heuristica, hillClimbing);
                  System.out.println();
                }
                System.out.println();
            }
            System.out.println();
        }
    }
    private static void BicingExperimento(int nest, int nbic, int nfurg, int dem, int seed, int iniTrivial, int heuristica, int hillClimbing) {
        Estaciones estaciones = new Estaciones(nest,nbic,dem,seed);
        BicingEstado furgonetas = new BicingEstado(estaciones);
        furgonetas.inicializarFurgos(nfurg);
        if(iniTrivial == 0) furgonetas.iniTrivial();
        else furgonetas.GreedyIni();
        furgonetas.setHeuristica(heuristica);
        if(hillClimbing == 1) BicingHillClimbingSearch(furgonetas);
        else BicingSimulatedAnnealingSearch(furgonetas);
    }

    private static void BicingHillClimbingSearch(BicingEstado furgonetas) {
         System.out.println("Bicing Hill Climbing:");  
      try {
            long startTime = System.nanoTime();
            Problem problem =  new Problem(furgonetas,new BicingSucesores(), new BicingEstadoSolucion(),new BicingHeuristica());
            Search search =  new HillClimbingSearch();
            SearchAgent agent = new SearchAgent(problem,search);   
            long endTime = System.nanoTime();
            long elapsedTime = System.nanoTime() - startTime;
            BicingEstado finalState = (BicingEstado) search.getGoalState();
            System.out.println("Tiempo: " + elapsedTime/1000000 + " milisegundos");
            System.out.println("Beneficio total: " + String.format("%.2f",-1*finalState.getBeneficioTotal()));
            System.out.println("Distancia total Recorrida: " + String.format("%.2f",finalState.getLongitudTotal()));
        }   
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void BicingSimulatedAnnealingSearch(BicingEstado furgonetas) {
        System.out.println("Bicing Simulated Annealing:");
        try {
            long startTime = System.nanoTime();
            Problem problem =  new Problem(furgonetas,new BicingSucesoresSA(), new BicingEstadoSolucion(),new BicingHeuristica());
            SimulatedAnnealingSearch search =  new SimulatedAnnealingSearch(200000,10,5,0.001);
            SearchAgent agent = new SearchAgent(problem,search);
            long endTime = System.nanoTime();
            long elapsedTime = System.nanoTime() - startTime;
            BicingEstado finalState = (BicingEstado) search.getGoalState();
            System.out.println("Tiempo: " + elapsedTime/1000000 + " milisegundos");
            System.out.println("Beneficio total: " + String.format("%.2f",-1*finalState.getBeneficioTotal()));
            System.out.println("Distancia total Recorrida: " + String.format("%.2f",finalState.getLongitudTotal()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    private static void printInstrumentation(Properties properties) {
        Iterator keys = properties.keySet().iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            String property = properties.getProperty(key);
            System.out.println(key + " : " + property);
        }
        
    }
}