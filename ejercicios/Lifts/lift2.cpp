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

using namespace std;

class Nodo {
public:
    int piso;
    int ascensor;
    bool sePuedeBajar;

    Nodo(int piso, int ascensor, bool sePuedeBajar = false) : piso(piso), ascensor(ascensor), sePuedeBajar(sePuedeBajar) {}

    bool operator==(const Nodo& otro) const {
        return piso == otro.piso && ascensor == otro.ascensor && sePuedeBajar == otro.sePuedeBajar;
    }

    bool operator!=(const Nodo& otro) const {
        return !(*this == otro);
    }

    bool operator<(const Nodo& otro) const {
        if (piso != otro.piso) return piso < otro.piso;
        if (ascensor != otro.ascensor) return ascensor < otro.ascensor;
        return sePuedeBajar < otro.sePuedeBajar;
    }
};

class Lift {
public:
    map<Nodo, list<pair<Nodo, int>>> edificio;
    vector<list<int>> listaParadasPorAscensor;
    vector<int> velocidadPorAscensor;

    Lift(const vector<list<int>>& paradas, const vector<int>& velocidades) : listaParadasPorAscensor(paradas), velocidadPorAscensor(velocidades) {
        crearEdificio();
    }

    void crearEdificio() {
        for (int ascensor = 0; ascensor < listaParadasPorAscensor.size(); ascensor++) {
            const list<int>& paradas = listaParadasPorAscensor[ascensor];
            for (auto it = paradas.begin(); it != paradas.end(); ++it) {
                auto next = it;
                ++next;
                if (next != paradas.end()) {
                    Nodo actual(*it, ascensor + 1, false);
                    Nodo siguiente(*next, ascensor + 1, false);
                    int peso = abs(*next - *it) * velocidadPorAscensor[ascensor];
                    edificio[actual].push_back(make_pair(siguiente, peso));
                    edificio[siguiente].push_back(make_pair(actual, peso));
                }
            }
        }

        for (int piso = 0; piso < 100; piso++) {
            for (int ascensor1 = 0; ascensor1 < listaParadasPorAscensor.size(); ascensor1++) {
                if (count(listaParadasPorAscensor[ascensor1].begin(), listaParadasPorAscensor[ascensor1].end(), piso)) {
                    for (int ascensor2 = ascensor1 + 1; ascensor2 < listaParadasPorAscensor.size(); ascensor2++) {
                        if (count(listaParadasPorAscensor[ascensor2].begin(), listaParadasPorAscensor[ascensor2].end(), piso)) {
                            Nodo nodo1(piso, ascensor1 + 1, false);
                            Nodo nodo2(piso, ascensor2 + 1, true);
                            edificio[nodo1].push_back(make_pair(nodo2, 60));
                            edificio[nodo2].push_back(make_pair(nodo1, 60));
                        }
                    }
                }
            }
        }
    }

    int dijkstra(Nodo inicio, Nodo destino) {
        map<Nodo, int> distancias;
        for (const auto& nodo : edificio) {
            distancias[nodo.first] = numeric_limits<int>::max();
        }
        distancias[inicio] = 0;

        priority_queue<pair<int, Nodo>, vector<pair<int, Nodo>>, greater<pair<int, Nodo>>> pq;
        pq.push(make_pair(0, inicio));

        while (!pq.empty()) {
            Nodo actual = pq.top().second;
            int distanciaActual = pq.top().first;
            pq.pop();

            if (actual.piso == destino.piso && actual.ascensor == destino.ascensor) {
                return distanciaActual;
            }

            if (distanciaActual > distancias[actual]) continue;

            for (const auto& vecino : edificio[actual]) {
                Nodo nodoVecino = vecino.first;
                int peso = vecino.second;
                int nuevaDistancia = distanciaActual + peso;

                if (nuevaDistancia < distancias[nodoVecino]) {
                    distancias[nodoVecino] = nuevaDistancia;
                    pq.push(make_pair(nuevaDistancia, nodoVecino));
                }
            }
        }

        return -1;
    }
};

int main() {
    int numTestCases;
    cin >> numTestCases;

    for (int t = 0; t < numTestCases; t++) {
        int n, k;
        cin >> n >> k;

        vector<int> velocidades(n);
        for (int i = 0; i < n; i++) {
            cin >> velocidades[i];
        }

        vector<list<int>> paradas(n);
        for (int i = 0; i < n; i++) {
            int numParadas, piso;
            cin >> numParadas;
            for (int j = 0; j < numParadas; j++) {
                cin >> piso;
                paradas[i].push_back(piso);
            }
        }

        Lift edificio(paradas, velocidades);

        int tiempoMinimo = numeric_limits<int>::max();

        for (int ascensor = 1; ascensor <= n; ascensor++) {
            if (count(paradas[ascensor - 1].begin(), paradas[ascensor - 1].end(), 0)) {
                Nodo inicio(0, ascensor, false);
                Nodo destino(k, ascensor, false);
                int tiempo = edificio.dijkstra(inicio, destino);
                if (tiempo != -1) {
                    tiempoMinimo = min(tiempoMinimo, tiempo);
                }
            }
        }

        if (tiempoMinimo == numeric_limits<int>::max()) {
            cout << "IMPOSSIBLE" << endl;
        } else {
            cout << tiempoMinimo << endl;
        }
    }

    return 0;
}
