public class rutaEficiente {

    public static void main(String[] args) {

        Integer[] estaciones = {0, 1, 4, 10, 15, 30, 31, 90, 110, 200, 201};

        int[] solucionParadas = new int[estaciones.length];

        int j = 1;

        int nafta = 100;

        int paradasMinimas = 0;
        //cargo nafta en la primera parada.
        solucionParadas[0] = 0;
        paradasMinimas++;

        for(int i = 1; i < estaciones.length; i++){
            //basicamente voy a ir fijandome si me alcanza para llegar a la siguiente.
            /*
              lo que gasto en cada paso es (estaciones[i] - estaciones[i-1]) , y lo que necesito para llegar a la siguiente es 
              
              (estaciones[i + 1] - estaciones[i])

              entonces si lo que gaste llegando a la actual es eso que dije, y se lo resto a mi nafta actual, entonces
              eso tiene que ser mayor a lo que me falta para llegar a la siguiente.

             */


            if((nafta - (estaciones[i] - estaciones[i-1])) >= (estaciones[i + 1] - estaciones[i])) {
                nafta -= (estaciones[i] - estaciones[i-1]);

            }
            else{

                solucionParadas[j] = estaciones[i];
                nafta = 100;
                paradasMinimas++;
                j++;

            }

        }

        for(int i = 0; i < j; i++){
            System.out.println(solucionParadas[i]);

        }

        System.out.println(paradasMinimas);

    }
}