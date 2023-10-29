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
public class BicingDemoEx2 {
    public static void main(String[] args) {
        int seeds[] = {0,3756, 583, 84787, 263, 7085, 25, 2884, 9683, 25654, 4983};

        // Estaciones(int nest, int nbic, int dem, int seed) Constructor
        // cogemos los datos de la entrada
        for(int i = 0; i < seeds.length; ++i){
            for(int j = 0; j < 2; ++j){
                    
                int nest = 25;
                int nbic = 1250;
                int nfurg = 5;
                int dem = 0;
                int seed = seeds[i];
                int iniTrivial = j;
                int heuristica = 0;
                int hillClimbing = 1;
                
                String s = "Trivial";
                if(j == 1) s = "Greedy";
                System.out.println("Tipo de inicializacion:" + s);

                // System.out.println("Numero de estaciones Predeterminado:"+nest);
                // System.out.println("Numero de bicicletas Predeterminado:"+nbic);
                // System.out.println("Numero de furgonetas Predeterminado:"+nfurg);
                // System.out.println("Tipo de demanda: 0 (equilibrada) 1 (hora punta) Predeterminado:" +dem);
                // System.out.println("Semilla Predeterminado:"+seed);
                // System.out.println("Algoritmo de busqueda: 0 (Simulated Annealing) 1 (Hill Climbing): Predeterminado:"+hillClimbing);
                // System.out.println("Tipo de heuristica 0 (sin coste) 1 (coste distancia) Predeterminado:"+heuristica);
                // System.out.println("Tipo de inicializacion 0 (trivial) 1 (greedy) Predeterminado:"+iniTrivial);
                // System.out.println("Quieres utilizar la conifguracion por defecto? (y/n)");
                
                // Scanner sc = new Scanner(System.in);
                // String answer = sc.nextLine();
                
                // if(answer.equals("n")){
                //     System.out.println("Numero de estaciones ");
                //     nest = sc.nextInt();
                //     System.out.println("Numero de bicicletas: ");
                //     nbic = sc.nextInt();
                //     if(nest * 50 > nbic) {
                //         System.out.println("Error: el numero de bicicletas es menor que 50*numero de estaciones");
                //         System.exit(1);
                //     }
                //     System.out.println("Numero de furgonetas: ");
                //     nfurg = sc.nextInt();
                //     System.out.println("Tipo de demanda: 0 (equilibrada) 1 (hora punta): ");
                //     dem = sc.nextInt();
                //     System.out.println("Semilla: ");
                //     seed = sc.nextInt();
                //     System.out.println("Algoritmo de busqueda: 0 (Simulated Annealing) 1 (Hill Climbing): ");
                //     hillClimbing = sc.nextInt();
                //     System.out.println("Tipo de heuristica: 0 (sin coste) 1 (coste distancia): ");
                //     heuristica = sc.nextInt();
                //     System.out.println("Tipo de inicializacion: 0 (trivial) 1 (greedy): ");
                //     iniTrivial = sc.nextInt();
                // }
                Estaciones estaciones = new Estaciones(nest, nbic, dem, seed);

                BicingEstado furgonetas = new BicingEstado(estaciones);
                furgonetas.inicializarFurgos(nfurg);

                if(iniTrivial == 0) furgonetas.iniTrivial();
                else furgonetas.GreedyIni();
                long startTime = System.nanoTime();     
                furgonetas.setHeuristica(heuristica);
                if(hillClimbing == 1) BicingHillClimbingSearch(furgonetas);
                else BicingSimulatedAnnealingSearch(furgonetas);
                long endTime = System.nanoTime();
                long elapsedTime = endTime - startTime;
                if(i !=0){
                System.out.println("Seed: " + seed + "  milisegundos: " + elapsedTime/1000 + "");
                }
                //un salto del ia
                System.out.println();
            }
            //dos saltos del ia
            System.out.println();
            System.out.println();
        }
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

            //System.out.println("Beneficio total: " + -1*finalState.getBeneficioTotal());
            //System.out.println("Longitud total: " + finalState.getLongitudTotal());
            System.out.println("B: " + -1*finalState.getBeneficioTotal() + " L: " + finalState.getLongitudTotal());
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