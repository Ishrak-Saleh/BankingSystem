class BankAccount {
    private String accountNumber;
    private String ownerName;
    private String pin;
    private double balance;
    private Date accountCreationDate;

    public BankAccount(String accountNumber, String ownerName, String pin, Date accountCreationDate) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.pin = pin;
        this.balance = 0;
        this.accountCreationDate = accountCreationDate;
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
        } else if (amount <= 0) {
            System.out.println("\nInvalid withdrawal amount. Please enter a positive value.");
        } else {
            System.out.println("\nInsufficient funds for withdrawal of $" + amount);
        }
        return false;
    }

    public boolean transfer(BankAccount recipient, double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            recipient.setBalance(recipient.getBalance() + amount);
            System.out.println("\nTransfer of $" + amount + " to account " + recipient.getAccountNumber() + " successful.");
            return true;
        } else if (amount <= 0) {
            System.out.println("\nInvalid transfer amount. Please enter a positive value.");
        } else {
            System.out.println("\nInsufficient funds for transfer of $" + amount);
        }
        return false;
    }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName() { return ownerName; }
    public String getPin() { return pin; }
    public Date getAccountCreationDate() { return accountCreationDate; }
}