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
import aima.search.framework.GraphSearch;
//import para formulas matematicas
import static java.lang.Math.abs;

import java.util.Scanner;
public class BicingDemo {
    public static void main(String[] args) {
        // Estaciones(int nest, int nbic, int dem, int seed) Constructor
        // cogemos los datos de la entrada
        //Scanner sc = new Scanner(System.in);
        // int nest = sc.nextInt();
        // int nbic = sc.nextInt();
        // //nbic tiene que ser como minimo 50*nest, es menor da error
        // if(nest* 50 > nbic) {
        //     System.out.println("Error: el numero de bicicletas es menor que 50*numero de estaciones");
        //     System.exit(1);
        // }
        // int nfurg = sc.nextInt();
        // int dem = sc.nextInt();
        // int seed = sc.nextInt();
        int nest = 25;
        int nbic = 1250;
        int nfurg = 5;
        int dem = 0;
        int seed = 1234;

        Estaciones estaciones = new Estaciones(nest, nbic, dem, seed);

        BicingEstado furgonetas = new BicingEstado(estaciones);
        furgonetas.inicializarFurgos(nfurg);
        furgonetas.GreedyIni();
        BicingHillClimbingSearch(furgonetas);
    }

    private static void BicingHillClimbingSearch(BicingEstado furgonetas) {
        System.out.println("\nTSP HillClimbing  -->");
        try {
            Problem problem =  new Problem(furgonetas,new BicingSucesores(), new BicingEstadoSolucion(),new BicingHeuristica());
            Search search =  new HillClimbingSearch();
            SearchAgent agent = new SearchAgent(problem,search);
            
            System.out.println();
            printActions(agent.getActions());
            printInstrumentation(agent.getInstrumentation());
        }   catch (Exception e) {
            e.printStackTrace();
        }
        furgonetas.printFurgos();
        furgonetas.printAsigFurgos();
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