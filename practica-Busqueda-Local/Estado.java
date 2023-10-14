import java.util.ArrayList;
import java.util.List;
import IA.Bicing.Estacion;

public class Estado{
    
    //matriz de fx6 
    private double[][] furgos;
    //funcion para inicializar esta matriz
    public void inicializarFurgos(int nfurg, int nest){
        furgos = new double[nfurg][nest];
        for(int i = 0; i < nfurg; ++i){
            for(int j = 0; j < nest; ++j){
                furgos[i][j] = 0;
            }
        }
    }
    
    //funcion para cada elemento de [][]furgo le asigne 6 enteros
    public void setFurgos(int furg, int Est0,int Est1,int Est2, int B1, int B2, double Km){
        furgos[furg][0] = Est0;
        furgos[furg][1] = Est1;
        furgos[furg][2] = Est2;
        furgos[furg][3] = B1;
        furgos[furg][4] = B2;
        furgos[furg][5] = Km;
    }
    
    //imprime la matriz de furgos
    public void printFurgos(){
        for(int i = 0; i < furgos.length; ++i){
            for(int j = 0; j < furgos[0].length; ++j){
                System.out.print(furgos[i][j] + " ");
            }
            System.out.println();
        }
    }

    //Crear una funcion que dado una estacion y otra, calcule la distancia entre ellas manhatan
    public double distanciaManhattan(Estacion e1, Estacion e2){
        double distancia = 0;
        distancia = Math.abs(e1.getCoordX()-e2.getCoordX())+Math.abs(e1.getCoordY()-e2.getCoordY());
        return distancia;
    }


    //OPERADOR 1 : Asignar Estación Destino/s y sus bicicletas (FxE) # varia la ruta de la furgo 
    public void asignarEstacionDestino(int furg, int est, int bic){
        furgos[furg][0] = est;
        furgos[furg][1] = bic;
    }

    //OPERADOR 2: Quitar furgoneta (asignar su estacion inicial a -1)
    public void quitarFurgoneta(int furg){
        furgos[furg][0] = -1;
    }

    //OPERADOR 3: añadir furgo (solo se puede añadir si su estacion inicial es -1), le asignamos una estacion inicial, una estacion destino y numeros de bicicletas que transporta y los kilomertos
    public void añadirFurgoneta(int furg, int estInicial, int estDestino, int bic, double km){
        furgos[furg][0] = estInicial;
        furgos[furg][1] = estDestino;
        furgos[furg][2] = -1;
        furgos[furg][3] = bic;
        furgos[furg][4] = 0;
        furgos[furg][5] = km;
    }

    //OPERADOR 4: asignar dos estaciones destino y sus bicicletas (FxE) # varia la ruta de la furgo y sus kilometros
    public void asignarEstacionDestino(int furg,int est0, int est1, int bic1, int est2, int bic2, int km){
        furgos[furg][0] = est0;
        furgos[furg][1] = est1;
        furgos[furg][2] = est2;
        furgos[furg][3] = bic1;
        furgos[furg][4] = bic2;
        furgos[furg][5] = km;

    }

    //Verificar si vale la pena hacer ruta con dos destinos, vale la pena si el primer destino esta dentro de unas determinadas coordenadas y el segundo esta en otras determindas coordendas
    public boolean valeLaPena(int furg, Estacion est1, Estacion est2){
        boolean vale = false;
        if(furgos[furg][0] == -1){
            if(est1.getCoordX() > 2500 && est1.getCoordX() < 7500 && est1.getCoordY() > 2500 && est1.getCoordY() < 7500){
                if(!(est2.getCoordX() > 2500 && est2.getCoordX() < 7500 && est2.getCoordY() > 2500 && est2.getCoordY() < 7500)){
                    vale = true;
                }
            }
        }
        return vale;
    }

    
    
    
}

