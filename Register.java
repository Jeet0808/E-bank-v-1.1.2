package ebank;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Register {
    long phono; // Phone number field
    int otp;    // OTP field

    // Create a connection object to interact with the database
    Conn conn;

    // Parameterized constructor
    public Register(long phono, int otp) {
        this.phono = phono;
        this.otp = otp;

        this.conn = new Conn();

        // Insert phone details into the database when an account is created
        insertPhonenoToDB();
    }

    public void getStatus() {
        System.out.println("Phone number: " + phono);
        System.out.println("OTP: " + otp);
    }


    // Method to insert phone number and OTP into the database
    public void insertPhonenoToDB() {
        try {
            // Ensure the connection is not null
            if (this.conn.c != null) {
                // Update the SQL query to use 'mobileno' instead of 'phono'
                String sql = "INSERT INTO registors (mobileno, otp) VALUES (?, ?)";

                PreparedStatement pstmt = this.conn.c.prepareStatement(sql);

                // Use setString for the phone number and setInt for the OTP
                pstmt.setString(1, String.valueOf(this.phono)); // assuming phono is a long
                pstmt.setInt(2, this.otp);

                pstmt.executeUpdate();
                System.out.println("Phone number and OTP inserted successfully!");
            } else {
                System.out.println("Connection is null!");
                conn.close(); // Make sure to handle connection closing properly
            }
        } catch (SQLException e) {
            // Check if the exception is due to the table not existing
            if (e.getMessage().contains("Table 'storemydata.registors' doesn't exist")) {
                System.out.println("Table 'registors' does not exist. Proceeding without inserting phone number and OTP...");
                // Optionally, you can log this or handle it as needed
            } else {
                // Handle other SQL exceptions
                System.out.println("SQL Exception: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

}
