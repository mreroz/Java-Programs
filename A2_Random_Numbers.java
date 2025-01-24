import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Random;

/**
 * Program that handles random numbers given some inputs from the user.
 * It generates the desired quantity of random numbers and then
 * rearrenges them following some rules based on their even/odd condition
 *
 * STEP 0. Declare constants
 * STEP 1. Declare the variables needed for the calculations
 * STEP 2. Ask the user for the inputs and validate
 * STEP 3. Generate the randomized numbers on the array
 * STEP 4. Classify by even and odd
 * STEP 5. Sort the even numbers (increasing order)
 * STEP 6. Sort the odd numbers (decreasing order)
 * STEP 7. Print out
 *
 * @author erocos-4
 * @version 1.0
 */

class Main {
    // STEP 0. Declare constants
    static final int MAX_RANDOM_NUMBER = 999;

    static final String USER_INPUT_PROMPT = "How many random numbers in the range 0 - 999 are desired?";
    static final String RANDOM_NUMBERS_LIST_MESSAGE = "Here are the random numbers:";
    static final String RANDOM_NUMBERS_SORTED_MESSAGE = "Here are the random numbers arranged:";
    static final String NO_ODD_NUMBERS_MESSAGE = "No Odd Numbers";
    static final String NO_EVEN_NUMBERS_MESSAGE = "No Even Numbers";
    static final String INVALID_INPUT_MESSAGE = "Invalid Input";

    public static void main(final String[] args) {
        // STEP 1. Declare the variables needed for the calculations
        int[] randomArray = null;
        int randomCount = 0;

        int[] evenArray = null;
        int evenCount = 0;

        int[] oddArray = null;
        int oddCount = 0;

        Scanner userInput = new Scanner(System.in);
        Random rand = new Random();

        // STEP 2. Ask the user for the inputs and validate
        System.out.println(USER_INPUT_PROMPT);
        if (userInput.hasNextInt()) {
            randomCount = userInput.nextInt();
            if (randomCount <= 0 || randomCount >= Integer.MAX_VALUE) {
                System.out.println(INVALID_INPUT_MESSAGE);
                userInput.close();
                System.exit(0);
            }
        } else {
            System.out.println(INVALID_INPUT_MESSAGE);
            userInput.close();
            System.exit(0);
        }

        try {
            randomArray = new int[randomCount];
            evenArray = new int[randomCount];
            oddArray = new int[randomCount];
        } catch (InputMismatchException e) {
            System.out.println(INVALID_INPUT_MESSAGE);
            userInput.close();
            System.exit(1);
        } catch (OutOfMemoryError e) {
            System.out.println(INVALID_INPUT_MESSAGE);
            userInput.close();
            System.exit(2);
        }

        // STEP 3. Generate the randomized numbers on the array
        for (int i = 0; i < randomCount; i++) {
            randomArray[i] = rand.nextInt(MAX_RANDOM_NUMBER);
        }

        // STEP 4. Classify by even and odd
        for (int i = 0; i < randomCount; i++) {
            if (randomArray[i] % 2 == 0) {
                evenArray[evenCount] = randomArray[i];
                evenCount++;
            } else {
                oddArray[oddCount] = randomArray[i];
                oddCount++;
            }
        }

        // STEP 5. Sort the even numbers (increasing order)
        for (int i = 0; i < evenCount; i++) {
            for (int j = 0; j < evenCount - 1; j++) {
                if (evenArray[j] > evenArray[j + 1]) {
                    int temp = evenArray[j];
                    evenArray[j] = evenArray[j + 1];
                    evenArray[j + 1] = temp;
                }
            }
        }

        // STEP 6. Sort the odd numbers (decreasing order)
        for (int i = 0; i < oddCount; i++) {
            for (int j = 0; j < oddCount - 1; j++) {
                if (oddArray[j] < oddArray[j + 1]) {
                    int temp = oddArray[j];
                    oddArray[j] = oddArray[j + 1];
                    oddArray[j + 1] = temp;
                }
            }
        }

        // STEP 7. Print out
        // Print full radom array
        System.out.println(RANDOM_NUMBERS_LIST_MESSAGE);
        for (int i = 0; i < randomCount; i++) {
            System.out.print(randomArray[i] + " ");
        }
        System.out.println(); //Print new line for astetics

        // Print ordered evenarray - oddarray
        System.out.println(RANDOM_NUMBERS_SORTED_MESSAGE);
        if (evenCount == 0) {
            System.out.print(NO_EVEN_NUMBERS_MESSAGE);
        } else {
            for (int i = 0; i < evenCount; i++) {
                System.out.print(evenArray[i] + " ");
            }
        }
        System.out.print("- ");
        if (oddCount == 0) {
            System.out.print(NO_ODD_NUMBERS_MESSAGE);
        } else {
            for (int i = 0; i < oddCount; i++) {
                System.out.print(oddArray[i] + " ");
            }
        }
        System.out.println(); //Print new line for astetics

        //Print out "stadistics"
        System.out.printf("Of the above %d numbers, %d were even and %d odd\n",
                                            randomCount, evenCount, oddCount);
        userInput.close();
    }
} // end of class
