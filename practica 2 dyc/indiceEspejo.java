public class indiceEspejo {
    //vamos a hacer una especie de busqueda binaria

    //la idea es la siguiente, miro el indice del medio, si el numero en ese indice es mayor que mi indice,
    //entonces si o si lo que quiero va a estar a la izquierda.
    //si es menor si o si va a estar a la derecha.

    indiceEspejo() {




    }
    

//complejidad O(log n)
    public static boolean solve(int[] a, int inicio, int fin){

        int mid = inicio + (fin - inicio)/2;

        //el combine es lineal, no me cuesta nada revisar esto.
        if(a[mid] == mid){
            return true;
        } 

        //parto en 2 partes , de tamaño a.length/2 . a = 2 , c = 2 , d = 1
        if(fin - inicio == 0){
            if(a[0] == 0){
                return true;
            } else{return false;}
        }

        if(a[mid] < mid){
            return solve(a, inicio, mid - 1);

        }
        else if(a[mid] > mid){
            return solve(a, mid + 1, fin);

        }
        return false;

    }
    
public static void main(String[] args) {
    
    int[] miArray = {-4,-1,2,4,7};
    int[] miArray2 = {8, 4, 7, 6, 5, 1, 3, 2};

    


    System.out.println(solve(miArray, 0, miArray.length));
    System.out.println(solve(miArray2, 0, miArray2.length));


    
}


}

