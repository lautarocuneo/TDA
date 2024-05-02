class Nodo {
    int valor;
    Nodo izquierda, derecha;

    Nodo(int item) {
        valor = item;
        izquierda = derecha = null;
    }
}

class ArbolBinario {
    Nodo raiz;
    int maxDistancia = 0;

    //la complejidad seria la altura del arbol izquierdo y la del arbol derecho, en el peor caso log(n), siendo n el total de nodos.


    public static void main(String[] args) {
    
        ArbolBinario arbol = new ArbolBinario();
        arbol.raiz = new Nodo(1);
        arbol.raiz.izquierda = new Nodo(2);
        arbol.raiz.derecha = new Nodo(3);
        arbol.raiz.izquierda.izquierda = new Nodo(4);
        arbol.raiz.izquierda.derecha = new Nodo(5);


        System.out.println(arbol.distanciaMaxima());

    
        
    }

    class Resultado {
        int altura;
        int maxDistancia;
    
        Resultado(int altura, int maxDistancia) {
            this.altura = altura;
            this.maxDistancia = maxDistancia;
        }
    }
    
    Resultado calcularAlturaYDistanciaMaxima(Nodo nodo) {
        if (nodo == null) {
            return new Resultado(0, 0);
        }
    
        Resultado izquierda = calcularAlturaYDistanciaMaxima(nodo.izquierda);
        Resultado derecha = calcularAlturaYDistanciaMaxima(nodo.derecha);
    
        int altura = Math.max(izquierda.altura, derecha.altura) + 1;
        int maxDistancia = Math.max(Math.max(izquierda.maxDistancia, derecha.maxDistancia), izquierda.altura + derecha.altura + 1);
    
        return new Resultado(altura, maxDistancia);
    }
    
    int distanciaMaxima() {
        return calcularAlturaYDistanciaMaxima(raiz).maxDistancia;
    }
}