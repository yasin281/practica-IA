import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import IA.Bicing.Estacion;
import java.util.Random;
import IA.Bicing.Estaciones;
// import IA.Connectat.ES;

public class BicingEstado{
    // Punto a tener en cuenta por parte de J
    // estacionCogida (para cada estacion) Opcional si no hay origen
    // bicisQueFaltan (para cada estacion) HECHO
    // que el estado inicial tenga origenes ya asignados HECHO
    // los operadores que actualizen bien los atributos de estacionesVisitadas, furgos y bicisQueFaltan HECHO
    // operador de cambiar destino 2 y luego 1

    //Matriz de furgonetas
    private double[][] furgos;    
    //TODOS LOS CAMBIOS SE HACEN EN ESTOS DOS VECTORES
    private double[]bicisQueFaltan; //son aquellas que bicicletas que faltan en cada estacion
    private double[]biciQueSobran; //son aquellas que sobran en cada estacion
    private double[]asigFurgos; //asignacion de furgonetas a estaciones
    //funcion para tener el objeto de las estaciones
    private Estaciones estaciones;
        public BicingEstado(Estaciones estaciones) {
        this.estaciones = estaciones;
    }

    public void inicializarFurgos(int nfurg){
        furgos = new double[nfurg][7];
        for(int i = 0; i < nfurg; ++i){
            for(int j = 0; j < 7; ++j){
                // if(j == 0 && i==0 || j==1 && i==1){
                //     furgos[i][j] = -1;
                // }
                // furgos[i][j] = 0;
                if(j == 0 || j == 1 || j == 2) furgos[i][j] = -1;
                else furgos[i][j] = 0;
            }
        }
        //inciializar bicis que faltan
        bicisQueFaltan = new double[estaciones.size()];
        asigFurgos = new double[estaciones.size()];
        for(int i = 0; i < estaciones.size(); ++i){
            bicisQueFaltan[i] = Math.max(estaciones.get(i).getDemanda() - estaciones.get(i).getNumBicicletasNext(),0);
            asigFurgos[i] = -1;
        }
        //inicilizar bicis que sobran
        biciQueSobran = new double[estaciones.size()];
        for(int i = 0; i < estaciones.size(); ++i){
            if(estaciones.get(i).getNumBicicletasNext()-estaciones.get(i).getDemanda()>0){ 
                double aux=(estaciones.get(i).getNumBicicletasNext()-estaciones.get(i).getDemanda());
                biciQueSobran[i] = Math.min(aux,estaciones.get(i).getNumBicicletasNoUsadas());
            }
            else biciQueSobran[i] = 0;
        }
    }

    //Operador 1: Dado una furgoneta asigna un destino
    public void cambiarDestino(int Idfurg, int IDdest){
        //Si el destino es el mismo que el que ya tiene o no hay bicis que faltan en la estacion o ya tiene un destino asignado
        if((furgos[Idfurg][0]==IDdest) || (bicisQueFaltan[IDdest]<= 0)) return;
        
        //Si ya tiene un destino asignado se le quita las bicis que se lleva y se le suma a las que faltan 
        if(furgos[Idfurg][1]!=-1){
            Estacion estAntiguo=estaciones.get((int)furgos[Idfurg][1]);
            
            furgos[Idfurg][5]-=distanciaManhattan(estaciones.get((int)furgos[Idfurg][0]),estAntiguo)/1000;

            // if(bicisQueFaltan[(int)furgos[Idfurg][1]]>0) {
            //     double cubrirFalta=bicisQueFaltan[(int)furgos[Idfurg][1]]-furgos[Idfurg][3];
            //     if(cubrirFalta<0){
            //         biciQueSobran[(int)furgos[Idfurg][0]]+=-1*cubrirFalta;
            //         bicisQueFaltan[(int)furgos[Idfurg][1]]=0;
            //     }
            //     else bicisQueFaltan[(int)furgos[Idfurg][1]]-=furgos[Idfurg][3];
            // }
            // else biciQueSobran[(int)furgos[Idfurg][1]]+=furgos[Idfurg][3];
            // //restar el beneficio de la ruta            
            furgos[Idfurg][6]-=furgos[Idfurg][3];
            bicisQueFaltan[(int)furgos[Idfurg][0]]+=furgos[Idfurg][3];
        }

            Estacion e1=estaciones.get(getEstacionInicial(Idfurg));
            Estacion dest1=estaciones.get(IDdest);

            furgos[Idfurg][1] = IDdest;
            furgos[Idfurg][2] = -1;

            furgos[Idfurg][3]  = Math.min(bicisQueFaltan[IDdest],biciQueSobran[(int)furgos[Idfurg][0]]);

            biciQueSobran[(int)furgos[Idfurg][0]]-=furgos[Idfurg][3];
            //biciQueSobran[IDdest]+=furgos[Idfurg][3];
            bicisQueFaltan[IDdest]-=furgos[Idfurg][3];        

            furgos[Idfurg][5] += distanciaManhattan(e1,dest1)/1000;

            furgos[Idfurg][6] += furgos[Idfurg][3];//calculaBeneficioRuta(e1,dest1);
    }

    /*FALTA POR IMPLEMENTAR */

    //OPERADOR 2 : DADO UNA FURGONETA ASIGNA UN DESTINO 2
    //tiene sentido ir a otro destino si aun sobran bicicletas cuando ya hemos ido
    public void cambiarDestino2(int Idfurg, int IDdest){

        //SI ENTRAMOS AQUI ES PORQUE LA FURGO YA TIENE 1 DESTINO ASIGNADO Y QUEREMOS ASIGNARLE UN SEGUNDO DESTINO O CAMBIAR EL SEGUNDO DESTINO 
        //Y NOS ASEGURAMOS QUE CON EL NUEVO DESTINO HABRA BENEFICIO
        if(furgos[Idfurg][0]==IDdest || furgos[Idfurg][1]==IDdest || (bicisQueFaltan[IDdest] <= 0 ||furgos[Idfurg][1]==-1 )) return;
        int dest1 = (int)furgos[Idfurg][1];
        int dest2 = IDdest;
        //Si la furgoneta ya tenia un segundo destino, hay que cambiar los datos (sobras, faltas, beneficio, kilometros...)
        if(furgos[Idfurg][2]!=-1){
            Estacion estAntiguo=estaciones.get((int)furgos[Idfurg][2]);
            furgos[Idfurg][5]-=distanciaManhattan(estaciones.get((int)furgos[Idfurg][1]),estAntiguo)/1000;
            //si ya tiene un destino 2 asignado
            //restar el beneficio de la ruta
            furgos[Idfurg][6]-=furgos[Idfurg][4];
            //sumar las bicis que faltan en el destino 2
            //si habiamos decido ir al antigua destino 2, es porque faltaban bicis, pues ahora como cambiamos, hay que sumar las bicis que faltan que no transportaremos
            bicisQueFaltan[(int)furgos[Idfurg][2]]+=furgos[Idfurg][4];
            //restar las bicis que sobran en el destino 1
            biciQueSobran[(int)furgos[Idfurg][0]]+=furgos[Idfurg][4];
            }

            int sobran = (int)biciQueSobran[(int)furgos[Idfurg][0]]; //aqui sobraran bicicletas si al primer destino no se ha llevado todas 
            if(sobran > 0){
                Estacion e1=estaciones.get(dest1);
                Estacion e2=estaciones.get(dest2);

                furgos[Idfurg][2] = dest2;
                furgos[Idfurg][4]  = Math.min(bicisQueFaltan[dest2],sobran);

                biciQueSobran[(int)furgos[Idfurg][0]]-=furgos[Idfurg][4];
                bicisQueFaltan[dest2]-=furgos[Idfurg][4];        

                furgos[Idfurg][5] += distanciaManhattan(e1,e2)/1000;

                furgos[Idfurg][6] += furgos[Idfurg][4];//calculaBeneficioRuta(e1,dest1);
                //printa algo
                }
    }




    //solucion desarollada --> un arraylist de tantos elementos como estaciones y donde cada posicion tiene 2 elementos, donde el primer elemento es la beneficio y el segundo es la posicion de la estacion

    public void GreedyIni(){
        double[][] beneficio;
        int nest = estaciones.size();
        beneficio = new double[nest][2];
        for(int j = 0; j < nest; ++j) {
            if(estaciones.get(j).getDemanda()>estaciones.get(j).getNumBicicletasNext()){//esto quiere decir que el bene
                beneficio[j][0] = estaciones.get(j).getDemanda()-estaciones.get(j).getNumBicicletasNext();
            }
            else{
                //demanda menor que las que habrán, hay que hacer un min entre no usadas y la diferencia
                beneficio[j][0] = -Math.min(estaciones.get(j).getNumBicicletasNoUsadas(),estaciones.get(j).getNumBicicletasNext()-estaciones.get(j).getDemanda());
            }
            beneficio[j][1] = j;
        }
        //ordena de mayor a menor pero solo mira el elemento [0] 
        for(int j = 0; j < nest; ++j){
            for(int k = j+1; k < nest; ++k){
                if(beneficio[j][0] < beneficio[k][0]){
                    double aux = beneficio[j][0];
                    beneficio[j][0] = beneficio[k][0];
                    beneficio[k][0] = aux;
                    aux = beneficio[j][1];
                    beneficio[j][1] = beneficio[k][1];
                    beneficio[k][1] = aux;
                }
            }
        }
        int tamf = furgos.length;
        if(tamf < nest){
            for(int i = 0; i < tamf; ++i){ 
                //assigna estacion inicial de la furgos como i
                furgos[i][0] = beneficio[nest-i-1][1]; //asignamos estacion de abajo de beneficio
                asigFurgos[(int)beneficio[nest-i-1][1]]=i;
            }
        }
        else{
            for(int i = 0 ; i < nest;++i){
                furgos[i][0] = beneficio[nest-i-1][1]; //asignamos estacion de abajo de beneficio
                asigFurgos[(int)beneficio[nest-i-1][1]]=i;
            }
        }
        this.printFurgos(); 
        //printea asigfurgos
        for(int i = 0; i < asigFurgos.length; ++i){
            System.out.print(asigFurgos[i] + " ");
        }   
    }

    /*Funciones de operaciones */
    public double calculaBeneficioRuta(Estacion e1, Estacion e2){
        int beneficio = Math.min(Math.min(Math.max(e2.getDemanda() - e2.getNumBicicletasNext(), 0), e1.getNumBicicletasNoUsadas()),30);
        return beneficio;
    }

    //Crear una funcion que dado una estacion y otra, calcule la distancia entre ellas manhatan
    public double distanciaManhattan(Estacion e1, Estacion e2){
        double distancia = 0;
        distancia = Math.abs(e1.getCoordX()-e2.getCoordX())+Math.abs(e1.getCoordY()-e2.getCoordY());
        return distancia;
    }

    /*Funcion para imprimir la matriz */
    public void printFurgos(){
        for(int i = 0; i < furgos.length; ++i){
            for(int j = 0; j < furgos[0].length; ++j){
                System.out.print(furgos[i][j] + " ");
            }
            System.out.println();
        }
    }

    /*Funcion Copia */
    public BicingEstado(BicingEstado e){
        furgos=new double[e.furgos.length][e.furgos[0].length];
        for (int i = 0; i < e.furgos.length; i++) {
          for (int j = 0; j < e.furgos[0].length; j++) {
            furgos[i][j]=e.furgos[i][j];
          }
        }
        bicisQueFaltan=new double[e.bicisQueFaltan.length];
        for (int i = 0; i < e.bicisQueFaltan.length; i++) {
          bicisQueFaltan[i]=e.bicisQueFaltan[i];
        }
        biciQueSobran=new double[e.biciQueSobran.length];
        for (int i = 0; i < e.biciQueSobran.length; i++) {
          biciQueSobran[i]=e.biciQueSobran[i];
        }

        asigFurgos=new double[e.asigFurgos.length];
        for (int i = 0; i < e.asigFurgos.length; i++) {
          asigFurgos[i]=e.asigFurgos[i];
        }
        estaciones=e.estaciones;
    }

    public void printAsigFurgos(){
        for(int i = 0; i < asigFurgos.length; ++i){
            System.out.print(asigFurgos[i] + " ");
        }
        System.out.println();
    }

    /*Funciones para obtener informacions */

    public int getNumFurgonetas(){
        return furgos.length;
    }

    public int getNest(){
        return estaciones.size();
    }

    public int getEstacionInicial(int furg){
        return (int)furgos[furg][0];
    }

    public int getEstacionDestino1(int furg){
        return (int)furgos[furg][1];
    }

    public int getEstacionDestino2(int furg){
        return (int)furgos[furg][2];
    }

    //Funcion para calcular el beneficio de una solucion
    public double getBeneficioTotal(){
        double beneficio = 0;
        for(int i = 0; i < furgos.length; ++i){
            beneficio += furgos[i][6];
        }
        System.out.println("Beneficio total: " + beneficio);
        return -beneficio;
    }

    /*Funcion Heuristica */
    public double valorHeuristica(){
        return heuristca1();
    }

    private double heuristca1(){
         return getBeneficioTotal();
    }
}