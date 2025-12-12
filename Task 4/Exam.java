import java.util.Scanner;

public class Exam {

    private Question[] questions;
    private int score;

    public Exam() {
        questions = new Question[5];
        
        questions[0] = new Question(
            "Which language is used for Android development?",
            new String[]{"Python", "Java", "C++", "PHP"},
            2
        );
        questions[1] = new Question(
            "Which keyword creates an object in Java?",
            new String[]{"create", "new", "object", "init"},
            2
        );
        questions[2] = new Question(
            "What is the size of int in Java?",
            new String[]{"2 bytes", "4 bytes", "8 bytes", "depends on OS"},
            2
        );
        questions[3] = new Question(
            "OOP stands for?",
            new String[]{"Object Oriented Programming", "Open Object Program", "Old Order Principle", "None"},
            1
        );
        questions[4] = new Question(
            "Which of the following is not a Java feature?",
            new String[]{"Secure", "Portable", "Dynamic", "Pointers"},
            4
        );

        score = 0;
    }

    public void startExam() {
        Scanner sc = new Scanner(System.in);

        int timer = 40; // seconds
        Thread countdown = new Thread(() -> {
            try {
                while (timer > 0) {
                    System.out.print("\rTime Left: " + timer + " seconds");
                    Thread.sleep(1000);
                    timer--;
                }
            } catch (Exception e) {}
        });

        countdown.start();

        for (Question q : questions) {
            if (!countdown.isAlive()) {
                System.out.println("\nTime Over! Auto-submitting...");
                break;
            }

            System.out.println("\n\n----------------------------------");
            q.display();
            System.out.print("Your answer: ");

            int ans = sc.nextInt();
            if (q.checkAnswer(ans)) score++;
        }

        System.out.println("\nExam Finished!");
        System.out.println("Your Score: " + score + " / " + questions.length);
    }
}
