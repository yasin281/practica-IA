package Bicing;

import aima.search.framework.GoalTest;

public class EstadoSolucion implements GoalTest {
    @Override
    public boolean isGoalState(Object o) {
        return false;
    }
    
    //funcion para calcular el beneficio de una solucion
    public double getBeneficio(Estado e){
        double beneficio = 0;
        for(int i = 0; i < e.getFurgos().length; ++i){
            for(int j = 0; j < e.getFurgos()[0].length; ++j){
                if(e.getFurgos()[i][j] != -1){
                    beneficio += e.getFurgos()[i][j];
                }
            }
        }
        return beneficio;
    }

    //funcion para calcular el coste de una solucion
    public double getCoste(Estado e){
        double coste = 0;
        for(int i = 0; i < e.getFurgos().length; ++i){
            for(int j = 0; j < e.getFurgos()[0].length; ++j){
                if(e.getFurgos()[i][j] != -1){
                    coste += e.getFurgos()[i][j];
                }
            }
        }
        return coste;
    }
    //funcion para calcular el beneficio/coste de una solucion
    public double getBeneficioCoste(Estado e){
        double beneficio = 0;
        double coste = 0;
        for(int i = 0; i < e.getFurgos().length; ++i){
            for(int j = 0; j < e.getFurgos()[0].length; ++j){
                if(e.getFurgos()[i][j] != -1){
                    beneficio += e.getFurgos()[i][j];
                    coste += e.getFurgos()[i][j];
                }
            }
        }
        return beneficio/coste;
    }

}
