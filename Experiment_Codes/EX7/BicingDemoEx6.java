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
public class BicingDemoEx6 {
    private static double beneficio = 0;
    public static void main(String[] args) { //3756
        int seeds[] = {0,3756, 583, 84787, 263, 7085, 25, 2884, 9683, 25654, 4983};
        // Estaciones(int nest, int nbic, int dem, int seed) Constructor
        // cogemos los datos de la entrada
        for(int i = 0; i < 11; ++i){ //que solo puebe para 2 seeds diferentes
            for(int j = 0; j < 2; ++j){
                int k = 5;
                Boolean sigue = true; //mirara el beneficio anterior y el siguente, si son iguales sigue = false
                double beneficioAnterior = 0;
                while(sigue){
                    int nest = 25;
                    int nbic = 1250;
                    int nfurg = k;
                    int dem = j;
                    int seed = seeds[i];
                    int iniTrivial = 0;
                    int heuristica = 0;
                    int hillClimbing = 1;

                    Estaciones estaciones = new Estaciones(nest, nbic, dem, seed);

                    BicingEstado furgonetas = new BicingEstado(estaciones);
                    furgonetas.inicializarFurgos(nfurg);

                    if(iniTrivial == 0) furgonetas.iniTrivial();
                    else furgonetas.GreedyIni();
                    // long startTime = System.nanoTime();     
                    furgonetas.setHeuristica(heuristica);
                    if(hillClimbing == 1) BicingHillClimbingSearch(furgonetas);
                    else BicingSimulatedAnnealingSearch(furgonetas);
                    if(beneficioAnterior == beneficio) sigue = false;
                    else{
                        beneficioAnterior = beneficio;
                        k+=5;
                    }
                    // long endTime = System.nanoTime();
                    // long elapsedTime = endTime - startTime;
                    //if(i !=0){
                    // System.out.println("Seed: " + seed + "  milisegundos: " + elapsedTime/1000 );
                    //printea el beneficio
                    if(i != 0)System.out.println("Beneficio total: " + beneficio);
                    }
                   System.out.println();
                }
                //salto
                System.out.println();
            }
            //salto
            System.out.println();
        }

    private static void BicingHillClimbingSearch(BicingEstado furgonetas) {
        try {
            Problem problem =  new Problem(furgonetas,new BicingSucesores(), new BicingEstadoSolucion(),new BicingHeuristica());
            Search search =  new HillClimbingSearch();
            SearchAgent agent = new SearchAgent(problem,search);
            
            //System.out.println();
            //printActions(agent.getActions());
            //printInstrumentation(agent.getInstrumentation());
            BicingEstado finalState = (BicingEstado) search.getGoalState();
            //finalState.printFurgos();

            // System.out.println("Beneficio total: " + -1*finalState.getBeneficioTotal());
            // System.out.println("Longitud total: " + finalState.getLongitudTotal());
            beneficio = -1*finalState.getBeneficioTotal();
        }   catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void BicingSimulatedAnnealingSearch(BicingEstado furgonetas) {
        System.out.println("\nBicing Simulated Annealing  -->");
        try {
            Problem problem =  new Problem(furgonetas,new BicingSucesoresSA(), new BicingEstadoSolucion(),new BicingHeuristica());
            SimulatedAnnealingSearch search =  new SimulatedAnnealingSearch(200000,10,5,0.001);

            SearchAgent agent = new SearchAgent(problem,search);
            BicingEstado finalState = (BicingEstado) search.getGoalState();
            finalState.printFurgos();
            // System.out.println();
            // printActions(agent.getActions());
            // printInstrumentation(agent.getInstrumentation());
            System.out.println("Beneficio total: " + -1*finalState.getBeneficioTotal());
            System.out.println("Longitud total: " + finalState.getLongitudTotal());
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