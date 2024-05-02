public class MagicSquare {
    int N; // The order of the magic square
    int[] numbers; // The numbers to be arranged
    boolean[] used; // Whether a number has been used
    int[][] square; // The magic square

    public MagicSquare(int N) {
        this.N = N;
        this.numbers = new int[N*N];
        this.used = new boolean[N*N];
        this.square = new int[N][N];

        for (int i = 0; i < N*N; i++) {
            numbers[i] = i + 1;
        }
    }

    public void generate(int row, int col) {
        if (row == N) {
            if (isMagicSquare()) {
                printSquare();
            }
            return;
        }

        for (int i = 0; i < N*N; i++) {
            if (!used[i]) {
                used[i] = true;
                square[row][col] = numbers[i];

                int nextRow = row;
                int nextCol = col + 1;
                if (nextCol == N) {
                    nextRow++;
                    nextCol = 0;
                }

                generate(nextRow, nextCol);
                //square[row][col] = 0;
                used[i] = false;
            }
        }
    }

    private boolean isMagicSquare() {
        int sum = 0;
        for (int i = 0; i < N; i++) {
            sum += square[0][i];
        }

        for (int i = 1; i < N; i++) {
            int rowSum = 0;
            for (int j = 0; j < N; j++) {
                rowSum += square[i][j];
            }
            if (rowSum != sum) return false;
        }

        for (int i = 0; i < N; i++) {
            int colSum = 0;
            for (int j = 0; j < N; j++) {
                colSum += square[j][i];
            }
            if (colSum != sum) return false;
        }

        int diagSum1 = 0;
        int diagSum2 = 0;
        for (int i = 0; i < N; i++) {
            diagSum1 += square[i][i];
            diagSum2 += square[i][N-i-1];
        }
        if (diagSum1 != sum || diagSum2 != sum) return false;

        return true;
    }

    private void printSquare() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(square[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        MagicSquare ms = new MagicSquare(3);
        ms.generate(0, 0);
    }
}