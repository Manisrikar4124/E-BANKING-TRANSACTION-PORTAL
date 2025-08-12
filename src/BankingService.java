import java.sql.*;
import java.util.Scanner;

public class BankingService {
    private Connection conn;
    private Scanner sc;

    public BankingService() {
        try {
            conn = DBConnection.getConnection();
            sc = new Scanner(System.in);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createAccount() {
        try {
            System.out.print("Enter name: ");
            String name = sc.nextLine();
            System.out.print("Enter initial deposit: ");
            double balance = sc.nextDouble();
            sc.nextLine(); // consume leftover newline

            String query = "INSERT INTO accounts (name, balance) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setDouble(2, balance);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int accountId = rs.getInt(1);
                System.out.println("\n Account Created Successfully!");
                System.out.println("Account ID: " + accountId);
                System.out.println("Name: " + name);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewBalance() {
        try {
            System.out.print("Enter account number: ");
            int accNo = sc.nextInt();
            sc.nextLine();

            String query = "SELECT name, balance FROM accounts WHERE account_number = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, accNo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Balance: " + rs.getDouble("balance"));
            } else {
                System.out.println("Account not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deposit() {
        try {
            System.out.print("Enter account number: ");
            int accNo = sc.nextInt();
            System.out.print("Enter amount to deposit: ");
            double amount = sc.nextDouble();
            sc.nextLine();

            String query = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setDouble(1, amount);
            ps.setInt(2, accNo);
            int rows = ps.executeUpdate();

            if (rows > 0) System.out.println("Deposit Successful!");
            else System.out.println("Account not found.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void withdraw() {
        try {
            System.out.print("Enter account number: ");
            int accNo = sc.nextInt();
            System.out.print("Enter amount to withdraw: ");
            double amount = sc.nextDouble();
            sc.nextLine();

            String checkQuery = "SELECT balance FROM accounts WHERE account_number = ?";
            PreparedStatement checkPs = conn.prepareStatement(checkQuery);
            checkPs.setInt(1, accNo);
            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {
                double balance = rs.getDouble("balance");
                if (balance >= amount) {
                    String query = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
                    PreparedStatement ps = conn.prepareStatement(query);
                    ps.setDouble(1, amount);
                    ps.setInt(2, accNo);
                    ps.executeUpdate();
                    System.out.println("Withdrawal Successful!");
                } else {
                    System.out.println("Insufficient balance.");
                }
            } else {
                System.out.println("⚠ Account not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
