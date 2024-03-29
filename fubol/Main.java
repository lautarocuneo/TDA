import java.util.*;


public class Main {
    public static ArrayList<String> optimosDelanteros = new ArrayList<String>();
    public static ArrayList<String> optimosDefensores = new ArrayList<String>();
    int mejorSumaAtaque = 0;
    int mejorSumaDefensa = 0;


    public boolean esMenorLexgraf(ArrayList<String> lista1, ArrayList<String> lista2) {
        for(int i = 0; i < lista1.size(); i++) {
            int valorVerdad = lista1.get(i).compareTo(lista2.get(i));
            if(valorVerdad < 0) {
                return true;
            } 
            else if (valorVerdad > 0){
                return false;
            }
        }
        return false;
    } 

    public int calculaSumaAtaque(ArrayList<Tripla<String, Integer, Integer>> jugadores) {
        int suma = 0;
        for (Tripla<String, Integer, Integer> jugador : jugadores) {
            suma = suma + jugador.getSegundo();
        }
        return suma;
    }

    public int calculaSumaDefensa(ArrayList<Tripla<String, Integer, Integer>> jugadores) {
        int suma = 0;
        for (Tripla<String, Integer, Integer> jugador : jugadores) {
            suma = suma + jugador.getTercero();
        }
        return suma;
    }

    public void algoritmoBT(int i, int j, ArrayList<Tripla<String, Integer, Integer>> defensoresActuales, ArrayList<Tripla<String, Integer, Integer>> delanterosActuales, ArrayList<Tripla<String, Integer, Integer>> Jugadores) {
        if (i == 5 && j == 5) { //caso base, cuando los dos equipos tienen 5 jugadores, tienen que cumplirse simultaneamente, o pierdo posibilidades.
            int sumaAtaque = calculaSumaAtaque(delanterosActuales); //funciones que dadas unas listas de triplas con ataque defensa y nombre calcula la suma del ataque
            int sumaDefensa = calculaSumaDefensa(defensoresActuales); // lo mismo para la defensa.

            //esto lo hago porque me quiero guardar en una lista los nombres de los delanteros actuales (porque delanterosActuales toma triplas, no strings)
            ArrayList<String> delanterosActualesNombres = new ArrayList<String>(); 
            for(Tripla<String, Integer, Integer> delantero : delanterosActuales) {
                delanterosActualesNombres.add(delantero.getPrimero());
            }
            Collections.sort(delanterosActualesNombres); //esta habia que ordenarla si o si o no funcaba. el tema es que para comparar lexicograficamente las dos listas, ambas tienen que estar ordenadas.
            
            // aca comparo por ataque, desempato por defensa, y al final por orden lexicografico de las listas de strings.
            if (sumaAtaque > mejorSumaAtaque || (sumaAtaque == mejorSumaAtaque && sumaDefensa > mejorSumaDefensa) || (sumaAtaque == mejorSumaAtaque && sumaDefensa == mejorSumaDefensa && esMenorLexgraf(delanterosActualesNombres, optimosDelanteros))) {
                mejorSumaAtaque = sumaAtaque;
                mejorSumaDefensa = sumaDefensa;

                optimosDelanteros = new ArrayList<>();
                for (Tripla<String, Integer, Integer> jugador : delanterosActuales) {
                    optimosDelanteros.add(jugador.getPrimero());
                }
                Collections.sort(optimosDelanteros); //hago un sort una vez actualizada la lista para que la proxima vez que las compare esten ordenadas lexicograficamente.
                

                optimosDefensores = new ArrayList<>();
                for (Tripla<String, Integer, Integer> jugador : defensoresActuales) {
                    optimosDefensores.add(jugador.getPrimero());
                }
                Collections.sort(optimosDefensores);
                
            }
            return;
        }
        //este indice es el que voy revisando de la lista de jugadores.
        int jugadorindice = i + j;

        //caso recursivo , agrego al jugador a los delanteros actuales, hago el llamado recursivo y lo saco, para revisar correctamente todas las posibilidades.
        if (i < 5) {
            delanterosActuales.add(Jugadores.get(jugadorindice));
            algoritmoBT(i + 1, j, defensoresActuales, delanterosActuales, Jugadores);
            delanterosActuales.remove(delanterosActuales.size() - 1);
        }
        //lo mismo con los defensores.
        if (j < 5) {
            defensoresActuales.add(Jugadores.get(jugadorindice));
            algoritmoBT(i, j + 1, defensoresActuales, delanterosActuales, Jugadores);
            defensoresActuales.remove(defensoresActuales.size() - 1);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //cantidad de casos de test.
        int T = scanner.nextInt();
        scanner.nextLine();
        
        //agrego los jugadores pasados por input a la lista de jugadores.
        for (int test = 0; test < T; test++) {
            ArrayList<Tripla<String, Integer, Integer>> jugadores = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                String nombre = scanner.next();
                int ataque = scanner.nextInt();
                int defensa = scanner.nextInt();
                jugadores.add(new Tripla<>(nombre, ataque, defensa));
            }
            //instancio la clase main
            Main f = new Main();
            //solucion con bt
            f.algoritmoBT(0, 0, new ArrayList<>(), new ArrayList<>(), jugadores);
            //imprimo
            System.out.println("Case " + (test + 1) + ":");
            System.out.println("(" + String.join(", ", optimosDelanteros) + ")");
            System.out.println("(" + String.join(", ", optimosDefensores) + ")");
        }
        scanner.close();
       
    }
}

class Tripla<A, B, C> {
    A primero;
    B segundo;
    C tercero;

    public Tripla(A primero, B segundo, C tercero) {
        this.primero = primero;
        this.segundo = segundo;
        this.tercero = tercero;
    }

    public A getPrimero() {
        return primero;
    }

    public B getSegundo() {
        return segundo;
    }

    public C getTercero() {
        return tercero;
    }
}
