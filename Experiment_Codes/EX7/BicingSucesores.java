
import aima.search.framework.SuccessorFunction;
import java.util.List;
import aima.search.framework.Successor;
import java.util.ArrayList;

public class BicingSucesores implements SuccessorFunction {
    public List<Successor> getSuccessors(Object state) {
        ArrayList<Successor> retval = new ArrayList<>();
        BicingEstado board = (BicingEstado) state;
        // Asignar Destino 1
        for(int i = 0; i < board.getNumFurgonetas();++i){
            for(int j = 0; j < board.getNest(); ++j){
                if(board.condicionAplicabilidadDestino1(i,j)){
                    BicingEstado new_state=new BicingEstado(board);
                    new_state.cambiarDestino(i,j);
                    String s = "Furgoneta " + i + " Destino " + j + " Numero de bicis: " + new_state.Bicis1(i) ;
                    String action= "asignar destino 1 " +s;
                    Successor successor = new Successor(action,new_state); 
                            
                    retval.add(successor);
                }
            }
            
        }
        // Asignar Destino 2
        for(int i = 0; i < board.getNumFurgonetas();++i){
            for(int j = 0; j < board.getNest(); ++j){
                    if(board.condicionAplicabilidadDestino2(i,j)){
                    BicingEstado new_state=new BicingEstado(board);
                    new_state.cambiarDestino2(i,j);
                    String s = "Furgoneta " + i + " Destino1 " + new_state.getEstacionDestino1(i)+ " LLeva bicis1: "+ new_state.Bicis1(i)+ " Destino2 " + j + " Lleva bicis2: " + new_state.Bicis2(i);
                    String action= "asignar destino 2 " +s;
                    Successor successor = new Successor(action,new_state);

                    retval.add(successor);
                }
            }
        }
        
        //cambiar origen
        for(int i = 0; i < board.getNumFurgonetas();++i){
            for(int j = 0; j < board.getNest(); ++j){
                if(board.condicionAplicabilidadOrigen(i,j)){
                    BicingEstado new_state=new BicingEstado(board);
                    new_state.CambiarOrigen(i,j);
                    String s = "Furgoneta " + i + " Origen " + j + " Numero de bicis1 : " + new_state.Bicis1(i) + " bicis 2 :" + new_state.Bicis2(i);
                    String action= "cambiar origen " +s; 
                    Successor successor = new Successor(action,new_state);
                        
                    retval.add(successor);
                }
            }
        }

        // //swapear ruta
        for(int i = 0; i < board.getNumFurgonetas();++i){
            if(board.condicionAplicabilidadSwap(i)){
                BicingEstado new_state=new BicingEstado(board);
                new_state.aplicaSwap(i);
                String s = "Furgoneta " + i + " intercambiar ruta " + new_state.getEstacionDestino1(i)+ " LLeva bicis1: "+ new_state.Bicis1(i)+ " Destino2 " + new_state.getEstacionDestino2(i) + " Lleva bicis2: " + new_state.Bicis2(i);
                String action= "Swapear ruta: " +s;
                Successor successor = new Successor(action,new_state);
                    
                retval.add(successor);
            }
        }
        //Desasginar Destino1
        for(int i = 0; i < board.getNumFurgonetas();++i){
            if(board.condicionAplicabilidadDesasignarDestino1(i)){
                BicingEstado new_state=new BicingEstado(board);
                String s = "Furgoneta " + i + "Desasgina Destino 1" + new_state.getEstacionDestino1(i);
                new_state.desasignarDestino1(i);
                String action= "Desasignar Destino 1: " +s;
                Successor successor = new Successor(action,new_state);


                retval.add(successor);
            }
        }
        
        //Desasginar Destino2
        for(int i = 0; i < board.getNumFurgonetas();++i){
            if(board.condicionAplicabilidadDesasignarDestino2(i)){
                BicingEstado new_state=new BicingEstado(board);
                String s = "Furgoneta " + i + "Desasgina Destino 2" + new_state.getEstacionDestino2(i);
                new_state.desasignarDestino2(i);
                String action= "Desasignar Destino 2: " +s;
                Successor successor = new Successor(action,new_state);

                retval.add(successor);
            }
        }

        return retval;
    }
}
