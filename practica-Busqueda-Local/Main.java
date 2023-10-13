
import IA.Bicing.Estaciones;
import IA.Bicing.Estacion;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.framework.GraphSearch;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Estaciones(int nest, int nbic, int dem, int seed) Constructor
        // cogemos los datos de la entrada
        Scanner sc = new Scanner(System.in);
        int nest = sc.nextInt();
        int nbic = sc.nextInt();
        int dem = sc.nextInt();
        int seed = sc.nextInt();
        //ahora pasamos estos datos a la clase estaciones
        Estaciones estaciones = new Estaciones(nest, nbic, dem, seed);
        //itera sobre las estaciones y printea el numero de bicicletas por estacion
        // Estaciones es una Array de estaciones, no hay getEstaciones
        for(Estacion e : estaciones){
            System.out.println(e.getDemanda());
        }
    }
}

