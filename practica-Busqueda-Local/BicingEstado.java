import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import IA.Bicing.Estacion;
import java.util.Random;
import IA.Bicing.Estaciones;
// import IA.Connectat.ES;
import IA.Connectat.ES;

public class BicingEstado{

    // estacionCogida (para cada estacion) Opcional si no hay origen
    // bicisQueFaltan (para cada estacion) 
    // que el estado inicial tenga origenes ya asignados 
    // los operadores que actualizen bien los atributos de estacionesVisitadas, furgos y bicisQueFaltan 
    // operador de cambiar destino 2 y luego 1

    //Matriz de furgonetas
    private double[][] furgos;    
    //TODOS LOS CAMBIOS SE HACEN EN ESTOS DOS VECTORES
    private double[]bicisQueFaltan; //son aquellas que bicicletas que faltan en cada estacion
    private double[]biciQueSobran; //son aquellas que sobran en cada estacion
    private double[]estacionOrigenFurgo; //asignacion de furgonetas a estaciones
    //funcion para tener el objeto de las estaciones
    private int tipoHeuristica;

    private Estaciones estaciones;
        public BicingEstado(Estaciones estaciones) {
        this.estaciones = estaciones;
    }

    //Funcion que inicializa las furgonetas (todos sin estacion inicial ni destinos)
    //inicializa los vectores de bicis que faltan y sobran
    public void inicializarFurgos(int nfurg){
        estacionOrigenFurgo = new double[nfurg];
        furgos = new double[nfurg][5];
        for(int i = 0; i < nfurg; ++i){
            for(int j = 0; j < 5; ++j){
                if(j == 0 && i==0 || j==1 && i==1){
                    furgos[i][j] = -1;
                }
                furgos[i][j] = 0;
                if(j == 0 || j == 1 || j == 2) furgos[i][j] = -1;
                else furgos[i][j] = 0;
            }
            estacionOrigenFurgo[i] = -1;
        }
        //inciializar bicis que faltan
        bicisQueFaltan = new double[estaciones.size()];
        //inicilizar bicis que sobran
        biciQueSobran = new double[estaciones.size()];

        for(int i = 0; i < estaciones.size(); ++i){
            
            bicisQueFaltan[i] = Math.max(estaciones.get(i).getDemanda() - estaciones.get(i).getNumBicicletasNext(),0);
            
            if(estaciones.get(i).getNumBicicletasNext()-estaciones.get(i).getDemanda()>0){ 
                double aux=(estaciones.get(i).getNumBicicletasNext()-estaciones.get(i).getDemanda());
                biciQueSobran[i] = Math.min(Math.min(aux,30),estaciones.get(i).getNumBicicletasNoUsadas());
            }
            
            else biciQueSobran[i] = 0;
        }

    }



    /*Funciones de Operadores y Condiciones de Aplicabilidad */

    //Verifca condicion de aplicabilidad del operador 1 : cambiarDestino
    //Si el destino es el mismo que el que ya tiene o no hay bicis que faltan en la estacion o ya tiene un destino asignado
    public boolean condicionAplicabilidadDestino1(int Idfurg, int IDdest){
        if((furgos[Idfurg][0]==IDdest) || (bicisQueFaltan[IDdest]<= 0) || furgos[Idfurg][0]==-1) return false;
        return true;
    }


    //Operador 1: Dado una furgoneta asigna un destino
    public void cambiarDestino(int Idfurg, int IDdest){
        //Si el destino es el mismo que el que ya tiene o no hay bicis que faltan en la estacion o ya tiene un destino asignado
        //Si ya tiene un destino asignado se le quita las bicis que se lleva y se le suma a las que faltan 
        if(furgos[Idfurg][1]!=-1){
            Estacion estAntiguo=estaciones.get((int)furgos[Idfurg][1]);
            

            if(bicisQueFaltan[(int)furgos[Idfurg][1]]>0) {
                double cubrirFalta=bicisQueFaltan[(int)furgos[Idfurg][1]]-furgos[Idfurg][3];
                if(cubrirFalta<0){
                    biciQueSobran[(int)furgos[Idfurg][0]]+=-1*cubrirFalta;
                    bicisQueFaltan[(int)furgos[Idfurg][1]]=0;
                }
                else bicisQueFaltan[(int)furgos[Idfurg][1]]-=furgos[Idfurg][3];
            }
            else biciQueSobran[(int)furgos[Idfurg][1]]+=furgos[Idfurg][3];
            //restar el beneficio de la ruta            
            bicisQueFaltan[(int)furgos[Idfurg][0]]+=furgos[Idfurg][3];
        }
        Estacion e1=estaciones.get(getEstacionInicial(Idfurg));
        Estacion dest1=estaciones.get(IDdest);

        furgos[Idfurg][1] = IDdest;
        furgos[Idfurg][2] = -1;

        furgos[Idfurg][3]  = Math.min(bicisQueFaltan[IDdest],biciQueSobran[(int)furgos[Idfurg][0]]);

        biciQueSobran[(int)furgos[Idfurg][0]]-=furgos[Idfurg][3];
        bicisQueFaltan[IDdest]-=furgos[Idfurg][3];        
    }


    //SI ENTRAMOS AQUI ES PORQUE LA FURGO YA TIENE 1 DESTINO ASIGNADO Y QUEREMOS ASIGNARLE UN SEGUNDO DESTINO O CAMBIAR EL SEGUNDO DESTINO
    //Verifica condicion de aplicabilidad del operador 2 : cambiarDestino2
    public boolean condicionAplicabilidadDestino2(int Idfurg, int IDdest){
        if(furgos[Idfurg][0]==IDdest || furgos[Idfurg][1]==IDdest || furgos[Idfurg][1]==-1 || furgos[Idfurg][0]==-1) return false;
        return true;
    }


    //OPERADOR 2 : DADO UNA FURGONETA ASIGNA UN DESTINO 2
    //tiene sentido ir a otro destino si aun sobran bicicletas cuando ya hemos ido
    public void cambiarDestino2(int Idfurg, int IDdest){
        Estacion origen=estaciones.get((int)furgos[Idfurg][0]);
        Estacion dest1=estaciones.get((int)furgos[Idfurg][1]);

        //si ya existe un destino 2 asignado
        if(furgos[Idfurg][2]!=-1){
            //System.out.println("Estoy aqui1");
            //si las bicis que faltan al origen es estrictamente mayor se le intenta disminuir y en caso de disminuir si es negativo se suma en bicis que sobran. En caso contrario simplemente se añade bicis a las que sobran
            if(bicisQueFaltan[(int)furgos[Idfurg][0]]>0) {
                double cubrirFalta=bicisQueFaltan[(int)furgos[Idfurg][0]]-furgos[Idfurg][4];
                if(cubrirFalta<0){
                    biciQueSobran[(int)furgos[Idfurg][0]]+=-1*cubrirFalta;
                    bicisQueFaltan[(int)furgos[Idfurg][1]]=0;
                }
                else bicisQueFaltan[(int)furgos[Idfurg][1]]-=furgos[Idfurg][4];
            }
            else biciQueSobran[(int)furgos[Idfurg][1]]+=furgos[Idfurg][4];
        }
        //System.out.println("Estoy aqui2");
        furgos[Idfurg][2] = IDdest;
        Estacion dest2=estaciones.get((int)furgos[Idfurg][2]);
        furgos[Idfurg][4]  = Math.min(bicisQueFaltan[IDdest],biciQueSobran[(int)furgos[Idfurg][0]]);

        biciQueSobran[(int)furgos[Idfurg][0]]-=furgos[Idfurg][4];
    }


    //Retorna true si el origen al que queremos asignar una furgoneta ya esta en uso
    private boolean esOrigenEnUso(int newOrigen){
        for(int i = 0; i < furgos.length; ++i){
            if(estacionOrigenFurgo[i] == newOrigen) return true;
        }
        return false;
    }
    

    //Condicion de aplicabilidad del operador 3: CambiarOrigen
    //Si el origen es el mismo que el que ya tiene o no hay bicis que sobran en la estacion o ya tiene un origen asignado
    public boolean condicionAplicabilidadOrigen(int Idfurg, int IDnewOrigin){
        if(Idfurg < 0 || IDnewOrigin < 0 || Idfurg >=furgos.length || IDnewOrigin >= estaciones.size() ) return false;
        if(esOrigenEnUso(IDnewOrigin) || (IDnewOrigin==furgos[Idfurg][1] ) || (IDnewOrigin==furgos[Idfurg][2])) return false;
        return true;
    }


    //Operador 3: Dado una furgoneta asigna un origen
    public void CambiarOrigen(int Idfurg,int IDnewOrigin){

        estacionOrigenFurgo[Idfurg] = IDnewOrigin;
        int estacion_ant = (int)furgos[Idfurg][0]; 

        if(estacion_ant == -1 || (estacion_ant != -1 && furgos[Idfurg][1] == -1) ){
            furgos[Idfurg][0] = IDnewOrigin;
        }
        
        else if(furgos[Idfurg][1] != -1 && furgos[Idfurg][0] != -1){
            //suma las bicis que tiene destino 1 al origen
            biciQueSobran[estacion_ant] += furgos[Idfurg][3];

            // System.out.println("EstoyAqui1 Cambiar Origen");
            furgos[Idfurg][0] = IDnewOrigin;

            Estacion nuevoOrigen = estaciones.get(IDnewOrigin);

            Estacion destino1 = estaciones.get((int)furgos[Idfurg][1]);

            // System.out.println("EstoyAqui2 Cambiar Origen");
            //actualizar las bicis que faltaban anteriormente
            bicisQueFaltan[(int)furgos[Idfurg][1]] += furgos[Idfurg][3];
            
            //reasignar bici para el destino 1
            furgos[Idfurg][3] = Math.min(Math.min(biciQueSobran[IDnewOrigin],30),bicisQueFaltan[(int)furgos[Idfurg][1]]);

            //System.out.println("EstoyAqui3 Cambiar Origen");

            //actualizar las bicis que sobran en el origen
            biciQueSobran[IDnewOrigin] -= furgos[Idfurg][3];

            //disminuir la demanda del destino 1
            bicisQueFaltan[(int)furgos[Idfurg][1]] -= furgos[Idfurg][3];

            // System.out.println("EstoyAqui4 Cambiar Origen");    
            if(biciQueSobran[IDnewOrigin] > 0 && furgos[Idfurg][2] != -1){
                //asignar bicis que sobran a la estacion destino 2
                // System.out.println("EstoyAqui5 Cambiar Origen");  
                //System.out.println(biciQueSobran[IDnewOrigin]);  
                
                bicisQueFaltan[(int)furgos[Idfurg][2]] += furgos[Idfurg][4];
                
                furgos[Idfurg][4] = Math.min(Math.min(biciQueSobran[IDnewOrigin],30),(bicisQueFaltan[(int)furgos[Idfurg][2]]));
                
                biciQueSobran[IDnewOrigin] -= furgos[Idfurg][4];
                bicisQueFaltan[(int)furgos[Idfurg][2]] -= furgos[Idfurg][4];
            }
            else if (furgos[Idfurg][2] != -1){
                
                bicisQueFaltan[(int)furgos[Idfurg][2]] += furgos[Idfurg][4];
                biciQueSobran[estacion_ant] += furgos[Idfurg][4];
                furgos[Idfurg][4]=0;
                furgos[Idfurg][2] = -1;
            // System.out.println("EstoyAqui6 Cambiar Origen");    
            }
            //System.out.println("EstoyAqui7 Cambiar Origen");    
            estacionOrigenFurgo[Idfurg] = IDnewOrigin;
            //System.out.println("EstoyAqui8 Cambiar Origen");    
            //si le queda
        }
   }


   //OPERADOR 4: SWAPEAR RUTA, EN VEZ DE HACER 0 -> 1 -> 2  HACER 0 -> 2 -> 1
   //distancia entre 1 y 2 son el mismo, pero tal vez es mejor ir a 2 y luego a 1 que a 1 y luego a 2
   public void SwapRuta(int Idfurg){
    if(furgos[Idfurg][1] == -1 || furgos[Idfurg][2] == -1 || furgos[Idfurg][0] == -1) return;
        bicisQueFaltan[(int)furgos[Idfurg][1]] += furgos[Idfurg][3];
        bicisQueFaltan[(int)furgos[Idfurg][2]] += furgos[Idfurg][4];
        biciQueSobran[(int)furgos[Idfurg][0]] += furgos[Idfurg][3];
        biciQueSobran[(int)furgos[Idfurg][0]] += furgos[Idfurg][4];
        furgos[Idfurg][3] = Math.min(biciQueSobran[(int)furgos[Idfurg][0]],bicisQueFaltan[(int)furgos[Idfurg][2]]);
        biciQueSobran[(int)furgos[Idfurg][0]] -= furgos[Idfurg][3];
        double aux = furgos[Idfurg][1]; //aqui esta el destino 1 anterior  que ahora sera el destino 2
        furgos[Idfurg][1] = furgos[Idfurg][2];//el antiguo destino 2 es ahora destino 1
        furgos[Idfurg][2] = aux;
        furgos[Idfurg][4] = Math.min(biciQueSobran[(int)furgos[Idfurg][0]],bicisQueFaltan[(int)aux]);
        biciQueSobran[(int)furgos[Idfurg][0]] -= furgos[Idfurg][4];
        // furgos[Idfurg][6] += furgos[Idfurg][3];
        // furgos[Idfurg][6] += furgos[Idfurg][4];
        bicisQueFaltan[(int)furgos[Idfurg][1]] -= furgos[Idfurg][3];
        bicisQueFaltan[(int)aux] -= furgos[Idfurg][4];

   }



    /* Funciones de Inicializaciones */

    //Funcion que inicializa las furgonetas con una disposicion secuencial, es decir la furgoneta i
    //tiene asignado como origen la estacion i (teniendo en cuenta las casos con diferencia de tamaños)
    public void iniTrivial(){
        if(estaciones.size()>furgos.length){
            for(int i=0; i<furgos.length;++i){
                furgos[i][0]=i;
            }
        }
        else{
            for(int i=0; i<estaciones.size();++i){
                furgos[i][0]=i;
            }
        }
    }


    //Solucion Desarollada : Asignar la estacion origen de los furgonetas a estaciones donde la demanda sea menor
    //De esta manera nos aseguramos que podemos llegar bicicletas donde haya mas demanda y tener mayor beneficio
    public void GreedyIni(){
        double[][] Demanda;
        int nest = estaciones.size();
        Demanda = new double[nest][2];
        for(int j = 0; j < nest; ++j) {
            if(estaciones.get(j).getDemanda()>estaciones.get(j).getNumBicicletasNext()){//esto quiere decir que el bene
                Demanda[j][0] = estaciones.get(j).getDemanda()-estaciones.get(j).getNumBicicletasNext();
            }
            else{
                //demanda menor que las que habrán, hay que hacer un min entre no usadas y la diferencia
                Demanda[j][0] = -Math.min(estaciones.get(j).getNumBicicletasNoUsadas(),estaciones.get(j).getNumBicicletasNext()-estaciones.get(j).getDemanda());
            }
            Demanda[j][1] = j;
        }
        //ordena de mayor a menor pero solo mira el elemento [0] 
        for(int j = 0; j < nest; ++j){
            for(int k = j+1; k < nest; ++k){
                if(Demanda[j][0] < Demanda[k][0]){
                    double aux = Demanda[j][0];
                    Demanda[j][0] = Demanda[k][0];
                    Demanda[k][0] = aux;
                    aux = Demanda[j][1];
                    Demanda[j][1] = Demanda[k][1];
                    Demanda[k][1] = aux;
                }
            }
        }
        int tamf = furgos.length;
        if(tamf < nest){
            for(int i = 0; i < tamf; ++i){ 
                //assigna estacion inicial de la furgos como i
                furgos[i][0] = Demanda[nest-i-1][1]; //asignamos estacion de abajo de Demanda
                estacionOrigenFurgo[i]=(int)Demanda[nest-i-1][1];
            }
        }
        else{
            for(int i = 0 ; i < nest;++i){
                furgos[i][0] = Demanda[nest-i-1][1]; //asignamos estacion de abajo de Demanda
                estacionOrigenFurgo[i]=(int)Demanda[nest-i-1][1];
            }
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

        estacionOrigenFurgo=new double[e.estacionOrigenFurgo.length];

        for (int i = 0; i < e.estacionOrigenFurgo.length; i++) {
          estacionOrigenFurgo[i]=e.estacionOrigenFurgo[i];
        }
        estaciones=e.estaciones;
        tipoHeuristica=e.tipoHeuristica;
    }


    /*Funcion para imprimir la matriz */
    public void printestacionOrigenFurgo(){
        for(int i = 0; i < estacionOrigenFurgo.length; ++i){
            System.out.print(estacionOrigenFurgo[i] + " ");
        }
        System.out.println();
    }


    /*Funciones para obtener informacions */

    //Funcion que retorna el numero de furgonetas
    public int getFurgo(){
        return furgos.length;
    }

    //Funcion que retorna el numero de furgonetas
    public int getNumFurgonetas(){
        return furgos.length;
    }
    //Funcion que retorna el numero de estaciones
    public int getNest(){
        return estaciones.size();
    }
    //Funcion que retorna el numero de bicis que lleva una furgoneta a su estacion destino 1
    public double Bicis1(int furg){
        return furgos[furg][3];
    }
    //Funcion que retorna el numero de bicis que lleva una furgoneta a su estacion destino 2
    public double Bicis2(int furg){
        return furgos[furg][4];
    }
    //Funcion que retorna la estacion origen de una furgoneta
    public int getEstacionInicial(int furg){
        return (int)furgos[furg][0];
    }
    //Funcion que retorna la estacion destino 1 de una furgoneta
    public int getEstacionDestino1(int furg){
        return (int)furgos[furg][1];
    }
    //Funcion que retorna la estacion destino 2 de una furgoneta
    public int getEstacionDestino2(int furg){
        return (int)furgos[furg][2]; 
    }
    
    /*Funcion que retorna el beneficio total*/
    public double getBeneficioTotal(){
        if(tipoHeuristica==0) return heuristca1();
        else return heuristca2();
    }
    //Nos retorna la distancia total recorrida por las furgonetas en kilometros
    public double getLongitudTotal(){
        double km=0;
        for(int i = 0; i < furgos.length; ++i){

            if(furgos[i][0]!=-1 && furgos[i][1]!=-1){

                Estacion origen=estaciones.get((int)furgos[i][0]);
                Estacion destino1 = estaciones.get((int)furgos[i][1]);

                km+=distanciaManhattan(origen, destino1)/1000;

                if(furgos[i][2]!=-1) km+=distanciaManhattan(destino1, estaciones.get((int)furgos[i][2]))/1000;
            }
        }
        return km;
    }

    /*Funcion Heuristica */
    public double valorHeuristica(){
        if(tipoHeuristica==0) return heuristca1();
        else return heuristca2();
    }

    /*Heuristica1: solo se tiene en cuenta el beneficio (=bicicletas transportadas a una estacion con demanda) 
    sin tener en cuenta el coste de transporte */
    private double heuristca1(){
        double beneficio = 0;
        double km=0;
        for(int i = 0; i < furgos.length; ++i){

            if(furgos[i][0]!=-1 && furgos[i][1]!=-1){

                Estacion origen=estaciones.get((int)furgos[i][0]);
                Estacion destino1 = estaciones.get((int)furgos[i][1]);

                beneficio = beneficio+furgos[i][3]+furgos[i][4];

                if(furgos[i][2]!=-1) km+=distanciaManhattan(destino1, estaciones.get((int)furgos[i][2]))/1000;
               
                km+=distanciaManhattan(origen, destino1)/1000;

            }
        }
        // System.out.println("Beneficio total: " + beneficio);        
        // System.out.println("Longitud total: " + km);
        return -beneficio;
    }

    /*Heuristica2: se tiene en cuenta el beneficio (=bicicletas transportadas a una estacion con demanda) a demnas
    del coste de transporte [(int)(nb+9)/10]*dist */
    private double heuristca2(){
        double beneficio = 0;
        double km=0;        
        for(int i = 0; i < furgos.length; ++i){
            if(furgos[i][0]!=-1 && furgos[i][1]!=-1){

                Estacion origen=estaciones.get((int)furgos[i][0]);
                Estacion destino1 = estaciones.get((int)furgos[i][1]);

                double bicicletas = furgos[i][3]+furgos[i][4];
                double km1 = distanciaManhattan(origen,destino1)/1000;
                double costeDestino1 = ((int)(bicicletas+9)/10)*km1;
                km+=km1;
                beneficio+=bicicletas; //sumas el beneficio
                //restas el coste
                if(furgos[i][2]!=-1){ //tiene dos destinos
                    Estacion destino2 = estaciones.get((int)furgos[i][2]);
                    double km2 = distanciaManhattan(destino1, destino2)/1000;
                    double costeDestino2 = km2*(((int)((bicicletas+9)/10)));
                    beneficio-=(costeDestino2);
                    
                    km=km+km2;
                }
                beneficio-=(costeDestino1);
            }
        }
        // System.out.println("Beneficio total: " + beneficio);
        // System.out.println("Longitud total: " + km);
        return -beneficio;
    }

    public void setHeuristica(int heuristica){
        tipoHeuristica=heuristica;
    }

}