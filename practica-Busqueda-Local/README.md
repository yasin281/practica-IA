Problema:

Representacion del Estado
La representacion del estado consta de un vector de furgonetas, donde F[0] indica la furgoneta a tratar,este contiene 3 elementos como máximo de una posible ruta. Primer elemento, partiendo de un estacion inicial donde recogera x bicicletas, siendo 0<=x <=30 . Segundo elemento que será el primer destino y un entero que indica nb a descargar. El tercer elemento, sera opcional ya que e trata del posible tercer destino.

//Estructura de Datos
vector <vector<int>> F (f,6);
	{
		f[0] = Estacion ini;
	f[1] = Estacion1 dest1;		
	f[2] = Estacion2 dest2; (opcional)
	f[3]= nb1;
	f[4] = nb2;
	f[5] = recorrdioTotalKM;
}

//nb1 + nb2 =< 30(duda → una furgo debe estar vacia)
//recorrdioTotalKM = d(Est1) + d(Est2)
// nb1,nb2 son las bicis que hay que dejar en las estaciones respectivas, NO las que tienen

LAS ESTACIONES SERA UN "VECTOR" ESTATICO, DE TAL MANERA QUE PODEMOS MAPEAR UNA ESTACION A UN NUMERO DETERMINADO