
import aima.search.framework.SuccessorFunction;
import java.util.List;
import aima.search.framework.Successor;
import java.util.ArrayList;
import java.util.Random;

public class BicingSucesoresSA implements SuccessorFunction {



    //funcion de random entre 0 y n-1
    public int random(int n){
        Random myRandom=new Random();
        return myRandom.nextInt(n);
    }

    public List getSuccessors(Object state) {
      ArrayList<Successor> retval = new ArrayList<>();
      BicingEstado board = (BicingEstado) state;
      BicingEstado new_state=new BicingEstado(board);
      String action = "";
      int i,j,k;
      //Seleccionar una furgonetes aleatoriament
      i = random(board.getFurgo());
      //Seleccionar una estacion aleatoriament
      j = random(board.getNest());
      //Seleccionar una accion aleatoriament
      k = random(6); 
      //Comprobar si es aplicable la accion de asignar destino 1
      boolean aplicableDestino1 = board.condicionAplicabilidadDestino1(i,j);
      //Comprobar si es aplicable la accion de asignar destino 2
      boolean aplicableDestino2 = board.condicionAplicabilidadDestino2(i,j);
      //Comprobar si es aplicable la accion de cambiar origen
      boolean aplicableOrigen = board.condicionAplicabilidadOrigen(i,j);
      //Comprobar si es aplicable la accion de swapear ruta
      boolean aplicableSwap = board.condicionAplicabilidadSwap(i);
      //Comprobar si es aplicable la accion de desasignar destino 1
      boolean aplicableDesAsignarDestino1 = board.condicionAplicabilidadDesasignarDestino1(i);  
      //Comprobar si es aplicable la accion de desasignar destino 2    
      boolean aplicableDesAsignarDestino2 = board.condicionAplicabilidadDesasignarDestino2(i);

      if(k == 0 && aplicableDestino1){
          new_state.cambiarDestino(i,j);
          action= "asignar destino 1 ";
        }
        else if(k == 1 && aplicableDestino2){
          new_state.cambiarDestino2(i,j);
          action= "asignar destino 2 ";
        }
        else if(k == 2 && aplicableOrigen){
            new_state.CambiarOrigen(i,j);
            String s = "Furgoneta " + i + " Origen " + j + "Numero de get Nb1 : " + new_state.getNb1(i) + " nb2 :" + new_state.getNb2(i);
            action= "cambiar origen " +s;
        }
        else if(k == 3 && aplicableSwap){
            new_state.aplicaIntercambio(i);
            String s = "Furgoneta " + i + " Origen " + j + "Numero de get Nb1 : " + new_state.getNb1(i) + " nb2 :" + new_state.getNb2(i);
            action= "Swapear ruta: " +s;
        }
        else if(k == 4 && aplicableDesAsignarDestino1){
            new_state.desasignarDestino1(i);
            String s = "Furgoneta " + i + " Origen " + j + "Numero de get Nb1 : " + new_state.getNb1(i) + " nb2 :" + new_state.getNb2(i);
            action= "Desasignar Destino 1: " +s;
        }
        else if(k == 5 && aplicableDesAsignarDestino2){
            new_state.desasignarDestino2(i);
            String s = "Furgoneta " + i + " Origen " + j + "Numero de get Nb1 : " + new_state.getNb1(i) + " nb2 :" + new_state.getNb2(i);
            action= "Desasignar Destino 2: " +s;
        }
        else{
            if(aplicableDestino1){
              new_state.cambiarDestino(i,j);
              action= "asignar destino 1 ";
            }
            else if(aplicableDestino2){
              new_state.cambiarDestino2(i,j);
              action= "asignar destino 2 ";
            }
            else if(aplicableOrigen){
              new_state.CambiarOrigen(i,j);
              String s = "Furgoneta " + i + " Origen " + j + "Numero de get Nb1 : " + new_state.getNb1(i) + " nb2 :" + new_state.getNb2(i);
              action= "cambiar origen " +s;
            }
            else if(aplicableSwap){
              new_state.aplicaIntercambio(i);
              String s = "Furgoneta " + i + " Origen " + j + "Numero de Nb1 : " + new_state.getNb1(i) + " nb2 :" + new_state.getNb2(i);
              action= "Swapear ruta: " +s;

            }
            else if(aplicableDesAsignarDestino1){
                new_state.desasignarDestino1(i);
                String s = "Furgoneta " + i + " Origen " + j + "Numero de Nb1 : " + new_state.getNb1(i) + " nb2:" + new_state.getNb2(i);
                action= "Desasignar Destino 1: " +s;

            }
            else if(aplicableDesAsignarDestino2){
                new_state.desasignarDestino2(i);;
                String s = "Furgoneta " + i + " Origen " + j + "Numero de Nb1 : " + new_state.getNb1(i) + " nb2 :" + new_state.getNb2(i);
                action= "Desasignar Destino 2: " +s;
            }
            else{
              action = "No se puede aplicar ninguna accion";
            }
        }
        Successor successor = new Successor(action,new_state);
        retval.add(successor);
        
        return retval;
      }
}