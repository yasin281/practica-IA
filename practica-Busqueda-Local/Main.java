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



        // //solucion facil --> Furgo[0] coje en Estaciones[0] y lo deja en Estaciones[1]
        // int i = 0;
        // for(; i < nest && i < nfurg; ++i){
        //     int g = Math.min(estaciones.get(i).getNumBicicletasNoUsadas(),30);
        //     //kilometros manhattan entre estacion i y (i+1)%nest
        //     double km = Math.abs(estaciones.get(i).getCoordX()-estaciones.get((i+1)%nest).getCoordX())+Math.abs(estaciones.get(i).getCoordY()-estaciones.get((i+1)%nest).getCoordY());
        //     furgonetas.setFurgos(i, i, (i+1)%nest, -1, g, 0, km);
        // }   
        // if(i < nfurg){
        //     for(; i < nfurg; ++i){
        //         furgonetas.setFurgos(i, -1, 0, 0, 0, 0, 0);
        //     }
        // }

        // furgonetas.printFurgos();


        //solucion desarollada --> un arraylist de tantos elementos como estaciones y donde cada posicion tiene 2 elementos, donde el primer elemento es la beneficio y el segundo es la posicion de la estacion
        
        double[][] beneficio;
        beneficio = new double[nest][2];
        for(int j = 0; j < nest; ++j){
            beneficio[j][0] = estaciones.get(j).getDemanda()-estaciones.get(j).getNumBicicletasNoUsadas();
            beneficio[j][1] = j;
        }
        //ordena de mayor a menor pero solo mira el elemento [0] 
        for(int j = 0; j < nest; ++j){
            for(int k = j+1; k < nest; ++k){
                if(beneficio[j][0] < beneficio[k][0]){
                    double aux = beneficio[j][0];
                    beneficio[j][0] = beneficio[k][0];
                    beneficio[k][0] = aux;
                    aux = beneficio[j][1];
                    beneficio[j][1] = beneficio[k][1];
                    beneficio[k][1] = aux;
                }
            }
        }
        
        //furgonetas.printFurgos();

        //una vez tenemos el vector de beneficios para cada estacion, hay que asignar estaciones/2 furgonetas a las estaciones con menor beneficio para llevarles bicicletas a estaciones a mas demanda
        int halfEst = nest/2;
        if(halfEst > nfurg){ //en el caso de el numero de furgonetas sea menor que la mitad de las estaciones
            for(int j = 0; j < nfurg; ++j){
                int g = Math.min(estaciones.get(nest-j-1).getNumBicicletasNoUsadas(),30);
                int km = distanciaManhattan(estaciones.get((int)beneficio[j][1]),estaciones.get((int)beneficio[nest-j-1][1]));
                furgonetas.setFurgos(j, (int)beneficio[nest-j-1][1],(int)beneficio[j][1], -1, g, 0, km);
            }
        }
        else { 
            for(int j = 0; j < halfEst; ++j){
                int g = Math.min(estaciones.get(nest-j-1).getNumBicicletasNoUsadas(),30);
                int km = distanciaManhattan(estaciones.get((int)beneficio[j][1]),estaciones.get((int)beneficio[nest-j-1][1]));
                furgonetas.setFurgos(j, (int)beneficio[nest-j-1][1],(int)beneficio[j][1], -1, g, 0, km);
            }
            //la otra mitad estara asignada a ninguna estacion (no disponible)
            for(int j = halfEst; j < nfurg; ++j){
                furgonetas.setFurgos(j, -1, 0, 0, 0, 0, 0);
            }
        }

        furgonetas.printFurgos();
    }
}

