import java.util.*;
import java.util.regex.*;

class Person {
    String name;
    String gender; // "male" or "female" or "unknown"
    List<Person> parents = new ArrayList<>();
    List<Person> children = new ArrayList<>();

    Person(String name, String gender) {
        this.name = name;
        this.gender = gender;
    }
}

class KnowledgeBaseFamilyTree {
    private Map<String, Person> people = new HashMap<>();

    void addPerson(String name, String gender) {
        people.putIfAbsent(name, new Person(name, gender));
    }

    void addFact(String relation, String parent, String child) {
        String gender = relation.equals("father") ? "male" : "female";
        addPerson(parent, gender);
        addPerson(child, "unknown");

        Person p = people.get(parent);
        Person c = people.get(child);

        p.children.add(c);
        c.parents.add(p);
    }

    List<String> getParents(String name) {
        List<String> result = new ArrayList<>();
        if (people.containsKey(name)) {
            for (Person p : people.get(name).parents) {
                result.add(p.name);
            }
        }
        return result;
    }

    List<String> getFather(String name) {
        List<String> fathers = new ArrayList<>();
        if (people.containsKey(name)) {
            for (Person p : people.get(name).parents) {
                if (p.gender.equals("male"))
                    fathers.add(p.name);
            }
        }
        return fathers;
    }

    List<String> getMother(String name) {
        List<String> mothers = new ArrayList<>();
        if (people.containsKey(name)) {
            for (Person p : people.get(name).parents) {
                if (p.gender.equals("female"))
                    mothers.add(p.name);
            }
        }
        return mothers;
    }

    List<String> getGrandmother(String name) {
        Set<String> grandmothers = new HashSet<>();
        for (String parent : getParents(name)) {
            for (String gp : getParents(parent)) {
                if (people.get(gp).gender.equals("female"))
                    grandmothers.add(gp);
            }
        }
        return new ArrayList<>(grandmothers);
    }

    List<String> getSiblings(String name) {
        Set<String> siblings = new HashSet<>();
        if (people.containsKey(name)) {
            for (Person parent : people.get(name).parents) {
                for (Person child : parent.children) {
                    if (!child.name.equals(name))
                        siblings.add(child.name);
                }
            }
        }
        return new ArrayList<>(siblings);
    }

    void displayResults(String relationship, String person, List<String> results) {
        System.out.println("\n" + relationship + " of " + person);
        if (results.isEmpty()) {
            System.out.println("| No " + relationship + " found                          |");
        } else {
            for (String res : results) {
                System.out.printf("| %-50s|\n", res);
            }
        }
    }

    // Parse facts like "father(John, Bob)." or "mother(Mary, Bob)."
    void parseFact(String fact) {
        Pattern pattern = Pattern.compile("(father|mother)\\((\\w+),\\s*(\\w+)\\)\\.");
        Matcher match = pattern.matcher(fact);
        if (match.matches()) {
            String relation = match.group(1);
            String parent = capitalize(match.group(2));
            String child = capitalize(match.group(3));
            addFact(relation, parent, child);
        }
    }

    static String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
}

public class FamilyTreeKnowledgeBase {
    public static void main(String[] args) {
        KnowledgeBaseFamilyTree tree = new KnowledgeBaseFamilyTree();

        // Knowledge base facts
        String[] facts = {
            "father(John, Bob).",
            "mother(Mary, Bob).",
            "father(Bob, Kate).",
            "mother(Alice, Kate).",
            "father(Bob, Frank).",
            "mother(Alice, Frank).",
            "father(John, Anna).",
            "mother(Mary, Anna)."
        };

        for (String f : facts) {
            tree.parseFact(f);
        }

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n====== Family Tree Knowledge-Base Query ======");
            System.out.println("1. Find Father");
            System.out.println("2. Find Mother");
            System.out.println("3. Find Grandmother");
            System.out.println("4. Find Siblings");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            
            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input!");
                continue;
            }

            if (choice == 5) {
                System.out.println("Exiting program...");
                break;
            }

            System.out.print("Enter person's name: ");
            String person = sc.nextLine().trim();
            person = KnowledgeBaseFamilyTree.capitalize(person);

            switch (choice) {
                case 1 -> tree.displayResults("Father", person, tree.getFather(person));
                case 2 -> tree.displayResults("Mother", person, tree.getMother(person));
                case 3 -> tree.displayResults("Grandmother", person, tree.getGrandmother(person));
                case 4 -> tree.displayResults("Siblings", person, tree.getSiblings(person));
                default -> System.out.println("Invalid choice!");
            }
        }
        sc.close();
    }
}
