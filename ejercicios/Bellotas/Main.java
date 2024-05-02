
import java.util.*;

public class Main {

static public int[][] memo;
static public int[][] memo2;



static public int[][] matrizBellotas;


// Inicializa la matriz memo en el constructor o en algún método de inicialización
public Main(int alturaMaxima, int cantArboles) {
    /* //esto era para la solucion top down.
    memo = new int[alturaMaxima + 1][cantArboles];
    for (int i = 0; i <= alturaMaxima; i++) {
        for (int j = 0; j < cantArboles; j++) {
            memo[i][j] = -1;
        }
    }
    */
   
}

//la idea es la siguiente, quiero el maximo entre dos opciones : quedarme en el arbol que estoy ahora, y saltar a cualquier otro arbol. Voy a implementar esa idea Top-down primero.
public int solucionRecursiva(int alturaActual, int arbolActual, int alturaVuelo) {

    //caso base sera cuando llego a la altura 0.

    if(alturaActual == 0) { 
        return 0; 
    }


    //caso recursivo 1: me quedo en el mismo arbol
    int mismoArbol = solucionRecursiva(alturaActual - 1, arbolActual, alturaVuelo) + matrizBellotas[alturaActual][arbolActual];

    //guardo el resultado hasta ahora.
    int resultado = mismoArbol;


    //caso recursivo 2: pruebo en todos los otros arboles volando (si me queda altura)
    if(alturaActual >= alturaVuelo){

        for (int otroArbol = 0; otroArbol < matrizBellotas.length; otroArbol++) { // Para cada otro árbol
            if (otroArbol != arbolActual) { // Si no es el árbol actual
                resultado = Math.max(mismoArbol, solucionRecursiva(alturaActual - alturaVuelo, otroArbol, alturaVuelo) + matrizBellotas[alturaActual][arbolActual]);
            }
        }
    
    }
    return resultado;

}

public int solucionBottomUp(int alturaMaxima, int cantArboles, int saltoArdilla) {


    //inicializo una matriz que me va a ir acumulando los maximos de volar o seguir por el mismo arbol en cada posicion.
    //tiene el mismo tamaño que la matriz de bellotas.
    memo2 = new int[cantArboles][alturaMaxima + 1];

    //se que en la altura maxima, es decir cuando todavia no empece el recorrido, el maximo posible en todos los arboles de la altura maxima
    //es simplemente la cantidad de bellotas que haya en esa altura maxima. Asi que completo el primer nivel de a matriz con esa data.
    for (int i = 0; i < cantArboles; i++) {
        memo2[i][alturaMaxima] = matrizBellotas[i][alturaMaxima];
    }

    //el primer for va desde la altura maxima - 1 (ya que la maxima ya la complete antes) hasta cero, ya que es el nivel que tengo completo el de la altura maxima
    for (int altura = alturaMaxima - 1; altura >= 0; altura--) {
        //el segundo for va desde el primer arbol hasta el ultimo simplemente.
        for (int arbol = 0; arbol < cantArboles; arbol++) {

            //para el arbol y altura actual primero me guardo el anterior + lo que hay en el arbol y actura actual (siguiendo por el mismo arbol digamos.)
            memo2[arbol][altura] = memo2[arbol][altura + 1] + matrizBellotas[arbol][altura];

            //si la altura actual + el salto de la ardilla es menor o igual que la altura maxima, o sea "puedo volar", entonces me fijo ese caso.
            if (altura + saltoArdilla <= alturaMaxima) {


                //me fijo todas las posibilidades volando a otro arbol.
                for (int otroArbol = 0; otroArbol < cantArboles; otroArbol++) {

                    //"arbol" es el arbol actual, entonces lo comparo con otroArbol y si es distinto ahi reviso la opcion.
                    if(otroArbol != arbol){
                        
                        // finalmente si me da la altura , y el arbol es distinto al actual, entonces 

                        //me fijo el maximo entre lo que ya tenia y saltar a ese otro arbol.
                    memo2[arbol][altura] = Math.max(memo2[arbol][altura], memo2[otroArbol][altura + saltoArdilla] + matrizBellotas[arbol][altura]);

                    }
                }
            }
        }
    }

    //finalmente el maximo va a estar almacenado en las posiciones 0 de altura de la matriz,
    //es decir, en la posicion memo2[arbol][0] , esta el maximo de haber empezado por cada arbol, por lo que voy a tener que buscar el maximo entre todos esos.
    int bellotas = 0;

    for (int arbol = 0; arbol < cantArboles; arbol++) {
        bellotas = Math.max(bellotas, memo2[arbol][0]);
    }

    return bellotas;
}

//el anterior era muy lento
//no hace falta iterar por todos los otros arboles buscando el maximo cada vez, simplemente alcanza con guardarme el maximo entre haber saltado o haber seguido por el mismo arbol
//para cada arbol en cada altura.
//al momento de hacer un salto
//creo que ni siquiera necesito la matriz memo2, podria hacerlo solo con la de bellotas.
public int solucionBottomUp2(int alturaMaxima, int cantArboles, int saltoArdilla) {


    //inicializo una matriz que me va a ir acumulando los maximos de volar o seguir por el mismo arbol en cada posicion.
    //tiene el mismo tamaño que la matriz de bellotas.
    memo2 = new int[cantArboles][alturaMaxima + 1];
    int[] acumulacionMaximaPorAltura = new int[alturaMaxima + 1];

    //se que en la altura maxima, es decir cuando todavia no empece el recorrido, el maximo posible en todos los arboles de la altura maxima
    //es simplemente la cantidad de bellotas que haya en esa altura maxima. Asi que completo el primer nivel de a matriz con esa data.
    for (int arbol = 0; arbol < cantArboles; arbol++) {
        //pongo el maximo posible de la altura maxima en el array de acumulacionMaximaPorAltura al comienzo
        acumulacionMaximaPorAltura[alturaMaxima] = Math.max(matrizBellotas[arbol][alturaMaxima], acumulacionMaximaPorAltura[alturaMaxima]);
        memo2[arbol][alturaMaxima] = matrizBellotas[arbol][alturaMaxima];

    }

    
    for (int altura = alturaMaxima - 1; altura >= 0; altura--) {
        
        for (int arbol = 0; arbol < cantArboles; arbol++) {

            //esto es lo mismo , me guardo el caso seguir bajando por el mismo arbol.
            memo2[arbol][altura] = memo2[arbol][altura + 1] + matrizBellotas[arbol][altura];

            //y ahora el caso "puedo saltar"
            if (altura + saltoArdilla <= alturaMaxima) {

                    //la diferencia esta aca, para cada arbol, voy a querer el maximo entre seguir por el mismo, o haber venido volando de otro arbol  + lo actual.
                    //pero no es cualquier arbol, es el MAXIMO de esa altura que ya lo teniamos calculado.
                    memo2[arbol][altura] = Math.max(memo2[arbol][altura], acumulacionMaximaPorAltura[altura + saltoArdilla] + matrizBellotas[arbol][altura]);
 
                
            }

            //ahora actualizo mi acumulacionMaximaPorAltura que estara guardado en uno de los arboles del memo
            //o sea lo mismo, tengo que buscar el maximo entre todos los arboles.
            //que a su vez cada arbol del memo2 tiene guardado cual seria el maximo entre haber venido por el mismo arbol o volando desde otro.
            acumulacionMaximaPorAltura[altura] = Math.max(memo2[arbol][altura], acumulacionMaximaPorAltura[altura]);

        }
    }
    //finalmente mi maximo de todos estara almacenado en el primer elemento de acumulacionMaximaPorAltura al final de los dos ciclos.
    return acumulacionMaximaPorAltura[0];
}



//bueno ahora lo mismo pero guardo todo tipo topDown en una matriz: 

public int solucionTopDown(int alturaActual, int arbolActual, int alturaVuelo) {

    

    // Caso base: cuando llego a la altura 0.
    if(alturaActual == 0) { 
        return 0; 
    }

    // Si el valor ya ha sido calculado, devuélvelo
    if(memo[alturaActual][arbolActual] != -1) {
        return memo[alturaActual][arbolActual];
    }

    // Caso recursivo 1: me quedo en el mismo árbol
    int mismoArbol = solucionTopDown(alturaActual - 1, arbolActual, alturaVuelo) + matrizBellotas[alturaActual][arbolActual];

    // Guardo el resultado hasta ahora.
    int resultado = mismoArbol;

    // Caso recursivo 2: pruebo en todos los otros árboles volando (si me queda altura)
    if(alturaActual >= alturaVuelo){
        for (int otroArbol = 0; otroArbol < matrizBellotas[0].length; otroArbol++) { // Para cada otro árbol
            if (otroArbol != arbolActual) { // Si no es el árbol actual
                resultado = Math.max(resultado, solucionTopDown(alturaActual - alturaVuelo, otroArbol, alturaVuelo) + matrizBellotas[alturaActual][arbolActual]);
            }
        }
    }

    // Almacena el resultado en la matriz memo y lo devuelve
    memo[alturaActual][arbolActual] = resultado;

    return memo[alturaActual][arbolActual];
}



public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    // Lee el número de conjuntos de datos
    int conjData = scanner.nextInt();

    for (int i = 0; i < conjData; i++) {
       
        int cantArboles = scanner.nextInt();
        int alturaArboles = scanner.nextInt();
        int saltoArdilla = scanner.nextInt();

        //inicializo la matriz de bellotas
        matrizBellotas = new int[cantArboles][alturaArboles + 1];

        // Llena la matriz de bellotas
        for (int j = 0; j < cantArboles; j++) {
            int a = scanner.nextInt();
            for (int k = 0; k < a; k++) {
                int n = scanner.nextInt();
                matrizBellotas[j][n]++;
            }
        }

        

        // Inicializa la matriz memo

        
        Main bellotas = new Main(alturaArboles, cantArboles); 

        
        
        //long inicio = System.currentTimeMillis();
        //int bellotasMaximas = 0;
        /* //esto era para la solucion top down
        for (int arbolActual = 0; arbolActual < cantArboles; arbolActual++) {
            bellotasMaximas = Math.max(bellotasMaximas, bellotas.solucionTopDown(alturaArboles, arbolActual, saltoArdilla));
        }
        */
        System.out.println(bellotas.solucionBottomUp2(alturaArboles, cantArboles, saltoArdilla));

        //long fin = System.currentTimeMillis();
        //long tiempoTranscurrido = fin - inicio;
        //System.out.println(bellotasMaximas);
        //System.out.println(tiempoTranscurrido + " milisegundos");
        

    }

    scanner.close();
}

}

