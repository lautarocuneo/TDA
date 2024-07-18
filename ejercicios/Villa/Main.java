

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Scanner;
import java.util.HashMap;



public class Main {

    public static Estado primerEstado;
    
    public static HashMap<Estado, LinkedList<Estado>> listaAdyacenciasEstados = new HashMap();

    

    public Main(int cuarto, boolean[] luces, boolean[][] puertas, boolean[][] interruptores) {

        primerEstado = new Estado(cuarto, luces);

        listaAdyacenciasEstados = new HashMap();

        

        crearGrafoEstados(primerEstado, puertas, interruptores);
    }

    public static void crearGrafoEstados(Estado estadoActual, boolean[][] puertas, boolean[][] interruptores){
            
            
            

            if(!listaAdyacenciasEstados.containsKey(estadoActual)){
                LinkedList<Estado> vecinos = new LinkedList<>();
                listaAdyacenciasEstados.put(estadoActual, vecinos);


            }

            


            //esta funcion me va a armar el grafo de estados en listaAdyacenciasEstados.

            //voy a hacer que sea recursiva con dos fors que revise todas las posibilidades de cambiar de habitacion o de prender apagar una luz.

            //arranco por las puertas
            for(int i = 0; i < puertas.length; i++){ 

                //me tengo que fijar si el cuarto al que me quiero mover tiene la luz prendida y si tiene una puerta que lo conecte con el que estoy primero.
                if(puertas[estadoActual.cuarto][i] && estadoActual.luces[i]){

                    //mantengo las luces iguales porque solo me movi de cuarto al cuarto i.
                    Estado nuevoEstado = new Estado(i, estadoActual.luces);

                    //agrego el nuevo estado a la lista de adyacencias del actual si no lo tenia.

                    listaAdyacenciasEstados.get(estadoActual).add(nuevoEstado);

                    
                    //ahora me fijo si ya habia creado ese estado,
                    if(!listaAdyacenciasEstados.containsKey(nuevoEstado)) {
                        crearGrafoEstados(nuevoEstado, puertas, interruptores);

                    }



                }
            }

            //sigo con las luces

            for(int i = 0; i < interruptores.length; i++){
                //me tengo que fijar si en el cuarto actual hay interruptor que prende la luz del cuarto i, y que sea distinto al actual que estoy ahora.
                if(interruptores[estadoActual.cuarto][i] && i != estadoActual.cuarto){

                    //me tengo que crear unas nuevas luces para el estado nuevo, que sean iguales a las del estado anterior pero les voy a poner el opuesto en el indice i (o sea prender o apagarlas).
                    boolean[] nuevasLuces = estadoActual.luces.clone();

                    //aca cambio el estado de la luz segun el interruptor.
                    nuevasLuces[i] = !nuevasLuces[i];

                    //creo el nuevo estado con las luces actualizadas.
                    Estado nuevoEstado = new Estado(estadoActual.cuarto, nuevasLuces);

                    //agrego el nuevo estado a la lista de adyacencias del actual si no lo tenia.

                    listaAdyacenciasEstados.get(estadoActual).add(nuevoEstado);

                    
                    //ahora me fijo si ya habia creado ese estado,
                    if(!listaAdyacenciasEstados.containsKey(nuevoEstado)) {
                        crearGrafoEstados(nuevoEstado, puertas, interruptores);

                    }


                }

            }

    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<String> soluciones = new ArrayList<>();


        int villa = 1;

        while(true){

        int cantCuartos = scanner.nextInt();

        int cantPuertas = scanner.nextInt();

        int cantInterruptores = scanner.nextInt();

        if (cantCuartos == 0 && cantPuertas == 0 && cantInterruptores == 0) {
            break;
        }

        //quiero el booleano de luces para empezar tambien, o sea que luces estan prendidas y apagadas.
        boolean[] luces = new boolean[cantCuartos];

        for(int i = 0; i < luces.length; i++){
            luces[i] = false;

        }
        luces[0] = true; //el primer cuarto arranca prendido y el resto false.

        //tambien quiero las conexiones entre cuartos.
        boolean[][] puertas = new boolean[cantCuartos][cantCuartos];

        for (int i = 0; i < cantPuertas; i++) {

            int cuarto1 = scanner.nextInt() - 1; //habitaciones arrancan en 1 los inputs.
            int cuarto2 = scanner.nextInt() - 1;
            puertas[cuarto1][cuarto2] = true;
            puertas[cuarto2][cuarto1] = true; // las puertas son bidireccionales.
        }

        //tambien quiero los interruptores.

        boolean[][] interruptores = new boolean[cantCuartos][cantCuartos];

        for (int i = 0; i < cantInterruptores; i++) {

            int cuartoInterruptor = scanner.nextInt() - 1; //habitaciones arrancan en 1 los inputs.
            int cuartoLampara = scanner.nextInt() - 1;
            interruptores[cuartoInterruptor][cuartoLampara] = true; //no es bidireccional
             
        }

        primerEstado = new Estado(0, luces);

        //creo grafo de estados : 
        Main grafo = new Main(0, luces, puertas, interruptores);

        

        //a partir del nodo final puedo reconstruir toda la solucion porque tengo guardado para cada nodo su padre.
        Nodo ultimoNodo = bfsmod(grafo.primerEstado, grafo.listaAdyacenciasEstados, cantCuartos);

        System.out.println("Villa #" + villa);
        if (ultimoNodo == null) {
            System.out.println("The problem cannot be solved.\n");
        } else {
            reconstruirSolucion(ultimoNodo);
            System.out.println();
        }

        villa++;

    }

    for (int i = 0; i < soluciones.size(); i++) {
        System.out.print(soluciones.get(i));
        if (i < soluciones.size() - 1) {
            System.out.println();
        }
    }
    

    }

    public static class Estado {
        int cuarto; 
        boolean[] luces; 
    
        public Estado(int cuarto, boolean[] luces) {
            this.cuarto = cuarto;
            this.luces = luces.clone(); //esto va para que no se pase como referencia, que cada estado tenga una copia.
        }

        //esto porque si no no me funcionaban las comparaciones entre estados
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null || getClass() != obj.getClass())
                return false;
            Estado estado = (Estado) obj;
            return cuarto == estado.cuarto && Arrays.equals(luces, estado.luces);
        }
    
        //esto porque si no no me funcionaban las estructuras hash.
        @Override
        public int hashCode() {
            int result = Objects.hash(cuarto);
            result = 31 * result + Arrays.hashCode(luces);
            return result;
        }


    }

    

    //esto lo necesito para reconstruirme la solucion.
    public static class Nodo {
        Estado estado;
        Nodo padre;

        public Nodo(Estado estado, Nodo padre) {
            this.estado = estado;
            this.padre = padre;
        }
    }

    //hago que devuelva el nodo que cumple la condicion de que este en el ultimo cuarto y esten todas las luces apagadas.
    public static Nodo bfsmod(Estado primerEstado, HashMap<Estado, LinkedList<Estado>> listaAdyacenciasEstados, int totalCuartos) {
        //implementacion bfsmod.
        Queue<Nodo> cola = new LinkedList<>();
        HashSet<Estado> visitados = new HashSet<>();

        //el actual empieza siendo el primero.
        Nodo nodoActual = new Nodo(primerEstado, null);
        cola.add(nodoActual);

        


        while (cola.size() != 0) {
            //en cada paso el actual va a ser el que desencole.
            nodoActual = cola.poll();

            Estado estado = nodoActual.estado;

            boolean lucesApagadasMenosUlt = true;

            for (int i = 0; i < estado.luces.length - 1; i++) {
                if (estado.luces[i]) {
                    lucesApagadasMenosUlt  = false;
                }
            }

            // si encuentro el nodoActual que cumple la condicion termino.
            if (estado.cuarto == totalCuartos - 1 && lucesApagadasMenosUlt) {
                return nodoActual;
            }

            // recorro los vecinos del estado actual.
            for (Estado vecino : listaAdyacenciasEstados.get(estado)) {
                if (!visitados.contains(vecino)) {
                    visitados.add(vecino);
                    //le agrego el vecino, y el padre sera el nodoActual.
                    cola.add(new Nodo(vecino, nodoActual));
                }
            }
        }

        // si no encontramos el nodo que queremos nunca devolvemos null.
        return null;
    }

    
    public static void reconstruirSolucion(Nodo ultimoNodo) {
        LinkedList<String> pasos = new LinkedList<>();
        Nodo nodoActual = ultimoNodo;
    
        while (nodoActual != null) {
            Estado estadoActual = nodoActual.estado;
            Nodo padre = nodoActual.padre;
    
            if (padre != null) {
                Estado estadoPadre = padre.estado;
    
                //me fijo si cambio el cuarto o cambio la luz.
                if (estadoActual.cuarto != estadoPadre.cuarto) {
                    pasos.addFirst("- Move to room " + (estadoActual.cuarto + 1) + ".");
                }
    
                // Si el estado de las luces cambió, entonces se usó un interruptor
                for (int i = 0; i < estadoActual.luces.length; i++) {
                    //me tengo que fijar para todas las luces si es distinta.
                    if (estadoActual.luces[i] != estadoPadre.luces[i]) {
                        if (estadoActual.luces[i]) {
                            pasos.addFirst("- Switch on light in room " + (i + 1) + ".");
                        } else {
                            pasos.addFirst("- Switch off light in room " + (i + 1) + ".");
                        }
                    }
                }
            }
    
            nodoActual = padre;
        }
    
        // Imprimir la solución directamente
        System.out.println("The problem can be solved in " + pasos.size() + " steps:");
        for (String paso : pasos) {
            System.out.println(paso);
        }
    }
    
}


