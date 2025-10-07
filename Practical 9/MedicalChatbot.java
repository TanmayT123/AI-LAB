import java.util.*;

public class MedicalChatbot {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println(" Hello! I am your Medical Assistant.");
        System.out.println("You can type 'bye' anytime to exit.");

        while (true) {
            System.out.print("\nWhat symptom are you experiencing? ");
            String symptom = sc.nextLine().trim().toLowerCase();

            if (symptom.equals("bye")) {
                System.out.println(" Take care! Wishing you good health.");
                break;
            }

            switch (symptom) {
                case "fever":
                    System.out.println(" You may have flu or infection. Stay hydrated and consult a doctor if it persists.");
                    break;
                case "cough":
                    System.out.println(" You may have a cold or throat infection. Drink warm fluids and take rest.");
                    break;
                case "headache":
                    System.out.println(" Possible causes: stress, dehydration, or migraine. Drink water and rest. If severe, consult a doctor.");
                    break;
                case "stomach pain":
                    System.out.println("It could be indigestion or infection. Avoid junk food and consider medical advice if pain continues.");
                    break;
                case "fatigue":
                    System.out.println(" You may be tired or low on sleep. Rest well and maintain a balanced diet.");
                    break;
                default:
                    System.out.println(" Sorry, I don't have information about that symptom. Please consult a doctor.");
            }
        }

        sc.close();
    }
}
