Problema:

Representacion del Estado
La representacion del estado consta de un vector de furgonetas, donde F[0] indica la furgoneta a tratar,este contiene 3 elementos como máximo de una posible ruta. Primer elemento, partiendo de un estacion inicial donde recogera x bicicletas, siendo 0<=x <=30 . Segundo elemento que será el primer destino y un entero que indica nb a descargar. El tercer elemento, sera opcional ya que e trata del posible tercer destino.

//Estructura de Datos
vector<vector<Pair<Estacion,nb>>> rutasFurgonetas;
//Ejemplo
F[0]=vec3({E0,nb a recoger},{Ed,nb-x},{Ed2,x})
