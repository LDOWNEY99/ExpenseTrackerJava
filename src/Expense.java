public class Expense {

    private static int nextID = 0;
    private final int iD;
    private double amount;
    private String category;
    private String description;

    public Expense(String description, double amount, String category) {
        /*
        Issue 2: Could not get ID to autoincrement as I was setting it to be always zero,
        fix was using a static int nextID and nextID auto increments after its assigned.
         */
        this.iD = nextID++;
        this.description = description;
        this.amount = amount;
        this.category = category;
    }

    public Expense(int id, String description, double amount, String category) {
        // New constructor for file read expenses to include ID.
        this.iD = id;
        this.description = description;
        this.amount = amount;
        this.category = category;

        if (id >= nextID) {
            nextID = id + 1;
        }
    }

    public int getID() {
        return this.iD;
    }

    public String getDescription() {
        return this.description;
    }

    public double getAmount() {
        return this.amount;
    }

    public String getCategory() {
        return this.category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
