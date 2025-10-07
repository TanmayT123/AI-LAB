import java.util.*;

class puzzle {
    static class Node {
        String state;   
        Node parent;    
        String move;    

        Node(String state, Node parent, String move) {
            this.state = state;
            this.parent = parent;
            this.move = move;
        }
    }

    static String goal = "123456780"; 

   
    static int[][] moves = {
            {-1, 0}, 
            {1, 0},  
            {0, -1},
            {0, 1}  
    };

    
    static void printPuzzle(String state) {
        for (int i = 0; i < 9; i++) {
            System.out.print(state.charAt(i) + " ");
            if (i % 3 == 2) System.out.println();
        }
        System.out.println();
    }

  
    static List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();
        String s = node.state;
        int zero = s.indexOf('0');
        int r = zero / 3, c = zero % 3;

        for (int[] m : moves) {
            int nr = r + m[0], nc = c + m[1];
            if (nr >= 0 && nr < 3 && nc >= 0 && nc < 3) {
                int newPos = nr * 3 + nc;
                char[] arr = s.toCharArray();
                arr[zero] = arr[newPos];
                arr[newPos] = '0';
                String newState = new String(arr);
                neighbors.add(new Node(newState, node, "move"));
            }
        }
        return neighbors;
    }

    
    static Node search(String initial, boolean bfs) {
        Set<String> visited = new HashSet<>();
        Deque<Node> frontier = new ArrayDeque<>();
        frontier.add(new Node(initial, null, null));
        visited.add(initial);

        while (!frontier.isEmpty()) {
            Node current = bfs ? frontier.pollFirst() : frontier.pollLast();

            if (current.state.equals(goal)) {
                return current; 
            }

            for (Node neighbor : getNeighbors(current)) {
                if (!visited.contains(neighbor.state)) {
                    visited.add(neighbor.state);
                    if (bfs) frontier.addLast(neighbor);
                    else frontier.addLast(neighbor);
                }
            }
        }
        return null;
    }

    
    static void printPath(Node goalNode) {
        List<Node> path = new ArrayList<>();
        Node n = goalNode;
        while (n != null) {
            path.add(n);
            n = n.parent;
        }
        Collections.reverse(path);

        System.out.println("Solution Path:");
        for (int i = 0; i < path.size(); i++) {
            System.out.println("Step " + i + ":");
            printPuzzle(path.get(i).state);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the 8-puzzle initial state (9 digits, use 0 for blank): ");
        String initial = sc.nextLine();

        System.out.println("Choose Search Method: 1 = BFS, 2 = DFS");
        int choice = sc.nextInt();

        boolean bfs = (choice == 1);
        Node result = search(initial, bfs);

        if (result != null) {
            printPath(result);
        } else {
            System.out.println("No solution found.");
        }
    }
}
