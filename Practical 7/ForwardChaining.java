import java.util.*;

public class ForwardChaining {
    // Rule class: each rule has a condition (IF) and a conclusion (THEN)
    static class Rule {
        String condition;
        String conclusion;

        Rule(String condition, String conclusion) {
            this.condition = condition;
            this.conclusion = conclusion;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Known facts
        Set<String> facts = new HashSet<>();

        // Ask the user if it rains
        System.out.print("Does it rain? (yes/no): ");
        String answer = sc.nextLine().trim().toLowerCase();

        if (answer.equals("yes")) {
            facts.add("It rains");
        }

        // Define rules (IF ... THEN ...)
        List<Rule> rules = new ArrayList<>();
        rules.add(new Rule("It rains", "Ground gets wet"));
        rules.add(new Rule("Ground gets wet", "Plants grow"));

        String goal = "Plants grow"; // our target
        boolean goalReached = false;

        System.out.println("Initial Facts: " + facts);

        // Forward chaining loop
        boolean newFactAdded;
        do {
            newFactAdded = false;
            for (Rule rule : rules) {
                if (facts.contains(rule.condition) && !facts.contains(rule.conclusion)) {
                    System.out.println("Applying Rule: IF " + rule.condition + " THEN " + rule.conclusion);
                    facts.add(rule.conclusion);
                    newFactAdded = true;

                    if (rule.conclusion.equals(goal)) {
                        goalReached = true;
                    }
                }
            }
        } while (newFactAdded);

        System.out.println("Final Facts: " + facts);
        if (goalReached) {
            System.out.println("Goal '" + goal + "' is reached using Forward Chaining.");
        } else {
            System.out.println("Goal '" + goal + "' cannot be reached.");
        }

        sc.close();
    }
}
