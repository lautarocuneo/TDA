/*
 
sea un vector v = {v1,v2,v4,..., vn} y un w natural. 
intercalar entre los elementos de v las operaciones + (suma) x (multiplicacion)  y potenciacion, de tal manera
que al evaluar la expresion obtenida el resultado sea w .

ejemplo : 

v = (3,1,5,2,1) 

las operaciones son +, x , ^ y x (en ese orden)

w = 400 

y 

(((3 + 1) × 5) ↑ 2) × 1 = 400.


a) escribir formulacion recursiva que sea la base de un algoritmo de PD  , que dados v y w encuentre una secuencia de operaciones como la deseada, en caso de que tal  sec
exista . explicar su semantica y cuales serian los parametros. 

la semantica de mi f(v,w) = "operaciones que necesito para dados numeros en v , llegar a w"

formulacion recursiva que se me ocurre: en cada paso me gustaria probar para cada resultado una operacion distinta y armar todo el arbol de posibilidades.
Esto seria , que para cada numero en v, tengo 3 operaciones (opciones) , esto en complejidad seria 3 ^ (n-1) , donde n es |v|-1 , porque para cada elemento tengo 3 operaciones. posibles.

f(i,x) = 

        x == w si i == n , donde n = v.size()-1
        f(i + 1, x + numeros[i]) || f(i + 1, x * numeros[i]) || f(i + 1, x ^ numeros[i]) cc
        
        
f(i,x,res) : "error" si i == n y x != w 
"error" si x > w
res si i == n y x == w
f(i+1,x^nums[i], res ++ "↑") si x^nums[i] <= w
f(i+1,x*nums[i], res ++ "x") si x*nums[i] <= w
f(i+1,x+nums[i], res ++ "+") si x+nums[i] <= w
 */
import java.util.Scanner;

public class ejercicio11 {
    public static int w;
    public static int[] numeros;
    public static int[][] memo;
    public static String resultado = "";
    
    ejercicio11(){}

    public static String operaciones(int i, int x, String res) {
        System.out.println(x);
        if(i == numeros.length) {
            if(x == w) {
                System.out.println(res);
                return res; 
            }else {
                return "Error";
            }
        }
    
        if(w < x) {
            return "Error";
        }
    
        String casoPow = "Error";
        String casoMult = "Error";
        String casoSum = "Error";
    
        if((int) Math.pow(x, numeros[i]) <= w){
            casoPow = operaciones(i + 1,(int) Math.pow(x, numeros[i]), res + "^");
        }
        if(x * numeros[i] <= w){
            casoMult = operaciones(i + 1,x * numeros[i], res + "x");
        }
        if(x + numeros[i] <= w) {
            casoSum = operaciones(i + 1, x + numeros[i], res + "+");
        }
    
        if(!casoPow.equals("Error")) {
            return casoPow;
        } else if(!casoMult.equals("Error")) {
            return casoMult;
        } else {
            return casoSum;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        w = scanner.nextInt();
        int tamañoNumeros = scanner.nextInt();
        numeros = new int[tamañoNumeros];
        memo = new int[numeros.length][w + 1]; // Corrección en la inicialización de la matriz de memorización

        for (int i = 0; i < numeros.length; i++) {
            for (int j = 0; j <= w; j++) {
                memo[i][j] = -1;
            }
        }

        for(int i = 0; i < numeros.length; i++) {
            numeros[i] = scanner.nextInt();
        }

        String respuesta = operaciones(0,0, resultado);
        System.out.println(respuesta);
        
    }
}