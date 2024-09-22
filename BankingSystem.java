import java.util.Scanner;

class BankAccount {
    private String accountNumber;
    private double balance;

    public BankAccount(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("$" + amount + " has been deposited.");
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("$" + amount + " has been withdrawn.");
            return true;
        } else if (amount > balance) {
            System.out.println("Insufficient funds for withdrawal.");
        } else {
            System.out.println("Invalid withdrawal amount.");
        }
        return false;
    }

    public double getBalance() {
        return balance;
    }
}

public class BankingSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BankAccount account = null;
        System.out.println("\nWelcome!!");

        while (true) {
            System.out.println("\n1. Create Account");
            System.out.println("2. Check Balance");
            System.out.println("3. Deposit Money");
            System.out.println("4. Withdraw Money");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            if (choice == 1) {
                if (account == null) {
                    System.out.print("Enter account number: ");
                    String accountNumber = scanner.next();
                    System.out.print("Enter initial balance: $");
                    double initialBalance = scanner.nextDouble();
                    account = new BankAccount(accountNumber, initialBalance);
                    System.out.println("Account created successfully.");
                } else {
                    System.out.println("An account already exists. Please choose another option.");
                }
            } else if (choice == 2) {
                if (account != null) {
                    System.out.println("Current balance: $" + account.getBalance());
                } else {
                    System.out.println("No account created yet.");
                }
            } else if (choice == 3) {
                if (account != null) {
                    System.out.print("Enter amount to deposit: $");
                    double depositAmount = scanner.nextDouble();
                    account.deposit(depositAmount);
                } else {
                    System.out.println("No account created yet.");
                }
            } else if (choice == 4) {
                if (account != null) {
                    System.out.print("Enter amount to withdraw: $");
                    double withdrawAmount = scanner.nextDouble();
                    if (account.withdraw(withdrawAmount)) {
                        System.out.println("New balance: $" + account.getBalance());
                    }
                } else {
                    System.out.println("No account created yet.");
                }
            } else if (choice == 5) {
                System.out.println("Thank you for using our banking system.");
                return;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }
}