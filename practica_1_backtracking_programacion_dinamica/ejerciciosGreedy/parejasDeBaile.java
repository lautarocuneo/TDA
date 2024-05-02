package ejerciciosGreedy;


public class parejasDeBaile {

/*
 tenemos dos conjuntos de personas A y B, para cada persona sabemos su habilidad.
 queremos armar la maxima cantidad de parejas de baile, sabiendo que para cada pareja debemos elegir exactamente una persona de cada conjunto de modo que:

 ABS (habilidad(personadeA) - habilidad(personadeB)) <= 1
  
  cada persona puede pertenecer solo a 1 pareja.

  ejemplo:

  A = {1,2,4,6}

  B = {1,5,5,7,9}

  maxima cantidad = 3 : {(1,1),(4,5),(6,5)}

  ejemplo 2: 

  A = {1,1,1,1,1}

  B = {1,2,3}

  maxima cantidad = 2 : {(1,1),(1,2)}

a) considerando que los conjuntos estan ordeandos en forma creciente, observar que se puede obtener la solucion recorriendo los conjuntos asi nomas.

b) diseñar algoritmo goloso basado en a) que recorra una unica vez cada multiconj. explicitar la complejidad temporal y espacial auxiliar.

  
 */



    
//complejidad temporal: en peor caso , que es cuando puedo armar todas las parejas, O(|A| + |B|) , es lineal, como una especie de algoritmo de merge.

//complejidad espacial auxiliar ? no se a que se refiere.
public static void main(String[] args) {
    int[] parejasA = {2,3,4};
    int[] parejasB = {4};

    int i = 0;
    int j = 0;

    int contadorParejas = 0;

    while (i < parejasA.length && j < parejasB.length) {
        if (Math.abs(parejasA[i] - parejasB[j]) <= 1) {
            contadorParejas++;
            i++;
            j++;
        } else if (parejasA[i] < parejasB[j]) {
            i++;
        } else {
            j++;
        }
    }

    System.out.println(contadorParejas);
}


    /* para probar que un greedy es correcto, tengo que mostrar dos cosas distintas. primero hay ue mostrar ue el algoritmo produce una solucion factible.
    despues hay que probar que el algoritmo hace una solucion iptima.

    probamos por induccion que despues de k comparaciones de parejas, contadorParejas es la solucion optima.
    como caso base , si las listas son vacias las parejas son cero.
    
    que quiero probar ?
    que mi solucion es la mejor. como lo hago ? 

    si ambos estan ordeandos , la mejor solucion que puedo conseguir es comparar indice a indice cada elemento y ver su diferencia 


    bue, ni idea como probarlo. preguntar.

        */
}