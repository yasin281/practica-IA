
//package Bicing;
import aima.search.framework.SuccessorFunction;
import java.util.List;
import aima.search.framework.Successor;
import java.util.ArrayList;


public class Sucesores implements SuccessorFunction {
    @Override
    public List getSuccessors(Object state) {
        //en esta funcion van aplicar los operadores y generar los sucesores
        ArrayList retval = new ArrayList(); //aqui se guardan los sucesores que retornaremos
        Estado vans = (Estado) state;


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

        //OPERADOR ASIGNAR UN DESTINO A LAS FURGONETAS
        
        
        return retval;

    }

}
