
    int primMod() {
        // Declaración de estructuras necesarias para el algoritmo de Prim
        unordered_map<int, Nodo*> nodosGrafo;
        priority_queue<Nodo> pq;

        // Inicialización del nodo inicial (0000) con peso 0 y padre null
        for (int codigo : listaCodigos) {
            nodosGrafo[codigo] = new Nodo(numeric_limits<int>::max(), codigo, nullptr);
        }
        nodosGrafo[0]->peso = 0;
        pq.push(*nodosGrafo[0]);

        int totalCost = 0;
        bool primerNodoConectado = false;
        int segundaIteracion = 0;

        while (!pq.empty()) {
            Nodo nodoActual = pq.top();
            pq.pop();
            if(segundaIteracion == 1){
                while(!pq.empty()){
                    pq.pop();
                }
            }
            //me fijo si el nodo esta visitado, en ese caso paso al siguiente.
            if (nodosGrafo[nodoActual.codigo] == nullptr) {
                continue;
            }

            totalCost += nodoActual.peso;

            nodosGrafo[nodoActual.codigo] = nullptr; // Marca el nodo como visitado

            // Limitar a un solo nodo conectado desde 0000
            if (nodoActual.codigo == 0 && primerNodoConectado) {
                continue;
            }
            primerNodoConectado = true;

            for (const Combinacion& vecino : listaAdyacencias[nodoActual.codigo]) {
                int codigoVecino = vecino.codigo;
                int pesoVecino = vecino.peso;

                if (nodosGrafo[codigoVecino] != nullptr) {
                    nodosGrafo[codigoVecino]->peso = pesoVecino;
                    nodosGrafo[codigoVecino]->padre = nodosGrafo[nodoActual.codigo];
                    pq.push(*nodosGrafo[codigoVecino]);
                }
            }
            segundaIteracion++;
        }

        // Limpieza de memoria
        for (auto& par : nodosGrafo) {
            delete par.second;
        }

        return totalCost;
    }
};