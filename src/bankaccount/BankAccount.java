package bankaccount;

import Interfaces.Authentication;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class BankAccount implements Authentication {

    private static ArrayList<BankAccount> accounts = new ArrayList<>();
    private static Random random = new Random();
    private int accountNumber;
    private String accountName;
    private double balance;
    private String accountType;
    private String accountStatus;
    private String password; // Added for authentication
    private int pin;

    // Constructor with all fields
    public BankAccount(String accountName, double balance, String accountType, String accountStatus) {
        this.accountName = accountName;
        this.balance = 1;
        this.accountType = accountType;
        this.accountStatus = accountStatus;
    }
    // // to be used later
    // public void createSubAccount(String accountName, String accountType, String accountStatus) {
    //     this.accountName = accountName;
    //     this.balance = 1.0; // default
    //     this.accountType = accountType;
    //     this.accountStatus = "Active";
    // }

    public int getAccountNumber() {
        return accountNumber;
        // return the account number for the account if i need to get the account number for viewing transcation
        // or any other purpose (modifying account details)
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    protected void setPassword(String password) {
        this.password = password;
        // account password != pin
        // password when logging in
    }
    
    protected void setPin(int pin) {
        this.pin = pin;
        // pin when launching the app
        // pin when making transactions
    }

    public void setBalance(double balance){
        this.balance = balance;
        // set the balance for the account
        // this will be used when making transactions
        // (keeping public because it will be used in the transaction class)
    }
    
    public String getAccountName() {
        return accountName;
        // return the account name for the account if i need to get the account name for viewing transcation
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
        // set the account name for the account
        // this will be used when creating the account

    }

    public double getBalance() {
        return balance;
    }

    public String getAccountType() {
        return accountType;
        // return the account type for the account if i need to get the account type for viewing transcation
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
        // set the account type for the account
        // this will be used when creating the account
    }

    public static void createAccount(String accountName, double balance, String accountType, String accountStatus) {
        //to be implemented: 

        // creates account and then add it into the list
        // generate a unique account number
        // create a new account object
        // set the account number
        // add the account to the list of accounts

    }

    public BankAccount getAccountByNumber(int accountNumber){
        // loop through the list of accounts and return the account with the account number

        return null; // Return null if account is not found
    }

    // Generate a unique account number
    private static int generateAccountNumber() {
        int accountNumber;
        do { // Loop until a unique account number is generated
            accountNumber = 1000000000 + random.nextInt(900000); // Generate 9-digit number
        } while (accountExists(accountNumber)); // Check if the account number already exists
        return accountNumber;
    }
    
    public static ArrayList<BankAccount> getAccountsList() {
        return accounts; // Return the list of accounts
    }
    
    // Check if an account number already exists
    private static boolean accountExists(int accountNumber) {
        for (BankAccount account : accounts) {
            if (account.getAccountNumber() == accountNumber) {
                return true;
            }
        } // Return true if the account number already exists
        return false;
    }

    // Override toString for better readability
    @Override
    public String toString() {
        return "BankAccount{" +
                "accountNumber=" + accountNumber +
                ", accountName='" + accountName + '\'' +
                ", balance=" + balance +
                ", accountType='" + accountType + '\'' +
                ", accountStatus='" + accountStatus + '\'' +
                '}';

                // override the toString method to return the account details
                // account number, account name, balance, account type, account status
    }


    @Override
    public boolean login() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter account number");
        int accountNumber = input.nextInt();
        System.out.println("Enter password: ");
        String password = input.nextLine();
    
        // for testing purposes
        if(password.equals("12345") && accountNumber == 123456789){
            input.close();
            return true;
        }
        input.close();
        return false;
    }

    @Override
    public void register() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter account name: ");
        String accountName = input.nextLine();
        System.out.println("Enter initial balance: ");
        double balance = input.nextDouble();
        System.out.println("Enter account type: ");
        String accountType = input.next();
        createAccount(accountName, balance, accountType, "Active");
        input.close();
    }
    
    @Override
    public void forgotPassword() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter account number: ");
        int accountNumber = input.nextInt();
        BankAccount account = getAccountByNumber(accountNumber);
        if (account != null) {
            System.out.println("Account found. Enter new password: ");
            String newPassword = input.nextLine();
            account.setPassword(newPassword);
        } else {
            System.out.println("Account not found.");
        }

        input.close();
    }

    private void changePIN() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter old PIN: ");
        int oldPin = input.nextInt();
        //for testing purpose
        if (oldPin == 123456) {
            System.out.println("Enter new PIN: ");
            int newPin = input.nextInt();
            setPin(newPin);
        }
        // setPassword(newPassword);
        input.close();
    }
}


