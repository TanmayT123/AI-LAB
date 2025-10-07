import java.util.*;

class PuzzleState {
    int[][] state;
    int g; // cost so far
    int h; // heuristic cost
    PuzzleState parent;

    PuzzleState(int[][] s, int g, PuzzleState parent, int[][] goal) {
        this.state = s;
        this.g = g;
        this.parent = parent;
        this.h = calculateHeuristic(goal);
    }

    int calculateHeuristic(int[][] goal) {
        // Manhattan distance heuristic
        int dist = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int val = state[i][j];
                if (val != 0) {
                    for (int x = 0; x < 3; x++) {
                        for (int y = 0; y < 3; y++) {
                            if (goal[x][y] == val) {
                                dist += Math.abs(i - x) + Math.abs(j - y);
                            }
                        }
                    }
                }
            }
        }
        return dist;
    }

    int f() {
        return g + h;
    }

    boolean isGoal(int[][] goal) {
        return Arrays.deepEquals(state, goal);
    }

    List<PuzzleState> getNeighbors(int[][] goal) {
        List<PuzzleState> neighbors = new ArrayList<>();
        int x = 0, y = 0;

        // find blank (0)
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (state[i][j] == 0) {
                    x = i;
                    y = j;
                }
            }
        }

        // possible moves
        int[][] moves = {{1,0}, {-1,0}, {0,1}, {0,-1}};
        for (int[] move : moves) {
            int nx = x + move[0], ny = y + move[1];
            if (nx >= 0 && nx < 3 && ny >= 0 && ny < 3) {
                int[][] newState = copy(state);
                // swap
                newState[x][y] = newState[nx][ny];
                newState[nx][ny] = 0;
                neighbors.add(new PuzzleState(newState, g + 1, this, goal));
            }
        }
        return neighbors;
    }

    static int[][] copy(int[][] src) {
        int[][] dest = new int[3][3];
        for (int i = 0; i < 3; i++) {
            dest[i] = src[i].clone();
        }
        return dest;
    }

    void printState() {
        for (int[] row : state) {
            for (int val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
        System.out.println("------");
    }
}

public class AStarPuzzle {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter initial state (9 digits, use 0 for blank): ");
        String initStr = sc.next();
        System.out.println("Enter goal state (9 digits, use 0 for blank): ");
        String goalStr = sc.next();

        int[][] init = strToMatrix(initStr);
        int[][] goal = strToMatrix(goalStr);

        PuzzleState start = new PuzzleState(init, 0, null, goal);

        PriorityQueue<PuzzleState> open = new PriorityQueue<>(Comparator.comparingInt(PuzzleState::f));
        Set<String> closed = new HashSet<>();

        open.add(start);

        while (!open.isEmpty()) {
            PuzzleState current = open.poll();

            if (current.isGoal(goal)) {
                System.out.println("Solution found!");
                printPath(current);
                return;
            }

            closed.add(matrixToString(current.state));

            for (PuzzleState neighbor : current.getNeighbors(goal)) {
                if (!closed.contains(matrixToString(neighbor.state))) {
                    open.add(neighbor);
                }
            }
        }

        System.out.println("No solution found.");
    }

    static int[][] strToMatrix(String str) {
        int[][] mat = new int[3][3];
        for (int i = 0; i < 9; i++) {
            mat[i/3][i%3] = str.charAt(i) - '0';
        }
        return mat;
    }

    static String matrixToString(int[][] mat) {
        StringBuilder sb = new StringBuilder();
        for (int[] row : mat) {
            for (int val : row) sb.append(val);
        }
        return sb.toString();
    }

    static void printPath(PuzzleState state) {
        if (state == null) return;
        printPath(state.parent);
        state.printState();
    }
}
