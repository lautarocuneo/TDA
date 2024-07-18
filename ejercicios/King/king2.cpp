#include <iostream>
#include <vector>
#include <limits>
#include <climits>
using namespace std;

void bellmanFord(int n, const vector<pair<pair<int, int>, int>>& grafo) {
    // Armo un vector con todas las distancias en infinito
    vector<int> distancias(n + 1, INT_MAX);
    int fuente = n; // Nodo fuente adicional

    // Pongo el nodo fuente en cero.
    distancias[fuente] = 0;

    // Relajo todas las aristas n veces (n vértices)
    for (int i = 0; i < n; i++) {
        for (const auto& aristas : grafo) {
            int u = aristas.first.first;
            int v = aristas.first.second;
            int peso = aristas.second;

            // Si la distancia de la fuente a u + el peso es menor a la distancia en v, actualizo la distancia a v por la de u más el peso.
            if (distancias[u] != INT_MAX && distancias[u] + peso < distancias[v]) {
                distancias[v] = distancias[u] + peso;
            }
        }
    }

    // Comprobar si hay ciclos de peso negativo
    for (const auto& aristas : grafo) {
        int u = aristas.first.first;
        int v = aristas.first.second;
        int peso = aristas.second;

        if (distancias[u] != INT_MAX && distancias[u] + peso < distancias[v]) {
            cout << "successful conspiracy" << endl;
            return;
        }
    }

    cout << "lamentable kingdom" << endl;
}

int main() {
    while (true) {
        int n; // Tamaño de S (cantidad de vértices del grafo)
        int m; // Cantidad de subproblemas de S (serán las conexiones / aristas del grafo)
        cin >> n >> m;

        if (n == 0) break;

        vector<pair<pair<int, int>, int>> grafo; // Lista de aristas pesada (grafo de n vértices)

        // For para los ejes.
        for (int i = 0; i < m; i++) {
            // Tenemos que transformar estas cositas en SRD que conocemos.
            int si; // Posición inicial
            int ni; // Ni es tipo hasta donde llega el último nodo de la sumatoria, que sería si + ni.
            string oi; // Símbolo de menor o mayor
            int ki; // Valor a la derecha de oi.

            cin >> si >> ni >> oi >> ki;

            // Caso oi == "lt"
            if (oi == "lt") {
                ki--;
                grafo.push_back(make_pair(make_pair(si - 1, si + ni), ki));
            }

            // Caso oi == "gt"
            if (oi == "gt") {
                ki++;
                grafo.push_back(make_pair(make_pair(si + ni, si - 1), -ki));
            }
        }

        // Agrego el nodo fuente unido a todos los otros vértices para poder correr Bellman-Ford.
        int fuente = n;
        for (int i = 0; i <= n; i++) {
            grafo.push_back(make_pair(make_pair(fuente, i), 0));
        }

        bellmanFord(n, grafo);
    }

    return 0;
}
