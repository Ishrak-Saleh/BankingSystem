import java.util.Date;

public class BankAccount {
    private String accountNumber;
    private String ownerName;
    private String pin;
    private double balance;
    private Date creationDate;
    private boolean isLoggedIn = true;


    public BankAccount(String accountNumber, String ownerName, String pin, Date creationDate) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.pin = pin;
        this.creationDate = creationDate;
    }

    public Date creationDate() {
        return creationDate;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("\nDeposit of $" + amount + " successful. New balance: $" + balance);
        } else {
            System.out.println("\nInvalid deposit amount. Please enter a positive value.");
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("\nWithdrawal of $" + amount + " successful. Remaining balance: $" + balance);
            return true;
        } else {
            System.out.println("\nInsufficient funds or invalid withdrawal amount.");
            return false;
        }
    }

    public void setLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getPin() {
        return pin;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public boolean transfer(BankAccount recipientAccount, double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            recipientAccount.deposit(amount);
            System.out.println("\nTransfer of $" + amount + " successful.");
            return true;
        } else {
            System.out.println("\nInsufficient funds or invalid transfer amount.");
            return false;
        }
    }

    public void viewAccountInfo() {
        System.out.println("Account Information:");
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Owner Name: " + ownerName);
        System.out.println("Current Balance: $" + balance);
        System.out.println("Account Creation Date: " + creationDate);
    }
}