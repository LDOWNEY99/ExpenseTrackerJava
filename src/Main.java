import java.util.ArrayList;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    static ArrayList<Expense> expenses = new ArrayList<>();

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        callMenu();

        int choice = scanner.nextInt();
        /* Issue one: added the following next line as when you hit enter,
        the scanner uses this as the next input, in this case I was selecting option 1
        and the description was consuming the left over enter I hit after typing 1.
         */
        scanner.nextLine();

        while(choice != 4) {
            System.out.println("You have selected option " + choice);

            switch (choice) {
                case 1:
                    addExpense(scanner);
                    break;

                case 2:
                    viewExpenses();
                    break;

                case 3:
                    deleteExpenses(scanner);
                    break;

                case 4:
                    System.out.println("GoodBye!");
                    break;

                default:
                    System.out.println("Invalid Option");
            }

            callMenu();
            choice = scanner.nextInt();
            scanner.nextLine();
        }

        scanner.close();

    }

    public static void addExpense(Scanner scanner) {
        System.out.println("Enter Description: ");
        String desc = scanner.nextLine();

        System.out.println("Enter Amount: ");
        double amt = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Enter Category Name: ");
        String cat = scanner.nextLine();

        Expense expense = new Expense(desc, amt, cat);

        expenses.add(expense);
        System.out.println("Expense added!");
    }

    public static void viewExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No Expenses found.");
            return;
        }

        System.out.println("\n=== Expenses ===");
        System.out.printf("%-8s %-20s %-15s %-20s%n",
                "ID",
                "Description",
                "Amount",
                "Category");
        System.out.println("------------------------------------------------------");

        for (Expense expense : expenses) {
            System.out.printf(
                    "%-8s %-20s %-15s %-20s%n",
                    expense.getID(),
                    expense.getDescription(),
                    String.format("€%.2f", expense.getAmount()),
                    expense.getCategory()
            );
        }
    }

    public static void deleteExpenses(Scanner scanner) {
        if (expenses.isEmpty()) {
            System.out.println("No Expenses found.");
            return;
        }

        viewExpenses();

        System.out.println("Select Expense ID to remove:");
        int iDToDelete = scanner.nextInt();
        scanner.nextLine();

        for(int i = 0; i < expenses.size(); i++) {
            if (expenses.get(i).getID() == iDToDelete) {
                expenses.remove(i);

                System.out.println("Expense removed Succesfully!");
                return;
            }
        }

        System.out.println("404: ID not Found!");
    }

    public static void callMenu() {
        System.out.println("=== Expense Tracker ===");
        System.out.println("1. Add Expense");
        System.out.println("2. View Expenses");
        System.out.println("3. Delete Expense");
        System.out.println("4. Exit");

        System.out.println("Select and Option: ");
    }
}