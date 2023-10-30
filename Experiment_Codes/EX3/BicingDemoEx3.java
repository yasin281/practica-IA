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
import java.time.LocalTime;
import java.time.temporal.ChronoUnit; 
import java.util.Random;

//import para formulas matematicas
import static java.lang.Math.abs;

import java.util.Scanner;
public class BicingDemoEx3 {
    private static int TTbeneficio = 0;
    public static void main(String[] args) {
        int seeds[] = {0,3756, 583, 84787, 263, 7085, 25, 2884, 9683, 25654, 4983};
        int K[] = {1,5,25,125};
        double lambda[] = {10,1,0.01,0.001};
        // Estaciones(int nest, int nbic, int dem, int seed) Constructor
        // cogemos los datos de la entrada
        for(int k = 0; k < K.length; ++k){
            for(int l = 0; l < lambda.length; ++l){
                int beneficio = 0;
                for(int i = 0; i < seeds.length; ++i){
                    TTbeneficio = 0;
                    for(int j = 0; j < 15; ++j){ //como es aleatorio, hacemos 15 veces cada seed
                        int nest = 25;
                        int nbic = 1250;
                        int nfurg = 5;
                        int dem = 0;
                        int seed = seeds[i];
                        int iniTrivial = 1;
                        int heuristica = 0;
                        int hillClimbing = 0;
                        
                        Estaciones estaciones = new Estaciones(nest, nbic, dem, seed);

                        BicingEstado furgonetas = new BicingEstado(estaciones);
                        furgonetas.inicializarFurgos(nfurg);

                        if(iniTrivial == 0) furgonetas.iniTrivial();
                        else furgonetas.GreedyIni();
                        furgonetas.setHeuristica(heuristica);
                        if(hillClimbing == 1) BicingHillClimbingSearch(furgonetas);
                        else BicingSimulatedAnnealingSearch(furgonetas, lambda[l], K[k]);
                    }
                    if(i != 0)beneficio += TTbeneficio/15; //beneficio medio para una seed en concreto con 15 ejecuciones
                }
                //printea beneficio
                System.out.println("K:" + K[k] + " lambda: " + lambda[l] + " beneficio: " + beneficio/10);
                //salta
                // System.out.println();
            }
            //salta 
            System.out.println();
        }
    }

    private static void BicingHillClimbingSearch(BicingEstado furgonetas) {
        try {
            Problem problem =  new Problem(furgonetas,new BicingSucesores(), new BicingEstadoSolucion(),new BicingHeuristica());
            Search search =  new HillClimbingSearch();
            SearchAgent agent = new SearchAgent(problem,search);
            
            System.out.println();
            printActions(agent.getActions());
            printInstrumentation(agent.getInstrumentation());
            BicingEstado finalState = (BicingEstado) search.getGoalState();
            finalState.printFurgos();

            System.out.println("Beneficio total: " + -1*finalState.getBeneficioTotal());
            System.out.println("Longitud total: " + finalState.getLongitudTotal());
        }   catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void BicingSimulatedAnnealingSearch(BicingEstado furgonetas, double lambda, int k) {
        //System.out.println("\nBicing Simulated Annealing  -->");
        try {
            Problem problem =  new Problem(furgonetas,new BicingSucesoresSA(), new BicingEstadoSolucion(),new BicingHeuristica());
            SimulatedAnnealingSearch search =  new SimulatedAnnealingSearch(10000,100,k,lambda);

            SearchAgent agent = new SearchAgent(problem,search);
            BicingEstado finalState = (BicingEstado) search.getGoalState();
            // finalState.printFurgos();
            // System.out.println();
            // printActions(agent.getActions());
            // printInstrumentation(agent.getInstrumentation());
            // System.out.println("Beneficio total: " + -1*finalState.getBeneficioTotal());
            // System.out.println("Longitud total: " + finalState.getLongitudTotal());
            TTbeneficio += -1*finalState.getBeneficioTotal();
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
    
    private static void printActions(List actions) {
        for (int i = 0; i < actions.size(); i++) {
            String action = (String) actions.get(i);
            System.out.println(action);
        }
    }
}