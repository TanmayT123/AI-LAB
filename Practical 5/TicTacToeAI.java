import java.util.*;

public class TicTacToeAI {
    static final char HUMAN = 'X';
    static final char AI = 'O';
    static final char EMPTY = '-';
    static char[][] board = {
        { EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY },
        { EMPTY, EMPTY, EMPTY }
    };

    static boolean usePruning = true; // default

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Tic Tac Toe Game (Human = X, AI = O)");

        // Ask about pruning
        System.out.print("Use alpha-beta pruning for AI? (yes/no): ");
        String choice = sc.nextLine().trim().toLowerCase();
        usePruning = choice.equals("yes");

        printBoard();

        while (true) {
            // Human move
            System.out.println("Enter your move (row[0-2] and col[0-2]): ");
            int row = sc.nextInt();
            int col = sc.nextInt();
            if (board[row][col] != EMPTY) {
                System.out.println("❌ Invalid move! Try again.");
                continue;
            }
            board[row][col] = HUMAN;
            printBoard();

            if (isGameOver(HUMAN)) {
                System.out.println("🎉 You win!");
                break;
            }
            if (isDraw()) {
                System.out.println("🤝 It's a draw!");
                break;
            }

            // AI move
            System.out.println("AI is making its move...");
            int[] bestMove = findBestMove();
            board[bestMove[0]][bestMove[1]] = AI;
            printBoard();

            if (isGameOver(AI)) {
                System.out.println("💻 AI wins!");
                break;
            }
            if (isDraw()) {
                System.out.println("🤝 It's a draw!");
                break;
            }
        }
        sc.close();
    }

    // Print current board
    static void printBoard() {
        for (char[] row : board) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // Check if a player has won
    static boolean isGameOver(char player) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) return true;
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) return true;
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) return true;
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) return true;
        return false;
    }

    // Check if board is full
    static boolean isDraw() {
        for (char[] row : board) {
            for (char cell : row) {
                if (cell == EMPTY) return false;
            }
        }
        return true;
    }

    // Find best move for AI
    static int[] findBestMove() {
        int bestScore = Integer.MIN_VALUE;
        int[] move = new int[2];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY) {
                    board[i][j] = AI;
                    int score;
                    if (usePruning) {
                        score = minimaxWithPruning(0, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    } else {
                        score = minimaxNoPruning(0, false);
                    }
                    board[i][j] = EMPTY;
                    if (score > bestScore) {
                        bestScore = score;
                        move[0] = i;
                        move[1] = j;
                    }
                }
            }
        }
        return move;
    }

    // Minimax without pruning
    static int minimaxNoPruning(int depth, boolean isMaximizing) {
        if (isGameOver(AI)) return 10 - depth;
        if (isGameOver(HUMAN)) return depth - 10;
        if (isDraw()) return 0;

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == EMPTY) {
                        board[i][j] = AI;
                        int score = minimaxNoPruning(depth + 1, false);
                        board[i][j] = EMPTY;
                        bestScore = Math.max(bestScore, score);
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == EMPTY) {
                        board[i][j] = HUMAN;
                        int score = minimaxNoPruning(depth + 1, true);
                        board[i][j] = EMPTY;
                        bestScore = Math.min(bestScore, score);
                    }
                }
            }
            return bestScore;
        }
    }

    // Minimax with Alpha-Beta pruning
    static int minimaxWithPruning(int depth, boolean isMaximizing, int alpha, int beta) {
        if (isGameOver(AI)) return 10 - depth;
        if (isGameOver(HUMAN)) return depth - 10;
        if (isDraw()) return 0;

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == EMPTY) {
                        board[i][j] = AI;
                        int score = minimaxWithPruning(depth + 1, false, alpha, beta);
                        board[i][j] = EMPTY;
                        bestScore = Math.max(bestScore, score);
                        alpha = Math.max(alpha, score);
                        if (beta <= alpha) return bestScore; // prune
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == EMPTY) {
                        board[i][j] = HUMAN;
                        int score = minimaxWithPruning(depth + 1, true, alpha, beta);
                        board[i][j] = EMPTY;
                        bestScore = Math.min(bestScore, score);
                        beta = Math.min(beta, score);
                        if (beta <= alpha) return bestScore; // prune
                    }
                }
            }
            return bestScore;
        }
    }
}
