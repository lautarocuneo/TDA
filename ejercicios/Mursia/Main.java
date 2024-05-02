import java.util.*;

public class Main {

public static int mejorAnchoAscendente;
public static int mejorAnchoDescendente;

public static ArrayList<Tupla<Integer, Integer>> listaEdificios;


public static int n;

//estas no sirven para nada , o sea servian para el otro enfoque que era muy lento.
public static int[][]  M;
public static int[][]  Md;

public static int[] anchosAsc;
public static int[] anchosDesc;

public static ArrayList<Tupla<Integer, Integer>> listaEdificiosAscendenteOptima;
public static ArrayList<Tupla<Integer, Integer>> listaEdificiosDescendenteOptima;


public Main(ArrayList<Tupla<Integer, Integer>> listaEdificios) {
    this.listaEdificios = listaEdificios;
    this.mejorAnchoAscendente = 0;
    this.mejorAnchoDescendente = 0;
    this.listaEdificiosAscendenteOptima = new ArrayList<>();
    this.listaEdificiosDescendenteOptima = new ArrayList<>();
    this.anchosAsc = new int[listaEdificios.size()];
    this.anchosDesc = new int[listaEdificios.size()];
    for(int i = 0; i < listaEdificios.size(); i++) {
        this.anchosAsc[i] = listaEdificios.get(i).getY();
        this.anchosDesc[i] = listaEdificios.get(i).getY();
    }
    
    
}

public boolean esAscendente(ArrayList<Tupla<Integer, Integer>> lista) {

    for (int i = 0; i < lista.size() - 1; i++) {
        if (lista.get(i).getX() >= lista.get(i + 1).getX()) {
            return false;
        }
    }
    return true;

}


public boolean esDescendente(ArrayList<Tupla<Integer, Integer>> lista) {
    for (int i = 0; i < lista.size() - 1; i++) {
        if (lista.get(i).getX() <= lista.get(i + 1).getX()) {
            return false;
        }
    }

    return true;
}


public int calculaAncho(ArrayList<Tupla<Integer, Integer>> l) {
    int suma = 0;
        for (Tupla<Integer, Integer> edif : l) {
            suma = suma + edif.getY();
        }
        return suma;

}


public int solveAscendenteIterativaDinamica(ArrayList<Tupla<Integer, Integer>> listaEdificios, int[] anchos){
    int maxActual = 0;
    for (int i = 0; i < listaEdificios.size(); i++){
        
        for(int j = 0; j < i; j++){
            if(listaEdificios.get(j).getX() < listaEdificios.get(i).getX()){
                if(anchos[i] <= anchos[j] + listaEdificios.get(i).getY()){
                    anchos[i] = anchos[j] + listaEdificios.get(i).getY();
                }
            }

        }
        if(anchos[i] > maxActual){
            maxActual = anchos[i];
        }
    }

    return maxActual;
}

public int solveDescendenteIterativaDinamica(ArrayList<Tupla<Integer, Integer>> listaEdificios, int[] anchos) {
    int maxActual = 0;
    for (int i = 0; i < listaEdificios.size(); i++) {
        for (int j = 0; j < i; j++) {
            if (listaEdificios.get(j).getX() > listaEdificios.get(i).getX()) {
                if (anchos[i] <= anchos[j] + listaEdificios.get(i).getY()) {
                    anchos[i] = anchos[j] + listaEdificios.get(i).getY();
                }
            }
        }
        if (anchos[i] > maxActual) {
            maxActual = anchos[i];
        }
    }
    return maxActual;
}



public int solveAscendenteDinamica(int i, ArrayList<Tupla<Integer, Integer>> listaEdificiosActual, int anchoActual){

    
    //si la actual no es ascedente ya esta lo corto
    if(!esAscendente(listaEdificiosActual)){
        return Integer.MIN_VALUE;
    }

    if(i == n){//caso base me quede sin edificios.
                 
            return 0;

    }

    else{ 

        if(M[i][anchoActual] > -1){

            return M[i][anchoActual];


        }

        //llamado con el edificio actual.
        listaEdificiosActual.add(listaEdificios.get(i));
        int included = (listaEdificiosActual.get(listaEdificiosActual.size()-1).getY()) + solveAscendenteDinamica(i + 1, listaEdificiosActual, anchoActual + listaEdificiosActual.get(listaEdificiosActual.size()-1).getY());
        listaEdificiosActual.remove(listaEdificiosActual.size()-1);

        //llamado sin el edificio actual.
        int excluded = solveAscendenteDinamica(i + 1, listaEdificiosActual, anchoActual);

        M[i][anchoActual] = Math.max(included, excluded);
        
        return M[i][anchoActual];

    }

}

public int solveDescendenteDinamica(int i, ArrayList<Tupla<Integer, Integer>> listaEdificiosActual, int anchoActual){

   
    if(!esDescendente(listaEdificiosActual)){
        return Integer.MIN_VALUE;
    }

    if(i == n){ 
        
        return 0;
    }

    else{ 

        if(Md[i][anchoActual] > -1){
            return Md[i][anchoActual];
        }

        
        listaEdificiosActual.add(listaEdificios.get(i));
        int included = (listaEdificiosActual.get(listaEdificiosActual.size()-1).getY()) + solveDescendenteDinamica(i + 1, listaEdificiosActual, anchoActual + listaEdificiosActual.get(listaEdificiosActual.size()-1).getY());
        listaEdificiosActual.remove(listaEdificiosActual.size()-1);

        
        int excluded = solveDescendenteDinamica(i + 1, listaEdificiosActual, anchoActual);

        Md[i][anchoActual] = Math.max(included, excluded);
        
        return Md[i][anchoActual];

    }

}


public void solveAscendenteBackTrack(int i, ArrayList<Tupla<Integer, Integer>> listaEdificiosActual, int anchoActual){

    //si la actual no es ascedente ya esta lo corto
    if(!esAscendente(listaEdificiosActual)){
        return;

    }

    if(i == n){//caso base me quede sin edificios.
                
            if(anchoActual >= mejorAnchoAscendente){
                
                mejorAnchoAscendente = anchoActual; //
                listaEdificiosAscendenteOptima = listaEdificiosActual;
                return;
            }

    }

    else{ 


        //llamado con el edificio actual.
        listaEdificiosActual.add(listaEdificios.get(i));
        solveAscendenteBackTrack(i + 1, listaEdificiosActual, anchoActual + listaEdificiosActual.get(listaEdificiosActual.size()-1).getY());
        listaEdificiosActual.remove(listaEdificiosActual.size()-1);

        //llamado sin el edificio actual.
        solveAscendenteBackTrack(i + 1, listaEdificiosActual, anchoActual);

    }

}


public void solveDescendenteBackTrack(int i, ArrayList<Tupla<Integer, Integer>> listaEdificiosActual, int anchoActual){

    //si la actual no es descendente ya esta lo corto
    if(!esDescendente(listaEdificiosActual)){
        return;
    }

    if(i == n){//caso base me quede sin edificios.

            if(anchoActual >= mejorAnchoDescendente){
                mejorAnchoDescendente = anchoActual;
                listaEdificiosDescendenteOptima = listaEdificiosActual;
                return;
            }

    }

    else{ 

        //llamado con el edificio actual.
        listaEdificiosActual.add(listaEdificios.get(i));
        solveDescendenteBackTrack(i + 1, listaEdificiosActual, anchoActual + listaEdificiosActual.get(listaEdificiosActual.size()-1).getY());
        listaEdificiosActual.remove(listaEdificiosActual.size()-1);

        //llamado sin el edificio actual.
        solveDescendenteBackTrack(i + 1, listaEdificiosActual, anchoActual);

    }

}

public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    //cantidad de casos de test.
    int T = scanner.nextInt();
    
    
    for (int test = 0; test < T; test++) {
        // Leer el número de edificios
        n = scanner.nextInt();
        
        // inicializao lista de edificios.
        listaEdificios = new ArrayList<>();
        
        // leo alturas de edificios
        for (int i = 0; i < n; i++) {
            int alto = scanner.nextInt();
            listaEdificios.add(new Tupla<>(alto, 0)); // Temporalmente pongo el ancho en 0
        }
        
        // leo anchos de los edificios.
        for (int i = 0; i < n; i++) {
            int ancho = scanner.nextInt();
            listaEdificios.get(i).setY(ancho); // Actualizar el ancho del edificio
        }
        Main m = new Main(listaEdificios);

        /* esto de la matriz era para el otro enfoque que no me servia.
        
        m.M = new int[n][m.calculaAncho(m.listaEdificios) + 1];
        // Llenar la matriz con -1
        for (int i = 0; i < m.listaEdificios.size(); i++) {
            Arrays.fill(m.M[i], -1);
        }
          */  //solucion con bt

        int resASC = m.solveAscendenteIterativaDinamica(listaEdificios, anchosAsc);

        /* esto de la matriz era para el otro enfoque que no me servia.
        m.Md = new int[n][m.calculaAncho(m.listaEdificios) + 1];
        // Llenar la matriz con -1
        for (int i = 0; i < m.listaEdificios.size(); i++) {
                Arrays.fill(m.Md[i], -1);
        }
        */
        int resDESC = m.solveDescendenteIterativaDinamica(listaEdificios, anchosDesc);
        
        if (resASC >= resDESC) {
            System.out.printf("Case %d. Increasing (%d). Decreasing (%d).\n", test + 1, resASC, resDESC);
        } else {
            System.out.printf("Case %d. Decreasing (%d). Increasing (%d).\n", test + 1, resDESC, resASC);
        }
            
    }
   
    scanner.close();
}


}


class Tupla<K, V> {
    private K X;
    private V Y;

    public Tupla(K X, V Y) {
        this.X = X;
        this.Y = Y;
    }

    public K getX() {
        return X;
    }

    public V getY() {
        return Y;
    }

    public void setY(V Y) {
        this.Y = Y;
    }

    public void setX(K X) {
        this.X = X;
    }

}