import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class BankingSystemGUI extends JFrame {
    private JTextField accountField;
    private JTextField ownerNameField;
    private JPasswordField pinField;
    private JButton loginButton;
    private JButton registerButton;
    private ArrayList<BankAccount> accounts = new ArrayList<>();
    private ArrayList<String> pins = new ArrayList<>();

    public BankingSystemGUI() {
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        accountField = new JTextField(10);
        ownerNameField = new JTextField(15);
        pinField = new JPasswordField(4);

        inputPanel.add(new JLabel("Account Number:"));
        inputPanel.add(accountField);
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(new JLabel("Owner Name:"));
        inputPanel.add(ownerNameField);
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(new JLabel("PIN:"));
        inputPanel.add(pinField);

        JPanel buttonPanel = new JPanel();
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.PAGE_END);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String accountNumber = accountField.getText();
                String pin = new String(pinField.getPassword());

                BankAccount account = findAccountByNumber(accountNumber);
                if (account != null && pins.contains(pin)) {
                    performActions(accountNumber);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid credentials.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String accountNumber = accountField.getText();
                String ownerName = ownerNameField.getText();
                String pin = new String(pinField.getPassword());

                if (validateAccountNumber(accountNumber)) {
                    JOptionPane.showMessageDialog(null, "Account number already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    Date currentDate = new Date(9, 23, 2024);
                    BankAccount account = new BankAccount(accountNumber, ownerName, pin, currentDate);
                    accounts.add(account);
                    pins.add(pin);
                    JOptionPane.showMessageDialog(null, "Account registered successfully!\nYou received $250 as a welcome bonus!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    account.deposit(250);
                }
            }
        });
    }

    private boolean validateAccountNumber(String accountNumber) {
        for (BankAccount account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return true;
            }
        }
        return false;
    }

    private BankAccount findAccountByNumber(String accountNumber) {
        for (BankAccount account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    private void performActions(String accountNumber) {
        BankAccount account = findAccountByNumber(accountNumber);
        /*if (account == null) {
            JOptionPane.showMessageDialog(this, "Account not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }*/

        String ownerName = account.getOwnerName();
        double balance = account.getBalance();

        while (true) {
            String input = JOptionPane.showInputDialog(
                    this,
                    "\nHello " + ownerName + "! \n\nChoose an option:\n"
                            + "1.Check Balance\n"
                            + "2.Deposit\n"
                            + "3.Withdraw\n"
                            + "4.Transfer\n"
                            + "5.Delete Account\n"
                            + "6.View Account Info\n"
                            + "7.Logout",
                    "Account Menu");

            if (input == null || input.trim().isEmpty()) {
                return; // Exit the loop when user cancels
            }

            try {
                int choice = Integer.parseInt(input.trim());

                switch (choice) {
                    case 1:
                        balance = account.getBalance();
                        JOptionPane.showMessageDialog(this, "Your current balance: $" + balance);
                        break;
                    case 2:
                        deposit(accountNumber);
                        balance = account.getBalance();
                        break;
                    case 3:
                        withdraw(accountNumber);
                        balance = account.getBalance();
                        break;
                    case 4:
                        transfer(accountNumber);
                        break;
                    case 5:
                        deleteAccount(accountNumber);
                        break;
                    case 6:
                        viewAccountInfo(findAccountByNumber(accountNumber));
                        break;
                    case 7:
                        logout(accountNumber);
                        return;
                    default:
                        JOptionPane.showMessageDialog(this, "Invalid option. Please try again.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deposit(String accountNumber) {
        double amount = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter amount to deposit:", "Deposit"));
        if (amount > 0) {
            findAccountByNumber(accountNumber).deposit(amount);
            JOptionPane.showMessageDialog(this, "Deposit successful! New balance: $" + findAccountByNumber(accountNumber).getBalance());
        } else {
            JOptionPane.showMessageDialog(this, "Amount must be positive.");
        }
    }

    private void withdraw(String accountNumber) {
        double amount = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter amount to withdraw:", "Withdrawal"));
        if (amount > 0 && amount <= findAccountByNumber(accountNumber).getBalance()) {
            if (findAccountByNumber(accountNumber).withdraw(amount)) {
                JOptionPane.showMessageDialog(this, "Withdrawal successful! Remaining balance: $" + findAccountByNumber(accountNumber).getBalance());
            } else {
                JOptionPane.showMessageDialog(this, "Insufficient funds.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Amount must be positive and not exceed your balance.");
        }
    }

    private void transfer(String accountNumber) {
        String recipient = JOptionPane.showInputDialog(this, "Enter recipient account number:");
        double amount = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter transfer amount:", "Transfer"));
        if (amount > 0) {
            BankAccount recipientAccount = findAccountByNumber(recipient);
            if (recipientAccount != null) {
                if (findAccountByNumber(accountNumber).transfer(recipientAccount, amount)) {
                    JOptionPane.showMessageDialog(this, "Transfer successful!");
                } else {
                    JOptionPane.showMessageDialog(this, "Transfer failed due to insufficient funds.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Recipient account not found.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Amount must be positive.");
        }
    }

    private void deleteAccount(String accountNumber) {
        int index = accounts.indexOf(findAccountByNumber(accountNumber));
        if (index >= 0) {
            accounts.remove(index);
            pins.remove(index);

            // Clear account details
            accountField.setText("");
            ownerNameField.setText("");
            pinField.setText("");

            JOptionPane.showMessageDialog(this, "Account deleted successfully.");
            performActions(""); // Call performActions with an empty string to return to main menu
        } else {
            JOptionPane.showMessageDialog(this, "Account not found.");
        }
    }

    private void logout(String accountNumber) {
        BankAccount account = findAccountByNumber(accountNumber);
        if (account != null) {
            // Mark the account as logged out
            account.setLoggedIn(false);

            // Clear account details
            accountField.setText("");
            ownerNameField.setText("");
            pinField.setText("");

            // Reset GUI state
            loginButton.setEnabled(true);
            registerButton.setEnabled(true);
            loginButton.setText("Login");
            registerButton.setText("Register");

            // Show logout confirmation message
            JOptionPane.showMessageDialog(this, "You have been logged out successfully.", "Logout Confirmation", JOptionPane.INFORMATION_MESSAGE);

            // Perform actions after logout
            performActions("");
        } else {
            JOptionPane.showMessageDialog(this, "Account not found. Please check your account number and try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String formatDateTime(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formatter);
    }

    private void viewAccountInfo(BankAccount account) {
        String info = "Account Information:\n\n";
        info += "Account Number: " + account.getAccountNumber() + "\n";
        info += "Owner Name: " + account.getOwnerName() + "\n";

        // Get current date
        LocalDate currentDate = LocalDate.now();
        String formattedDate = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(currentDate);

        info += "Creation Date: " + formattedDate + "\n";
        info += "Current Balance: $" + account.getBalance();

        JOptionPane.showMessageDialog(this, info, "Account Details", JOptionPane.PLAIN_MESSAGE);
    }

    private String formatDate(Date date) {
        return String.format("%tF", date);
    }


    public static void main(String[] args) {
        JFrame frame = new BankingSystemGUI();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 200);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Banking System V3");
        frame.setVisible(true);
    }
}
