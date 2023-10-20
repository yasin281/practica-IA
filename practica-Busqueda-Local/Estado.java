import java.util.ArrayList;
import java.util.List;
import IA.Bicing.Estacion;
import java.util.Random;
import IA.Bicing.Estaciones;

public class Estado{
    
    //matriz de fx6 
    private double[][] furgos;
    //funcion para inicializar esta matriz
    private Estaciones estaciones;
        public Estado(Estaciones estaciones) {
        this.estaciones = estaciones; // Almacena la instancia de Estaciones
    }
    //vector con nest elementos con -1 donde no hay furgos y i-furg donde hay furgo con i = id de furgo
    
    public void inicializarFurgos(int nfurg, int nest){
        furgos = new double[nfurg][nest];
        for(int i = 0; i < nfurg; ++i){
            for(int j = 0; j < nest; ++j){
                furgos[i][j] = 0;
            }
        }
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

    //OPERADOR 1 : DADO UNA FURGONETA LE ASIGNA UN DESTINO
    public void asignarDestino(int Idfurg, int IDdest){
        //System.out.println("IdDest: "+IDdest);
        if(furgos[Idfurg][0]==IDdest) return;
        furgos[Idfurg][1] = IDdest;
        furgos[Idfurg][2] = -1;
        furgos[Idfurg][4] = 0;
        Estacion e1=estaciones.get((int)furgos[Idfurg][0]);
        Estacion e2=estaciones.get(IDdest);
        //KM
        furgos[Idfurg][5] = distanciaManhattan(e1,e2)/1000;
        
        System.out.println("Furgo: "+Idfurg+" Estado Inicial "+ furgos[Idfurg][0]+" No usados: "+e1.getNumBicicletasNoUsadas()+" Destino: "+IDdest+" Demanda: "+e2.getDemanda()+ " Next Bicis "+e2.getNumBicicletasNext());
        furgos[Idfurg][3]  = Math.min(Math.max(e2.getDemanda() - e2.getNumBicicletasNext(), 0), e1.getNumBicicletasNoUsadas());
        //imprimir furgos[Idfurg][3]
        System.out.println("Bicis: "+furgos[Idfurg][3]);
        furgos[Idfurg][6] = calculaBeneficioRuta(e1,e2);
    }

    public int getNest(){
        return estaciones.size();
    }

    public double calculaBeneficioRuta(Estacion e1, Estacion e2){
        int beneficio = Math.min(Math.min(Math.max(e2.getDemanda() - e2.getNumBicicletasNext(), 0), e1.getNumBicicletasNoUsadas()),30);
        // beneficio = e2.getDemanda()-e2.getNumBicicletasNext(); //beneficio que puedo obtener en la estacion destino
        // beneficio -= e2.getDemanda()-e2.getNumBicicletasNext(); //beneficio que pierdo en la estacion origen
        return beneficio;
    }

    public void GreedyIni(){
      //solucion desarollada --> un arraylist de tantos elementos como estaciones y donde cada posicion tiene 2 elementos, donde el primer elemento es la beneficio y el segundo es la posicion de la estacion
        double[][] beneficio;
        int nest = estaciones.size();
        beneficio = new double[nest][2];
        for(int j = 0; j < nest; ++j){
            beneficio[j][0] = Math.min(estaciones.get(j).getDemanda()-estaciones.get(j).getNumBicicletasNext(),estaciones.get(j).getNumBicicletasNoUsadas());
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
        //printea beneficios
        for(int j = 0; j < nest; ++j){
            System.out.println(beneficio[j][0] + " " + beneficio[j][1]);
        }

        int tamf = furgos.length;

        if(tamf < nest){
            for(int i = 0; i < tamf; ++i){ 
                //assigna estacion inicial de la furgos como i
                furgos[i][0] = beneficio[nest-i-1][1]; //asignamos estacion de abajo de beneficio
            }
        }
        else{
            for(int i = 0 ; i < nest;++i){
                furgos[i][0] = beneficio[nest-i-1][1]; //asignamos estacion de abajo de beneficio
            }
        }
        //una vez tenemos el vector de beneficios para cada estacion, hay que asignar estaciones/2 furgonetas a las estaciones con menor beneficio para llevarles bicicletas a estaciones a mas demanda
        // int halfEst = nest/2;
        // if(halfEst > nfurg){ //en el caso de el numero de furgonetas sea menor que la mitad de las estaciones
        //     for(int j = 0; j < nfurg; ++j){
        //         int g = Math.min(estaciones.get(nest-j-1).getNumBicicletasNoUsadas(),30);
        //         double km = furgonetas.distanciaManhattan(estaciones.get((int)beneficio[j][1]),estaciones.get((int)beneficio[nest-j-1][1]));
        //         furgonetas.setFurgos(j, (int)beneficio[nest-j-1][1],(int)beneficio[j][1], -1, g, 0, km,0);
        //     }
        // }
        // else { 
        //     for(int j = 0; j < halfEst; ++j){
        //         int g = Math.min(estaciones.get(nest-j-1).getNumBicicletasNoUsadas(),30);
        //         double km = furgonetas.distanciaManhattan(estaciones.get((int)beneficio[j][1]),estaciones.get((int)beneficio[nest-j-1][1]));
        //         furgonetas.setFurgos(j, (int)beneficio[nest-j-1][1],(int)beneficio[j][1], -1, g, 0, km,0);
        //     }
        //     //la otra mitad estara asignada a ninguna estacion (no disponible)
        //     for(int j = halfEst; j < nfurg; ++j){
        //         furgonetas.setFurgos(j, -1, 0, 0, 0, 0, 0,0);
        //     }
        // }
        printFurgos(); 
    }

    public void SecuantialIni(){
        int i = 0;
        int nest = estaciones.size();
        int nfurg = furgos.length;
        for(; i < nest && i < nfurg; ++i){
            //kilometros manhattan entre estacion i y (i+1)%nest
                setFurgos(i,i, -1, 0, 0, 0, 0.0,0.0);
        }   
        if(i < nfurg){
            for(; i < nfurg; ++i){
                setFurgos(i,i, -1, 0, 0, 0, 0.0,0.0);
            }
        }
    }


    //funcion para cada elemento de [][]furgo le asigne 7 elementos
    public void setFurgos(int furg, int Est0,int Est1,int Est2, int B1, int B2, double Km, double beneficio){
        furgos[furg][0] = Est0;
        furgos[furg][1] = Est1;
        furgos[furg][2] = Est2;
        furgos[furg][3] = B1;
        furgos[furg][4] = B2;
        furgos[furg][5] = Km;
        furgos[furg][6] = beneficio;
    }

    //Getters
    public double[][] getFurgos(){
        return furgos;
    }

    public int getNumFurgonetas(){
        return furgos.length;
    }

    public int getEstacionInicial(int furg){
        return (int)furgos[furg][0];
    }

    public int getEstacionDestino(int furg){
        return (int)furgos[furg][1];
    }

    public int getEstacionDestino2(int furg){
        return (int)furgos[furg][2];
    }
    public int getNumBicicletasNoUsadasE(int e){
        return estaciones.get(e).getNumBicicletasNoUsadas();
    }

    public int getBicicletas(int furg){
        return (int)furgos[furg][3];
    }

    public int getBicicletas2(int furg){
        return (int)furgos[furg][4];
    }

    public double getKilometros(int furg){
        return furgos[furg][5];
    }

    public double getKilometrosTotales(){
        double km = 0;
        for(int i = 0; i < furgos.length; ++i){
            km += furgos[i][5];
        }
        return km;
    }

    //Funcion para calcular el beneficio de una solucion

    public double getBeneficioTotal(){
        double beneficio = 0;
        for(int i = 0; i < furgos.length; ++i){
            beneficio += furgos[i][6];
        }
        return beneficio;
    }

         /* Goal test */
     public boolean is_goal(){
         return false;
     }
    

    public Estado copy() {
        Estado copia = new Estado(this.estaciones);
        copia.inicializarFurgos(furgos.length, furgos[0].length);

        for (int i = 0; i < furgos.length; i++) {
            for (int j = 0; j < furgos[0].length; j++) {
                copia.furgos[i][j] = furgos[i][j];
            }
        }
        return copia;
    }


    public double valorHeuristica(){
        return heuristca1();
    }

    private double heuristca1(){
         return this.getBeneficioTotal();
    }

    private double heuristca0(){
        return 0;
    }

}

