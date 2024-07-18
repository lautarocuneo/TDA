#include <iostream>
#include <vector>
#include <queue>
#include <unordered_set>
#include <algorithm>

using namespace std;

class Lock {
public:
    vector<vector<int>> adjMatrix;
    vector<int> keys;

    Lock(const vector<int>& codigos) : keys(codigos) {
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
        int n = keys.size();
        adjMatrix = vector<vector<int>>(n, vector<int>(n, 0));
        
        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                int cost = rollsTotales(keys[i], keys[j]);
                adjMatrix[i][j] = cost;
                adjMatrix[j][i] = cost;
            }
        }
    }

    int primMod() {
        int n = keys.size();
        vector<bool> inMST(n, false);
        vector<int> minRolls(n, INT_MAX);
        minRolls[0] = 0;
        priority_queue<pair<int, int>, vector<pair<int, int>>, greater<pair<int, int>>> pq;
        pq.push({0, 0});
        int totalRolls = 0;

        while (!pq.empty()) {
            int u = pq.top().second;
            pq.pop();

            if (inMST[u]) continue;

            inMST[u] = true;
            totalRolls += minRolls[u];

            for (int v = 0; v < n; ++v) {
                if (!inMST[v] && adjMatrix[u][v] < minRolls[v]) {
                    minRolls[v] = adjMatrix[u][v];
                    pq.push({minRolls[v], v});
                }
            }
        }

        return totalRolls;
    }
};

int main() {
    int tests;
    cin >> tests;
    while (tests--) {
        int cantCodigos;
        cin >> cantCodigos;
        vector<int> codigos(cantCodigos);
        for (int i = 0; i < cantCodigos; ++i) {
            string s;
            cin >> s;
            codigos[i] = stoi(s);
        }

        codigos.push_back(0);  // Add the initial combination "0000"
        Lock lock(codigos);

        int costoAGM = lock.primMod();
        cout << costoAGM << endl;
    }

    return 0;
}
