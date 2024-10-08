package ebank;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Bankowner {
    String name;
    int age;
    long phoneno;
    private int balance;
    int specialno;

    Conn conn;  // Correct case for connection instance


    public Bankowner(String name, int age, long phoneno, int balance, int specialno) {
        this.name = name;
        this.age = age;
        this.phoneno = phoneno;
        this.balance = balance;
        this.specialno = specialno;
        this.conn = new Conn();  // Correct instantiation with lowercase 'conn'
        insertbankownerToDB();
    }

    public void printownerinfo() {
        System.out.println("Name of owner: " + name);
        System.out.println("Age of owner: " + age);
        System.out.println("Phone number of owner: " + phoneno);
        System.out.println("Balance of owner: " + balance);
    }

    // Method to insert account details into the database
    public void insertbankownerToDB() {
        try {
            // Ensure the connection is not null
            if (this.conn.c != null) {
                // Corrected SQL query with 5 placeholders to match 5 parameters
                String sql = "INSERT INTO bankowner (name, age, phoneno, balance, specialno) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement pstmt = this.conn.c.prepareStatement(sql);
                pstmt.setString(1, this.name);
                pstmt.setInt(2, this.age);  // Direct use of 'age' without converting
                pstmt.setLong(3, this.phoneno);  // Correct type for phone number (long)
                pstmt.setInt(4, this.balance);  // Direct use of 'balance' without converting
                pstmt.setInt(5, this.specialno);  // Direct use of 'specialno'
                pstmt.executeUpdate();
                System.out.println("Bank owner details inserted successfully!");
            } else {
                System.out.println("Connection is null!");
            }
        }catch (SQLException e) {
            // Check if the error is due to a duplicate entry
            if (e.getErrorCode() == 1062) {  // Error code for duplicate key
                System.out.println("Error: Duplicate entry. The email '" + this.age + "' is already registered.");
            } else {
                // Print any other SQL error
                System.out.println("SQL Exception: " + e.getMessage());
            }
            if (e.getMessage().contains("Table 'storemydata.bankowner' doesn't exist")) {
                System.out.println("Table 'profiles' does not exist. Proceeding without inserting phone number and OTP...");
                // Optionally, you can log this or handle it as needed
            } else {
                // Handle other SQL exceptions
                System.out.println("SQL Exception: " + e.getMessage());
                e.printStackTrace();
            }
            // Continue program execution without interruption
        }
    }
}
