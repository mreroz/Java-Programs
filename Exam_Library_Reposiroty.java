import java.util.Random;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
/**
 * Program that works as a simple book inventory and lending status for the LTU library. 
* The user menu has the following options:
* 1. Add book
* 2. Remove book
* 3. Loan a book
* 4. Return a book
* 5. Print book list
* 6. Print lending summary
* q. End program
* 
* @author erocos-4
* @version 1.0
**/
public class Main {
        
    // Constants for the item array
    public static final int BOOK_ID = 0;
    public static final int BOOK_ISBN = 1;
    public static final int BOOK_TITLE = 2;
    public static final int BOOK_STATUS = 3;
    public static final int BOOK_LENDER = 4;
    public static final int BOOK_COLUMN_SIZE = 5;
    public static final int INITIAL_BOOK_SIZE = 1;

    // Constants for the sales array
    public static final int LOAN_BOOK_ID = 0;
    public static final int LOAN_LENDER_NAME = 1;
    public static final int LOAN_START_DATE = 2;
    public static final int LOAN_END_DATE = 3;
    public static final int LOAN_COST = 4;
    public static final int LOAN_COLUMN_SIZE = 5;
    public static final int MAX_LOAN = 1000;

    // Other constants
    public static final int MENU_BOOK_1 = 1;
    public static final int MENU_BOOK_2 = 2;
    public static final int MENU_BOOK_3 = 3;
    public static final int MENU_BOOK_4 = 4;
    public static final int MENU_BOOK_5 = 5;
    public static final int MENU_BOOK_6 = 6;
    public static final int MENU_BOOK_Q = -1;

    public static final int INITIAL_LOAN_NUMBER = 0;

    public static final int MIN_BOOK_ID = 1000;
    public static final int MAX_BOOK_ID = 9999;
    public static final int DAY_PRICE = 15;
    public static final int FREE_DAYS = 10;

    private static Scanner userInputScanner = new Scanner(System.in);
    // Methods declarations
    /**
    * This method presents the menu, loads, and returns the user's selection
    *
    * @return user's selection
    */
    public static int menu() {
        // Show the menu
        System.out.println("1. Add book");
        System.out.println("2. Remove book");
        System.out.println("3. Loan a book");
        System.out.println("4. Return a book");
        System.out.println("5. Print book list");
        System.out.println("6. Print lending summary");
        System.out.println("q. End program");
        System.out.println("Enter your option:");
        return inputMenu();
    }
    /**
    * This method handles the inputs from the user. Returns the given int or 
    * -1 in case it was "q". Allows a user a new input chance as long as the 
    * entered input was not valid
    *
    * @return user's input
    */
    public static int inputMenu() {
        int input = 0;
        while (true) {
            if (userInputScanner.hasNextInt()) {
                input = userInputScanner.nextInt();
                if (input >= MENU_BOOK_1 && input <= MENU_BOOK_6) {
                    break;
                } else {
                    System.out.println("invalid menu item");
                }
            } else if (userInputScanner.hasNext()) {
                String inString = userInputScanner.next();
                // Q & q
                if (inString.equalsIgnoreCase("q")) {
                    input = MENU_BOOK_Q;
                    break;
                } else {
                    System.out.println("invalid menu item");
                }
            }
        }
        userInputScanner.nextLine();
        return input;
    }
    /**
    * This method handles the inputs from the user. Returns the given ID or a error msg 
    * if the format is not correct
    *
    * @return user's input or error code
    */
    public static String inputID() {
        int input = 0;
        while (true) {
            if (userInputScanner.hasNextInt()) {
                input = userInputScanner.nextInt();
                if (input >= MIN_BOOK_ID && input <= MAX_BOOK_ID) {
                    break;
                } else {
                    System.out.println("invalid ID");
                }
            } else if (userInputScanner.hasNext()) {
                System.out.println("invalid ID");
            }
        }
        userInputScanner.nextLine();
        return String.valueOf(input);
    }
    /**
    * This method calculates the number of free slots availables on the array.
    *
    * @param intems array with all the items of the inventory
    * @return number of free slots
    */
    public static int checkFreeSlots(final String[][] books) {
        int count = 1;
        if (books[0][0] != null) {
            count = 0;
            for (int i = 0; i < books.length; i++) {
                if (books[i][BOOK_ID].equals("0")) {
                    count++;
                }
            }
        }
        return count;
    }
    /**
    * This method generates a new Book ID in the inventory.
    *
    * @param books array with all the books of the inventory
    * @return new book ID
    */
    public static String generateBookID(final String[][] books) {
        Random rand = new Random();
        int temp = rand.nextInt(MAX_BOOK_ID - MIN_BOOK_ID + 1) + MIN_BOOK_ID;
        String newBookID = String.valueOf(temp);

        while (checkExisting(books, newBookID, BOOK_ID)) {
            temp = rand.nextInt(MAX_BOOK_ID - MIN_BOOK_ID + 1) + MIN_BOOK_ID;
            newBookID = String.valueOf(temp);
        }

        return newBookID;
    }
    /**
    * This method checks if the string passed is already on the books array.
    *
    * @param books array with all the books of the inventory
    * @param toCheck string to found a match with
    * @param pos int of the column of the array to look for
    * @return true if match found, false if not
    */
    public static boolean checkExisting(final String[][] books, final String toCheck, final int pos) {
        if (books[0][0] != null) {
            for (int i = 0; i < books.length; i++) {
                if (books[i][pos].equals(toCheck)) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
    * This method checks if the array can hold the current number if new items
    *
    * @param books array with all the books of the inventory
    * @return boolean indicating the full condition
    */
    public static boolean checkFull(final String[][] books) {
        // Calculate current books actual size
        // Sum the number of rows that is 0
        if (checkFreeSlots(books) < 1) {
            return true;
        }
        return false;
    }
    /**
    * This method checks if the ISBN matches the correct format
    *
    * @param newBookISBN user input 
    * @return boolean indicating the if it is accepted formal and range values
    */
    public static boolean valideISBN(final String newBookISBN) {
        String regex = "\\d{9}-\\d";
        return newBookISBN.matches(regex);
    }
    /**
    * This method checks if the date given matches the correct format
    *
    * @param date user input 
    * @return boolean indicating the if it is accepted formal or not
    */
    public static boolean valideDate(final String date) {
        Boolean validDate = false;
        String regex = "\\d{4}-\\d{2}-\\d{2}";
        // Check if the data matches the format
        if (date.matches(regex)) {
            String[] parts = date.split("-");
            try {
                int year = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1]);
                int day = Integer.parseInt(parts[2]);
                if (year == 2024) {
                    if (month > 0 && month < 13) {
                        if (day > 0 && day < 32) {
                            validDate = true;
                        }
                    }
                }
            } catch (Exception e) {
            }
        }
        return validDate;
    }
    /**
    * This method allocated th required amount of memory, copy existing contnts and
    * returns the new array
    *
    * @param books array with all the books of the inventory
    * @return new items array of the inventory
    */
    public static String[][] extendArray(final String[][]books) {
        String[][] newBooksArray = new String[books.length + 1][BOOK_COLUMN_SIZE];
        // Copy items from the old one to the new one
        System.arraycopy(books, 0, newBooksArray, 0, books.length);
        return newBooksArray;
    }
    /**
    * This method adds the specified number of books in the array after checking there
    * is sufficient space. 
    *
    * @param books array with all the books of the inventory
    * @param newBookID new ID to add to the inventory
    * @param newBookISBN new ISBN of the book
    * @param newBookTitle Title of the new Book
    * @return new array with all the items of the inventory
    */
    public static String[][] addBook(final String[][] books, final String newBookID, final String newBookISBN, final String newBookTitle) {
        String[][] workingArray = books;

        // Checkfull
        // Check if array if full or if it will be full when all items ara ard
        if (checkFull(books)) {
            workingArray = extendArray(books);
        }
        // Now for the new item
        for (int i = 0; i < workingArray.length; i++) {
            if (workingArray[i][BOOK_ID] == null || workingArray[i][BOOK_ID].equals("0")) {
                workingArray[i][BOOK_ID] = newBookID;
                workingArray[i][BOOK_ISBN] = newBookISBN;
                workingArray[i][BOOK_TITLE] = newBookTitle;
                workingArray[i][BOOK_STATUS] = "Available";
            }
        }
        return workingArray;
    }
    /**
    * This method removes all the info about the give book ID. 
    *
    * @param books array with all the books of the inventory
    * @param bookID ID of the book to be removed
    * @return title book if it was succesful, 1 if book ID not found, and 2 if the book is loaned to someone
    *
    */
    public static String[] removeBook(final String[][] books, final String bookID) {
        boolean found = false;
        int toRemove = 0;
        String[] bookTemp = new String[2];
        //validate that the ID exists on the database
        for (int i = 0; i < books.length; i++) {
            if (books[i][BOOK_ID] != null && books[i][BOOK_ID].equals(bookID)) {
                found = true;
                toRemove = i;
            }
        }
        if (!found) {
            bookTemp[0] = "1";
            return bookTemp;
        } else {
            // Check if the book if loaned
            if (books[toRemove][BOOK_STATUS].equals("Loaned")) {
                bookTemp[0] = "2";
                bookTemp[1] = books[toRemove][BOOK_LENDER];
                return bookTemp;
            }
            // Remove the data from the book 
            bookTemp[0] = "0";
            bookTemp[1] = books[toRemove][BOOK_TITLE];
            books[toRemove][BOOK_ID] = "0";
            books[toRemove][BOOK_ISBN] = "0";
            books[toRemove][BOOK_TITLE] = "0";
            books[toRemove][BOOK_STATUS] = "0";
            books[toRemove][BOOK_LENDER] = "0";
            return bookTemp;
        }
    }
    /**
    * This method loans the give book, and updates the info on book and loans database
    *
    * @param books array with all the books of the inventory
    * @param loans array with all the loans of the books
    * @param loanBookID ID of the book to be removed
    * @param name of the lender person
    * @param startDate on the format YYYY-MM-DD
    * @return title book if it was succesful, 1 if book ID not found, and 2 if the book is loaned to someone
    *
    */
    public static String[] loanBook(final String[][] books, final String[][] loans, final String loanBookID, final String name, final String startDate, final int lastLoaned) {
        boolean found = false;
        int toLoan = 0;
        String[] bookTemp = new String[3];
        // Check if the item ID exists in the inventory
        for (int i = 0; i < books.length; i++) {
            if (books[i][BOOK_ID] != null && books[i][BOOK_ID].equals(loanBookID)) {
                found = true;
                toLoan = i;
            }
        }
        if (!found) {
            bookTemp[0] = "1";
            return bookTemp;
        } else {
            // Check if the book is available to loan
            if (books[toLoan][BOOK_STATUS].equals("Loaned")) {
                bookTemp[0] = "2";
                bookTemp[1] = books[toLoan][BOOK_LENDER];
                return bookTemp;
            }
            // Update the book status to loaned
            bookTemp[0] = "0";
            bookTemp[1] = books[toLoan][BOOK_TITLE];
            bookTemp[2] = String.valueOf(lastLoaned + 1);
            books[toLoan][BOOK_STATUS] = "Loaned";
            books[toLoan][BOOK_LENDER] = name;
            loans[lastLoaned][LOAN_BOOK_ID] = loanBookID;
            loans[lastLoaned][LOAN_LENDER_NAME] = name;
            loans[lastLoaned][LOAN_START_DATE] = startDate;
            return bookTemp;
        }
    }
    /**
    * This method loans the give book, and updates the info on book and loans database
    *
    * @param books array with all the books of the inventory
    * @param loans array with all the loans of the books
    * @param loanBookID ID of the book to be removed
    * @param name of the lender person
    * @param startDate on the format YYYY-MM-DD
    * @return title book if it was succesful, 1 if book ID not found, and 2 if the book is loaned to someone
    *
    */
    public static String[] returnBook(final String[][] books, final String[][] loans, final String returnBookID, final String returnDate) {
        boolean found = false;
        int toReturnBook = 0;
        String[] bookTemp = new String[7];
        // Check if the item ID exists in the inventory
        for (int i = 0; i < books.length; i++) {
            if (books[i][BOOK_ID] != null && books[i][BOOK_ID].equals(returnBookID)) {
                found = true;
                toReturnBook = i;
            }
        }
        if (!found) {
            bookTemp[0] = "1";
            return bookTemp;
        } else {
            // Check if the book is available to loan
            if (books[toReturnBook][BOOK_STATUS].equals("Available")) {
                bookTemp[0] = "2";
                return bookTemp;
            }
            // Look for the book on the loans database
            int toReturnLoan = 0;
            for (int i = 0; i < loans.length; i++) {
                if (loans[i][LOAN_BOOK_ID] != null && loans[i][LOAN_BOOK_ID].equals(returnBookID)) {
                    toReturnLoan = i;
                }
            }
            // Update the book status to loaned
            books[toReturnBook][BOOK_STATUS] = "Available";
            books[toReturnBook][BOOK_LENDER] = "0";
            loans[toReturnLoan][LOAN_END_DATE] = returnDate;
            int duration = calculateDuration(loans[toReturnLoan][LOAN_START_DATE], loans[toReturnLoan][LOAN_END_DATE]);
            loans[toReturnLoan][LOAN_COST] = loanCost(duration);

            bookTemp[0] = loans[toReturnLoan][LOAN_LENDER_NAME]; // lender name
            bookTemp[1] = books[toReturnBook][BOOK_TITLE]; // 
            bookTemp[2] = books[toReturnBook][BOOK_ISBN]; // ISBN
            bookTemp[3] = loans[toReturnLoan][LOAN_START_DATE]; // D1
            bookTemp[4] = loans[toReturnLoan][LOAN_END_DATE]; // D2
            bookTemp[5] = String.valueOf(duration); // duration
            bookTemp[6] = loans[toReturnLoan][LOAN_COST]; // cost
            return bookTemp;
        }
    }
    /**
    * This method computes the cost for the loan period
    *
    * @param duration of the loan in days
    * @return String of the cost in kr
    */
    public static String loanCost(final int duration) {
        int cost = DAY_PRICE * (duration - FREE_DAYS);
        if (cost > 0) {
            return String.valueOf(cost);
        } else {
            return "0";
        }
    }
    /**
    * This method computes the duration of a period of time in days
    *
    * @param startDate YYYY-MM-DD
    * @param endDate YYYY-MM-DD
    * @return int of the number of days between
    */
    public static int calculateDuration(final String startDate, final String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        // Calculate the number of days with the built-in function
        return (int) ChronoUnit.DAYS.between(start, end);
    }
    /**
    * This method sorts the books table by ID number, in ascending order. 
    * It also prints the sorted books, with the correct information about the ID, ISBN, TITLE and STATUS
    *
    * @param books array with all the books of the inventory
    */
    public static void printBooks(final String[][] books) {
        // Sort in ascending order
        for (int i = 0; i < books.length - checkFreeSlots(books); i++) {
            for (int j = 0; j < books.length - checkFreeSlots(books) - 1; j++) {
                if (Integer.parseInt(books[j][BOOK_ID]) > Integer.parseInt(books[j + 1][BOOK_ID])) {
                    String[] temp = new String[BOOK_COLUMN_SIZE];
                    temp[BOOK_ID] = books[j][BOOK_ID];
                    temp[BOOK_ISBN] = books[j][BOOK_ISBN];
                    temp[BOOK_TITLE] = books[j][BOOK_TITLE];
                    temp[BOOK_STATUS] = books[j][BOOK_STATUS];
                    temp[BOOK_LENDER] = books[j][BOOK_LENDER];
                    

                    books[j][BOOK_ID] = books[j + 1][BOOK_ID];
                    books[j][BOOK_ISBN] = books[j + 1][BOOK_ISBN];
                    books[j][BOOK_TITLE] = books[j + 1][BOOK_TITLE];
                    books[j][BOOK_STATUS] = books[j + 1][BOOK_STATUS];
                    books[j][BOOK_LENDER] = books[j + 1][BOOK_LENDER];

                    books[j + 1][BOOK_ID] = temp[BOOK_ID];
                    books[j + 1][BOOK_ISBN] = temp[BOOK_ISBN];
                    books[j + 1][BOOK_TITLE] = temp[BOOK_TITLE];
                    books[j + 1][BOOK_STATUS] = temp[BOOK_STATUS];
                    books[j + 1][BOOK_LENDER] = temp[BOOK_LENDER];
                }
            }
        }

        // Print out the books
        System.out.println("ID         ISBN-10        Title                   Status");
        for (int i = 0; i < books.length - checkFreeSlots(books); i++) {
            if (Integer.parseInt(books[i][BOOK_ID]) != 0) {
                System.out.printf("%-8s   %-12s   %-20s   %-10s%n",
                    books[i][BOOK_ID], 
                    books[i][BOOK_ISBN], 
                    books[i][BOOK_TITLE], 
                    books[i][BOOK_STATUS]);
            }
        }
    }

    /**
    * This method prints the loaned books history, with the correct information 
    *
    * @param loans array with all the loaned books history
    */
    public static void printLoans(final String[][] loans) {
        int numberOfLoans = 0;
        int revenue = 0;
        // Print in the correct format
        System.out.println("ID         Lender          Start date              End date            Cost");
        for (int i = 0; i < loans.length; i++) {
            if (loans[i][BOOK_ID] != null) {
                numberOfLoans++;
                revenue += Integer.parseInt(loans[i][LOAN_COST]);
                System.out.printf("%-8s   %-12s   %-20s   %-10s   %-15s%n",
                    loans[i][LOAN_BOOK_ID], 
                    loans[i][LOAN_LENDER_NAME], 
                    loans[i][LOAN_START_DATE], 
                    loans[i][LOAN_END_DATE],
                    loans[i][LOAN_COST]);
            }
        }
        System.out.println("Number of loans: " + numberOfLoans);
        System.out.println("Total revenue: " + revenue + " kr");
    }

    /**
     * This method should be used only for unit testing on CodeGrade. Do not change this method!
     * Swaps userInputScanner with a custom scanner object bound to a test input stream
     *
     * @param inputScanner  test scanner object
     */
    public static void injectInput(final Scanner inputScanner) {
        userInputScanner = inputScanner;
    }
    public static void main(final String[] args) {
        // Other variables declarations
        String[][] books = new String[INITIAL_BOOK_SIZE][BOOK_COLUMN_SIZE]; // Data structure to store items
        String[][] loans = new String[MAX_LOAN][LOAN_COLUMN_SIZE]; // Data structure to store sales
        int lastLoaned = INITIAL_LOAN_NUMBER; // Keep track of last added ItemNumber
        while (true) {
            int userSelection = menu();
            switch (userSelection) {
                case MENU_BOOK_1:
                    // 1. Add book
                    // Ask the user & read inputs
                    String newBookID = generateBookID(books); //HACER FUNCION
                    System.out.println("Enter book title: ");
                    String newBookTitle = userInputScanner.nextLine();
                    System.out.println("Enter ISBN-10 code: ");
                    String newBookISBN = userInputScanner.nextLine();
                    if (!valideISBN(newBookISBN)) {
                        System.out.println("invalid ISBN");
                        break;
                    }
                    if (checkExisting(books, newBookISBN, BOOK_ISBN)) {
                        System.out.println("ISBN " + newBookISBN + " already exists");
                        break;
                    }
                    // Call the method
                    books = addBook(books, newBookID, newBookISBN, newBookTitle);
                    // Print msg to the user
                    System.out.println("Book with title " + newBookTitle + " was assigned ID " + newBookID + " and added to the system.");
                    break;
                case MENU_BOOK_2:
                    // 2. Remove a book
                    // Ask the user & read inputs
                    System.out.println("Enter book ID number: ");
                    String remBookID = inputID();
                    if (remBookID.equals("0")) {
                        break;
                    }
                    // Call the method
                    String[] returnRemove = removeBook(books, remBookID);
                    // Print msgs to the user
                    if (returnRemove[0].equals("1")) {
                        // ID no exists
                        System.out.println("Book with requested ID " + remBookID + " does not exist.");
                    } else if (returnRemove[0].equals("2")) {
                        // Book loaned
                        System.out.println("Book ID " + remBookID + " is loaned out to " + returnRemove[1] + " and needs to be returned");
                    } else {
                        System.out.println("Book " + returnRemove[1] + " was removed from the system");
                    }
                    break;
                case MENU_BOOK_3:
                    // 3. Loan a book
                    // Ask user which item ID and noOfItems
                    System.out.println("Enter book ID: ");
                    String loanBookID = inputID();
                    if (loanBookID.equals("0")) {
                        break;
                    }
                    System.out.println("Enter lender's name: ");
                    String name = userInputScanner.nextLine();
                    System.out.println("Enter start date of the loan: ");
                    String startDate = userInputScanner.nextLine();
                    if (!valideDate(startDate)) {
                        System.out.println("invalid date");
                    }
                    String[] returnLoan = loanBook(books, loans, loanBookID, name, startDate, lastLoaned);
                    // Print msgs to the user
                    if (returnLoan[0].equals("1")) {
                        // ID no exists
                        System.out.println("Book with requested ID " + loanBookID + " does not exist.");
                    } else if (returnLoan[0].equals("2")) {
                        // Book loaned
                        System.out.println("Book ID " + loanBookID + " is loaned out to " + returnLoan[1] + " and needs to be returned");
                    } else {
                        lastLoaned = Integer.parseInt(returnLoan[2]);
                        System.out.println("Book " + returnLoan[1] + " was loaned by " + name + " on " + startDate);
                    }
                    break;
                case MENU_BOOK_4:
                    // 4. Return a book
                    // Ask user which item ID and noOfItems
                    System.out.println("Enter book ID: ");
                    String returnBookID = inputID();
                    if (returnBookID.equals("0")) {
                        break;
                    }
                    System.out.println("Enter return date: ");
                    String returnDate = userInputScanner.nextLine();
                    if (!valideDate(returnDate)) {
                        System.out.println("invalid date");
                    }
                    String[] returnReturn = returnBook(books, loans, returnBookID, returnDate);
                    // Print msgs to the user
                    if (returnReturn[0].equals("1")) {
                        // ID no exists
                        System.out.println("Book with requested ID " + returnBookID + " does not exist.");
                    } else if (returnReturn[0].equals("2")) {
                        // Book loaned
                        System.out.println("Book ID " + returnBookID + " is available and has not been loaned");
                    } else {
                        System.out.println();
                        System.out.println("RECEIPT LTU LIBRARY");
                        System.out.println();
                        System.out.println("Lender's name: " + returnReturn[0]);
                        System.out.println("Book title: " + returnReturn[1]);
                        System.out.println("ISBN-10: " + returnReturn[2]);
                        System.out.println();
                        System.out.println("Period: " + returnReturn[3] + " - " + returnReturn[4]);
                        System.out.println("Duration: " + returnReturn[5] + " days");
                        System.out.println();
                        System.out.println("Cost: " + returnReturn[6] + " kr");
                    }
                    break;
                case MENU_BOOK_5:
                    // 5. Print book list
                    printBooks(books);
                    break;
                case MENU_BOOK_6:
                    // 6. Print lending summary
                    printLoans(loans);
                    break;
                case MENU_BOOK_Q:
                    // q. Quit
                    //System.out.println("Terminating...");
                    return;
                default:
                    System.out.println("invalid menu item");
                    break;
            }
        }
    }
}