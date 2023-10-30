import aima.search.framework.SuccessorFunction;
import java.util.List;
import aima.search.framework.Successor;
import java.util.ArrayList;

public class BicingSucesores implements SuccessorFunction {
    public List<Successor> getSuccessors(Object state) {
        ArrayList<Successor> retval = new ArrayList<>();
        BicingEstado board= (BicingEstado) state;
       //Asignar Destino 1
        for(int i = 0; i < board.getNumFurgonetas();++i){
            for(int j = 0; j < board.getNest(); ++j){
                if(board.condicionAplicabilidadDestino1(i,j)){
                    BicingEstado new_state=new BicingEstado(board);
                    new_state.cambiarDestino(i,j);
                    String s = "furgoneta " + i + " destino 1 es:" + j + " nb1:" + new_state.getNb1(i) ;
                    String action= "Asignar destino 1 para " +s;
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
                    String s = "furgoneta " + i + " destino 1 es:" + new_state.getEstacionDestino1(i)+ " nb1:"+ new_state.getNb1(i)+ " Destino 2 ahora es:" + j + " nb2:" + new_state.getNb2(i);
                    String action= "Asignar destino 2 para " +s;
                    Successor successor = new Successor(action,new_state);
                    retval.add(successor);
                }
            }
        }

        //Cambiar origen
        for(int i = 0; i < board.getNumFurgonetas();++i){
            for(int j = 0; j < board.getNest(); ++j){
                if(board.condicionAplicabilidadOrigen(i,j)){
                    BicingEstado new_state=new BicingEstado(board);
                    new_state.CambiarOrigen(i,j);
                    String s = "furgoneta " + i + " ahora es:" + j + " nb1:" + new_state.getNb1(i) + " nb2:" + new_state.getNb2(i);
                    String action= "Cambiar origen para la " +s; 
                    Successor successor = new Successor(action,new_state);
                    retval.add(successor);
                }
            }
        }

        // Intercambiar destinos
        for(int i = 0; i < board.getNumFurgonetas();++i){
            if(board.condicionAplicabilidadSwap(i)){
                BicingEstado new_state=new BicingEstado(board);
                new_state.aplicaIntercambio(i);
                String s = "furgoneta " + i + " destino 1 ahora es:" + new_state.getEstacionDestino1(i)+ " nb1:"+ new_state.getNb1(i)+ " Destino 2 ahora es:" + new_state.getEstacionDestino2(i) + " nb2:" + new_state.getNb1(i);
                String action= "Intercambiar ruta de la " +s;
                Successor successor = new Successor(action,new_state);
                retval.add(successor);
            }
        }
        
        //Desasginar Destino1
        for(int i = 0; i < board.getNumFurgonetas();++i){
            if(board.condicionAplicabilidadDesasignarDestino1(i)){
                BicingEstado new_state=new BicingEstado(board);
                String s = "furgoneta " + i + "destino desasignado:" + new_state.getEstacionDestino1(i);
                new_state.desasignarDestino1(i);
                String action= "Desasignar destino 1 de la " +s;
                Successor successor = new Successor(action,new_state);
                retval.add(successor);
            }
        }
        
        //Desasginar Destino2
        for(int i = 0; i < board.getNumFurgonetas();++i){
            if(board.condicionAplicabilidadDesasignarDestino2(i)){
                BicingEstado new_state=new BicingEstado(board);
                String s = "furgoneta " + i + "destino desasignado:" + new_state.getEstacionDestino2(i);
                new_state.desasignarDestino2(i);
                String action= "Desasignar destino 2 de la " +s;
                Successor successor = new Successor(action,new_state);
                retval.add(successor);
            }
        }

        return retval;
    }
}
