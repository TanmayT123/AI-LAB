import java.util.*;

public class CSP_NQueens {

    static int N = 4;  // board size (4-Queens)

    // Check if placing a queen is valid
    static boolean isSafe(int board[], int row, int col) {
        for (int i = 0; i < row; i++) {
            int qCol = board[i];
            if (qCol == col || Math.abs(qCol - col) == Math.abs(i - row)) {
                return false;
            }
        }
        return true;
    }

    // Backtracking with path printing
    static boolean solve(int board[], int row) {
        if (row == N) {
            printBoard(board);
            return true;
        }

        for (int col = 0; col < N; col++) {
            if (isSafe(board, row, col)) {
                board[row] = col;
                System.out.println("Step: Placed queen at (" + row + "," + col + ")");
                if (solve(board, row + 1)) return true;
                board[row] = -1; // backtrack
                System.out.println("Backtrack from (" + row + "," + col + ")");
            }
        }
        return false;
    }

    static void printBoard(int board[]) {
        System.out.println("\nFinal Solution:");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i] == j) System.out.print("Q ");
                else System.out.print(". ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[] board = new int[N];
        Arrays.fill(board, -1);

        if (!solve(board, 0)) {
            System.out.println("No solution found.");
        }
    }
}
