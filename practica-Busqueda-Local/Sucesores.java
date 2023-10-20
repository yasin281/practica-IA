
//package Bicing;
import aima.search.framework.SuccessorFunction;
import java.util.List;
import aima.search.framework.Successor;
import java.util.ArrayList;
import IA.Bicing.Estacion; 
import IA.Bicing.Estaciones;

public class Sucesores implements SuccessorFunction {
    
    @Override
    public List getSuccessors(Object state) {
        //en esta funcion van aplicar los operadores y generar los sucesores
        // ArrayList retval = new ArrayList(); //aqui se guardan los sucesores que retornaremos
        ArrayList<Successor> retval = new ArrayList<>();

        Estado van = (Estado) state;
        //vector de enteros de tamaño van.getNumFurgonetas() todos iniciados a -1
        //arraylist<Integer> vector = new ArrayList<>(van.getNumFurgonetas(),-1);
        //new_state=van.copy();
       
        for(int i = 0; i < van.getNumFurgonetas();++i){
            //Asignar Destino 1
            for(int j = 0; j < van.getNest(); ++j){
                Estado new_state;
                new_state=van.copy();
                new_state.asignarDestino(i,j);
                String s = "Furgoneta " + i + " Destino " + j;
                String action= "asignar destino 1 " +s;
                
                Successor successor = new Successor(action,new_state);
                
                retval.add(successor);
            }
        }
        return retval;
    }
}

// supongamos 3 furgos y 10 estaciones
// ej permutaciones:
// furgo1: 0 -> 1
//         0 -> 2
//         ...
//         0->10
//         if furgo1 coge 0
//         los otros dos pueden empezar en cualquiera excepto 0

// luego eso pero cuando furgo1 empieza en 1, 2 ...

// muchas permutaciones no

// ini --> con origen 

// retval == todos los sucesores con aplicacion de todos los operadores, pero los operadores se aplican a un furgo o todas?



// @Override
// public List<Successor> getSuccessors(Object state) {
//     ArrayList<Successor> retval = new ArrayList<>();
//     Estado van = (Estado) state;

//     // Itera sobre todas las furgonetas y todos los posibles destinos
//     for (int furgonetaIndex = 0; furgonetaIndex < van.getNumFurgonetas(); furgonetaIndex++) {
//         for (int destinoIndex = 0; destinoIndex < van.getNumDestinos(); destinoIndex++) {
//             // Realiza una copia del estado actual
//             Estado estadoSucesor = van.copiarEstado();

//             // Aplica el operador 1: Asignar Estación Destino/s y sus bicicletas
//             estadoSucesor.asignarEstacionDestino(furgonetaIndex, van.getEstacionInicial(furgonetaIndex), destinoIndex, van.getBicicletas(furgonetaIndex), van.getKilometros(furgonetaIndex), van.getBeneficio());

//             // Genera el sucesor con la acción correspondiente
//             String accion = "Asignar destino " + destinoIndex + " a furgoneta " + furgonetaIndex;
//             Successor sucesor = new Successor(accion, estadoSucesor);

//             // Agrega el sucesor a la lista de sucesores
//             retval.add(sucesor);
//         }

//         // Aplica el operador 2: Quitar furgoneta
//         Estado estadoSinFurgoneta = van.copiarEstado();
//         estadoSinFurgoneta.quitarFurgoneta(furgonetaIndex);
//         String accionQuitar = "Quitar furgoneta " + furgonetaIndex;
//         Successor sucesorQuitar = new Successor(accionQuitar, estadoSinFurgoneta);
//         retval.add(sucesorQuitar);

//         // Aplica el operador 3: Añadir furgoneta
//         if (van.getEstacionInicial(furgonetaIndex) == -1) {
//             for (int nuevaFurgonetaIndex = 0; nuevaFurgonetaIndex < van.getNumFurgonetas(); nuevaFurgonetaIndex++) {
//                 if (van.getEstacionInicial(nuevaFurgonetaIndex) == -1) {
//                     Estado estadoConNuevaFurgoneta = van.copiarEstado();
//                     estadoConNuevaFurgoneta.añadirFurgoneta(nuevaFurgonetaIndex, van.getEstacionInicial(furgonetaIndex), destinoIndex, van.getBicicletas(furgonetaIndex), van.getKilometros(furgonetaIndex), van.getBeneficio());
//                     String accionAñadir = "Añadir furgoneta " + nuevaFurgonetaIndex;
//                     Successor sucesorAñadir = new Successor(accionAñadir, estadoConNuevaFurgoneta);
//                     retval.add(sucesorAñadir);
//                 }
//             }
//         }

//         // Aplica el operador 4: Asignar dos estaciones destino y sus bicicletas
//         for (int segundoDestinoIndex = 0; segundoDestinoIndex < van.getNumDestinos(); segundoDestinoIndex++) {
//             Estado estadoConDosDestinos = van.copiarEstado();
//             estadoConDosDestinos.asignarEstacionDestino(furgonetaIndex, van.getEstacionInicial(furgonetaIndex), destinoIndex, van.getBicicletas(furgonetaIndex), segundoDestinoIndex, van.getBicicletas2(furgonetaIndex), van.getKilometros(furgonetaIndex), van.getBeneficio());
//             String accionDosDestinos = "Asignar destinos " + destinoIndex + " y " + segundoDestinoIndex + " a furgoneta " + furgonetaIndex;
//             Successor sucesorDosDestinos = new Successor(accionDosDestinos, estadoConDosDestinos);
//             retval.add(sucesorDosDestinos);
//         }

//         // Aplica el operador 5: Asignar dos estaciones destino y sus bicicletas (variante)
//         for (int segundoDestinoIndex = 0; segundoDestinoIndex < van.getNumDestinos(); segundoDestinoIndex++) {
//             if (van.valeLaPena(furgonetaIndex, destinoIndex, segundoDestinoIndex)) {
//                 Estado estadoConDosDestinosValeLaPena = van.copiarEstado();
//                 estadoConDosDestinosValeLaPena.asignarEstacionDestino2(furgonetaIndex, van.getEstacionInicial(furgonetaIndex), destinoIndex, segundoDestinoIndex, van.getBicicletas(furgonetaIndex), van.getBicicletas2(furgonetaIndex), van.getKilometros(furgonetaIndex), van.getBeneficio());
//                 String accionDosDestinosValeLaPena = "Asignar destinos " + destinoIndex + " y " + segundoDestinoIndex + " a furgoneta " + furgonetaIndex;
//                 Successor sucesorDosDestinosValeLaPena = new Successor(accionDosDestinosValeLaPena, estadoConDosDestinosValeLaPena);
//                 retval.add(sucesorDosDestinosValeLaPena);
//             }
//         }
//     }

//     return retval;


//OBRA MAESTRA YASIN

        /*
        DE MOMENTO SOLO APLICAREMOS UN UNICO OPERADOR, LA DE QUE UNA FURGO SE MUEVA A OTRO DESTINO
        OPERADOR 1 : Asignar Estación Destino/s y sus bicicletas (FxE) # varia la ruta de la furgo
        en este operador vamos a generar todos los sucesores posibles, es decir, vamos a generar todos los posibles destinos de cada furgo
        para ello, vamos a recorrer todas las furgonetas y para cada una de ellas vamos a generar todos los posibles destinos
        para cada destino, vamos a generar un sucesor
        para generar un sucesor, vamos a copiar el estado actual, y vamos a aplicar el operador a ese estado copiado
        una vez aplicado el operador, vamos a generar un sucesor con el estado copiado y el operador aplicado
        y lo añadimos a retval
         */
        
        
        //aplica operador de asignar destino a las furgonetas
        // Itera sobre todas las furgonetas y todos los posibles destinos

        // for(int i = 0; i < van.length(); ++i){
        //     for(int j = 0 ; j <)
        // }
        //    asignarEstacionDestino(int furg, int est0, int dest, int bic, double km,double bene)
        // for(int i=0; i<van.getNumFurgonetas();++i){
        //     Estado new_state;
        //     new_state=van.copy();
        //     new_state.asignarEstacionDestino(i,(i+1)%25,(i+2)%25,8,0,5);

        //     String s = i + " " + (i + 1);
        //     String action= "flip " +s;
        //     Successor successor = new Successor(action,new_state);
        //     retval.add(successor);
        // }



            // for(int j = 0 ; j < 25 && j!=estacionInicial; ++j){
            //     int bici=van.getNumBicicletasNoUsadasE(estacionInicial);
            //     new_state.asignarEstacionDestino(i,(estacionInicial),j,bici,0,-bici);
                
            //     new_state.printFurgos();
            //     String action = "Furgoneta: "+ " estacionInicial " +estacionInicial +" estacion destino "+j;
            //     Successor successor = new Successor(action,new_state);
            //     retval.add(successor);
            //     System.out.println("Hola que hace\n"+new_state.getBeneficioTotal());
            // }
            // ++estacionInicial;