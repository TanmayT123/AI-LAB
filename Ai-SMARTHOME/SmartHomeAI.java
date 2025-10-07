import java.util.*;
import java.util.PriorityQueue;

public class SmartHomeAI {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== Smart Home AI System ===");
            System.out.println("1. Forward Chaining Decisions");
            System.out.println("2. Optimal Energy Schedule (A*)");
            System.out.println("0. Exit");
            System.out.print("Choice: ");
            int choice = Integer.parseInt(sc.nextLine());
            if (choice == 1) forwardChaining();
            else if (choice == 2) aStarEnergy();
            else break;
        }
    }

    static void forwardChaining() {
        System.out.println("--- Forward Chaining ---");
        Set<String> facts = new HashSet<>();
        System.out.print("Is it night? (yes/no): ");
        if (sc.nextLine().equalsIgnoreCase("yes")) facts.add("night");
        System.out.print("Is someone in the room? (yes/no): ");
        if (sc.nextLine().equalsIgnoreCase("yes")) facts.add("movement");
        System.out.print("Temperature (Celsius): ");
        int temp = Integer.parseInt(sc.nextLine());

        List<Rule> rules = new ArrayList<>();
        rules.add(new Rule(Set.of("night"), "lightsOff"));
        rules.add(new Rule(Set.of("night", "movement"), "lightsOn"));
        rules.add(new Rule(Set.of(), temp<18 ? "heaterOn" : "heaterOff"));
        rules.add(new Rule(Set.of(), temp>26 ? "acOn" : "acOff"));

        boolean inferred = true;
        while (inferred) {
            inferred = false;
            for (Rule r : rules) {
                if (!facts.contains(r.action) && facts.containsAll(r.conditions)) {
                    facts.add(r.action);
                    inferred = true;
                    System.out.println("Decision: " + r.action);
                }
            }
        }
    }

    static class Rule {
        Set<String> conditions;
        String action;
        Rule(Set<String> c, String a) { conditions = c; action = a; }
    }

    static void aStarEnergy() {
        System.out.println("--- A* Energy Optimization ---");
        int[] appliances = {5,3,2,4}; // energy cost
        int target = 7; // max energy limit
        Node start = new Node(new ArrayList<>(),0);
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.f));
        pq.add(start);
        while(!pq.isEmpty()) {
            Node cur = pq.poll();
            if(cur.energy<=target && cur.appliances.size()==appliances.length) {
                System.out.println("Optimal Schedule: " + cur.appliances + " with energy " + cur.energy);
                return;
            }
            if(cur.appliances.size()>=appliances.length) continue;
            int idx = cur.appliances.size();
            Node include = new Node(new ArrayList<>(cur.appliances), cur.energy);
            include.appliances.add(appliances[idx]);
            include.energy += appliances[idx];
            include.f = include.energy + heuristic(idx+1, appliances, target);
            pq.add(include);
            Node exclude = new Node(new ArrayList<>(cur.appliances), cur.energy);
            exclude.f = exclude.energy + heuristic(idx+1, appliances, target);
            pq.add(exclude);
        }
        System.out.println("No feasible schedule found");
    }

    static int heuristic(int idx, int[] appliances, int target) {
        int sum = 0;
        for(int i=idx;i<appliances.length;i++) sum += appliances[i];
        return Math.max(0,sum); 
    }

    static class Node {
        List<Integer> appliances;
        int energy;
        int f;
        Node(List<Integer> apps, int e) { appliances = apps; energy = e; f = e; }
    }
}
