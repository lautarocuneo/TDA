
//izquierda Dominane. complejidad Theta(n*log(n)) por teorema maestro

public class izquierdaDominante {

    
    
    izquierdaDominante(){




        
    }

    public static boolean solve( int[] a, int inicio, int fin) {
        // Caso base: si el subarray tiene solo un elemento, es "más a la izquierda"
        if (fin - inicio == 1) {
            return true;
        }
        int mid = inicio + (fin - inicio) / 2;
        // Conquista: verifica si ambas mitades son "más a la izquierda"
        if (sumaMitadIzq(a, inicio, mid) > sumaMitadDerecha(a, mid, fin) && solve(a, inicio, mid) && solve(a, mid, fin)) {
            return true;
        } else {
            return false;
        }
     
    }


    public static int sumaMitadIzq(int[] a, int inicio, int mid) {
        int suma = 0;
        for (int i = inicio; i < mid; i++) {
            suma += a[i];
        }
        return suma;
    }
    
    public static int sumaMitadDerecha(int[] a, int mid, int fin) {
        int suma = 0;
        for (int i = mid; i < fin; i++) {
            suma += a[i];
        }
        return suma;
    }
    
    public static void main(String[] args) {
        // Aquí va tu código
        int[] miArray = {8, 6, 7, 4, 5, 1, 3, 2};
        int[] miArray2 = {8, 4, 7, 6, 5, 1, 3, 2};

        





        System.out.println(solve(miArray, 0, miArray.length));

        System.out.println(solve(miArray2, 0, miArray2.length));


        
    }
}