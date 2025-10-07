import java.util.*;

public class BackwardChaining {
    // Rule class
    static class Rule {
        String condition;
        String conclusion;

        Rule(String condition, String conclusion) {
            this.condition = condition;
            this.conclusion = conclusion;
        }
    }

    static List<Rule> rules = new ArrayList<>();
    static Set<String> facts = new HashSet<>();
    static Scanner sc = new Scanner(System.in);

    // Backward chaining function
    public static boolean prove(String goal) {
        if (facts.contains(goal)) {
            System.out.println("Fact already known: " + goal);
            return true;
        }

        // Ask user if this fact is true
        System.out.print("Is '" + goal + "' true? (yes/no/unknown): ");
        String answer = sc.nextLine().trim().toLowerCase();

        if (answer.equals("yes")) {
            facts.add(goal);
            return true;
        } else if (answer.equals("no")) {
            return false;
        }

        // If unknown, try to prove it using rules
        for (Rule rule : rules) {
            if (rule.conclusion.equals(goal)) {
                System.out.println("To prove '" + goal + "', checking rule: IF " + rule.condition + " THEN " + rule.conclusion);
                if (prove(rule.condition)) {
                    facts.add(goal); // derived fact
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        // Define rules
        rules.add(new Rule("It rains", "Ground gets wet"));
        rules.add(new Rule("Ground gets wet", "Plants grow"));

        String goal = "Plants grow";

        System.out.println("Trying to prove: " + goal);

        if (prove(goal)) {
            System.out.println(" Goal '" + goal + "' is proven using Backward Chaining.");
        } else {
            System.out.println(" Goal '" + goal + "' cannot be proven.");
        }

        sc.close();
    }
}
