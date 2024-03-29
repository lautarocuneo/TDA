import java.util.*;


class futbol {

    private ArrayList<String> optimosDelanteros = new ArrayList<String>();
    private ArrayList<String> optimosDefensores = new ArrayList<String>();
    int mejorSumaAtaque = 0;
    int mejorSumaDefensa = 0;


    public boolean esMenorLexgraf( ArrayList<String> defensoresActuales, ArrayList<String> delanterosActuales) {
        //ordeno las dos listas 
        //comparo string a string las dos 
        //me quedo con la que va primero en orden alfabetico ??? ni idea,...
        return true ;

    }

    public int calculaSuma(ArrayList<String> s) {



        return 0;

    }

    //i y j son para iterar en la cantidad de delanteros y defensores respectivamente.
    public void algoritmoBT(int i, int j, ArrayList<String> defensoresActuales, ArrayList<String> delanterosActuales
    , ArrayList<Tripla<String, Integer, Integer>> Jugadores ) {


        if(i == 5 ) { //caso base, ya esta terminado cuando elegi los delanteros, ya que los otros 5 seran los defensores.
                //si llego al caso base , calculo suma de ataque y suma de defensa 
               int sumaAtaque = calculaSuma(delanterosActuales);
               int sumaDefensa =  calculaSuma(defensoresActuales);

                if (sumaAtaque > mejorSumaAtaque || (sumaAtaque == mejorSumaAtaque && sumaDefensa > mejorSumaDefensa) ||
                (sumaAtaque == mejorSumaAtaque && sumaDefensa == mejorSumaDefensa && esMenorLexgraf(delanterosActuales, optimosDelanteros))) {
                    mejorSumaAtaque = sumaAtaque;
                    mejorSumaDefensa = sumaDefensa;
                    optimosDelanteros = delanterosActuales; //aca no se si es lo mismo pasar por referencia o por copia las listas a las optimas.
                    optimosDefensores = defensoresActuales; //creo que deberia ser por copia, porque si no cuando le agrego un jugador nuevo a delanteros actuales o defensores actuales , enrealidad estoy modificando las listas.
                    return;
                }
        }

        int jugadorindice = i + j; //voy por este jugador avanzando en jugadores.

        if(i < 5) {
            delanterosActuales.add(Jugadores.get(jugadorindice).getPrimero());
            algoritmoBT(i + 1, j, defensoresActuales, delanterosActuales, Jugadores);
            delanterosActuales.remove(delanterosActuales.size() - 1);
        }

        if(j < 5) {
            defensoresActuales.add(Jugadores.get(jugadorindice).getPrimero());
            algoritmoBT(i , j + 1, defensoresActuales, delanterosActuales, Jugadores);
            defensoresActuales.remove(defensoresActuales.size() - 1);



        }


    }

}