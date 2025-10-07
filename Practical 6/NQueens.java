public class EightQueens {
    static final int N = 8; // size of chessboard (8x8)

    public static void main(String[] args) {
        int[][] board = new int[N][N];

        if (solve(board, 0)) {
            printBoard(board);
        } else {
            System.out.println("❌ No solution exists");
        }
    }

    // Backtracking solver
    static boolean solve(int[][] board, int col) {
        // If all queens are placed
        if (col >= N) {
            return true;
        }

        // Try placing queen in all rows one by one
        for (int row = 0; row < N; row++) {
            if (isSafe(board, row, col)) {
                board[row][col] = 1; // place queen

                // Recur for next column
                if (solve(board, col + 1)) {
                    return true;
                }

                // If placing queen here leads to no solution → backtrack
                board[row][col] = 0;
            }
        }
        return false; // no place is valid
    }

    // Check if a queen can be placed at board[row][col]
    static boolean isSafe(int[][] board, int row, int col) {
        int i, j;

        // Check left side of row
        for (i = 0; i < col; i++) {
            if (board[row][i] == 1) return false;
        }

        // Check upper-left diagonal
        for (i = row, j = col; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 1) return false;
        }

        // Check lower-left diagonal
        for (i = row, j = col; i < N && j >= 0; i++, j--) {
            if (board[i][j] == 1) return false;
        }

        return true;
    }

    // Print chessboard
    static void printBoard(int[][] board) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] == 1) {
                    System.out.print("Q ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }
}
