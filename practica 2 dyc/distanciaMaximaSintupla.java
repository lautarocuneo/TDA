public class distanciaMaximaSintupla {

    static class Nodo {
        int valor;
        Nodo izquierda, derecha;
    
        Nodo(int item) {
            valor = item;
            izquierda = derecha = null;
        }
    }
    
    static class ArbolBinario {
        Nodo raiz;
    
        int altura(Nodo nodo) {
            if (nodo == null) {
                return 0;
            }
    
            int alturaIzq = altura(nodo.izquierda);
            int alturaDer = altura(nodo.derecha);
    
            return Math.max(alturaIzq, alturaDer) + 1;
        }
    
        int distanciaMaxima(Nodo nodo) {
            if (nodo == null) {
                return 0;
            }
    
            int alturaIzq = altura(nodo.izquierda);
            int alturaDer = altura(nodo.derecha);
            int maxDistanciaIzq = distanciaMaxima(nodo.izquierda);
            int maxDistanciaDer = distanciaMaxima(nodo.derecha);
    
            return Math.max(Math.max(maxDistanciaIzq, maxDistanciaDer), alturaIzq + alturaDer + 1);
        }
    
        int distanciaMaxima() {
            return distanciaMaxima(raiz);
        }
    }
    
    public static void main(String[] args) {
        ArbolBinario arbol = new ArbolBinario();
        arbol.raiz = new Nodo(1);
        arbol.raiz.izquierda = new Nodo(2);
        arbol.raiz.derecha = new Nodo(3);
        arbol.raiz.izquierda.izquierda = new Nodo(4);
        arbol.raiz.izquierda.derecha = new Nodo(5);

        System.out.println(arbol.distanciaMaxima());
    }
}
