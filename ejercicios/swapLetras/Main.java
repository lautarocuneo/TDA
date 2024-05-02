
/*


bueno, lo primero que tengo que chequear es que haya cantidad par de a y cantidad par de b
en caso contrario devuelvo -1 ya que no se pueden acomodar las letras.


ahora que ya pase eso tengo que hacer lo siguiente: 

voy iterando buscando los distintos : 

a  y b
b    a

y los cuento simplemente, y me guardo en que posicion estan. 

ahora hay dos posibilidades... o la cantidad de a y b es par , , , o la cantidad de a y b es impar. bueno... 
                                                b   a                               b   a
el minimo si es par sera simplemenet la cantidad de pares. Si no sera la cantidad de pares + 1 creo pero bue voy viendo.



     */


     import java.util.ArrayList;
     import java.io.BufferedReader;
     import java.io.InputStreamReader;
     import java.io.IOException;
     
     
     public class Main {

          public static ArrayList<Integer> ubicacionesAB = new ArrayList<>();

          public static ArrayList<Integer> ubicacionesBA = new ArrayList<>();
     
          public static int solucionGreedy(String primero, String segundo) {
               //bueno, primero cuento las a y b, y tienen que ser pares.

               int cantidadA = 0;
               int cantidadB = 0;

               for(int i = 0; i < primero.length(); i++){
                    if(primero.charAt(i) == 'a') {
                         cantidadA++;
                    }
                    if(primero.charAt(i) == 'b'){
                         cantidadB++;
                    }
                    if(segundo.charAt(i) == 'a') {
                         cantidadA++;
                    }
                    if(segundo.charAt(i) == 'b'){
                         cantidadB++;
                    }
               }

               if(cantidadA % 2 != 0 || cantidadB % 2 != 0){
                    return -1;
               }

               //bueno listo hasta aca.
               //ahora ir contando los ab y ba , y me guardo la posicion donde estan
               //uso dos vectores

               
               


               for(int i = 0; i < primero.length(); i++){
                    // caso ab
                    if(primero.charAt(i) == 'a' && segundo.charAt(i) == 'b') {
                         ubicacionesAB.add(i + 1); //es + 1 porque cuentan desde 1 las posiciones de las letras.
                    }

                    //caso ba
                    if(primero.charAt(i) == 'b' && segundo.charAt(i) == 'a') {
                         ubicacionesBA.add(i + 1); 
                    }

               }

               //bueno ya tengo guardadas las ubicaciones y ademas tengo la cantidad de AB Y BA , que es el rango de mis array lists.
               //ahora hay dos casos, o  mis (AB + BA) / 2 es impar, o es par.
               //enrealidad me di cuenta que no me importa que  (AB + AB) / 2, lo unico que importa es que sea par cualquiera de los dos.
               //ya que si uno es par y el otro no, entro en el caso de -1. 
               // si ninguno es par entro en el caso que tengo que sumar 1, ya que tendria una pareja de ab-ba que lleva 2 pasos hacer el swap.

               if ((ubicacionesAB.size() % 2) == 0 || (ubicacionesBA.size() % 2) == 0) {
                    return (ubicacionesAB.size() + ubicacionesBA.size()) / 2;
                } else {
                    return ((ubicacionesAB.size() + ubicacionesBA.size()) / 2) + 1;
                }
                
          }

     
         public static void main(String[] args) throws IOException {
               BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
     
               int longitudPalabras = Integer.parseInt(lector.readLine());
                         
               String primeraPalabra = lector.readLine();
               String segundaPalabra = lector.readLine();

               System.out.println(solucionGreedy(primeraPalabra, segundaPalabra));

               //me falta imprimir los swaps
               //hay dos casos, la lista de ab ba impar, y la lista par.
               //es muy facil por ejemplo si tengo 

               if(ubicacionesAB.size() % 2 == 0){
                    for(int i = 0; i < ubicacionesAB.size() - 1 ; i = i + 2){
                         System.out.println(ubicacionesAB.get(i) + " " + ubicacionesAB.get(i + 1));  
                    }
                    for(int i = 0; i < ubicacionesBA.size() - 1; i = i + 2 ) {
                         System.out.println(ubicacionesBA.get(i) + " " + ubicacionesBA.get(i + 1));
                    }
               }
               else { //en este caso tengo que hacer un swap extra con un mismo numero. para eso tengo que mandar uno de AB a uno de BA 
                    //primero imprimo el movimiento con el de la misma posicion.
                    System.out.println(ubicacionesBA.get(0) + " " + ubicacionesBA.get(0)); 
                    int temp = ubicacionesBA.get(0);
                    ubicacionesAB.add(temp);
                    ubicacionesBA.remove(0);

                    
                    for(int i = 0; i < ubicacionesAB.size() - 1; i = i + 2 ){
                         System.out.println(ubicacionesAB.get(i) + " " + ubicacionesAB.get(i + 1));
                    }

                    for(int i = 0; i < ubicacionesBA.size() - 1; i = i + 2 ) {
                         System.out.println(ubicacionesBA.get(i) + " " + ubicacionesBA.get(i + 1));
                    }

                    


               }                 
          }              
     }
     