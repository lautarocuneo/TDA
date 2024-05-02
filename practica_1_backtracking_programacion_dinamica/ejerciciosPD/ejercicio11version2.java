public class ejercicio11version2 {
    static int w = 400;
    static int[] numeros = {3, 1, 5, 2, 1};
    static Boolean[][] memo;

    public static void main(String[] args) {
        memo = new Boolean[numeros.length][w + 1];
        boolean tieneSolucion = operaciones(0, 0);
        System.out.println("Tiene solución: " + tieneSolucion);
        if (tieneSolucion) {
            System.out.println("La solución es: " + reconstruirSolucion());
        }
        for (int i = 0; i < memo.length; i++) {
            for (int j = 0; j < memo[i].length; j++) {
                System.out.print(memo[i][j] + " ");
            }
            System.out.println();
        }
        
    }

    public static boolean operaciones(int i, int x) {
        if (i == numeros.length) {
            return x == w;
        }
        if (x > w) {
            return false;
        }
        if (memo[i][x] != null) {
            return memo[i][x];
        }
        boolean caso1 = operaciones(i + 1, (int) Math.pow(x, numeros[i]));
        boolean caso2 = operaciones(i + 1, x * numeros[i]);
        boolean caso3 = operaciones(i + 1, x + numeros[i]);
        return memo[i][x] = caso1 || caso2 || caso3;
    }

    public static String reconstruirSolucion() {
        int x = 0;
        String res = "";
        for (int i = 0; i < numeros.length - 1; i++) {
            if ((int) Math.pow(x, numeros[i]) <= w && memo[i + 1][(int) Math.pow(x, numeros[i])]) {
                x = (int) Math.pow(x, numeros[i]);
                res += "^";
            } else if (x * numeros[i] <= w && memo[i + 1][x * numeros[i]]) {
                x *= numeros[i];
                res += "x";
            } else if (x + numeros[i] <= w && memo[i + 1][x + numeros[i]]) {
                x += numeros[i];
                res += "+";
            } else {
                return "Error";
            }
        }
        return x == w ? res : "Error";
    }
}