package Bicing;


import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;

import javax.sound.midi.Soundbank;
import java.sql.SQLOutput;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
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
        for(Estacion e : estacion.estaciones){
            System.out.println(e.getDemanda());
        }
    }
}

