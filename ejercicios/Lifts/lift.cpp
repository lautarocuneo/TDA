#include <iostream>
#include <map>
#include <vector>
#include <list>
#include <algorithm>
#include <limits>
#include <climits>
#include <queue>
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

    bool operator<(const Nodo& otro) const {
        if (piso != otro.piso) return piso < otro.piso;
        return ascensor < otro.ascensor;
    }
};

class Lift {
public:
    map<Nodo, list<pair<Nodo, int>>> edificio;
    vector<list<int>> listaParadasPorAscensor;
    vector<int> velocidadPorAscensor;
    int destino; // Piso objetivo, con 0 <= k <= 99

    Lift(const vector<list<int>>& paradas, const vector<int>& velocidades, int piso_objetivo) 
        : listaParadasPorAscensor(paradas), velocidadPorAscensor(velocidades), destino(piso_objetivo) {
        crearEdificio();
    }

    struct Compare { //esta funcion de comparacion la necesito para que la cola de prioridad me devuelva el nodo minimo segun su valor en el map distancias, por eso necesito definirla aparte.
        map<Nodo, int>& distancias;

        Compare(map<Nodo, int>& distancias) : distancias(distancias) {}

        bool operator()(const Nodo& a, const Nodo& b) {
            return distancias[a] > distancias[b];
        }
    };

    void crearEdificio() {
        for (int pisos = 0; pisos < 100; ++pisos) { 
            
                Nodo nodoFantasma(pisos,-1);
                edificio[nodoFantasma] = list<pair<Nodo, int>>();


            for (int ascensor = 1; ascensor <= 5; ++ascensor) {

                if(ascensor > listaParadasPorAscensor.size()){
                        continue;
                    }

                Nodo nuevoNodo(pisos, ascensor);
                
                edificio[nuevoNodo] = list<pair<Nodo, int>>();
                

                if (pisos < 99) {
                    edificio[nuevoNodo].push_back(make_pair(Nodo(pisos + 1, ascensor), velocidadPorAscensor[ascensor - 1])); 
                }

                if (pisos > 0) {
                    edificio[nuevoNodo].push_back(make_pair(Nodo(pisos - 1, ascensor), velocidadPorAscensor[ascensor - 1]));
                }

                if (pisos > 0 && pisos < 99) {
                    
                    if(ascensor > listaParadasPorAscensor.size()){
                        continue;
                    }
                    
                    if (count(listaParadasPorAscensor[nuevoNodo.ascensor - 1].begin(), listaParadasPorAscensor[nuevoNodo.ascensor - 1].end(), nuevoNodo.piso)) {
                        edificio[nuevoNodo].push_back(make_pair(nodoFantasma, 30));
                        edificio[nodoFantasma].push_back(make_pair(nuevoNodo, 30));
                    }
                }

                if (pisos == 0 || pisos == destino) {

                    if(ascensor > listaParadasPorAscensor.size()){
                        continue;
                    }

                    if (count(listaParadasPorAscensor[nuevoNodo.ascensor - 1].begin(), listaParadasPorAscensor[nuevoNodo.ascensor - 1].end(), nuevoNodo.piso)) {
                        edificio[nuevoNodo].push_back(make_pair(nodoFantasma, 0));
                        edificio[nodoFantasma].push_back(make_pair(nuevoNodo, 0));
                    }
                }
            }
        }
    }


    int dijkstra(Nodo inicio, Nodo destino) {
        map<Nodo, int> distancias;

        //le pongo a todos los nodos en las distancias un valor de infinito
        for (const auto& NodoClave : edificio) {
            distancias[NodoClave.first] = numeric_limits<int>::max();
        }

        //al de inicio le pongo 0
        distancias[inicio] = 0;

        Compare compara(distancias);

        //cola de tipo nodo, implementada con un vector de nodos, yla funcion distancia me devuelve el minimo segun el valor en el map de distancias.
        priority_queue<Nodo, vector<Nodo>, Compare> cola(compara);

        //agrego el valor de inicio a la cola.
        cola.push(inicio);

        //mientras la cola no este vacia sigo.
        while (!cola.empty()) {

            Nodo actual = cola.top();
            cola.pop();

            //si mi nodo actual que estoy viendo es el destino devuelvo asi nomas el valor.
            if (actual == destino) {
                return distancias[actual];
            }

            //ahora reviso los vecinos del primero
            for (const auto& vecino : edificio[actual]) {
                Nodo nodoVecino = vecino.first;
                int peso = vecino.second;

                //en principio la distancia de mi vecino va a ser infinito, asi que obvio que va a ser mayor al actual mas cualquier cosa
                //pero bueno la idea es ir actualizando por la minimad de todos los vecinos.
                if (distancias[nodoVecino] > distancias[actual] + peso) {
                    distancias[nodoVecino] = distancias[actual] + peso;
                    cola.push(nodoVecino);
                }
            }
        }

        return -1; // Devuelve -1 si no hay camino al destino
    }
};

int main() {

    int n;
    int k;
   
     while (cin >> n >> k)  {
        

        vector<int> velocidades(n);
        for (int& v : velocidades) {
            cin >> v; // Velocidad de cada ascensor
        }

        vector<list<int>> paradas(n);
        cin.ignore(); // Ignorar el salto de línea después de las velocidades

        for (int i = 0; i < n; ++i) {
            string line;
            getline(cin, line);
            istringstream iss(line);
            int piso;
            while (iss >> piso) {
                paradas[i].push_back(piso); // Pisos donde se detiene cada ascensor
            }
        }

        Lift lift(paradas, velocidades, k);

        Nodo inicio(0, -1); // empiezo en el piso 0, ascensor -1, que es el nodo fantasma unido a los otros  con coste cero.

        int tiempoMinimo = INT_MAX;

        
        //el destino es el nodo fantasma del piso k con ascensor -1. Si puedo llegar a este pude llegar al otro.
        Nodo destino(k, -1); 

        int tiempo = lift.dijkstra(inicio, destino);

        if (tiempo != -1) {
                tiempoMinimo = min(tiempoMinimo, tiempo);
            }
            
        

        if (tiempoMinimo == INT_MAX) {
            cout << "IMPOSSIBLE" << endl;
        } else {
            cout << tiempoMinimo << endl;
        }
    }

    return 0;
}
