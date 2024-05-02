package ejerciciosGreedy;
import java.util.Arrays;
import java.util.Collections;

public class sumaSelectiva {


/*para este problema podemos ordenar el conjunto de forma descendente y agarrar los primeros k. 

esto costaria O(n log n)
 * 
 * 
 */


public static void main(String[] args) {


    Integer[] conjunto = {3, 5, 2, 6, 1};

    // Ordenar el array en orden descendente
    Arrays.sort(conjunto, Collections.reverseOrder()); //esto cuesta O(n * log n)
    int k = 4;

    int res = 0;
    for(int i = 0; i < k; i++) {
        res += conjunto[i];


    }
    
    System.out.println(res);
    
}
//para el punto c podria usar un heap sort con un max heap? y ordenar los primeros k elementos.. ? 


}