import java.util.Random;
import java.util.Scanner;

/**
* Program that simulates a variation of the game "Shut the box"
* This a variant called "the dice game 12", where the user rolls 3
* dices every round. It is a win when score is 12 and a loss if it is >12.
*
* STEP 0. Declare constants
* STEP 1. Declare the variables needed for the calculations
* STEP 2. Start the game loop
* STEP 3. New round
* STEP 4. Ask the user to roll a dice and validate (3 iterations)
* STEP 5. End of the roung: print out the status
* STEP 6. Start a new round (initialize and go to Step 3)
*
* @author erocos-4 (LTU_username)
* @version 1.0
*/

class Main {
    // STEP 0. Declare constants
    static final int DICE1 = 1;
    static final int DICE2 = 2;
    static final int DICE3 = 3;
    static final String QUIT = "q";
    static final int WIN_VALUE = 12;
    static final int MAX_DICE_VALUE = 6;
    static final int MAX_NUMBER_ROLLS = 3;

    static final String DICE_ERROR_MSG = "Sorry, you have already rolled that dice. Try again";
    static final String INVALID_ENTRY_MSG = "Sorry, that is an invalid entry. Try again. Valid entries are 1, 2, 3, and q\n";
    static final String WELCOME_MSG = "Welcome to dice game 12. You must roll 1-3 dice and try to get the sum of 12 ...\n";
    static final String INSTRUCTIONS_MSG = "Enter which dice you want to roll [1,2,3] (exit with q):";
    static final String WIN_MSG = "\nYou won!!";
    static final String LOSE_MSG = "\nYou lost!!";
    static final String DRAW_MSG = "\nYou neither won nor lost the game.";
    static final String NEXT_ROUND_MSG = "\nNext round!\n";
    static final String GAME_OVER_MSG = "\nGame Over!";

    public static void main(final String[] args) {
        // STEP 1. Declare the variables needed for the calculations
        // Dice variables
        String inString = "";
        int numberOfRolls = 0;
        int selection = 0;
        int dice1 = 0;
        int dice2 = 0;
        int dice3 = 0;
        int sum = 0;
        int winTotal = 0;
        int lossTotal = 0;

        // Status variables
        boolean isValidInput = false;
        boolean rollDice1 = false;
        boolean rollDice2 = false;
        boolean rollDice3 = false;

        //Other
        Scanner userInput = new Scanner(System.in);
        Random rand = new Random();

        // STEP 2. Start the game loop
        System.out.println(WELCOME_MSG);
        while (true) {

            // STEP 3. New round
            while (numberOfRolls < 3) {
                // STEP 4. Ask the user to roll a dice and validate (3 iterations)
                while (!isValidInput) {
                    System.out.println(INSTRUCTIONS_MSG);

                    if (userInput.hasNextInt()) {
                        selection = userInput.nextInt();

                        // Validate the read input
                        if (selection >= DICE1 && selection <= DICE3) {
                            // Roll dice 1
                            if (selection == DICE1 && !rollDice1) {
                                dice1 = rand.nextInt(MAX_DICE_VALUE) + 1;
                                rollDice1 = true;
                                numberOfRolls++;
                                sum = dice1 + dice2 + dice3;

                                // If it is last round update the win/loss count
                                if (numberOfRolls == MAX_NUMBER_ROLLS) {
                                    if (sum == WIN_VALUE) {
                                        winTotal++;
                                    } else if (sum > WIN_VALUE) {
                                        lossTotal++;
                                    }
                                }
                                // Print out values and status
                                System.out.printf("%d %d %d sum: %d # win: %d # loss: %d\n",
                                                 dice1, dice2, dice3, sum, winTotal, lossTotal);
                                isValidInput = true;
                            // Roll dice 2
                            } else if (selection == DICE2 && !rollDice2) {
                                dice2 = rand.nextInt(MAX_DICE_VALUE) + 1;
                                rollDice2 = true;
                                numberOfRolls++;
                                sum = dice1 + dice2 + dice3;

                                // If it is last round update the win/loss count
                                if (numberOfRolls == MAX_NUMBER_ROLLS) {
                                    if (sum == WIN_VALUE) {
                                        winTotal++;
                                    } else if (sum > WIN_VALUE) {
                                        lossTotal++;
                                    }
                                }
                                // Print out values and status
                                System.out.printf("%d %d %d sum: %d # win: %d # loss: %d\n",
                                                 dice1, dice2, dice3, sum, winTotal, lossTotal);
                                isValidInput = true;

                            // Roll dice 3
                            } else if (selection == DICE3 && !rollDice3) {
                                dice3 = rand.nextInt(MAX_DICE_VALUE) + 1;
                                rollDice3 = true;
                                numberOfRolls++;
                                sum = dice1 + dice2 + dice3;

                                // If it is last round update the win/loss count
                                if (numberOfRolls == MAX_NUMBER_ROLLS) {
                                    if (sum == WIN_VALUE) {
                                        winTotal++;
                                    } else if (sum > WIN_VALUE) {
                                        lossTotal++;
                                    }
                                }
                                // Print out values and status
                                System.out.printf("%d %d %d sum: %d # win: %d # loss: %d\n",
                                                 dice1, dice2, dice3, sum, winTotal, lossTotal);
                                isValidInput = true;

                            // Selected dice has already been rolled
                            } else {
                                System.out.println(DICE_ERROR_MSG);
                            }
                            // Invalid integrer entry
                        } else {
                            System.out.println(INVALID_ENTRY_MSG);
                        }
                            // Consume the newline left after nextInt()
                        userInput.nextLine();
                    // If entered value is not an integrer
                    } else if (userInput.hasNext()) {
                        inString = userInput.nextLine();

                        // Quit the game and close the program
                        if (inString.equalsIgnoreCase(QUIT)) {
                            isValidInput = true;
                            System.out.printf("#win: %d #loss: %d", winTotal, lossTotal);
                            System.out.println(GAME_OVER_MSG);
                            userInput.close();
                            System.exit(0);

                        // Invalid user entry
                        } else {
                            System.out.println(INVALID_ENTRY_MSG);
                        }
                    }
                }
                // A correct value has been entered
                isValidInput = false;
            }
            // STEP 5. End of the round: print out the status
            if (sum == WIN_VALUE) {
                System.out.println(WIN_MSG);
            } else if (sum > WIN_VALUE) {
                System.out.println(LOSE_MSG);
            } else {
                System.out.println(DRAW_MSG);
            }
            // STEP 6. Start a new round (initialize and go to STEP 3)
            numberOfRolls = 0;
            dice1 = 0;
            dice2 = 0;
            dice3 = 0;
            sum = 0;
            rollDice1 = false;
            rollDice2 = false;
            rollDice3 = false;
            System.out.println(NEXT_ROUND_MSG);
        }
    }
}
