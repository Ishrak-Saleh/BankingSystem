import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BankingSystem {
    private static ArrayList<BankAccount> accounts = new ArrayList<>();
    private static ArrayList<String> pins = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nBanking System V2");

        while (true) {
            printMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            MenuOption option = MenuOption.fromNumber(choice);

            switch (option) {
                case LOGIN:
                    if (accounts.isEmpty()) {
                        System.out.println("No accounts available. Please register first.");
                    } else {
                        login(scanner);
                    }
                    break;
                case REGISTER:
                    registerAccount(scanner);
                    break;
                case EXIT:
                    System.exit(0);
                    break;
            }
        }
    }

    private static void printMenu() {
        for (MenuOption option : MenuOption.values()) {
            System.out.println(option.getDescription());
        }
    }

    private static void login(Scanner scanner) {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.next();
        System.out.print("Enter PIN: ");
        String pin = scanner.next();
        scanner.nextLine();

        BankAccount account = findAccountByNumber(accountNumber);
        if (account != null && pins.contains(pin)) {
            performActions(scanner, accountNumber);
        } else {
            System.out.println("\nLogin failed.");
        }
    }
    private static void registerAccount(Scanner scanner) {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.next();
        scanner.nextLine();


        if (findAccountByNumber(accountNumber) != null) {
            System.out.println("\nAccount number already exists. Please choose a different one.");
            return;
        }

        System.out.print("Enter owner name: ");
        String ownerName = scanner.next();
        System.out.print("Enter PIN: ");
        String pin = scanner.next();
        scanner.nextLine();

        Date currentDate = new Date(9, 23, 2024);

        BankAccount account = new BankAccount(accountNumber, ownerName, pin, currentDate);
        accounts.add(account);
        pins.add(pin);
        System.out.println("\nAccount registered successfully!");
        account.deposit(250);
    }

    private static void performActions(Scanner scanner, String accountNumber) {
        BankAccount account = findAccountByNumber(accountNumber);
        System.out.println("\nHello " + account.getOwnerName() + "!");
        while (true) {
            System.out.println("\n1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Delete Account");
            System.out.println("6. Check Account Info");
            System.out.println("7. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("\nYour balance: $" + findAccountByNumber(accountNumber).getBalance());
                    break;
                case 2:
                    deposit(scanner, accountNumber);
                    break;
                case 3:
                    withdraw(scanner, accountNumber);
                    break;
                case 4:
                    transfer(scanner, accountNumber);
                    break;
                case 5:
                    if (deleteAccount(accountNumber)) {
                        System.out.println("\nReturning to main menu...");
                        return;
                    }
                    break;
                case 6:
                    viewAccountInfo(accountNumber);
                    break;
                case 7:
                    if(logout(accountNumber)) {
                        System.out.println("\nReturning to main menu...");
                        return;
                    }
                    break;
                default:
                    System.out.println("\nInvalid option. Please try again.");
            }
        }
    }
    private static void deposit(Scanner scanner, String accountNumber) {
        boolean isValidInput = false;
        while (!isValidInput) {
            try {
                System.out.print("Enter amount to deposit: ");
                double amount = scanner.nextDouble();
                if (amount > 0) {
                    findAccountByNumber(accountNumber).deposit(amount);
                    System.out.println("\nDeposit successful!");
                    isValidInput = true;
                } else {
                    System.out.println("\nAmount must be positive.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid input. Please enter a valid amount.");
                scanner.next();
            }
        }
    }

    private static void withdraw(Scanner scanner, String accountNumber) {
        boolean isValidInput = false;
        while (!isValidInput) {
            try {
                System.out.print("Enter amount to withdraw: ");
                double amount = scanner.nextDouble();
                if (amount > 0 && amount <= findAccountByNumber(accountNumber).getBalance()) {
                    if (findAccountByNumber(accountNumber).withdraw(amount)) {
                        System.out.println("\nWithdrawal successful!");
                        isValidInput = true;
                    } else {
                        System.out.println("\nInsufficient funds.");
                    }
                } else {
                    System.out.println("\nAmount must be positive and not exceed your balance.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid input. Please enter a valid amount.");
                scanner.next();
            }
        }
    }

    private static void transfer(Scanner scanner, String accountNumber) {
        boolean isValidInput = false;
        while (!isValidInput) {
            try {
                System.out.print("Enter recipient account number: ");
                String recipient = scanner.next();
                scanner.nextLine();
                System.out.print("Enter transfer amount: ");
                double amount = scanner.nextDouble();

                if (amount > 0) {
                    int index = findAccountIndex(recipient);
                    if (index >= 0 && findAccountByNumber(accountNumber).transfer(findAccountByNumber(recipient), amount)) {
                        System.out.println("\nTransfer successful!");
                        isValidInput = true;
                    } else {
                        System.out.println("\nTransfer failed.");
                    }
                } else {
                    System.out.println("\nAmount must be positive.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid input. Please enter a valid amount.");
                scanner.next();
            }
        }
    }


    private static boolean deleteAccount(String accountNumber) {
        int index = findAccountIndex(accountNumber);
        if (index >= 0) {
            accounts.remove(index);
            pins.remove(index);
            System.out.println("\nAccount deleted successfully.");
            return true;

        } else {
            System.out.println("\nAccount not found.");
            return false;
        }
    }

    private static boolean logout(String accountNumber) {
        int index = findAccountIndex(accountNumber);
        if (index >= 0) {
            accounts.remove(index);
            pins.remove(index);
            System.out.println("\nLogged out successfully.");
            return true;
        } else {
            System.out.println("\nAccount not found.");
            return false;
        }
    }

    private static int findAccountIndex(String accountNumber) {
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getAccountNumber().equals(accountNumber)) {
                return i;
            }
        }
        return -1;
    }

    private static BankAccount findAccountByNumber(String accountNumber) {
        for (BankAccount account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    private static void viewAccountInfo(String accountNumber) {
        BankAccount account = findAccountByNumber(accountNumber);
        if (account != null) {
            System.out.println("\nAccount Information:");
            System.out.println("Account Number: " + account.getAccountNumber());
            System.out.println("Owner Name: " + account.getOwnerName());
            System.out.println("PIN: " + account.getPin());
            System.out.println("Current Balance: $" + account.getBalance());
            System.out.println("Account Creation Date: " + account.getAccountCreationDate());
        } else {
            System.out.println("\nAccount not found.");
        }
    }
}

