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
}

