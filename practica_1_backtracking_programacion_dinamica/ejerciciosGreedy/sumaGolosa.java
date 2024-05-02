package ejerciciosGreedy;
import java.util.Arrays;
import java.util.Collections;


public class sumaGolosa {
    
//suma golosa 
/*
 *
 * 
 * 
 * 
 * 
 * 
 */

public static void main(String[] args) {


    Integer[] conjunto = {1,2,5};

    // Ordenar el array en orden descendente
    Arrays.sort(conjunto, Collections.reverseOrder()); //esto cuesta O(n * log n)
  
    /*explicacion: podemos usar un min heap, 
     * basicamente metemos todos los numeros, despues sumamos los dos mas chicos , los sacamos y metemos la suma, 
     * automaticamente se acomodara donde tenga que ir, y asi sucesivamente mientras vamos sumando el costo total.
     * tiene complejidad O(n log n) debido a que agregar o quitar elementos en el min heap es O(log n) y realizamos la operacion n veces.
     * 
     * la complejidad espacial es los O(n) , ya que son n elementos los que tiene el heap.
     * 
     */




}



}
    

