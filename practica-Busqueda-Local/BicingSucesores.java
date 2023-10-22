
import aima.search.framework.SuccessorFunction;
import java.util.List;
import aima.search.framework.Successor;
import java.util.ArrayList;

public class BicingSucesores implements SuccessorFunction {
    public List<Successor> getSuccessors(Object state) {
        ArrayList<Successor> retval = new ArrayList<>();
        Estado board = (Estado) state;
       
        for(int i = 0; i < board.getNumFurgonetas();++i){
            for(int j = 0; j < board.getNest(); ++j){
                Estado new_state=new Estado(board);
                new_state.cambiarDestino(i,j);
                String s = "Furgoneta " + i + " Destino " + j;
                String action= "asignar destino 1 " +s;
                
                Successor successor = new Successor(action,new_state);
                
                retval.add(successor);
            }
        }
        //Asignar Destino 2
        // for(int i = 0; i < board.getNumFurgonetas();++i){
        //     for(int j = 0; j < board.getNest(); ++j){
                
        //         Estado new_state=new Estado(board);
        //         f(new_state.puedeIrDestino2(i,j)){//ESTA COMPROBACION SE PUEDE HACER EN EL ESTADO TAMBIEN
        //             new_state.asignarDestino2(i,j);
        //             String s = "Furgoneta " + i + " Destino " + j;
        //             String action= "asignar destino 2 " +s;
                    
        //             Successor successor = new Successor(action,new_state);
                    
        //             retval.add(successor);
        //         }
        //     }
        // }

        return retval;
    }
}
