import java.util.Scanner;
import java.util.Date;
import java.util.Random;
/**
 * Program that works as a simple cash register system. The user should be able to
 * add/remove items, sell items, and present sales history. The user menu has the following options:
 * 1. Insert items
 * 2. Remove an item
 * 3. Display a list of items
 * 4. Register a sale
 * 5. Display sales history
 * 6. Sort and display sales history table
 * q. Quit
 *
 @author erocos-4
 @version 1.0
 */
public class Main {

    // Constants for the item array
    public static final int ITEM_ID = 0;
    public static final int ITEM_COUNT = 1;
    public static final int ITEM_PRICE = 2;
    public static final int ITEM_COLUMN_SIZE = 3;
    public static final int INITIAL_ITEM_SIZE = 1;

    // Constants for the sales array
    public static final int SALE_ITEM_ID = 0;
    public static final int SALE_ITEM_COUNT = 1;
    public static final int SALE_ITEM_PRICE = 2;
    public static final int SALE_COLUMN_SIZE = 3;
    public static final int MAX_SALES = 1000;

    // Other constants
    public static final int MENU_ITEM_1 = 1;
    public static final int MENU_ITEM_2 = 2;
    public static final int MENU_ITEM_3 = 3;
    public static final int MENU_ITEM_4 = 4;
    public static final int MENU_ITEM_5 = 5;
    public static final int MENU_ITEM_6 = 6;
    public static final int MENU_ITEM_Q = -1;

    public static final int INITIAL_ITEM_NUMBER = 999;

    public static final int MIN_ITEM_UNITS = 1;
    public static final int MAX_ITEM_UNITS = 10;
    public static final int MIN_ITEM_PRICE = 100;
    public static final int MAX_ITEM_PRICE = 1000;

    private static Scanner userInputScanner = new Scanner(System.in);

    // Methods declarations

    /**
    * This method presents the menu, loads, and returns the user's selection
    *
    * @return user's selection
    */
    public static int menu() {
        // Show the menu
        System.out.println("1. Insert items");
        System.out.println("2. Remove an item");
        System.out.println("3. Display a list of items");
        System.out.println("4. Register a sale");
        System.out.println("5. Display sales history");
        System.out.println("6. Sort and display sales history table");
        System.out.println("q. Quit");
        System.out.println("Your Selection:");
        return input();
    }
    /**
    * This method handles the inputs from the user. Returns the given int or
    * -1 in case it was "q". Allows a user a new input chance as long as the
    * entered input was not valid
    *
    * @return user's input
    */
    public static int input() {
        int input = 0;

        while (true) {
            if (userInputScanner.hasNextInt()) {
                input = userInputScanner.nextInt();
                if (input >= 0) {
                    break;
                } else {
                    System.out.println("Invalid selection");
                }
            } else if (userInputScanner.hasNext()) {
                String inString = userInputScanner.next();
                // Q & q
                if (inString.equalsIgnoreCase("q")) {
                    input = MENU_ITEM_Q;
                    break;
                } else {
                    System.out.println("Invalid selection");
                }

            }
        }
        return input;
    }
    /**
    * This method calculates the number of free slots availables on the array.
    *
    * @param items array with all the items of the inventory
    * @return number of free slots
    */
    public static int checkFreeSlots(final int[][] items) {
        int count = 0;
        for (int i = 0; i < items.length; i++) {
            if (items[i][ITEM_ID] == 0) {
                count++;
            }
        }
        return count;
    }
    /**
    * This method checks if the array can hold the current number if new items
    *
    * @param items array with all the items of the inventory
    * @param noOfItems number of new items to add to the array
    * @return boolean indicating the full condition
    */
    public static boolean checkFull(final int[][] items, final int noOfItems) {
        // Calculate current items actual size
        // Sum the number of rows that is 0
        // If freeslots <= noOfItems -> return true
        if (noOfItems > checkFreeSlots(items)) {
            return true;
        }
        return false;
    }
    /**
    * This method allocated th required amount of memory, copy existing contnts and
    * returns the new array
    *
    * @param items array with all the items of the inventory
    * @param noOfItems number of items needed to be added to the array
    * @return new items array of the inventory
    */
    public static int[][] extendArray(final int[][]items, final int noOfItems) {
        int[][] newItemsArray = new int[items.length + noOfItems][ITEM_COLUMN_SIZE];
        // Copy items from the old one to the new one
        System.arraycopy(items, 0, newItemsArray, 0, items.length);
        return newItemsArray;
    }
    /**
    * This method adds the specified number of items in the array after checking there
    * is sufficient space.
    *
    * @param items array with all the items of the inventory
    * @param lastItemId ID of the last item added to the inventory
    * @param noOfItems number of new items to be added to the inventory
    * @return new array with all the items of the inventory
    */
    public static int[][] insertItems(final int[][] items, final int lastItemId, final int noOfItems) {
        int newItemNumber = lastItemId + 1;
        int[][] workingArray = items;
        Random rand = new Random();
        //int j = 0;

        // Checkfull
        // Check if array if full or if it will be full when all items ara ard
        if (checkFull(items, noOfItems)) {
            workingArray = extendArray(items, noOfItems - checkFreeSlots(items));
        }

        // Now for the new item
        // Populate the values
        /*for (int i = itemNumber; i < itemNumber + noOfItems; i++) {
            workingArray[i][ITEM_ID] = newItemNumber++;
            workingArray[i][ITEM_COUNT] = rand.nextInt(MAX_ITEM_UNITS - MIN_ITEM_UNITS + 1) + MIN_ITEM_UNITS;
            workingArray[i][ITEM_PRICE] = rand.nextInt(MAX_ITEM_PRICE - MIN_ITEM_PRICE + 1) + MIN_ITEM_PRICE;
        }*/
        for (int i = 0; i < workingArray.length; i++) {
            if (workingArray[i][ITEM_ID] == 0) {
                workingArray[i][ITEM_ID] = newItemNumber++;
                workingArray[i][ITEM_COUNT] = rand.nextInt(MAX_ITEM_UNITS - MIN_ITEM_UNITS + 1) + MIN_ITEM_UNITS;
                workingArray[i][ITEM_PRICE] = rand.nextInt(MAX_ITEM_PRICE - MIN_ITEM_PRICE + 1) + MIN_ITEM_PRICE;
            }
        }

        return workingArray;
    }
    /**
    * This method removes all the info about the give item ID.
    *
    * @param items array with all the items of the inventory
    * @param itemId ID of the item to be removed
    * @return 0 if it was succesfully operation and -1 if the item was not found
    *
    */
    public static int removeItem(final int[][] items, final int itemId) {
        //validate that the number exists
        boolean valid = false;
        int toRemove = 0;

        for (int i = 0; i < items.length; i++) {
            if (items[i][ITEM_ID] == itemId) {
                valid = true;
                toRemove = i;
            }
        }
        // set all the values to 0 (for this row)
        if (valid) {
            items[toRemove][ITEM_ID] = 0;
            items[toRemove][ITEM_COUNT] = 0;
            items[toRemove][ITEM_PRICE] = 0;
            return 0;
        } else {
            return -1;
        }
    }
    /**
    * This method prints the current inventory
    *
    * @param items array with all the items of the inventory
    */
    public static void printItems(final int[][] items) {
        //print values in tabular form except the 0
        System.out.println("Item number   Count   Price");
        for (int i = 0; i < items.length - checkFreeSlots(items); i++) {
            /*if (items[i][ITEM_ID]!=0) {
                System.out.printf("%4d          %2d       %3d\n",
                            items[i][ITEM_ID], items[i][ITEM_COUNT], items[i][ITEM_PRICE]);
            }*/
            System.out.printf("%4d          %2d       %3d\n",
                            items[i][ITEM_ID], items[i][ITEM_COUNT], items[i][ITEM_PRICE]);
        }
    }
    /**
    * This method registers a sale, reducing the number of items from the inventory.
    * It also saves the date and time of the transaction
    *
    * @param sales array with all the sales of the inventory
    * @param salesDate array of dates of the selling transactions
    * @param items array with all the items of the inventory
    * @param itemIdToSell ID of the item to sell
    * @param amountToSell qty of items to sell
    * @return 0 when sale was successfully, -1 if not found and >0 if not enough stock for the sale
    */
    public static int sellItem(final int[][] sales, final Date[] salesDate, final int[][] items, final int itemIdToSell, final int amountToSell) {
        boolean found = false;
        int toSell = 0;
        // Check if the item ID exists in the inventory
        for (int i = 0; i < items.length; i++) {
            if (items[i][ITEM_ID] == itemIdToSell) {
                found = true;
                toSell = i;
            }
        }
        // set all the values to 0 (for this row)
        if (found) {
            if (items[toSell][ITEM_COUNT] >= amountToSell) {
                // Update the inventory (items matrix)
                items[toSell][ITEM_COUNT] -= amountToSell;
                // Add the sale to the sales matrix
                for (int i = 0; i < sales.length; i++) {
                    if (sales[i][SALE_ITEM_ID] == 0) {
                        sales[i][SALE_ITEM_ID] = itemIdToSell;
                        sales[i][SALE_ITEM_COUNT] = amountToSell;
                        sales[i][SALE_ITEM_PRICE] = amountToSell * items[toSell][ITEM_PRICE];
                        salesDate[i] = new Date();
                        break;
                    }
                }
                //success
                return 0;


            } else {
                // not enough qty
                return items[toSell][ITEM_COUNT];
            }
        } else {
            // item not found
            return -1;
        }
    }
    /**
    * This method prints all sales transactions with data, item number, number of items
    * and total amount of the sale
    *
    * @param sales array with all the sales of the inventory
    * @param saleDates array of dates of the selling transactions
    */
    public static void printSales(final int[][] sales, final Date[] saleDates) {
        // Print out the sales and dates tables
        System.out.println("Item number   Count   Price     Date");
        for (int i = 0; i < sales.length - checkFreeSlots(sales); i++) {
            /*if (sales[i][SALE_ITEM_ID]!=0) {
                System.out.printf("%4d          %2d       %3d       %s%n\n",
                            sales[i][SALE_ITEM_ID], sales[i][SALE_ITEM_COUNT], sales[i][SALE_ITEM_PRICE], saleDates[i]);
            }*/
            System.out.printf("%4d          %2d       %4d       %s%n\n",
                            sales[i][SALE_ITEM_ID], sales[i][SALE_ITEM_COUNT], sales[i][SALE_ITEM_PRICE], saleDates[i]);
        }
    }
    /**
    * This method sorts the selling table by item number, in ascending order.
    * It also prints the sorted table, with the correct information about the time and price of the sold items
    *
    * @param sales array with all the sales of the inventory
    * @param salesDate array of dates of the selling transactions
    */
    public static void sortedTable(final int[][] sales,  final Date[] salesDate) {
        // Sort in ascending order
        for (int i = 0; i < sales.length - checkFreeSlots(sales); i++) {
            for (int j = 0; j < sales.length - checkFreeSlots(sales) - 1; j++) {
                if (sales[j][SALE_ITEM_ID] > sales[j + 1][SALE_ITEM_ID]) {
                    int[] temp = new int[SALE_COLUMN_SIZE];
                    Date tDate = new Date();
                    temp[SALE_ITEM_ID] = sales[j][SALE_ITEM_ID];
                    temp[SALE_ITEM_COUNT] = sales[j][SALE_ITEM_COUNT];
                    temp[SALE_ITEM_PRICE] = sales[j][SALE_ITEM_PRICE];
                    tDate = salesDate[j];

                    sales[j][SALE_ITEM_ID] = sales[j + 1][SALE_ITEM_ID];
                    sales[j][SALE_ITEM_COUNT] = sales[j + 1][SALE_ITEM_COUNT];
                    sales[j][SALE_ITEM_PRICE] = sales[j + 1][SALE_ITEM_PRICE];
                    salesDate[j] = salesDate[j + 1];

                    sales[j + 1][SALE_ITEM_ID] = temp[SALE_ITEM_ID];
                    sales[j + 1][SALE_ITEM_COUNT] = temp[SALE_ITEM_COUNT];
                    sales[j + 1][SALE_ITEM_PRICE] = temp[SALE_ITEM_PRICE];
                    salesDate[j + 1] = tDate;
                }
            }
        }
        // Print out the sales and dates tables
        System.out.println("Item number   Count   Price     Date");
        for (int i = 0; i < sales.length - checkFreeSlots(sales); i++) {
            /*if (sales[i][SALE_ITEM_ID]!=0) {
                System.out.printf("%4d          %2d       %3d       %s%n\n",
                            sales[i][SALE_ITEM_ID], sales[i][SALE_ITEM_COUNT], sales[i][SALE_ITEM_PRICE], saleDates[i]);
            }*/
            System.out.printf("%4d          %2d       %4d       %s%n\n",
                            sales[i][SALE_ITEM_ID], sales[i][SALE_ITEM_COUNT], sales[i][SALE_ITEM_PRICE], salesDate[i]);
        }
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
        int[][] items = new int[INITIAL_ITEM_SIZE][ITEM_COLUMN_SIZE]; // Data structure to store items
        int[][] sales = new int[MAX_SALES][SALE_COLUMN_SIZE]; // Data structure to store sales
        Date[] saleDates = new Date[MAX_SALES]; // Data structure to store sale dates
        int lastItemNumber = INITIAL_ITEM_NUMBER; // Keep track of last added ItemNumber

        System.out.println("This is Marked Assignment 4");

        while (true) {
            int userSelection = menu();
            switch (userSelection) {
                case MENU_ITEM_1:
                    // 1. Insert items
                    // Ask the user & read inputs
                    System.out.println("How many items do you want to add?");
                    int noOfItems = input();
                    // Call the method
                    items = insertItems(items, lastItemNumber, noOfItems);
                    lastItemNumber += noOfItems;
                    // Print msg to the user
                    System.out.println(noOfItems + " items added successfully");
                    break;
                case MENU_ITEM_2:
                    // 2. Remove an item
                    // Ask the user & read inputs
                    System.out.println("Please provide the ID of the item to be removed: ");
                    int itemID = input();
                    // Call the method
                    int returnRemove = removeItem(items, itemID);
                    // Print msg to the user
                    if (returnRemove == 0) {
                        System.out.println("Item remove successfully " + itemID);
                    } else if (returnRemove == -1) {
                        System.out.println("Could not find");
                    }
                    break;
                case MENU_ITEM_3:
                    // 3. Display a list of items
                    printItems(items);
                    break;
                case MENU_ITEM_4:
                    // 4. Register a sale
                    // Ask user which item ID and noOfItems
                    System.out.println("Please provide the ID of the item to sell: ");
                    int itemIdToSell = input();
                    System.out.println("How many items do you want to sell?");
                    int amountToSell = input();
                    // validate sell Item
                    int returnSell = sellItem(sales, saleDates, items, itemIdToSell, amountToSell);
                    // Check for different return values for printing the appropiate msg
                    if (returnSell == 0) {
                        System.out.println(amountToSell + " of Item " + itemIdToSell + " were sold sucessfully");
                    } else if (returnSell == -1) {
                        System.out.println("Could not find");
                    } else if (returnSell > 0) {
                        System.out.println("Failed to sell specified amount. Currently on stock: " + returnSell);
                    }
                    break;
                case MENU_ITEM_5:
                    // 5. Display sales history
                    //Date tempDate = new Date();
                    printSales(sales, saleDates);
                    break;
                case MENU_ITEM_6:
                    // 6. Sort and display sales history table
                    //Sort the Sales matrix AND the sales Date ALSO
                    sortedTable(sales, saleDates);
                    break;
                case MENU_ITEM_Q:
                    // q. Quit
                    System.out.println("Terminating...");
                    return;
                default:
                    //System.out.println("Unexpected user selection: " + userSelection);
                    System.out.println("Invalid selection");
                    break;
            }
        }
    }
}