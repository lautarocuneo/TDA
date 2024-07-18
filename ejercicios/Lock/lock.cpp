#include <iostream>
#include <map>
#include <vector>
#include <list>
#include <algorithm>
#include <limits>
#include <queue>
#include <unordered_set>
#include <unordered_map>

using namespace std;

class Combinacion {
public:
    int codigo;
    int peso;

    Combinacion(int codigo, int peso) : codigo(codigo), peso(peso) {}

    bool operator==(const Combinacion& otro) const {
        return codigo == otro.codigo && peso == otro.peso;
    }

    bool operator<(const Combinacion& otro) const {
        return peso > otro.peso; // Nota: Inverso para usar con priority_queue (min-heap)
    }
};

class Nodo {
public:
    int peso;
    int codigo;
    //el padre es al pedo pero bue.
    Nodo* padre;

    Nodo() : peso(0), codigo(0), padre(nullptr) {} // Constructor por defecto

    Nodo(int peso, int codigo, Nodo* padre) : peso(peso), codigo(codigo), padre(padre) {}

    bool operator<(const Nodo& otro) const {
        return peso > otro.peso; // Nota: Inverso para usar con priority_queue (min-heap)
    }

    bool operator==(const Nodo& otro) const {
        return peso == otro.peso && codigo == otro.codigo;
    }
};

class Lock {
public:
    map<int, list<Combinacion>> listaAdyacencias;
    vector<int> listaCodigos;
    bool estaElCeroComoCodigo = false;


    Lock(const vector<int>& codigos) : listaCodigos(codigos) {
        
        crearGrafoLock();
    }

    int rollsTotales(int desde, int hasta) {
        int rolls = 0;
        for (int i = 0; i < 4; ++i) {
            int desdeDigito = desde % 10;
            int hastaDigito = hasta % 10;
            int rollsDirectos = abs(hastaDigito - desdeDigito);
            int rollsReversos = 10 - rollsDirectos;
            rolls += min(rollsDirectos, rollsReversos);
            desde /= 10;
            hasta /= 10;
        }
        return rolls;
    }

    void crearGrafoLock() {
        listaCodigos.push_back(0); // Asegura que el código 0000 esté presente

        for (int codigo : listaCodigos) {
            listaAdyacencias[codigo] = list<Combinacion>();
        }

        for (int codigo : listaCodigos) {
            for (int vecino : listaCodigos) {
                if (codigo != vecino) {
                    listaAdyacencias[codigo].push_back(Combinacion(vecino, rollsTotales(codigo, vecino)));
                }
            }
        }
        //ahora lo borro al 0 de la lista de codigos.
        
    }

    int primMod() {
        // me armo un conjunto(en este caso es map, pero da igual.) de los nodos, que le paso el codigo y me devuelve el nodo que se corresponde con ese codigo, con su peso , codigo
        unordered_map<int, Nodo> nodosGrafo;
        priority_queue<Nodo> Q;
        unordered_set<int> visitados;

        // inicializo todos los nodos en infinito (max()) y con padre null, excepto el primero (el nodo 0000) que lo inicializo con peso 0.
        for (int codigo : listaCodigos) {
            nodosGrafo[codigo] = Nodo(numeric_limits<int>::max(), codigo, nullptr);
        }
        //aca manejo el primero.
        nodosGrafo[0].peso = 0;

        //lo agrego a la cola de prioridad.
        Q.push(nodosGrafo[0]);

        //empiezo el costo total en 0
        int costoTotal = 0;

        //esto lo voy a usar para eliminar todas las conexiones con el 0000 luego de la segunda iteracion, no es muy elegante.
        int segundaIteracion = 0;

        //codigo generico prim del cormen.
        while (!Q.empty()) {
            //me guardo el actual.
            Nodo nodoActual = Q.top();
            //lo saco de la queue
            Q.pop();

            //aca manejo el caso que estoy en la segunda iteracion, donde borro la posibilidad de pasar por el 0000 para elegir otra arista
            //, salvo que sea un codigo
            if(!estaElCeroComoCodigo){
                if(segundaIteracion == 1){

                while(!Q.empty()){

                    Q.pop();

                }
            }
            }
            
            //me fijo si el nodo actual esta visitado, en ese caso paso al siguiente.
            if (visitados.count(nodoActual.codigo) != 0) {
                continue;
            }

            costoTotal += nodoActual.peso;

            visitados.insert(nodoActual.codigo); // Marca el nodo como visitado

            for (const Combinacion& vecino : listaAdyacencias[nodoActual.codigo]) {

                if(segundaIteracion == 1){

                    nodosGrafo[vecino.codigo].peso = vecino.peso;
                    
                    Q.push(nodosGrafo[vecino.codigo]);
                }
                else {
                //esto es clave para actualizar el peso del nodo segun el peso de la arista del grafo original.
                if(nodosGrafo[vecino.codigo].peso > vecino.peso) {
                    nodosGrafo[vecino.codigo].peso = vecino.peso;
                    
                    Q.push(nodosGrafo[vecino.codigo]);
                }
             }
            }
            segundaIteracion++;
        }

        return costoTotal;
    }
};

int main() {
    bool estaElCero = false;
    int tests;
    cin >> tests;
    while (tests--) {
        int cantCodigos;
        cin >> cantCodigos;
        vector<int> codigos(cantCodigos);
        for (int i = 0; i < cantCodigos; ++i) {
            cin >> codigos[i];
            if (codigos[i] == 0) {
                estaElCero = true;
            }
        }
        Lock lock(codigos);
        lock.estaElCeroComoCodigo = estaElCero;

        int costoAGM = lock.primMod();
        cout << costoAGM << endl;
        estaElCero = false;
    }

    return 0;
}