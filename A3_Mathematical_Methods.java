import java.util.Scanner;

/**
 * Program that perform various mathematical tasks (e.g.
 * area of a circle) using input values given by the user
 *
 * STEP 0. Declarations
 *  a. Declare the constants
 *  b. Declare the methods
 *  c. Declare the variables for the main
 * STEP 1. AREA & VOLUME METHODS
 *  a. Read & verify input
 *  b. Call the methods & print out
 * STEP 2. FRACTIONAL METHODS
 *  a. Read & verify input
 *  b. Call the methods & print out
 *
 * @author erocos-4
 * @version 1.0
 */
public class Main {
    //STEP 0. DECLARATIONS
    //Creation of scanner object.
    private static Scanner userInputScanner = new Scanner(System.in);

    //Step 0a. Declare the constants
    static final int QUIT = -1;

    //Step 0b. Declare the methods

    /**
    * This method calculates the area of a circle
    * A = PI * r^2
    *
    * @param radius of the circle
    * @return area of the circle
    */
    public static double area(final int radius) {
        double area = 0.0;
        area = Math.PI * Math.pow(radius, 2);
        return area;
    }

    /**
    * This method calculates the lateral surface area of a cone
    * A = PI * r * s
    *
    * @param radius of the cone's base
    * @param height of the cone
    * @return lateral surface of the cone
    */
    public static double area(final int radius, final int height) {
        double area = 0.0;
        area = Math.PI * radius * pythagoras(radius, height);
        return area;
    }

    /**
    * This method calculates the Pythagora's theorem
    * c = sqrt (a^2 + b^2)
    *
    * @param sideA of the triangle
    * @param sideB of the triangle
    * @return side C or hypotenuse
    */
    public static double pythagoras(final int sideA, final int sideB) {
        double sideC = 0.0;
        sideC = Math.sqrt(Math.pow(sideA, 2) + Math.pow(sideB, 2));
        return sideC;
    }

    /**
    * This method calculates the volume of a cone
    * V = PI * r2 * h / 3
    *
    * @param radius of the cone's base
    * @param height of the cone
    * @return volume of the cone
    */
    public static double volume(final int radius, final int height) {
        double volume = 0.0;
        volume = Math.PI * Math.pow(radius, 2) * height / 3;
        return volume;
    }

    /**
    * This method converts a fraction given a numerator and denominator
    *
    * @param numerator
    * @param denominator
    * @return an interger array containing:
    *   integer part
    *   numerator of the remaining fraction in its simplest form
    *   denominator of the remaining fraction in its simplest form
    */
    public static int[] fraction(final int numerator, final int denominator) {
        int integer = 0;
        int num = 0;
        int den = 0;

        if (denominator == 0) {
            //if denominator = 0, return null
            return null;
        } else if (numerator == 0) {
            //if numerator = 0, return {0, 0, 0}
            return new int[]{0, 0, 0};
        }
        //compose the fraction
        int gcd = gcd(numerator, denominator);
        integer = numerator / denominator;
        num = numerator % denominator;
        //reduce the array
        num = num / gcd;
        den = denominator / gcd;

        return new int[]{integer, num, den};
    }

    /**
    * This method computes the greatest common divisor (GCD) given 2 integers
    *
    * @param a first integer
    * @param b second integer
    * @return greatest common divisor of a and b
    */
    public static int gcd(final int a, final int b) {
        int tempA = a;
        int tempB = b;
        int tempC = 0;
        //1. Make sure a > b (change if necessary)
        if (a <= b) {
            int temp = tempA;
            tempA = tempB;
            tempB = temp;
        }

        //2. As long as b is not O
        while (tempB != 0) {
            //2.1 c = a % b
            tempC = tempA % tempB;
            //2.2 a = b
            tempA = tempB;
            //2.3 b = c
            tempB = tempC;
        }

        //3. Return c
        return tempA;
    }

    /**
    * This method prints by screen the given fraction followig the given specs
    *
    * @param parts is an integer array with the structure:
    *   integer part
    *   numerator of the remaining fraction in its simplest form
    *   denominator of the remaining fraction in its simplest form
    */
    public static void printFraction(final int[] parts) {
        if (parts == null) {
            System.out.println("Error");
        } else if (parts[2] == 0) {
            System.out.println(0);
        } else if (parts[1] == 0) {
            System.out.println(parts[0]);
        } else if (parts[0] == 0) {
            System.out.printf("%d/%d\n", parts[1], parts[2]);
        } else {
            System.out.printf("%d %d/%d\n", parts[0], parts[1], parts[2]);
        }
    }

    /**
    * This method reads and returns a non-negative integer input from the user
    *
    * @return numeric value or QUIT (-1) in case the user selects to exit
    */
    public static int input() {
        int number = 0;

        while (true) {
            if (userInputScanner.hasNextInt()) {
                number = userInputScanner.nextInt();
                if (number >= 0) {
                    break;
                } else {
                    number = Math.abs(number);
                    break;
                }
            } else if (userInputScanner.hasNext()) {
                String inString = userInputScanner.next();
                // Q & q
                if (inString.equalsIgnoreCase("q")) {
                    number = QUIT;
                    break;
                }
            }
        }
        return number;
    }

    /**
     * This method should be used only for unit testing on CodeGrade.
     * Do not change this method!
     * Do not remove this method!
     * Swaps userInputScanner with a custom scanner object bound
     * to a test input stream
     *
     * @param inputScanner - test scanner object
     */
    public static void injectInput(final Scanner inputScanner) {
        userInputScanner = inputScanner;
    }

    public static void main(final String[] args) {
        // Step 0c. Declare the variables for the main
        int radius = 0;
        int height = 0;
        int numerator = 0;
        int denominator = 0;

        //STEP 1. AREA & VOLUME METHODS
        //Print the header of the program for area and volume.
        System.out.println("----------------------------------");
        System.out.println("# Test of area and volume methods");
        System.out.println("----------------------------------");

        // While loop that runs until user enters "q" for area and volume.
        while (true) {
            //Step 1a. Read and verify inputs
            radius = input();
            if (radius == QUIT) {
                break;
            }

            height = input();
            if (height == QUIT) {
                break;
            }

            //Step 1b. Call the methods & print out
            System.out.println("r = " + radius + ", h = " + height);
            System.out.printf("Circle area: %.2f %n", area(radius));
            System.out.printf("Cone area: %.2f %n", area(radius, height));
            System.out.printf("Cone volume: %.2f %n", volume(radius, height));
        }

        //STEP 2. FRACTIONAL METHODS
        //Print the header of the program for area and volume.
        System.out.println("----------------------------------");
        System.out.println("# Test of the fractional methods");
        System.out.println("----------------------------------");


        // While loop that runs until user enters "q" for the fraction part
        while (true) {
            //Step 2a. Read and verify inputs
            numerator = input();
            if (numerator == QUIT) {
                break;
            }

            denominator = input();
            if (denominator == QUIT) {
                break;
            }

            //Step 2b. Call the methods & print out
            System.out.printf("%d/%d = ", numerator, denominator);
            printFraction(fraction(numerator, denominator));
        }
        userInputScanner.close();
    }
}
