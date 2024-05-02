import java.util.Scanner;

public class solucionExplicada {

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

    //otro enfoque: realmente lo que necesitamos es satisfacer la oferta y demanda con el mas cercano.
    // o sea si queremos vender vamos a buscar al mas cercano que quiera comprar, y visceversa
    //para ello siemplemente podemos ir acumulando compra/venta mientras nos movemos hacia la derecha del arreglo.
    //si lo pensaramos en la vida real es como caminar en una linea recta pasando por todas las casas comprando y vendiendo, ya que sabemos que al final siempre vamos a terminar en cero.
    //es como que la mejor forma de hacerlo es en una sola pasada, acumulando cuando tenga que acumular y vendiendo cuando tenga que vender.
    // o sea la decision greedy es simplemente comprar y vender al mas cercano , en este caso "el mas cercano" es el de al lado en el arreglo.
    //en cada paso sea negativo o positivo , la acumulacion es lo que me cuesta moverme, por ejemplo [5, -4, 1 - 3, 1]
    //en el primer paso me costara moverme a la derecha 5. En el segundo paso como vendi 4 me costara 1. en el tercer paso me costara 2. en el cuarto paso me costara |-1|.
    //por que el valor absoluto si tengo -1 botellas ? por lo siguiente: que yo tenga -1 significa que voy a tener que llegar a alguien que quiera comprarla, para suplir la demanda.
    //y luego "llevarla", lo cual me termina costando 1 de trabajo . 
    //el unico caso en que un paso no me cuesta trabajo es cuando estoy en 0.

    public static int solveGreedy(int[] ciudad) {
        int acumulacionOfertaDemanda = 0;

        int trabajo = 0;


        for(int i = 0; i < ciudad.length ; i++){
            acumulacionOfertaDemanda += ciudad[i];
            trabajo += Math.abs(acumulacionOfertaDemanda);

        }
        return trabajo;

    }





    public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);

            int cantVendedores = -1;
            
            while(cantVendedores != 0){

            cantVendedores = scanner.nextInt();

            if(cantVendedores == 0){break;}

            int[] ciudad = new int[cantVendedores];

            for(int i = 0; i < cantVendedores; i ++){

                ciudad[i] = scanner.nextInt();

            }

            int trabajoMinimo = solveGreedy(ciudad);

            System.out.println(trabajoMinimo);

        }

    }


}