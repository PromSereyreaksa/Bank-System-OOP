package database;
import bankaccount.BankAccount;
import java.sql.*;
import java.util.ArrayList;

public class BankAccountDAO {

    // Save a new bank account to the database
    public void saveBankAccount(BankAccount account) {
        String query = "INSERT INTO bankaccounts (account_number, first_name, last_name, account_type, status, balance) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, account.getAccountNumber()); // Account number should be inserted from the account object
            ps.setString(2, account.getFirstName()); // Insert first name
            ps.setString(3, account.getLastName());  // Insert last name
            ps.setString(4, account.getAccountType());
            ps.setString(5, account.getAccountStatus());
            ps.setDouble(6, account.getBalance());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve a bank account by account number
    public BankAccount getBankAccountById(int accountNumber) {
        String query = "SELECT * FROM bankaccounts WHERE account_number = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, accountNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // Rebuild the BankAccount object from the result set
                BankAccount account = new BankAccount(
                        rs.getString("first_name"),  // Retrieve first name
                        rs.getString("last_name"),   // Retrieve last name
                        rs.getString("account_type"),
                        rs.getString("status")
                );
                account.setBalance(rs.getDouble("balance")); // Set the balance from DB
                return account;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Retrieve all bank accounts
    public ArrayList<BankAccount> getAllBankAccounts() {
        ArrayList<BankAccount> accounts = new ArrayList<>();
        String query = "SELECT * FROM bankaccounts";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // Rebuild the BankAccount object from the result set
                BankAccount account = new BankAccount(
                        rs.getString("first_name"),  // Retrieve first name
                        rs.getString("last_name"),   // Retrieve last name
                        rs.getString("account_type"),
                        rs.getString("status")
                );
                account.setBalance(rs.getDouble("balance"));
                accounts.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }
}
