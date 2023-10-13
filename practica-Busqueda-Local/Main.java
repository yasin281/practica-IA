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
//import para formulas matematicas
import static java.lang.Math.abs;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Estaciones(int nest, int nbic, int dem, int seed) Constructor
        // cogemos los datos de la entrada
        Scanner sc = new Scanner(System.in);
        int nest = sc.nextInt();
        int nbic = sc.nextInt();
        //nbic tiene que ser como minimo 50*nest, es menor da error
        if(nest* 50 > nbic) {
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

        //solucion facil --> Furgo[0] coje en Estaciones[0] y lo deja en Estaciones[1]
        int i = 0;
        for(; i < nest && i < nfurg; ++i){
            int g = Math.min(estaciones.get(i).getNumBicicletasNoUsadas(),30);
            //kilometros manhattan entre estacion i y (i+1)%nest
            double km = Math.abs(estaciones.get(i).getCoordX()-estaciones.get((i+1)%nest).getCoordX())+Math.abs(estaciones.get(i).getCoordY()-estaciones.get((i+1)%nest).getCoordY());
            furgonetas.setFurgos(i, i, (i+1)%nest, -1, g, 0, km);
        }   
        if(i < nfurg){
            for(; i < nfurg; ++i){
                furgonetas.setFurgos(i, -1, 0, 0, 0, 0, 0);
            }
        }

        furgonetas.printFurgos();
    }
}

