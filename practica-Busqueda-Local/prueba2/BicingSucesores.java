
import aima.search.framework.SuccessorFunction;
import java.util.List;
import aima.search.framework.Successor;
import java.util.ArrayList;

public class BicingSucesores implements SuccessorFunction {
    public List<Successor> getSuccessors(Object state) {
        ArrayList<Successor> retval = new ArrayList<>();
        BicingEstado board = (BicingEstado) state;

        for(int i = 0; i < board.getNumFurgonetas();++i){
            for(int j = 0; j < board.getNest(); ++j){
                BicingEstado new_state=new BicingEstado(board);
                new_state.cambiarDestino2(i,j);
                String s = "Furgoneta " + i + " Destino1 " + new_state.getEstacionDestino1(i) + " Destino2 " + j;
                String action= "asignar destino 2 " +s;
                    
                Successor successor = new Successor(action,new_state);
                    
                retval.add(successor);
            }
        }

        for(int i = 0; i < board.getNumFurgonetas();++i){
            for(int j = 0; j < board.getNest(); ++j){
                BicingEstado new_state=new BicingEstado(board);
                new_state.cambiarDestino(i,j);
                String s = "Furgoneta " + i + " Destino " + j;
                String action= "asignar destino 1 " +s;
                
                Successor successor = new Successor(action,new_state);         
                retval.add(successor);
            }
            for(int j = 0; j < board.getNest(); ++j){
                BicingEstado new_state=new BicingEstado(board);
                new_state.cambiarDestino2(i,j);
                String s = "Furgoneta " + i + " Destino1 " + new_state.getEstacionDestino1(i) + " Destino2 " + j;
                String action= "asignar destino 2 " +s;
                    
                Successor successor = new Successor(action,new_state);
                    
                retval.add(successor);
            }
            
        }
        // Asignar Destino 2
        return retval;
    }
}
