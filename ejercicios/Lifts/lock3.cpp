#include <iostream>
#include <map>
#include <vector>
#include <list>
#include <algorithm>
#include <limits>
#include <queue>
#include <unordered_set>
#include <unordered_map>
#include <utility>
#include <sstream>

using namespace std;

class Nodo {
public:
    int piso;
    int ascensor;

    Nodo(int piso, int ascensor) : piso(piso), ascensor(ascensor) {}

    bool operator==(const Nodo& otro) const {
        return piso == otro.piso && ascensor == otro.ascensor;
    }

    bool operator!=(const Nodo& otro) const {
        return piso != otro.piso || ascensor != otro.ascensor;
    }

    bool operator<(const Nodo& otro) const {
        if (piso != otro.piso) return piso < otro.piso;
        return ascensor < otro.ascensor;
    }
};

class Lift {

public:



    //represento con lista de adyacencias nodo, y par nodo con vecino.
    map<Nodo, list<pair<Nodo, int>>> edificio;
    vector<list<int>> listaParadasPorAscensor;
    
    int k; //piso objetivo , con 0 <= k <= 99
    vector<int> velocidadPorAscensor;

    
    Lift(const vector<list<int>>& paradas, const vector<int>& velocidades) : listaParadasPorAscensor(paradas), velocidadPorAscensor(velocidades) {
        
        crearEdificio();
    }

    struct Compare {
        map<Nodo, int>& distancias;

        Compare(map<Nodo, int>& distancias) : distancias(distancias) {}

        bool operator()(const Nodo& a, const Nodo& b) {
            return distancias[a] > distancias[b];
        }
    };
    
    void crearEdificio() {
        //simplemente voy a llenar de n * k nodos (500 maximo) , luego voy a decir en cual se puede bajar y cual no , en base a eso hago las conexiones de vecinos.
        for(int pisos = 0; pisos < 100 ; pisos++) { 
            for(int ascensor = 1; ascensor <= 5; ascensor++){

                Nodo nuevoNodo = Nodo(pisos,ascensor);

                //defino como clave el nodo, y le instancio la lista de vecinos, que cada vecino es un nodo y su peso.
                edificio[nuevoNodo] = list<pair<Nodo,int>>();

                

                if (pisos < 99) { // evito crear nodo en piso que noe existe.

                    //a cada nuevoNodo le pongo de vecino un nodo que sera el mismo ascensor , pero el siguiente piso, y el peso es la velocidad del ascensor.
                    edificio[nuevoNodo].push_back(make_pair(Nodo(pisos + 1, ascensor), velocidadPorAscensor[ascensor - 1])); 


                }

                if(pisos > 0){ //devuelta evito poner un nodo que vaya al -1.

                    //tambien le tengo que poner de vecino el piso anterior, para que pueda bajar:
                    edificio[nuevoNodo].push_back(make_pair(Nodo(pisos - 1, ascensor), velocidadPorAscensor[ascensor - 1]));


                }

               if (pisos > 0 && pisos < k){
                //si se cumple que para mi nuevoNodo, el ascensor tiene puerta en el piso de mi nuevo nodo, entonces le voy a poner de vecino
                //un nodo fantasma de peso 30
                //este nodo fantasma solo tiene peso 30
                    if(count(listaParadasPorAscensor[nuevoNodo.ascensor-1].begin(), listaParadasPorAscensor[nuevoNodo.ascensor-1].end(), nuevoNodo.piso)){

                        edificio[nuevoNodo].push_back(make_pair(Nodo(-1,-1), 30));

                    }


               }

               if(pisos == 0 || pisos == k){
                //lo mismo de arriba pero le pongo peso 0 al nodo fantasma en estos casos.
                    if(count(listaParadasPorAscensor[nuevoNodo.ascensor-1].begin(), listaParadasPorAscensor[nuevoNodo.ascensor-1].end(), nuevoNodo.piso)){

                        edificio[nuevoNodo].push_back(make_pair(Nodo(-1,-1), 0));

                    }


               }

               

            }
        //hasta aca ya tengo todos los nodos del grafo y cada uno tiene como vecino el anterior y siguiente piso.
        //y tambien tiene de vecino al nodo fantasma este de peso 30 o 0 dependiendo en que piso estemos.
     }
}


int dijkstra(Nodo inicio, Nodo destino) {
    map<Nodo, int> distancias;

    // Inicializar todas las distancias a infinito
    for (const auto& entry : edificio) {
        distancias[entry.first] = numeric_limits<int>::max();
    }

    distancias[inicio] = 0;

    Compare compara(distancias);
    priority_queue<Nodo, vector<Nodo>, Compare> cola(compara);
    cola.push(inicio);

    while (!cola.empty()) {
        Nodo actual = cola.top();
        cola.pop();

        if (actual == destino) {
            return distancias[actual];
        }

        for (const auto& vecino : edificio[actual]) {
            Nodo nodoVecino = vecino.first;
            int peso = vecino.second;

            if (distancias[actual] != numeric_limits<int>::max() && 
                distancias[nodoVecino] > distancias[actual] + peso) {
                distancias[nodoVecino] = distancias[actual] + peso;
                cola.push(nodoVecino);
            }
        }
    }

    return -1; // Devuelve -1 si no hay camino al destino
}


int main() {
    int T;
    cin >> T; // Número de casos de prueba

    while (T--) {
        int n, d;
        cin >> n >> d; // Número de ascensores y piso de destino

        vector<int> velocidades(n);
        for (int& v : velocidades) {
            cin >> v; // Velocidad de cada ascensor
        }

        vector<list<int>> paradas(n);
        for (list<int>& lista : paradas) {
            int piso;
            while (cin >> piso) {
                lista.push_back(piso); // Pisos donde se detiene cada ascensor
            }
        }

        Lift lift(paradas, velocidades);

        Nodo inicio(0, 1); // Inicio en el piso 0, ascensor 1

        int tiempoMinimo = INT_MAX;

        // Considera todos los ascensores que tienen una parada en el piso de destino
        for (int ascensor = 1; ascensor <= n; ++ascensor) {
            if (count(lift.listaParadasPorAscensor[ascensor-1].begin(), lift.listaParadasPorAscensor[ascensor-1].end(), d)) {
                Nodo destino(d, ascensor); // Destino en el piso d, ascensor actual
                int tiempo = lift.dijkstra(inicio, destino);
                if (tiempo != -1) {
                    tiempoMinimo = min(tiempoMinimo, tiempo);
                }
            }
        }

        if (tiempoMinimo == INT_MAX) {
            cout << "IMPOSIBLE" << endl;
        } else {
            cout << tiempoMinimo << endl;
        }
    }

    return 0;
}
};