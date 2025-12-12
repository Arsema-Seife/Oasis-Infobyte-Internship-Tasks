import java.util.*;

public class GuessTheNumber {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int totalScore = 0;
        int round = 1;
        String playAgain;

        System.out.println("=========================================");
        System.out.println("        WELCOME TO GUESS THE NUMBER");
        System.out.println("=========================================");

        do {
            System.out.println("\n---------- ROUND " + round + " ----------");

            int randomNumber = (int) (Math.random() * 100) + 1;
            int attempts = 0;
            int maxAttempts = 7; // limit attempts to make game interesting
            boolean guessedCorrectly = false;

            System.out.println("I'm thinking of a number between 1 and 100.");
            System.out.println("You have " + maxAttempts + " attempts. Good luck!");

            while (attempts < maxAttempts) {
                System.out.print("\nEnter your guess: ");
                int userGuess = sc.nextInt();
                attempts++;

                if (userGuess == randomNumber) {
                    System.out.println("🎉 Correct! You guessed the number!");
                    guessedCorrectly = true;

                    // scoring based on attempts
                    int points = 100 - (attempts * 10);
                    if (points < 0) points = 0;

                    System.out.println("Attempts taken: " + attempts);
                    System.out.println("Points earned: " + points);

                    totalScore += points;
                    break;
                } 
                else if (userGuess > randomNumber) {
                    System.out.println("Too high! Try a lower number.");
                } 
                else {
                    System.out.println("Too low! Try a higher number.");
                }
            }

            if (!guessedCorrectly) {
                System.out.println("\n❌ You've used all attempts!");
                System.out.println("The correct number was: " + randomNumber);
                System.out.println("Points earned: 0");
            }

            System.out.println("\nYour total score so far: " + totalScore);

            System.out.print("\nDo you want to play another round? (yes/no): ");
            sc.nextLine(); // clear buffer
            playAgain = sc.nextLine().toLowerCase();

            round++;

        } while (playAgain.equals("yes"));

        System.out.println("\n=========================================");
        System.out.println("              GAME OVER!");
        System.out.println("        Your Final Score: " + totalScore);
        System.out.println("=========================================");
    }
}
