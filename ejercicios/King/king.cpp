#include <iostream>
#include <vector>
#include <limits>
#include <climits>
using namespace std;


void bellmanFord(int n, vector<pair<pair<int, int>, int>>& grafo) {

        //armo un vector con todas las distancias en infinito
        vector<int> distancias(n + 1, INT_MAX);

        int fuente = n;


        //pongo el primero en cero.
        distancias[fuente] = 0;

        //relajo n veces las aristas

        for(int i = 0; i <= n; i++) {
            for(const auto& aristas : grafo) {
                
                int u = aristas.first.first;
                int v = aristas.first.second;
                int peso = aristas.second;

                 //si en la iteracion i == n cambia una distancia, entonces habia un ciclo.
                 if(i == n){
                    if(distancias[u] != INT_MAX && distancias[u] + peso < distancias[v]){

                        cout << "successful conspiracy" << endl;
                        return;

                    }
                

                } 

                //si la distancia de la fuente a u + el peso es menor a la distancia en v, actualizo la distancia a v por la de u mas el peso.
                if(distancias[u] != INT_MAX && distancias[u] + peso < distancias[v]){

                    distancias[v] = distancias[u] + peso;

                }
            }
    }  

    //si nunca entra a ese if todo ok , sigue el rey.
    cout << "lamentable kingdom" << endl;
                       
    
  
}


int main() {



    
    int n; //tamaño de S (cantidad de vertices del grafo)
    int m; //cantidad de subproblemas de S (seran las conexiones / aristas del grafo)
    
    while(n != 0){
        cin >> n >> m;

        if(n == 0){
            break;
        }

        vector<pair<pair<int,int>, int>> grafo; //lista de aristas pesada (grafo de n vertices.) me conviene este formato de grafo porque bellman ford lo usa directamente
                                            // es + 1 por que la ultima posicion la uso para el nodo fuente unido a todos
        

        //for para los ejes.
        for(int i = 0; i < m; i++){

            //tenemos que transformar estas cositas en SRD que conocemos.

            int si; //posicion inicial
            int ni; //ni es tipo hasta donde llega el ultimo nodo de la sumatoria, que seria si + ni.
            string oi; //simbolo de menor o mayor
            int ki; // valor a la derecha de oi.

            cin >> si >> ni >> oi >> ki;

            // caso oi == "lt"

            if(oi == "lt"){

                //simplemente decrementar ki en 1. y ya tenemos el <=. 

                //IMPORTANTE:
                //con los inputs tenemos que armar la entrada
                //teniendo si y ni tenemos todos los nodos desde si a si + ni sumados por ejemplo y ki = 5 
                // si  = 2 y ni = 2 tenemos x2 + x3 + x4 < 5
                //luego, deinimos a[i] que tiene las sumas parciales de los primeros i elemenetos
                //entonces vamos a querer a[4] - a[1] <= 4.
                //o sea lo que realmente necesitamos es a[si + ni] - a[si - 1] < / > ki - 1 ;
                // y el grafo sera (si - 1) ----> (si + ni) con peso (ki - 1)

                grafo.push_back(make_pair(make_pair(si - 1 , si + ni), ki - 1));

            }

            if(oi == "gt") {
            
            //en este caso es medio lo mismo , pero hay que multiplicar por -1 de los dos lados para dar vuelta el signo.
            //con los inputs tenemos que armar la entrada
            //teniendo si y ni tenemos todos los nodos desde si a si + ni sumados por ejemplo y ki = 5 
            // si  = 2 y ni = 2 tenemos x2 + x3 + x4 > 5
            //luego, deinimos a[i] que tiene las sumas parciales de los primeros i elemenetos
            //entonces vamos a querer a[4] - a[1] > 5.
            //ahora multiplicamos por -1 y nos queda
            // -a[4] + a[1] < -5
            // nos falta que quede <= 
            //esto es:
            // a[1] - a[4] <= -6

            //entonces el grafo sera (si + ni) ----> (si - 1) con peso -(ki + 1)

            grafo.push_back(make_pair(make_pair(si + ni , si - 1), -(ki + 1)));

            }
        }

        //agrego el nodo fuente unido a todos los otros vertices para poder correr bellman ford.
        int fuente = n + 1;
        for (int i = 0; i <= n; i++) {
                grafo.push_back(make_pair(make_pair( fuente , i), 0));
        }

        bellmanFord(n + 1, grafo);


    }

    return 0;
}


