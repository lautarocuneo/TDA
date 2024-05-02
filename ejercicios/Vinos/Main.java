import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;


public class Main {

    //esta no sirve, me paso del tiempo. esto es cuadratico en el peor caso si no me equivoco
    //porque para cada tipo que quiere comprar vino estoy viendo todos los otros , seria O((n/2)^2) = O(n^2)
    public static int solveNaive(int[] ciudad) {
        int trabajo = 0;
        for(int i = 0; i < ciudad.length; i++) {
            int j = 0;
            if(ciudad[i] > 0 ){

                while(ciudad[i] > 0){
                    if(ciudad[j] < 0){
                        if(ciudad[i] > Math.abs(ciudad[j])) {
                            trabajo += (-ciudad[j]) * (distancia(i,j));
                            ciudad[i] += ciudad[j];
                            ciudad[j] = 0;

                        }else {
                            trabajo += (ciudad[i]) * (distancia(i,j));
                            ciudad[j] += ciudad[i];
                            ciudad[i] = 0;
                            

                        }
                        
                        
                        j++;

                    }else{j++;}

                }

            }else{continue;}

        }


        return trabajo;

    }




    
    public static int distancia(int i , int j) {
        if(i < j){
            return j - i;

        }else{return (i - j);}

    }


    //aclaracion: la idea estaba bien hace muchas entregas, pero se necesitaba long para que pase los test en la suma de trabajo.
    //y aparte el scanner no era lo suficientemente rapido, por lo que necesitaba un buffered reader.
    public static void main(String[] args) throws IOException {
            BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));

            int cantVendedores = -1;
            
            while(cantVendedores != 0){

            cantVendedores = Integer.parseInt(lector.readLine());

            if(cantVendedores == 0){break;}

            long acumulacionOfertaDemanda = 0;
            long trabajo = 0;

            //esto lo tengo que hacer porque si no no puedo meter la linea completa de "5 -4 1 -3 1"
            String[] casas = lector.readLine().split(" ");

            for(int i = 0; i < cantVendedores ; i++){
                acumulacionOfertaDemanda += Integer.parseInt(casas[i]);
                trabajo += Math.abs(acumulacionOfertaDemanda);
            }
            System.out.println(trabajo);
            }

            
    }
}