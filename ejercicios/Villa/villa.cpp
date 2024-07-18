#include <iostream>
#include <map>
#include <vector>
#include <list>
#include <queue>
#include <algorithm>
#include <set>

using namespace std;

class Estado {
public:
    int cuarto;
    vector<bool> luces;

    

    Estado(int cuarto, vector<bool> luces) : cuarto(cuarto), luces(luces) {}

    bool operator==(const Estado& otro) const {
        return cuarto == otro.cuarto && luces == otro.luces;
    }

    bool operator!=(const Estado& otro) const {
        return !(*this == otro);
    }


    bool operator<(const Estado& otro) const {
        return cuarto < otro.cuarto || (cuarto == otro.cuarto && luces < otro.luces);
    }
};

class Nodo {
public:
    Estado estado;
    Nodo* padre;

    Nodo(const Estado& estado, Nodo* padre) : estado(estado), padre(padre) {}
};

class Grafo {
public:
    Estado primerEstado;
    map<Estado, list<Estado>> listaAdyacenciasEstados;

    Grafo(int cuarto, vector<bool> luces, vector<vector<bool>> puertas, vector<vector<bool>> interruptores) : primerEstado(cuarto, luces) {
        
        crearGrafoEstados(primerEstado, puertas, interruptores);
    }

    void crearGrafoEstados(Estado& estadoActual, vector<vector<bool>>& puertas, vector<vector<bool>>& interruptores) {
        if (listaAdyacenciasEstados.count(estadoActual) == 0) {
            list<Estado> vecinos;
            listaAdyacenciasEstados[estadoActual] = vecinos;
        }

        //esta funcion me va a armar el grafo de estados en listaAdyacenciasEstados.

        //voy a hacer que sea recursiva con dos fors que revise todas las posibilidades de cambiar de habitacion o de prender apagar una luz.

        //arranco por las puertas

        for (int i = 0; i < puertas.size(); ++i) {
            //me tengo que fijar si el cuarto al que me quiero mover tiene la luz prendida y si tiene una puerta que lo conecte con el que estoy primero.
            if (puertas[estadoActual.cuarto][i] && estadoActual.luces[i]) {

                //mantengo las luces iguales porque solo me movi de cuarto al cuarto i.
                Estado nuevoEstado(i, estadoActual.luces);

                //agrego el nuevo estado a la lista de adyacencias del actual si no lo tenia.


                listaAdyacenciasEstados[estadoActual].push_back(nuevoEstado);
                
                //ahora me fijo si ya habia creado ese estado,

                if (listaAdyacenciasEstados.count(nuevoEstado) == 0) {
                    crearGrafoEstados(nuevoEstado, puertas, interruptores);
                }
            }
        }

        //sigo con las luces


        for (int i = 0; i < interruptores.size(); ++i) {
            if (interruptores[estadoActual.cuarto][i] && i != estadoActual.cuarto) {

                //me tengo que crear unas nuevas luces para el estado nuevo, que sean iguales a las del estado anterior pero les voy a poner el opuesto en el indice i (o sea prender o apagarlas).
                vector<bool> nuevasLuces = estadoActual.luces;
                
                
                //aca cambio el estado de la luz segun el interruptor.
                nuevasLuces[i] = !nuevasLuces[i];

                Estado nuevoEstado(estadoActual.cuarto, nuevasLuces);
                listaAdyacenciasEstados[estadoActual].push_back(nuevoEstado);

                //ahora me fijo si ya habia creado ese estado,
                if (listaAdyacenciasEstados.count(nuevoEstado) == 0) {
                    crearGrafoEstados(nuevoEstado, puertas, interruptores);
                }
            }
        }
    }

    Nodo* bfsmod(Estado& primerEstado, map<Estado, list<Estado>>& listaAdyacenciasEstados, int totalCuartos) {
    queue<Nodo*> cola;
    set<Estado> visitados; //implementacion bfsmod.
    //el actual empieza siendo el primero.
    Nodo* nodoActual = new Nodo(primerEstado, nullptr);
    cola.push(nodoActual);
    visitados.insert(primerEstado);

    while (!cola.empty()) {
        //en cada paso el actual va a ser el que desencole.
        nodoActual = cola.front();
        cola.pop();

        Estado estado = nodoActual->estado;

        bool lucesApagadasMenosUlt = true;
        for (int i = 0; i < estado.luces.size() - 1; ++i) {
            if (estado.luces[i]) {
                lucesApagadasMenosUlt = false;
                break;
            }
        }
        // si encuentro el nodoActual que cumple la condicion termino.
        if (estado.cuarto == totalCuartos - 1 && lucesApagadasMenosUlt) {
            return nodoActual;
        }
         // recorro los vecinos del estado actual.
        for (Estado& vecino : listaAdyacenciasEstados.at(estado)) {
            if (visitados.find(vecino) == visitados.end()) {
                visitados.insert(vecino);
                cola.push(new Nodo(vecino, nodoActual));
            }
        }
    }
        // si no encontramos el nodo que queremos nunca devolvemos null.
    return nullptr;
}


    void reconstruirSolucion(Nodo* ultimoNodo) {
    list<string> pasos;
    Nodo* nodoActual = ultimoNodo;

    while (nodoActual != nullptr) {
        Estado estadoActual = (*nodoActual).estado;
        Nodo* padre = (*nodoActual).padre; 

        if (padre != nullptr) {
            Estado estadoPadre = padre->estado;

            //me fijo si cambio el cuarto o cambio la luz.
            if (estadoActual.cuarto != estadoPadre.cuarto) {
                pasos.push_front("- Move to room " + to_string(estadoActual.cuarto + 1) + ".");
            }

            for (int i = 0; i < estadoActual.luces.size(); ++i) {
                //me tengo que fijar para todas las luces si es distinta.
                if (estadoActual.luces[i] != estadoPadre.luces[i]) {
                    if (estadoActual.luces[i]) {
                        pasos.push_front("- Switch on light in room " + to_string(i + 1) + ".");
                    } else {
                        pasos.push_front("- Switch off light in room " + to_string(i + 1) + ".");
                    }
                }
            }
        }

        nodoActual = padre;
    }

    cout << "The problem can be solved in " << pasos.size() << " steps:" << endl;
    for (const string& paso : pasos) {
        cout << paso << endl;
    }
}

};


int main() {
    int villa = 1;

    while (true) {
        int cantCuartos, cantPuertas, cantInterruptores;
        cin >> cantCuartos >> cantPuertas >> cantInterruptores;

        if (cantCuartos == 0 && cantPuertas == 0 && cantInterruptores == 0) {
            break;
        }

        vector<bool> luces(cantCuartos, false);
        luces[0] = true;

        vector<vector<bool>> puertas(cantCuartos, vector<bool>(cantCuartos, false));

        for (int i = 0; i < cantPuertas; i++) {
            int cuarto1, cuarto2;
            cin >> cuarto1 >> cuarto2;
            cuarto1--; 
            cuarto2--;
            puertas[cuarto1][cuarto2] = true;
            puertas[cuarto2][cuarto1] = true;
        }

        vector<vector<bool>> interruptores(cantCuartos, vector<bool>(cantCuartos, false));

        for (int i = 0; i < cantInterruptores; i++) {
            int cuartoInterruptor, cuartoLampara;
            cin >> cuartoInterruptor >> cuartoLampara;
            cuartoInterruptor--;
            cuartoLampara--;
            interruptores[cuartoInterruptor][cuartoLampara] = true;
        }

        Grafo grafoEstados(0, luces, puertas, interruptores);

        Nodo* ultimoNodo = grafoEstados.bfsmod(grafoEstados.primerEstado, grafoEstados.listaAdyacenciasEstados, cantCuartos);

        cout << "Villa #" << villa << endl;
        if (ultimoNodo == nullptr) {
            cout << "The problem cannot be solved." << endl;
        } else {
            grafoEstados.reconstruirSolucion(ultimoNodo);
        }
        
        villa++;
        std::cout << std::endl;
    }
    return 0;
}
