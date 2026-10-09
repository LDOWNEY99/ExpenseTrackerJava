import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    static ArrayList<Expense> expenses = new ArrayList<>();

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        loadExpensesFromCsv();

        printMenu();

        String choice = scanner.nextLine();
        /* Issue one: Was using nextInt, added the following next line as when you hit enter,
        the scanner uses this as the next input, in this case I was selecting option 1
        and the description was consuming the left over enter I hit after typing 1.
         */

        while(true) {
            System.out.println("You have selected option " + choice);

            switch (choice) {
                case "1":
                    addExpense(scanner);
                    break;

                case "2":
                    viewExpenses();
                    break;

                case "3":
                    deleteExpense(scanner);
                    break;

                case "4":
                    editExpense(scanner);
                    break;

                case "5":
                    saveExpensesToCsv();
                    System.out.println("GoodBye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid Option");
            }

            printMenu();
            choice = scanner.nextLine();
        }
    }

    public static void saveExpensesToCsv() {

        try (FileWriter writer = new FileWriter("expense.csv")) {

            writer.write("ID,DESCRIPTION,AMOUNT,CATEGORY\n");

            for (Expense expense : expenses) {

                writer.write(expense.getID() + "," +
                                expense.getDescription() + "," +
                                expense.getAmount() + "," +
                                expense.getCategory() + "\n"
                );
            }

            System.out.println("Expenses saved successfully to csv file!");

        } catch (IOException e) {
            System.out.println("CSV Saving Error has occurred!");
            System.out.println(e.getMessage());
        }
    }

    public static void loadExpensesFromCsv() {

        File file = new File("expense.csv");

        if (!file.exists()) {
            System.out.println("No existing file found, creating new file.");
            saveExpensesToCsv();
            return;
        }

        try (BufferedReader reader = new BufferedReader(
                new FileReader(file))) {
            String line;

            //Following line skips the column headers.
            reader.readLine();

            while ((line = reader.readLine()) != null) {

                String[] values = line.split(",");

                int id = Integer.parseInt((values[0]));
                String description = values[1];
                double amount = Double.parseDouble(values[2]);
                String category = values[3];

                Expense expense = new Expense(
                        id,
                        description,
                        amount,
                        category
                );

                expenses.add(expense);
            }

            System.out.println("Expenses loaded Successfully from expense.csv file!");

        } catch (IOException e) {
            System.out.println("Error loading expense.csv file!");
            System.out.println(e.getMessage());
        }
    }

    public static Double parseDouble(String amount) {
        Double amountDouble;
        try {
            amountDouble = Double.parseDouble(amount);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number between 1 and 5.");
            return null;
        }
        return amountDouble;
    }

    public static void addExpense(Scanner scanner) {
        System.out.println("Enter Description: ");
        String desc = scanner.nextLine();

        System.out.println("Enter Amount: ");
        String amt = scanner.nextLine();
        Double amtDouble = parseDouble(amt);

        if(amtDouble == null) {
            return;
        }

        System.out.println("Enter Category Name: ");
        String cat = scanner.nextLine();

        Expense expense = new Expense(desc, amtDouble, cat);

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

    public static void deleteExpense(Scanner scanner) {
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

                System.out.println("Expense removed Successfully!");
                return;
            }
        }

        System.out.println("404: ID not Found!");
    }

    public static void editExpense(Scanner scanner) {
        if (expenses.isEmpty()) {
            System.out.println("No Expenses found.");
            return;
        }

        boolean editMode = true;
        viewExpenses();

        System.out.println("Select Expense ID to edit:");
        int iDToEdit = scanner.nextInt();
        scanner.nextLine();

        while(editMode) {
            for (Expense expense : expenses) {
                if (expense.getID() == iDToEdit) {

                    printEditMenu();

                    int editChoice = scanner.nextInt();
                    scanner.nextLine();

                    switch (editChoice) {
                        case 1:
                            editDescription(expense, scanner);
                            break;

                        case 2:
                            editAmount(expense, scanner);
                            break;

                        case 3:
                            editCategory(expense, scanner);
                            break;

                        case 4:
                            System.out.println("Exited!");
                            break;

                        default:
                            System.out.println("Invalid Option");
                    }

                    System.out.println("Expense edited Successfully!");
                }
            }
        }

        System.out.println("404: ID not Found!");
    }

    public static void editDescription(Expense expense, Scanner scanner) {
        System.out.println("Enter New Description: ");
        String description = scanner.nextLine();
        expense.setDescription(description);
        System.out.println("Description Updated Successfully!");
    }

    public static void editAmount(Expense expense, Scanner scanner) {
        System.out.println("Enter New Amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        expense.setAmount(amount);
        System.out.println("Amount Updated Successfully!");
    }

    public static void editCategory(Expense expense, Scanner scanner) {
        System.out.println("Enter New Category: ");
        String category = scanner.nextLine();
        expense.setCategory(category);
        System.out.println("Category Updated Successfully!");
    }

    public static void printMenu() {
        System.out.println("=== Expense Tracker ===");
        System.out.println("1. Add Expense");
        System.out.println("2. View Expenses");
        System.out.println("3. Delete Expense");
        System.out.println("4. Edit Expense");
        System.out.println("5. Exit");

        System.out.println("Select and Option: ");
    }

    public static void printEditMenu() {
        System.out.println("=== Edit Menu ===");
        System.out.println("1. Edit Description");
        System.out.println("2. Edit Amount");
        System.out.println("3. Edit Category");
        System.out.println("4. Exit");

        System.out.println("Select and Option: ");
    }
}
