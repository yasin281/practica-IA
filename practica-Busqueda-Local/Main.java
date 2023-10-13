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
        //nbic tiene que ser como minimo 50*nest, es menor da error
        if(nest* 50 < nbic) {
            System.out.println("Error: el numero de bicicletas es menor que 50*numero de estaciones");
            System.exit(1);
        }
        int nfurg = sc.nextInt();
        int dem = sc.nextInt();
        int seed = sc.nextInt();

        //ahora pasamos estos datos a la clase estaciones
        Estaciones estaciones = new Estaciones(nest, nbic, dem, seed);

        Estado furgonetas = new Estado();
        furgonetas.inicializarFurgos(nfurg, 6);
        furgonetas.setFurgos(0, 0, 1, 2, 0, 0, 0);
        furgonetas.setFurgos(1, 3, 4, 5, 0, 0, 0);
        furgonetas.setFurgos(2, 6, 7, 8, 0, 0, 0);
        furgonetas.setFurgos(3, 9, 10, 11, 0, 0, 0);
        furgonetas.setFurgos(4, 12, 13, 14, 0, 0, 0);
        furgonetas.printFurgos();


    }
}

