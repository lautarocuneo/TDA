#include <iostream>
#include <vector>
#include <algorithm>
#include <limits>
#include <climits>
#include <sstream>

using namespace std;

void reordenarTorres(vector<vector<long long>>& torresOrdenViejo, vector<int>& orden) {

    vector<vector<long long>> torresTemp(torresOrdenViejo.size(), vector<long long>(torresOrdenViejo.size())); 
    
    
    
    vector<int> ordenInverso(torresOrdenViejo.size());


    for (int i = 0; i < torresOrdenViejo.size(); i++) {
        ordenInverso[i] = orden[orden.size() - 1 - i];
    }

    //para poder aprovechar el invariante de dantzig reordeno las torres de forma que primero aparezcan en la matriz las torres que ultimas voy a destruir.
    //para eso doy vuelta el orden de destruccion y reordeno la matriz segun este orden invertido,
    //es como que estoy reconstruyendolas en cada paso y el costo es de reconstruirlas (asumo que ya estan destruidas las anteriores)
    //es una forma de verlo....

    
    for (int i = 0; i < torresOrdenViejo.size(); i++) {

        for (int j = 0; j < torresOrdenViejo.size(); j++) {

            //en cada posicion de torres temp, estoy poniendo lo que habia en la matriz de orden viejo, en el orden de destruccion inverso.
            //orden de destruccion inverso tiene los indices de la matriz de torres orden viejo.
            torresTemp[i][j] = torresOrdenViejo[ordenInverso[i]][ordenInverso[j]];

        }
    }

    

    torresOrdenViejo = torresTemp;

    

}

long long dantzigMod(vector<vector<long long>>& torres) {
    long long energiaTotal = 0;

    for (int k = 0; k < torres.size(); k++) {
       
        for (int i = 0; i <= k; i++) {

            for(int j = 0; j <= k; j++){

                torres[i][k] = min(torres[i][k], torres[i][j] + torres[j][k]);
                torres[k][i] = min(torres[k][i], torres[k][j] + torres[j][i]);

                
            }
            
        }

        for (int i = 0; i <= k; i++) {
            for (int j = 0; j <= k; j++) {
                torres[i][j] = min(torres[i][j], torres[i][k] + torres[k][j]);
                energiaTotal += torres[i][j];
            }
        }
    }


    return energiaTotal;
}


int main(int argc, char const *argv[])
{
    
    int tests;

    cin >> tests; 


    for(int i = 0; i < tests; i++){

        int cantTorres;

        cin >> cantTorres;

        vector<int> ordenDestruccion(cantTorres);

        vector<vector<long long>> torres(cantTorres, vector<long long>(cantTorres));

        for(int i = 0; i < cantTorres; i++){
            for(int j = 0; j < cantTorres; j++){
                
                long long señal;

                cin >> señal; 

                torres[i][j] = señal;

            }
        }

        for(int i = 0; i < cantTorres; i++){

            int orden;
            cin >> orden;

            ordenDestruccion[i] = orden;

        }

        reordenarTorres(torres, ordenDestruccion);

        long long res = 0;


        res = dantzigMod(torres);

        cout << res << endl; 
    }




    return 0;
}
